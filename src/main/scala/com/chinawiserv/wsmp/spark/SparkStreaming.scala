package com.chinawiserv.wsmp.spark

import com.chinawiserv.wsmp.jedis.JedisClient
import com.chinawiserv.wsmp.jedis.JedisClient.JedisExtended
import com.chinawiserv.wsmp.spark.operator.Operator
import kafka.serializer.StringDecoder
import org.apache.log4j.{Level, Logger}
import org.apache.spark.SparkConf
import org.apache.spark.streaming.kafka.KafkaUtils
import org.apache.spark.streaming.{Durations, StreamingContext}

object SparkStreaming {
  def main(args: Array[String]): Unit = {

    Logger.getLogger("org.apache.spark").setLevel(Level.OFF);
    Logger.getLogger("org.eclipse.jetty.server").setLevel(Level.OFF);

    val sparkConf = new SparkConf().setAppName("SparkStreaming").setMaster("local[2]");
    val sc = new StreamingContext(sparkConf, Durations.seconds(1));

    val topic = Set("dom");
    val brokers = "slave1.dom:9092,slave2.dom:9092,slave3.dom:9092";
    val kafkaParams = Map("metadata.broker.list" -> brokers, "bootstrap.servers" -> brokers);

    val lines = KafkaUtils.createDirectStream[String, String, StringDecoder, StringDecoder](sc, kafkaParams, topic);

    val cmds = lines.map(x => x._2).map(json => Operator.toCmd(json));

    cmds.foreachRDD(rdd => rdd.foreachPartition(par => {
      val jedis = JedisClient.pool.getResource;
      par.foreach(cmd => {
        val list = jedis.readMsg(cmd.id);
        var abcd = 0;
        for (json <- list) {
          println("    "+ abcd + " " + json.substring(0, 20));
          abcd += 1;
        }
        jedis.addMsg(cmd.id, Operator.toRedis(cmd));
      })
      jedis.close();
    }));

    sc.start();
    sc.awaitTermination();
  }

}
