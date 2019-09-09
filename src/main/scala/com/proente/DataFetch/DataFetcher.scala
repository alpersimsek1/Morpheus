package com.proente.DataFetch

import com.proente.SparkSessionBuilder
import com.proente.Util.Helper.{readDfFromPostgresWithTableNames, writeDFToCSV}
import org.apache.spark.sql.SparkSession

object DataFetcher extends SparkSessionBuilder {
  // TODO: take this parameter from user, do not specify dir here
  val dataDir = "/home/safa/Proente/Files/"

  override def run(spark: SparkSession): Unit = {
    // define tableNames
    val tables: Seq[String] = Seq("shot_hypers", "failures", "defects", "off_grades", "machine_actions", "goods")

    // read data from db with tableNames and store them in RAM
    val tableDFDuo = readDfFromPostgresWithTableNames(tables, spark)

    // write data to csv files
    tableDFDuo.foreach { case (key, value) => writeDFToCSV(key, value, dataDir) }
  }

  override def appName: String = "DataFetcher"
}
