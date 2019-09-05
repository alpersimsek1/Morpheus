package com.proente.DataFetch

import com.proente.SparkSessionBuilder
import org.apache.spark.sql.{DataFrame, SparkSession}
import scala.collection.immutable.Map

object DataFetcher extends SparkSessionBuilder {
  // TODO: take this parameter from user, do not specify dir here
  val dataDir = "/home/safa/Proente/Files/"
  override def run(spark: SparkSession): Unit = {
    def getDF( map: Map[String, DataFrame], str: String ) : Map[String, DataFrame] = {
      val df = spark
        .read
        .format("jdbc")
        .option("url", "jdbc:postgresql://localhost/prowmes")
        .option("dbtable", str).option("user", "prowmes")
        .option("password", "12345")
        .load()
      map + (str -> df )
    }

    def writeDFToCSV( key: String, df : DataFrame ) : Unit = {
      df.write
        .option("header", "true")
        .csv(dataDir + key)
    }

    val tables: Seq[String] = Seq("shot_hypers", "failures", "defects", "off_grades", "machine_actions", "goods")
    val tableDFMap = tables.foldLeft(Map[String, DataFrame]())(getDF)

    tableDFMap foreach {case (key, value) => writeDFToCSV(key, value)}
    println("JOBS COMPLETED!\n\n\n")
  }

  override def appName : String = "DataFetcher"
}
