package com.chinawiserv.wsmp.occupancy
package store
package mem

import java.util.Date
import java.util.concurrent.LinkedBlockingQueue

import com.chinawiserv.wsmp.model.Cmd
import com.chinawiserv.wsmp.occupancy.model.OccupancyData
import com.chinawiserv.wsmp.util.DateTime
import org.apache.commons.lang.StringUtils

import scala.collection.mutable.{ArrayBuffer, ListBuffer, Map}

/**
  * Created by zengpzh on 2017/1/6.
  */
private[occupancy] object FlushMem {

  private val flushMemQueue = new LinkedBlockingQueue[List[OccupancyData]]();

  def offer(cmds: List[Cmd]): Unit = {
    val occupancyDatas = ListBuffer[OccupancyData]();
    cmds.map(cmd => {
      occupancyDatas += OccupancyData(cmd.id, DateTime.convertDateTime(new Date(cmd.scanOverTime * 1000), TIME_FORMAT), cmd.levels.clone);
    });
    flushMemQueue.offer(occupancyDatas.toList);
  }

  private[mem] def flush: Unit = {
    var occupancyDatas = ListBuffer[OccupancyData]();
    val flushMemSize = flushMemQueue.size();
    var count = 0;
    while ((occupancyDatas ++= flushMemQueue.take).length < 300 && count < flushMemSize) {
      count += 1;
    }
    println("-----------flush memory start, length: " + occupancyDatas.length);
    val isTimeSame = this.checkTime(occupancyDatas.toList);
    if (isTimeSame) {
      println("--------time same");
      val time = occupancyDatas.head.time;
      removeExpiredData(time);
      occupancyDatas.foreach(occupancyData => {
        doFlush(time, occupancyData);
      });
    } else {
      println("--------time is not same");
      occupancyDatas.foreach(occupancyData => {
        doFlush(occupancyData.time, occupancyData);
      });
    }
    if (!NEED_FLUSH_DISK) {
      NEED_FLUSH_DISK = true;
    }
    println("-----------flush memory over");
  }

  private def doFlush(time: String, occupancyData: OccupancyData): Unit = {
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
        }
      }
    }
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
