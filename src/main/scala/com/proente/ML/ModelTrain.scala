package com.proente.ML

import org.apache.spark.sql.SparkSession

object ModelTrain {

  val appName = "Model Training"

  val spark: SparkSession = SparkSession
    .builder()
    .master("local[*]")
    .appName(appName)
    .getOrCreate()

}
