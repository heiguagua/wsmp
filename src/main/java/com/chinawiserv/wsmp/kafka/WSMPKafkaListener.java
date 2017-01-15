package com.chinawiserv.wsmp.kafka;

import com.chinawiserv.model.Cmd;
import com.chinawiserv.wsmp.handler.DataHandler;
import com.chinawiserv.wsmp.statistics.DataFlow;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class WSMPKafkaListener{
	
	final private static Logger logger  = LoggerFactory.getLogger(WSMPKafkaListener.class);

	private static DataFlow dataFlow = new DataFlow();

	private static Collection<DataHandler> dataHandlers;
	
	@KafkaListener(topics = "${kafka.consumer.topic}", group = "1")
	public void onMessage(ConsumerRecord<String, Cmd> record) {
        showDataFlow();
	}

	@SuppressWarnings("Unchecked")
	public static <K, V>  void onMessages(ArrayList<ConsumerRecord<K, V>> records, int count) {

		dataFlow.inc(count);

/*
        for(DataHandler handler : dataHandlers){
			handler.compute((List<Cmd>) records.clone());
		}
*/

		logger.info("receive messge {}, dataHandlers {}", count, dataHandlers.size());
		showDataFlow();
	}

	@Autowired
	public void setDataHandlers(ApplicationContext context){
		Map<String, DataHandler> beans = context.getBeansOfType(DataHandler.class);
		dataHandlers = beans.values();
		logger.info("receive dataHandlers {}", dataHandlers.size());
	}

	private static void showDataFlow()  {
		System.out.println("收到数据:"+ dataFlow.getTotalVal()+" 条, 接收速度:"+dataFlow.getAvgVal()+" 条/秒");
	}

}
