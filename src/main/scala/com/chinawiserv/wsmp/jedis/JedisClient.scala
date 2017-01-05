package com.chinawiserv.wsmp.jedis

import org.apache.commons.pool2.impl.GenericObjectPoolConfig
import redis.clients.jedis.{Jedis, JedisPool}
import scala.collection.mutable.ListBuffer

object JedisClient extends Serializable {

  val redisHost = "web.dom"
  val redisPort = 6379
  val redisTimeout = 30000
  lazy val pool = new JedisPool(new GenericObjectPoolConfig(), redisHost, redisPort, redisTimeout)

  lazy val hook = new Thread {
    override def run = {
      println("Execute hook thread: " + this)
      pool.destroy();
    }
  }
  sys.addShutdownHook(hook.run);

  implicit class JedisExtended(jedis: Jedis) {

    def addMsg(key: String, element: String): Unit = {
      synchronized({
        val number = jedis.rpush(key, element);
        if (number > 10) {
          jedis.ltrim(key, (number - 10), (number - 1));
        }
        println("Add to Redis（hashCode="+jedis.hashCode()+"） "+key);
      });
    }

    def readMsg(key: String): List[String] = {
      synchronized({
        val result = new ListBuffer[String];
        val number = jedis.llen(key);
        var index = 0L;
        if (number > 10) {
          index = number - 10;
        }
        val list = jedis.lrange(key, index, number - 1);
        for (i <- 0 until list.size()) {
          result += list.get(i);
        }
        list.clear();
        result.toList;
      });
    }
  }
}