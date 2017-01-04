package com.chinawiserv.wsmp.spark

import com.chinawiserv.wsmp.mongo.MongoDB
import com.chinawiserv.wsmp.spark.operator.Operator
import kafka.serializer.StringDecoder
import org.apache.log4j.{Level, Logger}
import org.apache.spark.SparkConf
import org.apache.spark.streaming.kafka.KafkaUtils
import org.apache.spark.streaming.{Durations, StreamingContext}

object SparkStreaming {
  def main(args: Array[String]): Unit = {

    Logger.getLogger("org.apache.spark").setLevel(Level.WARN);
    Logger.getLogger("org.eclipse.jetty.server").setLevel(Level.OFF);

    val sparkConf = new SparkConf().setAppName("SparkStreaming").setMaster("local[2]");
    val sc = new StreamingContext(sparkConf, Durations.seconds(1));

    val topic = Set("dom");
    val brokers = "slave1.dom:9092,slave2.dom:9092,slave3.dom:9092";
    val kafkaParams = Map("metadata.broker.list" -> brokers, "bootstrap.servers" -> brokers);

    val lines = KafkaUtils.createDirectStream[String, String, StringDecoder, StringDecoder](sc, kafkaParams, topic);

    val cmds = lines.map(x => x._2).map(json => Operator.toCmd(json));
    val list = cmds.map(cmd => Operator.toList(cmd));
    list.foreachRDD(rdd => rdd.foreachPartition(x => MongoDB.saveRecords(x.toList)));

    sc.start();
    sc.awaitTermination();
  }

}
