package com.proente.Util

import com.datastax.spark.connector.cql.CassandraConnector
import com.datastax.driver.core.querybuilder.QueryBuilder
import com.datastax.driver.core.Statement
import org.apache.spark.SparkConf
import org.apache.spark.sql.Row
import org.apache.spark.sql.ForeachWriter

class CassandraSink(sparkConf: SparkConf) extends ForeachWriter[Row] {
  def open(partitionId: Long, version: Long): Boolean = true

  def process(row: Row) = {
    def buildStatement: Statement =
      QueryBuilder.insertInto("fordotosan", "machine_data")
        .value("value", row.getAs[String]("value"))
    CassandraConnector(sparkConf).withSessionDo { session =>
      session.execute(buildStatement)
    }
  }

  def close(errorOrNull: Throwable) : Unit = {
    println(s"Close connection")
  }
}
