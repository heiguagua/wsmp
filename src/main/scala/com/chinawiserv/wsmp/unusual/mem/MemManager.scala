package com.chinawiserv.wsmp.unusual.mem

import com.chinawiserv.model.Cmd
import com.chinawiserv.wsmp.jedis.JedisClient
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
  private val webMap = new HashMap[Int, Int]();

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

  def readDataFromMem(id: Int): List[Array[Byte]] = {
    synchronized({
      val result = new ListBuffer[Array[Byte]]();
      val list = memMap.get(id).getOrElse(new ListBuffer[Mem]());
      list.foreach(x => {
        result += x.levels;
      });
      return result.toList;
    });
  }

  def saveDataToMem(cmd: Cmd): Boolean = {
    synchronized({
      var result = false;
      val mem = Operator.toMem(cmd);
      if (mem != null) {
        val list = memMap.get(mem.id).getOrElse(new ListBuffer[Mem]());
        if (list.length >= dataCount) {
          list.trimStart(list.length - (dataCount - 1));
        }
        list += mem;
        memMap += (mem.id -> list);
        //this.saveToRedis(mem.id, Json.generate[Mem](mem));
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

  def saveDataToWeb(id: Int, un: Int): Unit = {
    synchronized({
      webMap += (id -> un);
    });
  }

  def readDataFromWeb(): String = {
    synchronized({
      if (!webMap.isEmpty) {
        var result = new  ListBuffer[Map[String, Any]]();
        val map = webMap.clone();
        webMap.clear();
        map.foreach(x => {
          result += Map("id" -> x._1.toString, "un" -> x._2);
        });
        Json.generate[ListBuffer[Map[String, Any]]](result);
      }
      else {
        "";
      }
    });
  }

}
