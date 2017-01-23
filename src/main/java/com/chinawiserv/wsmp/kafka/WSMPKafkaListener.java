package com.chinawiserv.wsmp.kafka;

import com.chinawiserv.model.Cmd;
import com.chinawiserv.wsmp.handler.DataHandler;
import com.chinawiserv.wsmp.statistics.DataFlow;
import com.chinawiserv.wsmp.statistics.Monitor;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.nustaq.serialization.FSTConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import java.util.concurrent.Semaphore;

// kafka-topics.sh --zookeeper 192.168.13.151:2181,192.168.13.152:2181,192.168.13.154:2181 --create --topic wsmp --replication-factor 1 --partitions 9
// kafka-topics.sh --zookeeper 192.168.13.151:2181,192.168.13.152:2181,192.168.13.154:2181 --delete --topic wsmp
// kafka-topics.sh --zookeeper 192.168.13.151:2181,192.168.13.152:2181,192.168.13.154:2181 --list
// kafka-topics.sh --zookeeper 192.168.13.151:2181,192.168.13.152:2181,192.168.13.154:2181 --describe wsmp
// kafka-console-consumer.sh --zookeeper 192.168.13.151:2181,192.168.13.152:2181,192.168.13.154:2181 --topic wsmp --from-beginning
@Component
public class WSMPKafkaListener{
	
	final private static Logger logger  = LoggerFactory.getLogger(WSMPKafkaListener.class);

	private static DataFlow dataFlow = new DataFlow();
	private static DecimalFormat df = new DecimalFormat("#.000");
	private static FSTConfiguration conf = FSTConfiguration.createDefaultConfiguration();
	private static Collection<DataHandler> dataHandlers;
	private static String dhName = "";
	private static int handlerCount = 0;
	public static Semaphore semaphore;
    private static int packSize = 286538;

	@KafkaListener(topics = "${kafka.consumer.topic}", group = "1")
	public void onMessage(ConsumerRecord<String, Cmd> record) {
	}

	public static <K, V>  void onMessages(ConsumerRecords<K, V> records, int count) {

        dataFlow.inc(count);

        final ArrayList<Cmd> cmds = new ArrayList<>( count );

        records.forEach( record -> {
            Cmd cmd = (Cmd) record.value();
            packSize = conf.asByteArray(cmd).length;
            cmds.add(cmd);
        } );

        if(handlerCount > 0){
            try {
                semaphore.acquire(handlerCount);
                dataHandlers.stream().forEach( dataHandler -> dataHandler.compute((ArrayList<Cmd>) cmds.clone()));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        logger.info("receive messge {}, dataHandlers {}, {}", count, handlerCount, dhName);
        showDataFlow();
	}

	@Autowired
	public void setDataHandlers(ApplicationContext context){
		Map<String, DataHandler> beans = context.getBeansOfType(DataHandler.class);
		dataHandlers = beans.values();
		dhName = "[";
		for (DataHandler dh : dataHandlers) {
			dhName = dhName + " " + dh.getClass().getSimpleName();
		}
		dhName = dhName + " ]" ;
        handlerCount = dataHandlers.size();
        semaphore = new Semaphore(handlerCount);
		logger.info("receive dataHandlers {}", handlerCount);
	}

	private static void showDataFlow()  {
		double flow = Double.valueOf(df.format(dataFlow.getAvgVal() * packSize / 1024 / 1024));
		System.out.println("处理数据:"+ dataFlow.getTotalVal()+" 条, 处理速度:"+dataFlow.getAvgVal()+" 条/秒, 数据流量:"+ flow +" MB/S "+ Monitor.getInfo());
	}

}
