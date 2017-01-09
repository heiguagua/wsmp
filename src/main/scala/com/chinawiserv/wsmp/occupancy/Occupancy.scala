package com.chinawiserv.wsmp.occupancy

import com.chinawiserv.wsmp.occupancy.flush.mem.FlushMemTask
import com.chinawiserv.wsmp.thread.{CustomThreadFactory, ThreadPool}

import scala.collection.JavaConversions
import scala.collection.mutable.{ArrayBuffer, ListBuffer}
import scala.util.Random

/**
  * Created by zengpzh on 2017/1/6.
  */
class Occupancy {

  OCCUPANCY_MEM;//实例化该类时就加载磁盘数据到内存，避免延迟加载对第一次调用的影响

  def compute(occupancyDatas: List[OccupancyData]): Unit = {
    if (occupancyDatas != null && !occupancyDatas.isEmpty) {
      Occupancy.flushMemExecutorService.execute(new FlushMemTask(occupancyDatas));
    }
  }

  @throws[Exception]
  def compute(occupancyDatas: java.util.List[OccupancyData]): Unit ={
    if(occupancyDatas != null){
      this.compute(JavaConversions.asScalaBuffer(occupancyDatas).toList);
    }
  }

}

private[occupancy] object Occupancy {

  private val FLUSH_CONCURRENT_NUM: Int = 3;

  private val flushMemExecutorService = ThreadPool.newThreadPool(FLUSH_CONCURRENT_NUM, new CustomThreadFactory("Occupancy-Flush-Mem-Executor-"));

  def main(args: Array[String]): Unit = {
    while (true) {

      val o =  new Occupancy();
      val stationNum = 300;
      val levelNum = 140000;
      val time = System.currentTimeMillis();
      val occupancyDatas = ListBuffer[OccupancyData]();
      for (station <- 1 to stationNum) {
        val levels = ArrayBuffer[Byte]();
        for (index <- 0 until levelNum) {
          levels += (Random.nextInt(127)).toByte;
        }
        occupancyDatas += OccupancyData(station, time, levels);
      }

      o.compute(occupancyDatas.toList);

      Thread.sleep(1000);

    }

  }

}
