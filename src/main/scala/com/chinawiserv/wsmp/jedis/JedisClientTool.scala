package com.chinawiserv.wsmp.jedis

object JedisClientTool {

  private lazy val jedisClient = JedisClient();

  def addMsg(key: String, element: String): Unit = {
    jedisClient.addMsg(key, element);
  }

  def readMsg(key: String): List[String] = {
    jedisClient.readMsg(key);
  }

}
