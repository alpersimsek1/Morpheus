package com.proente.Util

import com.proente.Models.MachineData

import org.apache.spark.sql.{Column, DataFrame, SparkSession}
import org.apache.spark.sql.functions._
import spray.json._

object Helper {

  def getFieldAsString(jsObject: JsObject, field: String): String = {
    jsObject.getFields(field).head.toString
  }

  def parseMachineDataJson(data: String): MachineData = {
    import com.proente.Models.MachineDataJsonProtocol._
    val parsed = data.parseJson.asJsObject().getFields("action")
    parsed.head.asJsObject().convertTo[MachineData]
  }

  def readDfFromPostgres(spark: SparkSession) : String => (String, DataFrame) = (tableName: String) => {
    (
      tableName,
      spark
        .read
        .format("jdbc")
        .option("url", "jdbc:postgresql://localhost/prowmes")
        .option("dbtable", tableName).option("user", "prowmes")
        .option("password", "12345")
        .load()
    )
  }

  def readDfFromPostgresWithTableNames(tableNames: Seq[String], spark: SparkSession) : Seq[(String, DataFrame)] = {
    tableNames.map(readDfFromPostgres(spark))
  }

  /**
   * below function is implemented in order to read a json file which has a
   * depth of 1, i.e., doesn't have any nested objects. Each key represents
   * a field and each value represents a type
   */
  def readJsonAsTuple(spark: SparkSession): String => Seq[Seq[String]] = (jsonPath: String) => {
    val configDF = spark.read.format("json")
      .option("multiline", "true")
      .load(jsonPath)
    configDF.columns.map(field => {
      val `type` = configDF.select(field).collect()(0)(0).toString
      Seq(field, `type`)
    }).toSeq
  }

  def readDfFromCsv(spark: SparkSession): String => (String, DataFrame) = (path: String) => {
    (
      path,
      spark.read.format("csv").option("header", "true").load(path)
    )
  }

  def readDfFromCsvWithTableNames(filePaths: Seq[String], spark: SparkSession): Seq[(String, DataFrame)] = {
    filePaths.map(readDfFromCsv(spark))
  }

  def writeDFToCSV(key: String, df: DataFrame, dataDir: String): Unit = {
    df.write
      .option("header", "true")
      .csv(dataDir + key)
  }

  val ISO_DATE_PATTERN = "yyyy-MM-dd'T'HH:mm:ss.SSSXXX"
  val ISO_DATE_PATTERN_V1 = "''yyyy-MM-dd HH:mm:ss''"

  val stringDateToBigInt: String => Column = s =>
    unix_timestamp(col(s), ISO_DATE_PATTERN) * 1000 + substring(col(s), -9, 3).cast("int")
  val stringToInt: String => Column = s => col(s).cast("int")
  val stringToDouble: String => Column = s => col(s).cast("double")
  val stringToLong: String => Column = s => col(s).cast("long")
  val stringToBool: String => Column = s => col(s).cast("boolean")
  val stringToTimestamp: String => Column = s => unix_timestamp(col(s), ISO_DATE_PATTERN).cast("timestamp")
  val stringToBigInt: String => Column = s => col(s).cast("bigint")
  val stringShortDateToBigInt: String => Column = s => unix_timestamp(col(s), ISO_DATE_PATTERN_V1) * 1000

  def reformatDF( castArray: Seq[Seq[String]], df: DataFrame ): DataFrame = {
    castArray.foldLeft(df)( ( accDf, cast) => {
      val field = cast.head
      val `type` = cast(1)
      val f = `type` match {
        case "int" => stringToInt
        case "double" => stringToDouble
        case "long" => stringToLong
        case "boolean" => stringToBool
        case "date" => stringDateToBigInt
        case "bigint" => stringToBigInt
        case "timestamp" => stringToTimestamp
        case "shortDate" => stringShortDateToBigInt
      }
      accDf.withColumn(field, f(field))
    })
  }

  def writeToCassandra(): Unit = {

  }
}
