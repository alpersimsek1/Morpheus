package com.proente.Util

import com.proente.Models.MachineData
import spray.json._

object Helper {

  def getFieldAsString(jsObject: JsObject, field: String): String = {
    jsObject.getFields(field).head.toString
  }

  def parseMachineDataJson(data: String): MachineData = {
    import com.proente.Models.MachineDataJsonProtocol._
    val parsed = data.parseJson.asJsObject().getFields("action")
    parsed.head.asJsObject().convertTo[MachineData]
  }

  def writeToCassandra(): Unit = {

  }
}
