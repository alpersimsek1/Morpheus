package com.proente.Models

case class Defect(
                    id: Int,
                    shot_id: Int,
                    goods_id: Int,
                    product_id: Int,
                    machine_id: Int,
                    operator_id: Int,
                    timestamp: BigInt,
                    `type`: Int,
                    amount: BigInt,
                    rework_started: Boolean,
                    created_at: BigInt,
                    updated_at: BigInt
                  )
