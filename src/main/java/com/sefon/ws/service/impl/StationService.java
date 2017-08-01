package com.sefon.ws.service.impl;

import java.net.MalformedURLException;
import java.net.URL;

import javax.xml.namespace.QName;
import javax.xml.ws.Service;
import javax.xml.ws.WebEndpoint;
import javax.xml.ws.WebServiceClient;
import javax.xml.ws.WebServiceFeature;

/**
 * This class was generated by Apache CXF 3.1.12
 * 2017-07-27T14:35:53.546+08:00
 * Generated source version: 3.1.12
 * 
 */
@WebServiceClient(name = "StationService", 
                  wsdlLocation = "http://192.168.21.68:8080/ws/services/StationService?wsdl",
                  targetNamespace = "http://impl.service.ws.sefon.com") 
public class StationService extends Service {

    public final static URL WSDL_LOCATION;

    public final static QName SERVICE = new QName("http://impl.service.ws.sefon.com", "StationService");
    public final static QName StationServiceHttpSoap12Endpoint = new QName("http://impl.service.ws.sefon.com", "StationServiceHttpSoap12Endpoint");
    public final static QName StationServiceHttpEndpoint = new QName("http://impl.service.ws.sefon.com", "StationServiceHttpEndpoint");
    public final static QName StationServiceHttpSoap11Endpoint = new QName("http://impl.service.ws.sefon.com", "StationServiceHttpSoap11Endpoint");
    static {
        URL url = null;
        try {
            url = new URL("http://192.168.21.68:8080/ws/services/StationService?wsdl");
        } catch (MalformedURLException e) {
            java.util.logging.Logger.getLogger(StationService.class.getName())
                .log(java.util.logging.Level.INFO, 
                     "Can not initialize the default wsdl from {0}", "http://192.168.21.68:8080/ws/services/StationService?wsdl");
        }
        WSDL_LOCATION = url;
    }

    public StationService(URL wsdlLocation) {
        super(wsdlLocation, SERVICE);
    }

    public StationService(URL wsdlLocation, QName serviceName) {
        super(wsdlLocation, serviceName);
    }

    public StationService() {
        super(WSDL_LOCATION, SERVICE);
    }
    




    /**
     *
     * @return
     *     returns StationServicePortType
     */
    @WebEndpoint(name = "StationServiceHttpSoap12Endpoint")
    public StationServicePortType getStationServiceHttpSoap12Endpoint() {
        return super.getPort(StationServiceHttpSoap12Endpoint, StationServicePortType.class);
    }

    /**
     * 
     * @param features
     *     A list of {@link javax.xml.ws.WebServiceFeature} to configure on the proxy.  Supported features not in the <code>features</code> parameter will have their default values.
     * @return
     *     returns StationServicePortType
     */
    @WebEndpoint(name = "StationServiceHttpSoap12Endpoint")
    public StationServicePortType getStationServiceHttpSoap12Endpoint(WebServiceFeature... features) {
        return super.getPort(StationServiceHttpSoap12Endpoint, StationServicePortType.class, features);
    }


    /**
     *
     * @return
     *     returns StationServicePortType
     */
    @WebEndpoint(name = "StationServiceHttpEndpoint")
    public StationServicePortType getStationServiceHttpEndpoint() {
        return super.getPort(StationServiceHttpEndpoint, StationServicePortType.class);
    }

    /**
     * 
     * @param features
     *     A list of {@link javax.xml.ws.WebServiceFeature} to configure on the proxy.  Supported features not in the <code>features</code> parameter will have their default values.
     * @return
     *     returns StationServicePortType
     */
    @WebEndpoint(name = "StationServiceHttpEndpoint")
    public StationServicePortType getStationServiceHttpEndpoint(WebServiceFeature... features) {
        return super.getPort(StationServiceHttpEndpoint, StationServicePortType.class, features);
    }


    /**
     *
     * @return
     *     returns StationServicePortType
     */
    @WebEndpoint(name = "StationServiceHttpSoap11Endpoint")
    public StationServicePortType getStationServiceHttpSoap11Endpoint() {
        return super.getPort(StationServiceHttpSoap11Endpoint, StationServicePortType.class);
    }

    /**
     * 
     * @param features
     *     A list of {@link javax.xml.ws.WebServiceFeature} to configure on the proxy.  Supported features not in the <code>features</code> parameter will have their default values.
     * @return
     *     returns StationServicePortType
     */
    @WebEndpoint(name = "StationServiceHttpSoap11Endpoint")
    public StationServicePortType getStationServiceHttpSoap11Endpoint(WebServiceFeature... features) {
        return super.getPort(StationServiceHttpSoap11Endpoint, StationServicePortType.class, features);
    }

}