package com.chinawiserv.wsmp.kafka;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.Properties;

// kafka-topics.sh --zookeeper slave1.dom:2181,slave2.dom:2181,slave3.dom:2181 --create --topic dom --replication-factor 1 --partitions 9
// kafka-topics.sh --zookeeper slave1.dom:2181,slave2.dom:2181,slave3.dom:2181 --delete --topic dom
// kafka-topics.sh --zookeeper slave1.dom:2181,slave2.dom:2181,slave3.dom:2181 --list
// kafka-topics.sh --zookeeper slave1.dom:2181,slave2.dom:2181,slave3.dom:2181 --describe dom
// kafka-console-consumer.sh --zookeeper slave1.dom:2181,slave2.dom:2181,slave3.dom:2181 --topic dom --from-beginning
public class KafkaWriter extends Kafka {

    private Logger log = LoggerFactory.getLogger(KafkaWriter.class);

    private String brokers;
    private String topicName;
    private KafkaProducer<String, String> producer;

    public KafkaWriter(String brokers, String topicName) {
        this.brokers = brokers;
        this.topicName = topicName;
    }

    public org.apache.kafka.clients.producer.Producer connectKafka() {
        try {
            if (this.producer == null) {
                Properties props = this.getPropertiesForWriter(brokers);
                this.producer = new KafkaProducer<String, String>(props);
            }
            return this.producer;
        }
        catch (Exception e) {
            log("Failed to connect Kafka, error message: "+ e.getMessage());
            this.producer = null;
            return null;
        }
    }

    public boolean writeData(String k, String json) {
        boolean result = false;
        if (producer != null) {
            try {
                producer.send(new ProducerRecord(topicName, k, json));
                result = true;
                log("Message has been delivered: brokers="+brokers + "，topicName="+topicName);
            }
            catch (Exception e) {
                log("Writing to Kafka fails, trying to reconnect Kafka ...");
                this.connectKafka();
            }
        }
        else {
            log("Not connected to Kafka, try to connect ...");
            this.connectKafka();
        }
        return result;
    }

    private void log(String info, Object... obj) {
        if (log != null && log.isInfoEnabled()) {
            log.info(info, obj);
        }
    }
}
