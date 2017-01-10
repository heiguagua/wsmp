package com.chinawiserv.wsmp.occupancy

import com.chinawiserv.wsmp.handler.DataHandler
import com.chinawiserv.wsmp.model.Cmd
import com.chinawiserv.wsmp.occupancy.store.mem.FlushMemTask
import com.chinawiserv.wsmp.thread.{CustomThreadFactory, ThreadPool}
import com.chinawiserv.wsmp.util.DateTime
import org.apache.commons.lang.StringUtils
import org.bson.Document

import scala.collection.JavaConversions
import scala.collection.mutable.{ArrayBuffer, ListBuffer}
import scala.util.Random

/**
  * Created by zengpzh on 2017/1/6.
  */
class Occupancy extends DataHandler{

  OCCUPANCY_MEM;//实例化该类时就加载磁盘数据到内存，避免延迟加载对第一次调用的影响

  @throws[Exception]
  def compute(cmds: java.util.List[Cmd]): Unit ={
    if (cmds != null && !cmds.isEmpty) {
      Occupancy.flushMemExecutorService.execute(new FlushMemTask(JavaConversions.asScalaBuffer(cmds).toList));
    }
  }

}

private[occupancy] object Occupancy {

  private val FLUSH_CONCURRENT_NUM: Int = 3;

  private val flushMemExecutorService = ThreadPool.newThreadPool(FLUSH_CONCURRENT_NUM, new CustomThreadFactory("Occupancy-Flush-Mem-Executor-"));

  def main(args: Array[String]): Unit = {

    val o =  new Occupancy();

    while (true) {

     /*val stationNum = 300;
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

      o.compute(occupancyDatas.toList);*/

      println(DateTime.getCurrentDate_HHMMSS);
      println(getOccupancy("20170110", 125));
      println(DateTime.getCurrentDate_HHMMSS);

      Thread.sleep(1000);

    }

  }

  def getOccupancy(time: String, thresholdVal: Byte): List[Document] = {
    if(StringUtils.isNotBlank(time) && time.length == 8){
      if(DateTime.getCurrentDate_YYYYMMDDWithOutSeparator == time){
        store.mem.getOccupancyRate(time, thresholdVal)
      }else{
        println(store.mem.getOccupancyRate(time, thresholdVal));
        store.disk.getOccupancyRate(time, thresholdVal);
      }
    }else{
      List();
    }
  }

}
