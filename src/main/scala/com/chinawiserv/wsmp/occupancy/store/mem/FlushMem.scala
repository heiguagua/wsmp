package com.chinawiserv.wsmp.occupancy
package store
package mem

import java.util.Date
import java.util.concurrent.LinkedBlockingQueue

import com.chinawiserv.wsmp.model.Cmd
import com.chinawiserv.wsmp.occupancy.model.OccupancyData
import com.chinawiserv.wsmp.occupancy.store.disk.FlushDisk
import com.chinawiserv.wsmp.thread.{CustomThreadFactory, ThreadPool}
import com.chinawiserv.wsmp.util.DateTime
import org.apache.commons.lang.StringUtils

import scala.collection.mutable.{ArrayBuffer, ListBuffer, Map}

/**
  * Created by zengpzh on 2017/1/6.
  */
private[occupancy] object FlushMem {

  private val flushMemExecutorService = ThreadPool.newThreadPool(1, new CustomThreadFactory("Occupancy-Flush-Mem-Executor-"));

  private val flushMemQueue = new LinkedBlockingQueue[List[OccupancyData]]();

  flushMemExecutorService.execute(new FlushMemTask());

  def offer(cmds: List[Cmd]): Unit = {
    val occupancyDatas = cmds.map(cmd => {
      OccupancyData(cmd.id, DateTime.convertDateTime(new Date(cmd.scanOverTime * 1000), TIME_FORMAT), cmd.levels.clone);
    });
    flushMemQueue.offer(occupancyDatas);
  }

  private[mem] def flush: Unit = {
    var occupancyDatas = ListBuffer[OccupancyData]();
    var count = 0;
    while ((occupancyDatas ++= flushMemQueue.take).length < 300 && count < flushMemQueue.size()) {
      count += 1;
    }
    println("-----------flush memory start, length: " + occupancyDatas.length);
    val occupancyDatasMap = Map[String, ListBuffer[OccupancyData]]();
    val isTimeSame = this.checkTime(occupancyDatas.toList);
    if (isTimeSame) {
      println("--------time same");
      val time = occupancyDatas.head.time;
      removeExpiredData(time);
      occupancyDatas.foreach(occupancyData => {
        doFlush(time, occupancyData, occupancyDatasMap);
      });
    } else {
      println("--------time is not same");
      occupancyDatas.foreach(occupancyData => {
        val time = occupancyData.time;
        doFlush(time, occupancyData, occupancyDatasMap);
      });
    }
    for((_, occupancyDatas) <- occupancyDatasMap){
      FlushDisk.offer(occupancyDatas.toList);
    }
    println("-----------flush memory over");
  }

  private def doFlush(time: String, occupancyData: OccupancyData, occupancyDatasMap: Map[String, ListBuffer[OccupancyData]]): Unit = {
    val OCCUPANCY_MEM_DATA = OCCUPANCY_MEM.getOrElseUpdate(time, Map[Int, ArrayBuffer[Short]]());
    val levels = occupancyData.levels;
    val levelsLength = levels.length;
    val maxLevels = OCCUPANCY_MEM_DATA.getOrElseUpdate(occupancyData.id, levels);
    if (levelsLength == maxLevels.length) {
      for (i <- 0 until levelsLength) {
        val level = levels(i);
        val maxLevel = maxLevels(i);
        if (level > maxLevel) {
          maxLevels(i) = levels(i);
        }else if(level < maxLevel){
          levels(i) = maxLevels(i);
        }
      }
    }
    val sameTimeOccupancyDatas = occupancyDatasMap.getOrElseUpdate(time, ListBuffer[OccupancyData]());
    sameTimeOccupancyDatas += occupancyData;
  }

  private def removeExpiredData(time: String): Unit = {
    if (StringUtils.isNotBlank(time)) {
      import scala.util.control.Breaks._;
      breakable({
        for ((tempTime, tempData) <- OCCUPANCY_MEM.toMap if time != tempTime) {
          OCCUPANCY_MEM -= tempTime;
        }
      });
    }
  }

  private def checkTime(occupancyDatas: List[OccupancyData], time: String = null): Boolean = {
    if (occupancyDatas != null && !occupancyDatas.isEmpty) {
      if (time == null) {
        this.checkTime(occupancyDatas.tail, occupancyDatas.head.time);
      } else {
        occupancyDatas.forall(occupancyData => {
          time == occupancyData.time;
        });
      }
    } else {
      false;
    }
  }

}
