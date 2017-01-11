package com.chinawiserv.wsmp.unusual.mem

import com.chinawiserv.wsmp.jedis.JedisClient
import com.chinawiserv.wsmp.model.Cmd
import com.chinawiserv.wsmp.operator.Operator
import com.codahale.jerkson.Json
import scala.collection.mutable.{HashMap, ListBuffer}
import com.chinawiserv.wsmp.jedis.JedisClient.JedisExtended
import scala.collection.JavaConversions._;

/**
  * 内存数据管理
  */
class MemManager {

  private val keyPrefix = "wsmp";
  private val dataCount = 10;
  private val memMap = new HashMap[Int, ListBuffer[Mem]]();

  def initMem(): Unit = {
    try {
      val jedis = JedisClient.pool.getResource;
      val keys = jedis.keys(keyPrefix+"*");
      if (keys != null && !keys.isEmpty) {
        keys.foreach(key => {
          val list = jedis.readMsg(key);
          if (list != null && !list.isEmpty) {
            val memList = new ListBuffer[Mem];
            val id = key.replace(keyPrefix, "").toInt;
            list.foreach(memjson => {
              val mem = Json.parse[Mem](memjson);
              memList += mem;
            })
            memMap += (id -> memList);
          }
        });
      }
      jedis.close();
    }
    catch {
      case e: Exception => e.printStackTrace();
    }
  }

  def saveData(cmd: Cmd): Boolean = {
    saveData(Operator.toMem(cmd));
  }

  def saveData(mem: Mem): Boolean = {
    synchronized({
      var result = false;
      if (mem != null) {
        val list = memMap.get(mem.id).getOrElse(new ListBuffer[Mem]());
        if (list.length >= dataCount) {
          list.trimStart(list.length - (dataCount - 1));
        }
        list += mem;
        memMap += (mem.id -> list);
        this.saveToRedis(mem.id, Json.generate[Mem](mem));
        result = true;
      }
      return result;
    });
  }

  def saveToRedis(key: Int, element: String): Unit = {
    try {
      val jedis = JedisClient.pool.getResource;
      jedis.addMsg(keyPrefix+key, element);
      jedis.close();
    }
    catch {
      case e: Exception => e.printStackTrace();
    }
  }

  def readData(id: Int): List[Mem] = {
    memMap.get(id).getOrElse(new ListBuffer[Mem]()).toList;
  }

}
