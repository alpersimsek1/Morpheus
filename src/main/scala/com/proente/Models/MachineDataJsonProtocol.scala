package com.proente.Models

import spray.json.DefaultJsonProtocol

object MachineDataJsonProtocol extends DefaultJsonProtocol {
  implicit val machineDataFormat = jsonFormat5(MachineData)
}