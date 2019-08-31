package com.proente

import org.apache.spark.sql.SparkSession

trait SparkSessionBuilder extends Serializable {

  def main(args: Array[String]): Unit = {

    val spark: SparkSession = SparkSession
      .builder()
//      .master("local[*]")
      .appName(appName)
      .getOrCreate()

    run(spark)
  }

  def run(spark: SparkSession)

  def appName: String
}
