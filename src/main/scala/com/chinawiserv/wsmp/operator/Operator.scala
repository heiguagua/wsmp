package com.chinawiserv.wsmp.operator

import com.chinawiserv.model.Cmd
import com.chinawiserv.wsmp.unusual.mem.Mem
import com.codahale.jerkson.Json

import scala.collection.mutable

object Operator {

  def toCmd(json: String): Cmd = {
    Json.parse[Cmd](json);
  }

  def toMem(cmd: Cmd): Mem = {
    Mem(cmd.getId, cmd.getScanOverTime, cmd.getStartFreq,
      cmd.getStopFreq, cmd.getStepFreq, (cmd.getFlon * 1000).toLong ,
      (cmd.getFlat * 1000).toLong, cmd.getNumOfTraceItems, cmd.getLevels);
  }

  def toRedis(cmd: Cmd): String = {
    val map = new mutable.HashMap[String, Any];
    map += ("id" -> cmd.getId);
    map += ("levels" -> cmd.getLevels);
    Json.generate[mutable.HashMap[String, Any]](map);
  }

}
