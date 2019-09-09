package com.proente.DataParser

import com.proente.SparkSessionBuilder
import com.proente.Util.Helper.{readJsonAsTuple, readDfFromCsv, reformatDF}
import org.apache.spark.sql.SparkSession

object DataParser extends SparkSessionBuilder {
  val dataBaseDir = "/home/safa/Proente/Files/"
  override def run(spark: SparkSession): Unit = {
    val tables = Seq("defects", "failures", "off_grades", "goods", "machine_actions")

    tables.foreach( table => {
      val dataDir = dataBaseDir + "/" + table + "/"
      val configPath = dataDir + "config.json"
      val dataPath = dataDir + table + ".csv"

      // get configFileContent
      val fieldTypeDuoSeq = readJsonAsTuple(spark)(configPath)

      // get dataWithDataPath
      val tableDFDuo = readDfFromCsv(spark)(dataPath)

      val result = reformatDF(fieldTypeDuoSeq, tableDFDuo._2)
      result.show(false)
    } )
  }

  override def appName : String = "DataParser"
}
