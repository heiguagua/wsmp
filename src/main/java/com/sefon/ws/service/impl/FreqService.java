package com.sefon.ws.service.impl;

import java.net.MalformedURLException;
import java.net.URL;
import javax.xml.namespace.QName;
import javax.xml.ws.WebEndpoint;
import javax.xml.ws.WebServiceClient;
import javax.xml.ws.WebServiceFeature;
import javax.xml.ws.Service;

/**
 * This class was generated by Apache CXF 3.1.12
 * 2017-08-12T18:25:32.836+08:00
 * Generated source version: 3.1.12
 * 
 */
@WebServiceClient(name = "FreqService", 
                  wsdlLocation = "http://192.168.21.105:7080/ws/services/FreqService?wsdl",
                  targetNamespace = "http://impl.service.ws.sefon.com") 
public class FreqService extends Service {

    public final static URL WSDL_LOCATION;

    public final static QName SERVICE = new QName("http://impl.service.ws.sefon.com", "FreqService");
    public final static QName FreqServiceHttpEndpoint = new QName("http://impl.service.ws.sefon.com", "FreqServiceHttpEndpoint");
    public final static QName FreqServiceHttpSoap12Endpoint = new QName("http://impl.service.ws.sefon.com", "FreqServiceHttpSoap12Endpoint");
    public final static QName FreqServiceHttpSoap11Endpoint = new QName("http://impl.service.ws.sefon.com", "FreqServiceHttpSoap11Endpoint");
    static {
        URL url = null;
        try {
            url = new URL("http://192.168.21.105:7080/ws/services/FreqService?wsdl");
        } catch (MalformedURLException e) {
            java.util.logging.Logger.getLogger(FreqService.class.getName())
                .log(java.util.logging.Level.INFO, 
                     "Can not initialize the default wsdl from {0}", "http://192.168.21.105:7080/ws/services/FreqService?wsdl");
        }
        WSDL_LOCATION = url;
    }

    public FreqService(URL wsdlLocation) {
        super(wsdlLocation, SERVICE);
    }

    public FreqService(URL wsdlLocation, QName serviceName) {
        super(wsdlLocation, serviceName);
    }

    public FreqService() {
        super(WSDL_LOCATION, SERVICE);
    }
    




    /**
     *
     * @return
     *     returns FreqServicePortType
     */
    @WebEndpoint(name = "FreqServiceHttpEndpoint")
    public FreqServicePortType getFreqServiceHttpEndpoint() {
        return super.getPort(FreqServiceHttpEndpoint, FreqServicePortType.class);
    }

    /**
     * 
     * @param features
     *     A list of {@link javax.xml.ws.WebServiceFeature} to configure on the proxy.  Supported features not in the <code>features</code> parameter will have their default values.
     * @return
     *     returns FreqServicePortType
     */
    @WebEndpoint(name = "FreqServiceHttpEndpoint")
    public FreqServicePortType getFreqServiceHttpEndpoint(WebServiceFeature... features) {
        return super.getPort(FreqServiceHttpEndpoint, FreqServicePortType.class, features);
    }


    /**
     *
     * @return
     *     returns FreqServicePortType
     */
    @WebEndpoint(name = "FreqServiceHttpSoap12Endpoint")
    public FreqServicePortType getFreqServiceHttpSoap12Endpoint() {
        return super.getPort(FreqServiceHttpSoap12Endpoint, FreqServicePortType.class);
    }

    /**
     * 
     * @param features
     *     A list of {@link javax.xml.ws.WebServiceFeature} to configure on the proxy.  Supported features not in the <code>features</code> parameter will have their default values.
     * @return
     *     returns FreqServicePortType
     */
    @WebEndpoint(name = "FreqServiceHttpSoap12Endpoint")
    public FreqServicePortType getFreqServiceHttpSoap12Endpoint(WebServiceFeature... features) {
        return super.getPort(FreqServiceHttpSoap12Endpoint, FreqServicePortType.class, features);
    }


    /**
     *
     * @return
     *     returns FreqServicePortType
     */
    @WebEndpoint(name = "FreqServiceHttpSoap11Endpoint")
    public FreqServicePortType getFreqServiceHttpSoap11Endpoint() {
        return super.getPort(FreqServiceHttpSoap11Endpoint, FreqServicePortType.class);
    }

    /**
     * 
     * @param features
     *     A list of {@link javax.xml.ws.WebServiceFeature} to configure on the proxy.  Supported features not in the <code>features</code> parameter will have their default values.
     * @return
     *     returns FreqServicePortType
     */
    @WebEndpoint(name = "FreqServiceHttpSoap11Endpoint")
    public FreqServicePortType getFreqServiceHttpSoap11Endpoint(WebServiceFeature... features) {
        return super.getPort(FreqServiceHttpSoap11Endpoint, FreqServicePortType.class, features);
    }

}
