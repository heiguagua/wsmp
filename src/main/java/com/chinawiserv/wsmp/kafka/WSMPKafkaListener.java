package com.chinawiserv.wsmp.kafka;

import com.chinawiserv.model.Cmd;
import com.chinawiserv.util.FstUtil;
import com.chinawiserv.wsmp.configuration.SpringContextManager;
import com.chinawiserv.wsmp.handler.DataHandler;
import com.chinawiserv.wsmp.statistics.DataFlow;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import java.util.*;

@Component
public class WSMPKafkaListener{
	
	final private static Logger logger  = LoggerFactory.getLogger(WSMPKafkaListener.class);

	private static DataFlow dataFlow = new DataFlow();
	private static Collection<DataHandler> dataHandlers;

    private static int packSize = 286538;

	@KafkaListener(topics = "${kafka.consumer.topic}", group = "1")
	public void onMessage(ConsumerRecord<String, Cmd> record) {
	}

	@Async
	public <K, V> void distribute(ConsumerRecords<K, V> records, int count){
        dataFlow.inc(count);

        final ArrayList<Cmd> cmds = new ArrayList<>( count );

        for(ConsumerRecord<K, V> record : records){
            Cmd cmd = (Cmd) record.value();
            packSize = FstUtil.fst.asByteArray(cmd).length;
			cmds.add(cmd);
        }

        for(DataHandler handler : dataHandlers){
            handler.compute(new ArrayList<>( cmds ));
        }

        logger.info("receive messge {}, dataHandlers {}", count, dataHandlers.size());

        this.showDataFlow();
    }

	@SuppressWarnings("Unchecked")
	public static <K, V>  void onMessages(ConsumerRecords<K, V> records, int count) {

        final WSMPKafkaListener listener =  SpringContextManager.getBean( WSMPKafkaListener.class );
        listener.distribute( records, count );
	}

	@Autowired
	public void setDataHandlers(ApplicationContext context){
		Map<String, DataHandler> beans = context.getBeansOfType(DataHandler.class);
		dataHandlers = beans.values();
		logger.info("receive dataHandlers {}", dataHandlers.size());
	}

	private void showDataFlow()  {
		double flow = Double.valueOf(dataFlow.getDf().format(dataFlow.getAvgVal() * packSize / 1024 / 1024));
		System.out.println("收到数据:"+ dataFlow.getTotalVal()+" 条, 处理速度:"+dataFlow.getAvgVal()+" 条/秒, 数据流量:"+ flow +" MB/S");
	}

}
