package com.chinawiserv.wsmp.occupancy
package flush.mem

import java.util.Date

import com.chinawiserv.wsmp.util.DateTime
import org.apache.commons.lang.StringUtils

import scala.collection.mutable.{ArrayBuffer, Map}

/**
  * Created by zengpzh on 2017/1/6.
  */
object FlushMem{

  def flush(occupancyDatas: List[OccupancyData]): Unit ={
    println("-----------flush memory start, length: " + occupancyDatas.length);
    val isTimeSame = this.checkTime(occupancyDatas);
    if(isTimeSame){
      println("--------time same");
      val time = DateTime.convertDateTime(new Date(occupancyDatas.head.scanOverTime), TIME_FORMAT);
      removeExpiredData(time);
      occupancyDatas.foreach(occupancyData => {
        doFlush(time, occupancyData);
      });
    }else{
      println("--------time is not same");
      occupancyDatas.foreach(occupancyData => {
        val time = DateTime.convertDateTime(new Date(occupancyData.scanOverTime), TIME_FORMAT);
        doFlush(time, occupancyData);
      });
    }
    println("-----------flush memory over");
  }

  private def doFlush(time: String, occupancyData: OccupancyData): Unit ={
    var OCCUPANCY_MEM_DATA = OCCUPANCY_MEM.getOrElse(time, null);
    if(OCCUPANCY_MEM_DATA == null){
      OCCUPANCY_MEM_DATA = Map[String, ArrayBuffer[Byte]]();
      OCCUPANCY_MEM += (time -> OCCUPANCY_MEM_DATA);
    }
    var maxLevels = OCCUPANCY_MEM_DATA.getOrElse(occupancyData.id, null);
    val levels = occupancyData.levels;
    val levelsLength = levels.length;
    for(i <- 0 until levelsLength){
      if(maxLevels == null){
        maxLevels = levels;
        OCCUPANCY_MEM_DATA += (occupancyData.id -> maxLevels);
      }else if(i >= maxLevels.length){
        maxLevels += levels(i);
      }else{
        val level = levels(i);
        val maxLevel = maxLevels(i)
        if(level > maxLevel){
          maxLevels(i) = levels(i);
        }
      }
    }
  }

  private def removeExpiredData(time: String): Unit ={
    if(StringUtils.isNotBlank(time)){
      import scala.util.control.Breaks._;
      breakable({
        for((tempTime, tempData) <- OCCUPANCY_MEM.toMap if time != tempTime){
          OCCUPANCY_MEM.remove(tempTime);
        }
      });
    }
  }

  private def checkTime(occupancyDatas: List[OccupancyData], time: String = null): Boolean ={
    if(occupancyDatas != null && !occupancyDatas.isEmpty) {
      if(time == null){
        this.checkTime(occupancyDatas.tail, DateTime.convertDateTime(new Date(occupancyDatas.head.scanOverTime), TIME_FORMAT));
      }else{
        occupancyDatas.forall(occupancyData => {
          time == DateTime.convertDateTime(new Date(occupancyData.scanOverTime), TIME_FORMAT);
        });
      }
    }else{
      false;
    }
  }

}
