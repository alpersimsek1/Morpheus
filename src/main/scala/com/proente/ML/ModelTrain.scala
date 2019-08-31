package com.proente.ML

import com.proente.SparkSessionBuilder
import org.apache.spark.sql.SparkSession

object ModelTrain extends SparkSessionBuilder {

  val appName = "Model Training"

  override def run(spark: SparkSession): Unit = {

  }
}
