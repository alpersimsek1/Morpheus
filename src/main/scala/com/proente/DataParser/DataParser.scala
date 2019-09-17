package com.proente.DataParser

import com.proente.SparkSessionBuilder
import com.proente.Util.Helper.{readJsonAsTuple, readDfFromCsv, reformatDF}
import org.apache.spark.sql.{SparkSession, DataFrame}

object DataParser extends SparkSessionBuilder {
  val dataBaseDir = "/home/safa/Proente/Files/ScriptExports"
  val tables = Seq("shots")

  override def run(spark: SparkSession): Unit = {
    tables.foreach( table => {
      val dataDir = dataBaseDir + "/" + table + "/"
      val configPath = dataDir + "config.json"
      val dataPath = dataDir + table + ".csv"

      // get configFile content
      val fieldTypeDuoSeq = readJsonAsTuple(spark)(configPath)

      // get data with dataPath
      val tableDFTuple = readDfFromCsv(spark)(dataPath)

      val result = reformatDF(fieldTypeDuoSeq, tableDFTuple._2)
      result.show(false)
    } )
  }

  def runSparkShell(spark: SparkSession): Seq[DataFrame] = {
    var data = Seq[DataFrame]()
    tables.foreach( table => {
      val dataDir = dataBaseDir + "/" + table + "/"
      val configPath = dataDir + "config.json"
      val dataPath = dataDir + table + ".csv"

      // get configFile content
      val fieldTypeDuoSeq = readJsonAsTuple(spark)(configPath)

      // get data with dataPath
      val tableDFTuple = readDfFromCsv(spark)(dataPath)

      val result = reformatDF(fieldTypeDuoSeq, tableDFTuple._2)
      data = data :+ result
    } )
    data
  }

  override def appName : String = "DataParser"
}
