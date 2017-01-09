package com.chinawiserv.wsmp

import scala.collection.mutable.{ArrayBuffer, Map}

/**
  * Created by Administrator on 2017/1/8.
  */
package object occupancy {

  private[occupancy] val OCCUPANCY_MEM = Map[String, Map[String, ArrayBuffer[Byte]]]();

  private[occupancy] val TIME_FORMAT = "yyyyMMdd";

}
