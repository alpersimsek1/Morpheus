package com.proente.ML

import com.proente.Models._
import com.proente.SparkSessionBuilder
import org.apache.spark.sql.SparkSession

object ModelTrain extends SparkSessionBuilder {

  val appName = "Model Training"

  override def run(spark: SparkSession): Unit = {
    import spark.implicits._

    val shots = spark.read.option("header", true).option("delimiter", "|").csv("Downloads/data/shotHypes/*.csv").as[Shot]
    val offGrades = spark.read.option("header", true).option("delimiter", "|").csv("Downloads/data/offGrades/*.csv").as[OffGrades]
    val machineActions = spark.read.option("header", true).option("delimiter", "|").csv("Downloads/data/machineActions/*.csv").as[MachineData]
    val goods = spark.read.option("header", true).option("delimiter", "|").csv("Downloads/data/goods/*.csv").as[Goods]
    val failures = spark.read.option("header", true).option("delimiter", "|").csv("Downloads/data/failures/*.csv").as[Failures]
    val defects = spark.read.option("header", true).option("delimiter", "|").csv("Downloads/data/defects/*.csv").as[Defects]

    val joinDefectsShotsDS = defects.join(shots, Seq("machine_id", "goods_id"), "left")
    joinDefectsShotsDS.show

    defects.join(shots, Seq("machine_id", "goods_id"), "left").map(_.getString(8)).distinct.show
  }
}
