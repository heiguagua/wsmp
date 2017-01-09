package com.chinawiserv.wsmp.occupancy

import scala.collection.mutable.ArrayBuffer

/**
  * Created by zengpzh on 2017/1/6.
  */
case class OccupancyData(

  //char[40]	Id	传感器编号（Int类型字符串）
  id: String,

  //int64	scanOverTime	本地的格林威治时间的秒数
  scanOverTime: Long,

  //sbyte[numOfTraceItems]	level	能量数据，8位有符号整数，取值[-128, 127]
  levels: ArrayBuffer[Byte]

)
