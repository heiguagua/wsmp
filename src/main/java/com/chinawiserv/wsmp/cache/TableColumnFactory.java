package com.chinawiserv.wsmp.cache;

import java.io.IOException;
import java.util.Properties;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.support.PropertiesLoaderUtils;
import org.springframework.stereotype.Component;

import com.chinawiserv.wsmp.pojo.BandStatusTable;

@Component
public class TableColumnFactory {
	
	@Cacheable(value = "BandStatus", sync = true)
	public static BandStatusTable getBandStatusTable() {
		DefaultResourceLoader loader = new DefaultResourceLoader();

		try {
			Properties p = PropertiesLoaderUtils.loadProperties(loader.getResource("table_column/band_status.properties"));
			String radioName = p.getProperty("radio_name");
			System.out.println();
		}
		catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

}
