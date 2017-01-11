package com.chinawiserv.wsmp.occupancy

import com.chinawiserv.wsmp.handler.DataHandler
import com.chinawiserv.wsmp.model.Cmd
import com.chinawiserv.wsmp.occupancy.store.mem.FlushMemTask
import com.chinawiserv.wsmp.thread.{CustomThreadFactory, ThreadPool}
import com.chinawiserv.wsmp.util.DateTime
import org.apache.commons.lang.StringUtils
import org.bson.Document
import org.springframework.stereotype.Component

import scala.collection.JavaConversions._
import scala.collection.mutable.ArrayBuffer
import scala.util.Random

/**
  * Created by zengpzh on 2017/1/6.
  */
//@Component
class Occupancy extends DataHandler{

  OCCUPANCY_MEM;//实例化该类时就加载磁盘数据到内存，避免延迟加载对第一次调用的影响

  @throws[Exception]
  def compute(cmds: java.util.List[Cmd]): Unit ={
    if (cmds != null && !cmds.isEmpty) {
      Occupancy.flushMemExecutorService.execute(new FlushMemTask(cmds.toList));
    }
  }

}

private[occupancy] object Occupancy {

  private val FLUSH_CONCURRENT_NUM: Int = 3;

  private val flushMemExecutorService = ThreadPool.newThreadPool(FLUSH_CONCURRENT_NUM, new CustomThreadFactory("Occupancy-Flush-Mem-Executor-"));

  def main(args: Array[String]): Unit = {

    val o =  new Occupancy();

    while (true) {

     val stationNum = 300;
      val levelNum = 140000;
      val time = System.currentTimeMillis() / 1000;
      val cmds = new java.util.ArrayList[Cmd]();
      for (station <- 1 to stationNum) {
        val levels = ArrayBuffer[Short]();
        for (index <- 0 until levelNum) {
          levels += (Random.nextInt(127)).toShort;
        }
        cmds.add(Cmd(0L, 0L, 0L, "", station, time, 0L, 0L, 0L, 0L, 0L, "", 0L, 0, 0, 0L, levels, null));
      }

      o.compute(cmds);

      /*println(DateTime.getCurrentDate_HHMMSS);
      println(getOccupancy("20170109", 125));
      println(DateTime.getCurrentDate_HHMMSS);*/

      Thread.sleep(1000);

    }

  }

  def getOccupancy(time: String, thresholdVal: Byte): List[Document] = {
    if(StringUtils.isNotBlank(time) && time.length == (TIME_YEAR_LENGTH + TIME_DAY_LENGTH)){
      if(DateTime.getCurrentDate_YYYYMMDDWithOutSeparator == time){
        store.mem.getOccupancyRate(time, thresholdVal)
      }else{
        store.disk.getOccupancyRate(time, thresholdVal);
      }
    }else{
      List();
    }
  }

}
