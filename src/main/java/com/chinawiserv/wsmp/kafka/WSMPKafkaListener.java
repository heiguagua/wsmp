package com.chinawiserv.wsmp.kafka;

import com.chinawiserv.wsmp.handler.DataHandler;
import com.chinawiserv.wsmp.model.Cmd;
import com.chinawiserv.wsmp.operator.Operator;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class WSMPKafkaListener{
	
	final static Logger logger  = LoggerFactory.getLogger(WSMPKafkaListener.class);


	static List<DataHandler> dataHandlers;
	
	@KafkaListener(topics = "dom", group = "1")
	public void onMessage(ConsumerRecord<String, String> record) {
		logger.info("receive messge {}", record);	
	}

	@SuppressWarnings("Unchecked")
	public static <K, V>  void onMessages(ConsumerRecords<K, V> records) {

		final ArrayList<Cmd> cmds = new ArrayList<>();
		for(ConsumerRecord<K, V> record : records){
			cmds.add(Operator.toCmd(record.value().toString()));
		}
		for(DataHandler handler : dataHandlers){
			handler.compute((List<Cmd>) cmds.clone());
		}
		logger.info("receive messge {}, dataHandlers{}", cmds.size(), dataHandlers.size());
		cmds.clear();
	}

	@Autowired
	public void setDataHandlers(List<DataHandler> handlers){
		dataHandlers = handlers;
	}
}
