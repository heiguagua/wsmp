package com.chinawiserv.wsmp.occupancy.flush

import com.chinawiserv.wsmp.occupancy.OccupancyData
import com.chinawiserv.wsmp.occupancy.flush.disk.FlushDisk
import com.chinawiserv.wsmp.occupancy.flush.mem.FlushMem

/**
  * Created by zengpzh on 2017/1/7.
  */
class FlushTask(occupancyDatas: List[OccupancyData]) extends Runnable{

  override def run(): Unit = {
    synchronized({
      FlushMem.flush(occupancyDatas);
      FlushDisk.flush();
    });
  }

}
