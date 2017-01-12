package com.chinawiserv.wsmp.occupancy.model

import scala.collection.mutable.ArrayBuffer

/**
  * Created by zengpzh on 2017/1/11.
  */
private[occupancy] case class OccupancyData(id: Int, time: String, levels: ArrayBuffer[Short])
