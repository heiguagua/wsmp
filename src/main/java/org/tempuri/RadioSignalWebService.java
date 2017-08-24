package org.tempuri;

import java.net.MalformedURLException;
import java.net.URL;
import javax.xml.namespace.QName;
import javax.xml.ws.WebEndpoint;
import javax.xml.ws.WebServiceClient;
import javax.xml.ws.WebServiceFeature;
import javax.xml.ws.Service;

/**
 * This class was generated by Apache CXF 3.1.11
 * 2017-08-24T19:37:31.478+08:00
 * Generated source version: 3.1.11
 * 
 */
@WebServiceClient(name = "RadioSignalWebService", 
                  wsdlLocation = "http://192.168.21.106:8192/RadioSignalWebService.asmx?wsdl",
                  targetNamespace = "http://tempuri.org/") 
public class RadioSignalWebService extends Service {

    public final static URL WSDL_LOCATION;

    public final static QName SERVICE = new QName("http://tempuri.org/", "RadioSignalWebService");
    public final static QName RadioSignalWebServiceSoap12 = new QName("http://tempuri.org/", "RadioSignalWebServiceSoap12");
    public final static QName RadioSignalWebServiceSoap = new QName("http://tempuri.org/", "RadioSignalWebServiceSoap");
    static {
        URL url = null;
        try {
            url = new URL("http://192.168.21.106:8192/RadioSignalWebService.asmx?wsdl");
        } catch (MalformedURLException e) {
            java.util.logging.Logger.getLogger(RadioSignalWebService.class.getName())
                .log(java.util.logging.Level.INFO, 
                     "Can not initialize the default wsdl from {0}", "http://192.168.21.106:8192/RadioSignalWebService.asmx?wsdl");
        }
        WSDL_LOCATION = url;
    }

    public RadioSignalWebService(URL wsdlLocation) {
        super(wsdlLocation, SERVICE);
    }

    public RadioSignalWebService(URL wsdlLocation, QName serviceName) {
        super(wsdlLocation, serviceName);
    }

    public RadioSignalWebService() {
        super(WSDL_LOCATION, SERVICE);
    }
    




    /**
     *
     * @return
     *     returns RadioSignalWebServiceSoap
     */
    @WebEndpoint(name = "RadioSignalWebServiceSoap12")
    public RadioSignalWebServiceSoap getRadioSignalWebServiceSoap12() {
        return super.getPort(RadioSignalWebServiceSoap12, RadioSignalWebServiceSoap.class);
    }

    /**
     * 
     * @param features
     *     A list of {@link javax.xml.ws.WebServiceFeature} to configure on the proxy.  Supported features not in the <code>features</code> parameter will have their default values.
     * @return
     *     returns RadioSignalWebServiceSoap
     */
    @WebEndpoint(name = "RadioSignalWebServiceSoap12")
    public RadioSignalWebServiceSoap getRadioSignalWebServiceSoap12(WebServiceFeature... features) {
        return super.getPort(RadioSignalWebServiceSoap12, RadioSignalWebServiceSoap.class, features);
    }


    /**
     *
     * @return
     *     returns RadioSignalWebServiceSoap
     */
    @WebEndpoint(name = "RadioSignalWebServiceSoap")
    public RadioSignalWebServiceSoap getRadioSignalWebServiceSoap() {
        return super.getPort(RadioSignalWebServiceSoap, RadioSignalWebServiceSoap.class);
    }

    /**
     * 
     * @param features
     *     A list of {@link javax.xml.ws.WebServiceFeature} to configure on the proxy.  Supported features not in the <code>features</code> parameter will have their default values.
     * @return
     *     returns RadioSignalWebServiceSoap
     */
    @WebEndpoint(name = "RadioSignalWebServiceSoap")
    public RadioSignalWebServiceSoap getRadioSignalWebServiceSoap(WebServiceFeature... features) {
        return super.getPort(RadioSignalWebServiceSoap, RadioSignalWebServiceSoap.class, features);
    }

}
