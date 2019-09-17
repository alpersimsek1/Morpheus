package com.proente.Models

case class OffGrade(
                      id: Integer,
                      shot_id: Integer,
                      goods_id: Integer,
                      product_id: Integer,
                      machine_id: Integer,
                      operator_id: Integer,
                      timestamp: BigInt,
                      amount: BigInt,
                      created_at: BigInt,
                      updated_at: BigInt,
                      `type`: Integer
                    )
