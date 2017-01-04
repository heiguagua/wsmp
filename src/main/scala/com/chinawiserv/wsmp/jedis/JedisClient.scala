package com.chinawiserv.wsmp.jedis

import redis.clients.jedis.Jedis;
import scala.collection.mutable.ListBuffer;

class JedisClient(host: String, port: Int) extends Jedis(host: String, port: Int) {

  def addMsg(key: String, element: String): Unit = {
    while (this.llen(key) >= 10) {
      this.lpop(key);
    }
    this.rpush(key, element);
  }

  def readMsg(key: String): List[String] = {
    val result = new ListBuffer[String];
    var length = this.llen(key);
    if (length >= 10) {
      length = 10L;
    }
    val list = this.lrange(key, 0, length - 1);
    import collection.JavaConversions._;
    for (json <- list) {
      result += json;
    }
    result.toList;
  }

}

object JedisClient {

  private val REDIS_IP = "web.dom";

  private val REDIS_PORT = 6379;

  def apply(): JedisClient = {
    new JedisClient(REDIS_IP, REDIS_PORT);
  }

}
