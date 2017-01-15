package com.chinawiserv.wsmp.kafka;

import com.chinawiserv.model.Cmd;
import com.chinawiserv.wsmp.handler.DataHandler;
import com.chinawiserv.wsmp.statistics.DataFlow;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.nustaq.serialization.FSTConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.text.DecimalFormat;
import java.util.*;
import java.util.stream.Collectors;

@Component
public class WSMPKafkaListener{
	
	final private static Logger logger  = LoggerFactory.getLogger(WSMPKafkaListener.class);

	private static DataFlow dataFlow = new DataFlow();
	private static DecimalFormat df = new DecimalFormat("#.000");
	private static FSTConfiguration conf = FSTConfiguration.createDefaultConfiguration();
	private static int packSize = 286538;

	private static Collection<DataHandler> dataHandlers;
	
	@KafkaListener(topics = "${kafka.consumer.topic}", group = "1")
	public void onMessage(ConsumerRecord<String, Cmd> record) {
		dataFlow.inc();
        showDataFlow();
	}

	@SuppressWarnings("Unchecked")
	public static <K, V>  void onMessages(ArrayList<ConsumerRecord<K, V>> records, int count) {

		dataFlow.inc(count);

		final List<Cmd> cmds = records.parallelStream().map( record -> {
			Cmd cmd = (Cmd) record.value();
			packSize = conf.asByteArray(cmd).length;
			return cmd;
		}).collect( Collectors.toList());

        for(DataHandler handler : dataHandlers){
			handler.compute(new ArrayList<>( cmds ));
		}

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
		double flow = Double.valueOf(df.format(dataFlow.getAvgVal() * packSize / 1024 / 1024));
		System.out.println("收到数据:"+ dataFlow.getTotalVal()+" 条, 处理速度:"+dataFlow.getAvgVal()+" 条/秒, 数据流量:"+ flow +" MB/S");
	}

}
