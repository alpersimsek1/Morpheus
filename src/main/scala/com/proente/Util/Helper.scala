package com.proente.Util

import com.proente.Models.MachineData
import spray.json._

object Helper {

  def getFieldAsString(jsObject: JsObject, field: String): String = {
    jsObject.getFields(field).head.toString
  }

  def parseMachineDataJson(data: String): MachineData = {
    val parsed = data.parseJson.asJsObject().getFields("action")
    val fields = parsed.head.asJsObject()
    val machineId = getFieldAsString(fields, "machine_id")
    val typeOf = getFieldAsString(fields, "type")
    val isFinish = getFieldAsString(fields, "is_finish")
    val processStatus = getFieldAsString(fields, "process_status")
    val timestamp = getFieldAsString(fields, "timestamp")

    MachineData(machineId, typeOf, isFinish, processStatus, timestamp)
  }

  def writeToCassandra(): Unit = {

  }
}
