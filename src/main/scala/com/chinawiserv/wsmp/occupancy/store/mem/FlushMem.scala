package com.chinawiserv.wsmp.occupancy
package store
package mem

import java.util.Date
import java.util.concurrent.LinkedBlockingQueue

import com.chinawiserv.model.Cmd
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
      OccupancyData(cmd.getId(), DateTime.convertDateTime(new Date(cmd.getScanOverTime() * 1000), TIME_FORMAT), Array(cmd.getLevels: _*));
    });
    flushMemQueue.offer(occupancyDatas);
  }

  private[mem] def flush: Unit = {
    println("----------flushMemQueue.size = " + flushMemQueue.size);
    val occupancyDatas = new ArrayBuffer[OccupancyData]();
    if(flushMemQueue.size == 0){
      occupancyDatas ++= flushMemQueue.take;
    }
    while(flushMemQueue.size > 0 && occupancyDatas.length < SHARD_SIZE){
      occupancyDatas ++= flushMemQueue.take;
    }
    //println("-----------flush memory start, length: " + occupancyDatas.length);
    val occupancyDatasMap = Map[String, ListBuffer[OccupancyData]]();
    val isTimeSame = this.checkTime(occupancyDatas.toList);
    if (isTimeSame) {
      val time = occupancyDatas.head.time;
      removeExpiredData(time);
      occupancyDatas.foreach(occupancyData => {
        doFlush(time, occupancyData, occupancyDatasMap);
      });
    } else {
      occupancyDatas.foreach(occupancyData => {
        val time = occupancyData.time;
        doFlush(time, occupancyData, occupancyDatasMap);
      });
    }
    for((_, occupancyDatas) <- occupancyDatasMap){
      FlushDisk.offer(occupancyDatas.toList);
    }
    //println("-----------flush memory over");
  }

  private def doFlush(time: String, occupancyData: OccupancyData, occupancyDatasMap: Map[String, ListBuffer[OccupancyData]]): Unit = {
    val OCCUPANCY_MEM_DATA = OCCUPANCY_MEM.getOrElseUpdate(time, Map[Int, Array[Byte]]());
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
