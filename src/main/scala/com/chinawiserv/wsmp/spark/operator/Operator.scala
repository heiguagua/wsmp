package com.chinawiserv.wsmp.spark.operator

import com.chinawiserv.wsmp.spark.model.{Cmd}
import com.codahale.jerkson.Json
import org.bson.Document

import scala.collection.mutable
import scala.collection.mutable.ListBuffer

object Operator {

  def toCmd(json: String): Cmd = {
    Json.parse[Cmd](json);
  }

  def toRedis(cmd: Cmd): String = {
    val map = new mutable.HashMap[String, Any];
    map += ("id" -> cmd.id);
    map += ("levels" -> cmd.levels);
    Json.generate[mutable.HashMap[String, Any]](map);
  }

  def toList(cmd: Cmd): List[Document] = {
    val records = new ListBuffer[Document];
    val startFreq = cmd.startFreq / 1000000;
    val stepFreq = cmd.stepFreq / 1000000;
    var index = 0;
    cmd.levels.foreach(byte => {
      val doc = new Document();
      doc.put("id", cmd.id);
      doc.put("scanOverTime", cmd.scanOverTime);
      doc.put("freq", startFreq + (stepFreq * index));
      doc.put("level", byte);
      index += 1;
    });
    records.toList;
  }

}
