package com.chinawiserv

import com.chinawiserv.wsmp.jedis.JedisClient
import com.chinawiserv.wsmp.jedis.JedisClient.JedisExtended

import scala.collection.mutable.ListBuffer
import scala.util.Random

object AppTestScala {

  def main(args: Array[String]): Unit = {

    val random = new Random();
    for (j <- 0.to(1000)) {
      val list = new ListBuffer[Int];
      for (i <- 0.to(140000)) {
        list += random.nextInt(128);
      }
      val jedis =  JedisClient.pool.getResource;
      val now = System.currentTimeMillis();
      jedis.addMsg(""+j, list.mkString);
      println("时间="+(System.currentTimeMillis() - now));
      jedis.close();
    }

/*    for (j <- 0.to(1000)) {
      val jedis =  JedisClient.pool.getResource;
      val now = System.currentTimeMillis();
      jedis.readMsg("52010057");
      println("时间="+(System.currentTimeMillis() - now));
      jedis.close();
    }*/

/*    val temp = new ListBuffer[String]();
    for (j <- 0.to(1000)) {
      val now = System.currentTimeMillis();
      val list = new ListBuffer[Int];
      for (i <- 0.to(140000)) {
        list += i;
      }
      println(j+"  时间="+(System.currentTimeMillis() - now));
      temp += list.mkString;
    }
    temp.foreach(x => println(x.substring(0, 100)));*/

  }

}
