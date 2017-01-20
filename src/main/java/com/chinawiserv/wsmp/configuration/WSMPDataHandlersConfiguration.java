package com.chinawiserv.wsmp.configuration;

import com.chinawiserv.wsmp.handler.DataHandler;
import com.chinawiserv.wsmp.hbase.HbaseDataHandlers;
import com.chinawiserv.wsmp.occupancy.Occupancy;
import com.chinawiserv.wsmp.unusual.Unusual;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by chinawiserv-0006 on 2017/1/20.
 */

@Configuration
public class WSMPDataHandlersConfiguration {

    @ConditionalOnProperty(prefix = "datahandler.hbase", value = "enable", havingValue = "true")
    @Bean(name = "hbaseHandler")
    public DataHandler hbaseHandler(){
        return new HbaseDataHandlers();
    }

    @ConditionalOnProperty(prefix = "datahandler.mongostream", value = "enable", havingValue = "true")
    @Bean(name = "mongoStreamHandler")
    public DataHandler mongoStreamHandler(){
        return new Unusual();
    }

    @ConditionalOnProperty(prefix = "datahandler.mongoflush", value = "enable", havingValue = "true")
    @Bean(name = "mongoflushHandler")
    public DataHandler mongoflushHandler(){
        return new Occupancy();
    }
}
