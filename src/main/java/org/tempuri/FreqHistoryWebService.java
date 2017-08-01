package org.tempuri;

import java.net.MalformedURLException;
import java.net.URL;

import javax.xml.namespace.QName;
import javax.xml.ws.Service;
import javax.xml.ws.WebEndpoint;
import javax.xml.ws.WebServiceClient;
import javax.xml.ws.WebServiceFeature;

/**
 * This class was generated by Apache CXF 3.1.11
 * 2017-07-14T13:59:22.435+08:00
 * Generated source version: 3.1.11
 * 
 */
@WebServiceClient(name = "FreqHistoryWebService", 
                  wsdlLocation = "http://192.168.21.2:8000/FreqHistoryWebService.asmx?wsdl",
                  targetNamespace = "http://tempuri.org/") 
public class FreqHistoryWebService extends Service {

    public final static URL WSDL_LOCATION;

    public final static QName SERVICE = new QName("http://tempuri.org/", "FreqHistoryWebService");
    public final static QName FreqHistoryWebServiceSoap12 = new QName("http://tempuri.org/", "FreqHistoryWebServiceSoap12");
    public final static QName FreqHistoryWebServiceSoap = new QName("http://tempuri.org/", "FreqHistoryWebServiceSoap");
    static {
        URL url = null;
        try {
            url = new URL("http://192.168.21.2:8000/FreqHistoryWebService.asmx?wsdl");
        } catch (MalformedURLException e) {
            java.util.logging.Logger.getLogger(FreqHistoryWebService.class.getName())
                .log(java.util.logging.Level.INFO, 
                     "Can not initialize the default wsdl from {0}", "http://192.168.21.2:8000/FreqHistoryWebService.asmx?wsdl");
        }
        WSDL_LOCATION = url;
    }

    public FreqHistoryWebService(URL wsdlLocation) {
        super(wsdlLocation, SERVICE);
    }

    public FreqHistoryWebService(URL wsdlLocation, QName serviceName) {
        super(wsdlLocation, serviceName);
    }

    public FreqHistoryWebService() {
        super(WSDL_LOCATION, SERVICE);
    }
    
    public FreqHistoryWebService(WebServiceFeature ... features) {
        super(WSDL_LOCATION, SERVICE, features);
    }

    public FreqHistoryWebService(URL wsdlLocation, WebServiceFeature ... features) {
        super(wsdlLocation, SERVICE, features);
    }

    public FreqHistoryWebService(URL wsdlLocation, QName serviceName, WebServiceFeature ... features) {
        super(wsdlLocation, serviceName, features);
    }    




    /**
     *
     * @return
     *     returns FreqHistoryWebServiceSoap
     */
    @WebEndpoint(name = "FreqHistoryWebServiceSoap12")
    public FreqHistoryWebServiceSoap getFreqHistoryWebServiceSoap12() {
        return super.getPort(FreqHistoryWebServiceSoap12, FreqHistoryWebServiceSoap.class);
    }

    /**
     * 
     * @param features
     *     A list of {@link javax.xml.ws.WebServiceFeature} to configure on the proxy.  Supported features not in the <code>features</code> parameter will have their default values.
     * @return
     *     returns FreqHistoryWebServiceSoap
     */
    @WebEndpoint(name = "FreqHistoryWebServiceSoap12")
    public FreqHistoryWebServiceSoap getFreqHistoryWebServiceSoap12(WebServiceFeature... features) {
        return super.getPort(FreqHistoryWebServiceSoap12, FreqHistoryWebServiceSoap.class, features);
    }


    /**
     *
     * @return
     *     returns FreqHistoryWebServiceSoap
     */
    @WebEndpoint(name = "FreqHistoryWebServiceSoap")
    public FreqHistoryWebServiceSoap getFreqHistoryWebServiceSoap() {
        return super.getPort(FreqHistoryWebServiceSoap, FreqHistoryWebServiceSoap.class);
    }

    /**
     * 
     * @param features
     *     A list of {@link javax.xml.ws.WebServiceFeature} to configure on the proxy.  Supported features not in the <code>features</code> parameter will have their default values.
     * @return
     *     returns FreqHistoryWebServiceSoap
     */
    @WebEndpoint(name = "FreqHistoryWebServiceSoap")
    public FreqHistoryWebServiceSoap getFreqHistoryWebServiceSoap(WebServiceFeature... features) {
        return super.getPort(FreqHistoryWebServiceSoap, FreqHistoryWebServiceSoap.class, features);
    }

}
