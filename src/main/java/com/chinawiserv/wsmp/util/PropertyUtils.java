package com.chinawiserv.wsmp.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Properties;

public class PropertyUtils {

	private static final String APPLICATION_PROPERTIES_PATH ="src/main/resources/application.properties";
	/**
	* 修改/添加application.properties资源文件中键值对;
	* 如果K值原先存在则，修改该K值对应的value值;
	* 如果K值原先不存在则，添加该键值对到资源中.
	* @param key
	* @param value
	* @author cyw
	*/
	public static void updateProperiesToApplication(String key,String value){
	   File file = new File(APPLICATION_PROPERTIES_PATH); 
	   Properties prop = new Properties(); 
	   InputStream inputFile = null;  
	   OutputStream outputFile = null;  
	        try {  
	            inputFile = new FileInputStream(file);  
	            prop.load(inputFile);  
	           // inputFile.close();//一定要在修改值之前关闭inputFile  
	            outputFile = new FileOutputStream(file); 
	          //设值-保存
	            prop.setProperty(key, value); 
	            //添加注释
	            prop.store(outputFile, "Update '" + key + "'+ '"+value);  
	        } catch (IOException e) {
	       e.printStackTrace();  
	        }  
	        finally{  
	            try {  
	           if(null!=outputFile){
	           outputFile.close();  
	           }
	            } catch (IOException e) {  
	                e.printStackTrace();  
	            } 
	            try {  
	           if(null!=inputFile){
	           inputFile.close(); 
	           } 
	            } catch (IOException e) {  
	                e.printStackTrace();  
	            } 
	        }
	 }

	public static String getProperiesFromApplication(String key){
	   File file = new File(APPLICATION_PROPERTIES_PATH); 
	   Properties prop = new Properties(); 
	   InputStream inputFile = null;  
	   OutputStream outputFile = null;
	        try {  
	            inputFile = new FileInputStream(file);  
	            prop.load(inputFile);  
	            return prop.getProperty(key);
	        } catch (IOException e) {
	       e.printStackTrace();  
	        }  
	        finally{  
	            try {  
	           if(null!=outputFile){
	           outputFile.close();  
	           }
	            } catch (IOException e) {  
	                e.printStackTrace();  
	            } 
	            try {  
	           if(null!=inputFile){
	           inputFile.close(); 
	           } 
	            } catch (IOException e) {  
	                e.printStackTrace();  
	            } 
	        }
	        return null;
	 }
}
