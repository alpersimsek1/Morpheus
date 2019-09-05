package com.proente.Models

case class Defects(
                    id: String,
                    shot_id: String,
                    goods_id: String,
                    product_id: String,
                    machine_id: String,
                    operator_id: String,
                    timestamp: String,
                    `type`: String,
                    amount: String,
                    rework_started: String,
                    created_at: String,
                    updated_at: String
                  )
