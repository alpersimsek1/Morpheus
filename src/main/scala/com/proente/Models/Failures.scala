package com.proente.Models

case class Failures(
                     id: String,
                     `type`: String,
                     machine_id: String,
                     operator_id: String,
                     description: String,
                     start: String,
                     end: String,
                     duration: String,
                     created_at: String,
                     updated_at: String
                   )
