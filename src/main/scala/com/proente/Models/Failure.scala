package com.proente.Models

case class Failure(
                     id: Integer,
                     `type`: Integer,
                     machine_id: Integer,
                     operator_id: Integer,
                     description: String,
                     start: BigInt,
                     end: BigInt,
                     duration: BigInt,
                     created_at: BigInt,
                     updated_at: BigInt
                   )
