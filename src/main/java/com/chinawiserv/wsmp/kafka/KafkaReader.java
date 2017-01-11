package com.chinawiserv.wsmp.kafka;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Properties;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

// kafka-topics.sh --zookeeper slave1.dom:2181,slave2.dom:2181,slave3.dom:2181 --create --topic dom --replication-factor 1 --partitions 9
// kafka-topics.sh --zookeeper slave1.dom:2181,slave2.dom:2181,slave3.dom:2181 --delete --topic dom
// kafka-topics.sh --zookeeper slave1.dom:2181,slave2.dom:2181,slave3.dom:2181 --list
// kafka-topics.sh --zookeeper slave1.dom:2181,slave2.dom:2181,slave3.dom:2181 --describe dom
// kafka-console-consumer.sh --zookeeper slave1.dom:2181,slave2.dom:2181,slave3.dom:2181 --topic dom --from-beginning
public class KafkaReader extends Kafka {

    private Logger log = LoggerFactory.getLogger(KafkaReader.class);

    private String brokers;
    private String topicName;
    private KafkaConsumer<String, String> consumer;
    private ScheduledExecutorService scheduler;

    public KafkaReader(String brokers, String topicName) {
        this.brokers = brokers;
        this.topicName = topicName;
        this.scheduler = Executors.newScheduledThreadPool(1);
    }

    public KafkaConsumer connect() {
        try {
            if (this.consumer == null) {
                ArrayList<String> topics = new ArrayList<String>();
                topics.add(topicName);
                Properties props = this.getPropertiesForReader(brokers);
                this.consumer = new KafkaConsumer<String, String>(props);
                this.consumer.subscribe(topics);
            }
            return this.consumer;
        }
        catch (Exception e) {
            log("Failed to connect Kafka, error message: "+ e.getMessage());
            this.consumer = null;
            return null;
        }
    }

    public void start() throws Exception {
        if (consumer != null) {
            try {
                this.scheduler.scheduleWithFixedDelay(new ReadDataRunnable(), 1000, 1, TimeUnit.MILLISECONDS);
            }
            catch (Exception e) {
                log("Reading from Kafka fails, trying to reconnect Kafka ...");
                this.connect();
            }
        }
        else {
            log("Not connected to Kafka, try to connect ...");
            this.connect();
        }
    }

    public void waitToStop() throws Exception {
        try {
            while (true) {
                Thread.sleep(1000);
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void log(String info, Object... obj) {
        if (log != null && log.isInfoEnabled()) {
            log.info(info, obj);
        }
    }

    private class ReadDataRunnable implements Runnable {

        public void run() {
            ConsumerRecords<String, String> records = consumer.poll(1000);
            if (records != null && !records.isEmpty()) {
                for (ConsumerRecord<String, String> record : records) {
                    System.out.println(record.key());
                    System.out.println(record.topic());
                }
            }
        }
    }

}
