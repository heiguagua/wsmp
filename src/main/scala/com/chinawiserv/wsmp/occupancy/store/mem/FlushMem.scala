package com.chinawiserv.wsmp.occupancy
package store
package mem

import java.util.Date

import com.chinawiserv.wsmp.model.Cmd
import com.chinawiserv.wsmp.util.DateTime
import org.apache.commons.lang.StringUtils

import scala.collection.mutable.{ArrayBuffer, Map}

/**
  * Created by zengpzh on 2017/1/6.
  */
private[store] object FlushMem{

  def flush(cmds: List[Cmd]): Unit ={
    println("-----------flush memory start, length: " + cmds.length);
    val isTimeSame = this.checkTime(cmds);
    if(isTimeSame){
      println("--------time same");
      val time = DateTime.convertDateTime(new Date(cmds.head.scanOverTime), TIME_FORMAT);
      removeExpiredData(time);
      cmds.foreach(cmd => {
        doFlush(time, cmd);
      });
    }else{
      println("--------time is not same");
      cmds.foreach(cmd => {
        val time = DateTime.convertDateTime(new Date(cmd.scanOverTime), TIME_FORMAT);
        doFlush(time, cmd);
      });
    }
    if(!NEED_FLUSH_DISK){
      NEED_FLUSH_DISK = true;
    }
    println("-----------flush memory over");
  }

  private def doFlush(time: String, cmd: Cmd): Unit ={
    val OCCUPANCY_MEM_DATA = OCCUPANCY_MEM.getOrElseUpdate(time, Map[Int, ArrayBuffer[Byte]]());
    val maxLevels = OCCUPANCY_MEM_DATA.getOrElseUpdate(cmd.id, ArrayBuffer[Byte]());
    val levels = cmd.levels;
    val levelsLength = levels.length;
    for(i <- 0 until levelsLength) {
      if (i >= maxLevels.length) {
        maxLevels += levels(i);
      } else {
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

  private def checkTime(cmds: List[Cmd], time: String = null): Boolean ={
    if(cmds != null && !cmds.isEmpty) {
      if(time == null){
        this.checkTime(cmds.tail, DateTime.convertDateTime(new Date(cmds.head.scanOverTime), TIME_FORMAT));
      }else{
        cmds.forall(cmd => {
          time == DateTime.convertDateTime(new Date(cmd.scanOverTime), TIME_FORMAT);
        });
      }
    }else{
      false;
    }
  }

}
