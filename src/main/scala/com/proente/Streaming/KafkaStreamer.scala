package com.proente.Streaming

import com.proente.SparkSessionBuilder
import org.apache.spark.sql.SparkSession

object KafkaStreamer extends SparkSessionBuilder {
  override def run(spark: SparkSession): Unit = {
    import spark.implicits._

    val df = spark
      .readStream
      .format("kafka")
      .option("kafka.bootstrap.servers", "kafka:9092")
      .option("subscribe", "test")
      .option("startingOffsets", "latest")
      .load()

    val ds = df.selectExpr( "CAST(value AS STRING)")
      .as[String]

    val query = ds.writeStream
      .format("console")
      .start()

    query.awaitTermination()
  }

  override def appName: String = "KafkaStreamer"
}
