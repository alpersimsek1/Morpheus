package com.proente.Models

case class Good(
                  id: Integer,
                  product_id: Integer,
                  work_order_id: Integer,
                  sorting_no: Integer,
                  `type`: Integer,
                  code: String,
                  barcode: String,
                  amount: BigInt,
                  start: BigInt,
                  end: BigInt,
                  current_produced: Integer,
                  created_at: BigInt,
                  updated_at: BigInt
                )