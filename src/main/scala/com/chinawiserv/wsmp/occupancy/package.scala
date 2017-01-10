package com.chinawiserv.wsmp

/**
  * Created by zengpzh on 2017/1/8.
  */
package object occupancy {

  private[occupancy] val TIME_FORMAT = "yyyyMMdd";

  private[occupancy] val TIME_YEAR_LENGTH = 4;

  private[occupancy] val TIME_DAY_LENGTH = 4;

  private[occupancy] val OCCUPANCY_MEM = com.chinawiserv.wsmp.occupancy.store.mem.load;

}
