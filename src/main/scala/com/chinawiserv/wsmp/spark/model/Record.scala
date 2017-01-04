package com.chinawiserv.wsmp.spark.model

case class Record(id: String,
                  scanOverTime: Long,
                  freq: Float,
                  level: Byte
                 );