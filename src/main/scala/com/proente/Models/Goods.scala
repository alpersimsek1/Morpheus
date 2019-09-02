package com.proente.Models

case class Goods(
                  id: String,
                  product_id: String,
                  work_order_id: String,
                  sorting_no: String,
                  `type`: String,
                  code: String,
                  barcode: String,
                  amount: String,
                  start: String,
                  end: String,
                  current_produced: String,
                  created_at: String,
                  updated_at: String

                )