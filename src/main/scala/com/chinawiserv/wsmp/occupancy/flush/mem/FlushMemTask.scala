package com.chinawiserv.wsmp.occupancy.flush.mem

import com.chinawiserv.wsmp.occupancy.OccupancyData

/**
  * Created by zengpzh on 2017/1/7.
  */
private[occupancy] class FlushMemTask(occupancyDatas: List[OccupancyData]) extends Runnable{

  override def run(): Unit = {
    synchronized({
      FlushMem.flush(occupancyDatas);
    });
  }

}
