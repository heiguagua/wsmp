package com.chinawiserv.wsmp.unusual.mem

import com.chinawiserv.wsmp.model.Cmd
import com.chinawiserv.wsmp.operator.Operator
import com.codahale.jerkson.Json

import scala.collection.mutable.{HashMap, ListBuffer}

/**
  * 内存数据管理
  */
class MemManager {

  private val dataCount = 10;
  private val memMap = new HashMap[Int, ListBuffer[Mem]]();
  private val webMap = new HashMap[Int, Web]();

  def saveData(cmd: Cmd): Boolean = {
    saveData(Operator.toMem(cmd));
  }

  def saveData(mem: Mem): Boolean = {
    synchronized({
      var result = false;
      if (mem != null && mem.id != null) {
        val list = memMap.get(mem.id).getOrElse(new ListBuffer[Mem]());
        if (list.length >= dataCount) {
          list.trimStart(list.length - (dataCount - 1));
        }
        list += mem;
        memMap += (mem.id -> list);
        result = true;
      }
      return result;
    });
  }

  def readData(id: Int): List[Mem] = {
    memMap.get(id).getOrElse(new ListBuffer[Mem]()).toList;
  }

  def updateWeb(id: Int, numsOfUnusual:Int): Unit = {
    synchronized({
      webMap += (id -> Web(id, numsOfUnusual));
    });
  }

  def jsonWeb(): String = {
    synchronized({
      Json.generate[Iterable[Web]](webMap.values);
    });
  }

}
