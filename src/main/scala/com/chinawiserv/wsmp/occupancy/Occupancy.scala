package com.chinawiserv.wsmp.occupancy

import com.chinawiserv.wsmp.occupancy.flush.FlushTask
import com.chinawiserv.wsmp.thread.{CustomThreadFactory, ThreadPool}

import scala.collection.mutable._
import scala.util.Random

/**
  * Created by zengpzh on 2017/1/6.
  */
class Occupancy {

  def compute(occupancyDatas: List[OccupancyData]): Unit = {
    if (occupancyDatas != null) {
      Occupancy.flushExecutorService.execute(new FlushTask(occupancyDatas));
    }
  }

}

object Occupancy {

  private val FLUSH_CONCURRENT_NUM: Int = 3;

  private val flushExecutorService = ThreadPool.newThreadPool(FLUSH_CONCURRENT_NUM, new CustomThreadFactory("Occupancy-Flush-Executor-"));

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
        occupancyDatas += OccupancyData(station.toString, time, levels);
      }

      o.compute(occupancyDatas.toList);

      Thread.sleep(1000);
    }
  }

}
