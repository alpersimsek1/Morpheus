package com.proente.Streaming

import com.proente.SparkSessionBuilder
import org.apache.spark.SparkConf
import org.apache.spark.sql.SparkSession
import org.apache.spark.streaming.{Seconds, StreamingContext}
import org.apache.spark.streaming.rabbitmq.RabbitMQUtils

object RabbitStreamer {

  def main(args: Array[String]): Unit = {

    val conf = new SparkConf()
      .setMaster("local[2]")
      .setAppName("rabbit")
    val ssc = new StreamingContext(conf, Seconds(5))
    val params = Map(
      "hosts" -> "amqp://dev.prowmes.com",
      "queueName" -> "processed_machine_action_notif"
    )
    val receiverStream = RabbitMQUtils.createStream[String](ssc, params)
    receiverStream.start()

    receiverStream.print()
//    receiverStream.foreachRDD {
//      rdd =>
//        if (!rdd.isEmpty()) {
//          val count = rdd.count()
//          // Do something with this message
//          println(s"EVENTS COUNT : \t $count")
//          //rdd.collect().sortBy(event => event.toInt).foreach(event => print(s"$event, "))
//        } else println("RDD is empty")
//        println(s"TOTAL EVENTS : none")
//    }

    ssc.start()
    ssc.awaitTermination()

  }

}
