package com.proente

import org.apache.spark.sql.SparkSession

trait SparkSessionBuilder extends Serializable {

  def main(args: Array[String]): Unit = {

    val host = "cassandra"
    val username = "cassandra"
    val password = "cassandra"

    val spark: SparkSession = SparkSession
      .builder()
      .config("spark.cassandra.connection.host", host)
      .config("spark.cassandra.auth.username", username)
      .config("spark.cassandra.auth.password", password)
      //      .master("local[*]")
      .appName(appName)
      .getOrCreate()

    run(spark)
  }

  def run(spark: SparkSession)

  def appName: String
}
