package com.chinawiserv.wsmp

import com.chinawiserv.wsmp.kafka.KafkaReader

object WsmpBooter {

  def main(args: Array[String]): Unit = {
    val kafkaReader = new KafkaReader("172.16.7.202:9092,172.16.7.203:9092,172.16.7.204:9092", "dom");
    kafkaReader.start();
    kafkaReader.waitToStop();
  }

}
