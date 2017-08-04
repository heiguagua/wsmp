
package com.sefon.ws.service.impl;

/**
 * Please modify this class to meet your needs
 * This class is not complete
 */

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import javax.xml.namespace.QName;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.ws.Action;
import javax.xml.ws.RequestWrapper;
import javax.xml.ws.ResponseWrapper;

/**
 * This class was generated by Apache CXF 3.1.11
 * 2017-08-04T10:54:46.910+08:00
 * Generated source version: 3.1.11
 * 
 */
public final class QueryToolsServicePortType_QueryToolsServiceHttpSoap12Endpoint_Client {

    private static final QName SERVICE_NAME = new QName("http://impl.service.ws.sefon.com", "QueryToolsService");

    private QueryToolsServicePortType_QueryToolsServiceHttpSoap12Endpoint_Client() {
    }

    public static void main(String args[]) throws java.lang.Exception {
        URL wsdlURL = QueryToolsService.WSDL_LOCATION;
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
      
        QueryToolsService ss = new QueryToolsService(wsdlURL, SERVICE_NAME);
        QueryToolsServicePortType port = ss.getQueryToolsServiceHttpSoap12Endpoint();  
        
        {
        System.out.println("Invoking querySelfFreqInfoByPID...");
        java.lang.String _querySelfFreqInfoByPID_pid = "";
        java.util.List<com.sefon.ws.model.freq.xsd.FreqSelfInfo> _querySelfFreqInfoByPID__return = port.querySelfFreqInfoByPID(_querySelfFreqInfoByPID_pid);
        System.out.println("querySelfFreqInfoByPID.result=" + _querySelfFreqInfoByPID__return);


        }
        {
        System.out.println("Invoking querySelfFreqInfoByID...");
        java.lang.String _querySelfFreqInfoByID_id = "";
        com.sefon.ws.model.freq.xsd.FreqSelfInfo _querySelfFreqInfoByID__return = port.querySelfFreqInfoByID(_querySelfFreqInfoByID_id);
        System.out.println("querySelfFreqInfoByID.result=" + _querySelfFreqInfoByID__return);


        }

        System.exit(0);
    }

}
