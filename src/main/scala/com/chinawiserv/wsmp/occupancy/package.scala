package com.chinawiserv.wsmp

import com.chinawiserv.wsmp.occupancy.flush.disk.LoadDisk

/**
  * Created by zengpzh on 2017/1/8.
  */
package object occupancy {

  private[occupancy] val TIME_FORMAT = "yyyyMMdd";

  private[occupancy] val OCCUPANCY_MEM = LoadDisk.load;

}
