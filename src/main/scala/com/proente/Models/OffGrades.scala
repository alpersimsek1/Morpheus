package com.proente.Models

case class OffGrades(
                      id: String,
                      timestamp: String,
                      machine_id: String,
                      operator_id: String,
                      goods_id: String,
                      product_id: String,
                      cycle: String,
                      amount: String,
                      duration: String,
                      quality_control_id: String,
                      should_ignore: String,
                      created_at: String,
                      updated_at: String
                    )
