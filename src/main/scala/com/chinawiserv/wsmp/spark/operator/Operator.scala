package com.chinawiserv.wsmp.spark.operator

import com.chinawiserv.wsmp.spark.model.{Cmd, Mem}
import com.codahale.jerkson.Json
import scala.collection.mutable

object Operator {

  def toCmd(json: String): Cmd = {
    Json.parse[Cmd](json);
  }

  def toMem(cmd: Cmd): Mem = {
    Mem(cmd.id, cmd.scanOverTime, cmd.startFreq, cmd.stopFreq, cmd.stepFreq, cmd.flon, cmd.flat, cmd.numOfTraceItems, cmd.levels);
  }

  def toRedis(cmd: Cmd): String = {
    val map = new mutable.HashMap[String, Any];
    map += ("id" -> cmd.id);
    map += ("levels" -> cmd.levels);
    Json.generate[mutable.HashMap[String, Any]](map);
  }

}
