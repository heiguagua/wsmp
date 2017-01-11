package com.chinawiserv.wsmp.occupancy
package store
package mem

import java.util.Date

import com.chinawiserv.wsmp.model.Cmd
import com.chinawiserv.wsmp.occupancy.model.OccupancyData
import com.chinawiserv.wsmp.util.DateTime
import org.apache.commons.lang.StringUtils

import scala.collection.mutable.{ArrayBuffer, Map}

/**
  * Created by zengpzh on 2017/1/6.
  */
private[store] object FlushMem{

  def flush(cmds: List[Cmd]): Unit ={
    //cmd为occupancyData
    val occupancyDatas = parse2OccupancyData(cmds);
    println("-----------flush memory start, length: " + occupancyDatas.length);
    val isTimeSame = this.checkTime(occupancyDatas);
    if(isTimeSame){
      println("--------time same");
      val time = occupancyDatas.head.time;
      removeExpiredData(time);
      occupancyDatas.foreach(occupancyData => {
        doFlush(time, occupancyData);
      });
    }else{
      println("--------time is not same");
      occupancyDatas.foreach(occupancyData => {
        doFlush(occupancyData.time, occupancyData);
      });
    }
    if(!NEED_FLUSH_DISK){
      NEED_FLUSH_DISK = true;
    }
    println("-----------flush memory over");
  }

  private def parse2OccupancyData(cmds: List[Cmd]): List[OccupancyData] ={
    cmds.map(cmd => {
      OccupancyData(cmd.id, DateTime.convertDateTime(new Date(cmd.scanOverTime * 1000), TIME_FORMAT), cmd.levels.clone);
    });
  }

  private def doFlush(time: String, occupancyData: OccupancyData): Unit ={
    val OCCUPANCY_MEM_DATA = OCCUPANCY_MEM.getOrElseUpdate(time, Map[Int, ArrayBuffer[Byte]]());
    val levels = occupancyData.levels;
    val levelsLength = levels.length;
    val maxLevels = OCCUPANCY_MEM_DATA.getOrElseUpdate(occupancyData.id, levels);
    if(levelsLength == maxLevels.length){
      for(i <- 0 until levelsLength) {
        val level = levels(i);
        val maxLevel = maxLevels(i);
        if (level > maxLevel) {
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
          OCCUPANCY_MEM -= tempTime;
        }
      });
    }
  }

  private def checkTime(occupancyDatas: List[OccupancyData], time: String = null): Boolean ={
    if(occupancyDatas != null && !occupancyDatas.isEmpty) {
      if(time == null){
        this.checkTime(occupancyDatas.tail, time);
      }else{
        occupancyDatas.forall(occupancyData => {
          time == occupancyData.time;
        });
      }
    }else{
      false;
    }
  }

}
