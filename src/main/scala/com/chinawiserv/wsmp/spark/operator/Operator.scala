package com.chinawiserv.wsmp.spark.operator

import com.chinawiserv.wsmp.spark.model.{Cmd, Record}
import com.codahale.jerkson.Json
import scala.collection.mutable.ListBuffer

object Operator {

  def toCmd(json: String): Cmd = {
    Json.parse[Cmd](json);
  }

  def toList(cmd: Cmd): List[Record] = {
    val records = new ListBuffer[Record];
    val startFreq = cmd.startFreq / 1000000;
    val stepFreq = cmd.stepFreq / 1000000;
    var index = 0;
    cmd.levels.foreach(byte => {
      records += Record(cmd.id, cmd.scanOverTime, (startFreq + (stepFreq * index)), byte);
      index += 1;
    });
    records.toList;
  }

}
