package com.proente.ML

import com.proente.SparkSessionBuilder
import org.apache.spark.sql.SparkSession

object ModelTrain extends SparkSessionBuilder{

  val appName = "Model Training"

  override def run(spark: SparkSession): Unit = {
    val lines = spark.readStream
      .format("rabbitmq")
      .option("host", "dev.prowmes.com")
      .option("queue","processed_machine_action_notif")
      .load()

    val query = lines.writeStream
      .outputMode("complete")
      .format("console")
      .start()

    query.awaitTermination()
  }
}
