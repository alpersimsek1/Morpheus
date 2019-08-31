package com.proente.Streaming

import com.proente.SparkSessionBuilder
import com.proente.Util.CassandraSink
import org.apache.spark.sql.{DataFrame, Dataset, SaveMode, SparkSession}
import com.datastax.spark.connector.streaming._

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

    val ds = df.selectExpr("CAST(value AS STRING)")
      .as[String]

    val query = ds.writeStream
      .outputMode("update")
      .foreachBatch {
        (batchDF: Dataset[String], batchId: Long) =>
          batchDF
            .write
            .format("org.apache.spark.sql.cassandra")
            .options(Map("keyspace" -> "ford", "table" -> "machine_data"))
            .mode(SaveMode.Append)
            .save()
      }
      .start()

    query.awaitTermination()
  }

  override def appName: String = "KafkaStreamer"
}
