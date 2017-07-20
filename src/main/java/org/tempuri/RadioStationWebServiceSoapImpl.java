
/**
 * Please modify this class to meet your needs
 * This class is not complete
 */

package org.tempuri;

import java.util.logging.Logger;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.ws.RequestWrapper;
import javax.xml.ws.ResponseWrapper;

/**
 * This class was generated by Apache CXF 3.1.11
 * 2017-07-14T14:25:17.971+08:00
 * Generated source version: 3.1.11
 * 
 */

@javax.jws.WebService(
                      serviceName = "RadioStationWebService",
                      portName = "RadioStationWebServiceSoap",
                      targetNamespace = "http://tempuri.org/",
                      wsdlLocation = "http://192.168.21.2:8000/RadioStationWebService.asmx?wsdl",
                      endpointInterface = "org.tempuri.RadioStationWebServiceSoap")
                      
public class RadioStationWebServiceSoapImpl implements RadioStationWebServiceSoap {

    private static final Logger LOG = Logger.getLogger(RadioStationWebServiceSoapImpl.class.getName());

    /* (non-Javadoc)
     * @see org.tempuri.RadioStationWebServiceSoap#rStatQuerySignalByFreqKey(org.tempuri.RStatQuerySignalByFreqKeyRequest key)*
     */
    public org.tempuri.RStatQuerySignalByFreqKeyResponse2 rStatQuerySignalByFreqKey(org.tempuri.RStatQuerySignalByFreqKeyRequest key) { 
        LOG.info("Executing operation rStatQuerySignalByFreqKey");
        System.out.println(key);
        try {
            org.tempuri.RStatQuerySignalByFreqKeyResponse2 _return = null;
            return _return;
        } catch (java.lang.Exception ex) {
            ex.printStackTrace();
            throw new RuntimeException(ex);
        }
    }

    /* (non-Javadoc)
     * @see org.tempuri.RadioStationWebServiceSoap#rStatInsertUpdateFreq(java.lang.String station, org.tempuri.RadioFreqDTO freq)*
     */
    public java.lang.String rStatInsertUpdateFreq(java.lang.String station, org.tempuri.RadioFreqDTO freq) { 
        LOG.info("Executing operation rStatInsertUpdateFreq");
        System.out.println(station);
        System.out.println(freq);
        try {
            java.lang.String _return = "";
            return _return;
        } catch (java.lang.Exception ex) {
            ex.printStackTrace();
            throw new RuntimeException(ex);
        }
    }

    /* (non-Javadoc)
     * @see org.tempuri.RadioStationWebServiceSoap#rStatQueryAreas()*
     */
    public org.tempuri.ArrayOfString rStatQueryAreas() { 
        LOG.info("Executing operation rStatQueryAreas");
        try {
            org.tempuri.ArrayOfString _return = null;
            return _return;
        } catch (java.lang.Exception ex) {
            ex.printStackTrace();
            throw new RuntimeException(ex);
        }
    }

    /* (non-Javadoc)
     * @see org.tempuri.RadioStationWebServiceSoap#rStatInsertUpdateStation(org.tempuri.RadioStationDTO station)*
     */
    public java.lang.String rStatInsertUpdateStation(org.tempuri.RadioStationDTO station) { 
        LOG.info("Executing operation rStatInsertUpdateStation");
        System.out.println(station);
        try {
            java.lang.String _return = "";
            return _return;
        } catch (java.lang.Exception ex) {
            ex.printStackTrace();
            throw new RuntimeException(ex);
        }
    }

    /* (non-Javadoc)
     * @see org.tempuri.RadioStationWebServiceSoap#rStatDeleteFreq(org.tempuri.ArrayOfString freqs)*
     */
    public void rStatDeleteFreq(org.tempuri.ArrayOfString freqs) { 
        LOG.info("Executing operation rStatDeleteFreq");
        System.out.println(freqs);
        try {
        } catch (java.lang.Exception ex) {
            ex.printStackTrace();
            throw new RuntimeException(ex);
        }
    }

    /* (non-Javadoc)
     * @see org.tempuri.RadioStationWebServiceSoap#rStatQueryStatName(org.tempuri.RStatQueryStatNameRequest request)*
     */
    public org.tempuri.ArrayOfString rStatQueryStatName(org.tempuri.RStatQueryStatNameRequest request) { 
        LOG.info("Executing operation rStatQueryStatName");
        System.out.println(request);
        try {
            org.tempuri.ArrayOfString _return = null;
            return _return;
        } catch (java.lang.Exception ex) {
            ex.printStackTrace();
            throw new RuntimeException(ex);
        }
    }

    /* (non-Javadoc)
     * @see org.tempuri.RadioStationWebServiceSoap#helloWorld()*
     */
    public java.lang.String helloWorld() { 
        LOG.info("Executing operation helloWorld");
        try {
            java.lang.String _return = "";
            return _return;
        } catch (java.lang.Exception ex) {
            ex.printStackTrace();
            throw new RuntimeException(ex);
        }
    }

    /* (non-Javadoc)
     * @see org.tempuri.RadioStationWebServiceSoap#rStatQuerySignals(org.tempuri.RStatQuerySignalsRequest request)*
     */
    public org.tempuri.RStatQuerySignalsResponse2 rStatQuerySignals(org.tempuri.RStatQuerySignalsRequest request) { 
        LOG.info("Executing operation rStatQuerySignals");
        System.out.println(request);
        try {
            org.tempuri.RStatQuerySignalsResponse2 _return = null;
            return _return;
        } catch (java.lang.Exception ex) {
            ex.printStackTrace();
            throw new RuntimeException(ex);
        }
    }

    /* (non-Javadoc)
     * @see org.tempuri.RadioStationWebServiceSoap#rStatDeleteStation(org.tempuri.ArrayOfString stations)*
     */
    public void rStatDeleteStation(org.tempuri.ArrayOfString stations) { 
        LOG.info("Executing operation rStatDeleteStation");
        System.out.println(stations);
        try {
        } catch (java.lang.Exception ex) {
            ex.printStackTrace();
            throw new RuntimeException(ex);
        }
    }

}
