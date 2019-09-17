package com.proente.Models

case class Shot(
                 id: Integer,
                 timestamp: BigInt,
                 machine_id: Integer,
                 operator_id: Integer,
                 goods_id: Integer,
                 product_id: Integer,
                 cycle: BigInt,
                 amount: BigInt,
                 duration: BigInt,
                 quality_control_id: Integer,
                 should_ignore: Boolean,
                 created_at: BigInt,
                 updated_at: BigInt
               )

