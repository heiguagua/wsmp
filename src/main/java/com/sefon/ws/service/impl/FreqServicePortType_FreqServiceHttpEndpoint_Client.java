
package com.sefon.ws.service.impl;

/**
 * Please modify this class to meet your needs
 * This class is not complete
 */

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import javax.xml.namespace.QName;
import javax.jws.Oneway;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.ws.Action;
import javax.xml.ws.RequestWrapper;
import javax.xml.ws.ResponseWrapper;

/**
 * This class was generated by Apache CXF 3.1.12
 * 2017-07-27T14:33:55.617+08:00
 * Generated source version: 3.1.12
 * 
 */
public final class FreqServicePortType_FreqServiceHttpEndpoint_Client {

    private static final QName SERVICE_NAME = new QName("http://impl.service.ws.sefon.com", "FreqService");

    private FreqServicePortType_FreqServiceHttpEndpoint_Client() {
    }

    public static void main(String args[]) throws java.lang.Exception {
        URL wsdlURL = FreqService.WSDL_LOCATION;
        if (args.length > 0 && args[0] != null && !"".equals(args[0])) { 
            File wsdlFile = new File(args[0]);
            try {
                if (wsdlFile.exists()) {
                    wsdlURL = wsdlFile.toURI().toURL();
                } else {
                    wsdlURL = new URL(args[0]);
                }
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
        }
      
        FreqService ss = new FreqService(wsdlURL, SERVICE_NAME);
        FreqServicePortType port = ss.getFreqServiceHttpEndpoint();  
        
        {
        System.out.println("Invoking queryFreqRangeDateOcc...");
        com.sefon.ws.model.electric.level.xsd.FreqRangeQuerySpec _queryFreqRangeDateOcc_spec = null;
        java.util.List<com.sefon.ws.model.electric.level.xsd.DateOccLevelDataInfo> _queryFreqRangeDateOcc__return = port.queryFreqRangeDateOcc(_queryFreqRangeDateOcc_spec);
        System.out.println("queryFreqRangeDateOcc.result=" + _queryFreqRangeDateOcc__return);


        }
        {
        System.out.println("Invoking queryContinuousFreqncyRange...");
        com.sefon.ws.model.freq.xsd.FrequencyRangeQuerySpec _queryContinuousFreqncyRange_spec = null;
        java.util.List<com.sefon.ws.model.freq.xsd.FrequencyRangeInfo> _queryContinuousFreqncyRange__return = port.queryContinuousFreqncyRange(_queryContinuousFreqncyRange_spec);
        System.out.println("queryContinuousFreqncyRange.result=" + _queryContinuousFreqncyRange__return);


        }
        {
        System.out.println("Invoking main...");
        java.util.List<java.lang.String> _main_args = null;
        port.main(_main_args);


        }
        {
        System.out.println("Invoking queryFrequencyRange...");
        com.sefon.ws.model.freq.xsd.FrequencyRangeQuerySpec _queryFrequencyRange_spec = null;
        java.util.List<com.sefon.ws.model.freq.xsd.FrequencyRangeInfo> _queryFrequencyRange__return = port.queryFrequencyRange(_queryFrequencyRange_spec);
        System.out.println("queryFrequencyRange.result=" + _queryFrequencyRange__return);


        }

        System.exit(0);
    }

}
