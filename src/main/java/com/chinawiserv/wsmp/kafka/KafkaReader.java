package com.chinawiserv.wsmp.kafka;

import com.chinawiserv.wsmp.handler.DataHandler;
import com.chinawiserv.wsmp.hbase.HbaseDataHandlers;
import com.chinawiserv.wsmp.model.Cmd;
import com.chinawiserv.wsmp.occupancy.Occupancy;
import com.chinawiserv.wsmp.operator.Operator;
import com.chinawiserv.wsmp.unusual.Unusual;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
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
    private List<DataHandler> dataHandlers;

    public KafkaReader(String brokers, String topicName) {
        this.brokers = brokers;
        this.topicName = topicName;
        this.dataHandlers = new ArrayList<DataHandler>();
        dataHandlers.add(new Unusual());
        //dataHandlers.add(new HbaseDataHandlers());
        //dataHandlers.add(new Occupancy());
        this.scheduler = Executors.newScheduledThreadPool(1);
        this.connect();
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
                this.scheduler.scheduleWithFixedDelay(new ReadDataRunnable(dataHandlers), 1000, 1, TimeUnit.MILLISECONDS);
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
        private List<DataHandler> dataHandlers;

        public ReadDataRunnable(List<DataHandler> dataHandlers) {
            this.dataHandlers  = dataHandlers;
        }

        public void run() {
            ConsumerRecords<String, String> records = consumer.poll(1000);
            if (records != null && !records.isEmpty()) {
                List<Cmd> cmds = new ArrayList<Cmd>();
                for (ConsumerRecord<String, String> record : records) {
                    Cmd cmd = Operator.toCmd(record.value());
                    cmds.add(cmd);
                }
                for (DataHandler dataHandler : dataHandlers) {
                    dataHandler.compute(cmds);
                }
            }
        }
    }

}
