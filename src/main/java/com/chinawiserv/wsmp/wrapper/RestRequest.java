package com.chinawiserv.wsmp.wrapper;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

public class RestRequest extends HttpServletRequestWrapper{

    public RestRequest(HttpServletRequest request) {
	super(request);
    }

    
    public Map<String, Object> getParameters(){
	
	final Map<String, String[]> paramMap = getParameterMap();
	final Map<String, Object> params = new LinkedHashMap<>(paramMap.size());
	
	for(Entry<String, String[]> entry : paramMap.entrySet()){
	    
	    final String[] value = entry.getValue();
	   
	    if(value.length == 1){
		params.put(entry.getKey(), value[0]);
	    }else{
		params.put(entry.getKey(), value);
	    }
	}
	
	return params;
    }
}
