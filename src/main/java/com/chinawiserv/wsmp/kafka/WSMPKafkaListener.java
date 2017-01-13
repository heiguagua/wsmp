package com.chinawiserv.wsmp.kafka;

import com.chinawiserv.wsmp.handler.DataHandler;
import com.chinawiserv.wsmp.model.Cmd;
import com.chinawiserv.wsmp.operator.Operator;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class WSMPKafkaListener{
	
	final static Logger logger  = LoggerFactory.getLogger(WSMPKafkaListener.class);


	static Collection<DataHandler> dataHandlers;
	
	@KafkaListener(topics = "${kafka.consumer.topic}", group = "1")
	public void onMessage(ConsumerRecord<String, String> record) {
		logger.info("receive messge {}", record);	
	}

	@SuppressWarnings("Unchecked")
	public static <K, V>  void onMessages(List<ConsumerRecord<K, V>> records, int count) {

		final ArrayList<Cmd> cmds = new ArrayList<>(count);
		for(Iterator<ConsumerRecord<K, V>> ite = records.iterator(); ite.hasNext();){
			ConsumerRecord<K, V> record = ite.next();
			ite.remove();
			cmds.add(Operator.toCmd(record.value().toString()));
		}
		for(DataHandler handler : dataHandlers){
			handler.compute((List<Cmd>) cmds.clone());
		}

		logger.info("receive messge {}, dataHandlers{}", count, dataHandlers.size());
		cmds.clear();
	}

	@Autowired
	public void setDataHandlers(ApplicationContext context){
		Map<String, DataHandler> beans = context.getBeansOfType(DataHandler.class);
		dataHandlers = beans.values();
		logger.info("receive dataHandlers {}", dataHandlers.size());
	}
}
