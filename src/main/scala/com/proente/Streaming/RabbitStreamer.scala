package com.proente.Streaming

import com.proente.SparkSessionBuilder
import org.apache.spark.sql.SparkSession

object RabbitStreamer extends SparkSessionBuilder {

  override def run(spark: SparkSession): Unit = {

  }

  override def appName: String = "Streaming"
}
