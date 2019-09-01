package com.proente.Streaming

import com.proente.SparkSessionBuilder
import org.apache.spark.sql.{Dataset, SaveMode, SparkSession}
import com.proente.Util.Helper._

object KafkaStreamer extends SparkSessionBuilder {
  override def run(spark: SparkSession): Unit = {
    import spark.implicits._

    val df = spark
      .readStream
      .format("kafka")
      .option("kafka.bootstrap.servers", "kafka:9092")
      .option("subscribe", "test")
      .option("startingOffsets", "earliest")
      .load()

    val ds = df.selectExpr("CAST(value AS STRING)")
      .as[String]
    //      .map(parseMachineDataJson)

    val query = ds.writeStream
      .outputMode("update")
      .foreachBatch {
        (batchDF: Dataset[String], batchId: Long) =>
          batchDF
            .map(parseMachineDataJson)
            .write
            .format("org.apache.spark.sql.cassandra")
            .options(Map("keyspace" -> "ford", "table" -> "machine"))
            .partitionBy("machine_id")
            .mode(SaveMode.Append)
            .save()
      }
      .start()

    query.awaitTermination()
  }

  override def appName: String = "KafkaStreamer"
}
