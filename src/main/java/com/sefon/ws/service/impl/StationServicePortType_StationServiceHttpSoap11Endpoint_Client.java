
package com.sefon.ws.service.impl;

/**
 * Please modify this class to meet your needs
 * This class is not complete
 */

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;

import javax.xml.namespace.QName;

/**
 * This class was generated by Apache CXF 3.1.12
 * 2017-07-27T14:35:53.530+08:00
 * Generated source version: 3.1.12
 * 
 */
public final class StationServicePortType_StationServiceHttpSoap11Endpoint_Client {

    private static final QName SERVICE_NAME = new QName("http://impl.service.ws.sefon.com", "StationService");

    private StationServicePortType_StationServiceHttpSoap11Endpoint_Client() {
    }

    public static void main(String args[]) throws java.lang.Exception {
        URL wsdlURL = StationService.WSDL_LOCATION;
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
      
        StationService ss = new StationService(wsdlURL, SERVICE_NAME);
        StationServicePortType port = ss.getStationServiceHttpSoap11Endpoint();  
        
        {
        System.out.println("Invoking queryStationWithPagination...");
        com.sefon.ws.model.xsd.StationQuerySpecInfo _queryStationWithPagination_spec = null;
        java.lang.Integer _queryStationWithPagination_pageNum = null;
        java.lang.Integer _queryStationWithPagination_pageSize = null;
        com.sefon.ws.model.xsd.StationInfoPagedResult _queryStationWithPagination__return = port.queryStationWithPagination(_queryStationWithPagination_spec, _queryStationWithPagination_pageNum, _queryStationWithPagination_pageSize);
        System.out.println("queryStationWithPagination.result=" + _queryStationWithPagination__return);


        }
        {
        System.out.println("Invoking queryStation...");
        com.sefon.ws.model.xsd.StationQuerySpecInfo _queryStation_spec = null;
        java.lang.Boolean _queryStation_removeSame = null;
        java.util.List<com.sefon.ws.model.xsd.StationInfo> _queryStation__return = port.queryStation(_queryStation_spec, _queryStation_removeSame);
        System.out.println("queryStation.result=" + _queryStation__return);


        }
        {
        System.out.println("Invoking getStationById...");
        java.lang.String _getStationById_id = "";
        com.sefon.ws.model.xsd.StationInfo _getStationById__return = port.getStationById(_getStationById_id);
        System.out.println("getStationById.result=" + _getStationById__return);


        }

        System.exit(0);
    }

}
