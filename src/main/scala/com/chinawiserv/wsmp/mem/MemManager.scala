package com.chinawiserv.wsmp.mem

import com.chinawiserv.wsmp.spark.model.{Cmd, Mem}
import com.chinawiserv.wsmp.spark.operator.Operator
import scala.collection.mutable.{HashMap, ListBuffer}

/**
  * 内存数据管理
  */
class MemManager {

  private val dataCount = 10;
  private val memMap = new HashMap[String, ListBuffer[Mem]]();

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

  def readData(id: String): ListBuffer[Mem] = {
    memMap.get(id).getOrElse(new ListBuffer[Mem]());
  }

}
