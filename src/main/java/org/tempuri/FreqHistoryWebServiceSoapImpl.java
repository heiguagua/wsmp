
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
 * 2017-07-14T13:59:22.404+08:00
 * Generated source version: 3.1.11
 * 
 */

@javax.jws.WebService(
                      serviceName = "FreqHistoryWebService",
                      portName = "FreqHistoryWebServiceSoap",
                      targetNamespace = "http://tempuri.org/",
                      wsdlLocation = "http://192.168.21.2:8000/FreqHistoryWebService.asmx?wsdl",
                      endpointInterface = "org.tempuri.FreqHistoryWebServiceSoap")
                      
public class FreqHistoryWebServiceSoapImpl implements FreqHistoryWebServiceSoap {

    private static final Logger LOG = Logger.getLogger(FreqHistoryWebServiceSoapImpl.class.getName());

    /* (non-Javadoc)
     * @see org.tempuri.FreqHistoryWebServiceSoap#insertFreq(org.tempuri.FreqHistoryDTO dto)*
     */
    public org.tempuri.FreqHistoryOperationResponse insertFreq(org.tempuri.FreqHistoryDTO dto) { 
        LOG.info("Executing operation insertFreq");
        System.out.println(dto);
        try {
            org.tempuri.FreqHistoryOperationResponse _return = null;
            return _return;
        } catch (java.lang.Exception ex) {
            ex.printStackTrace();
            throw new RuntimeException(ex);
        }
    }

    /* (non-Javadoc)
     * @see org.tempuri.FreqHistoryWebServiceSoap#insertFreqs(org.tempuri.ArrayOfFreqHistoryDTO dtos)*
     */
    public org.tempuri.FreqHistoryOperationResponse insertFreqs(org.tempuri.ArrayOfFreqHistoryDTO dtos) { 
        LOG.info("Executing operation insertFreqs");
        System.out.println(dtos);
        try {
            org.tempuri.FreqHistoryOperationResponse _return = null;
            return _return;
        } catch (java.lang.Exception ex) {
            ex.printStackTrace();
            throw new RuntimeException(ex);
        }
    }

    /* (non-Javadoc)
     * @see org.tempuri.FreqHistoryWebServiceSoap#updateByFreqId(org.tempuri.FreqHistoryDTO dto)*
     */
    public org.tempuri.FreqHistoryOperationResponse updateByFreqId(org.tempuri.FreqHistoryDTO dto) { 
        LOG.info("Executing operation updateByFreqId");
        System.out.println(dto);
        try {
            org.tempuri.FreqHistoryOperationResponse _return = null;
            return _return;
        } catch (java.lang.Exception ex) {
            ex.printStackTrace();
            throw new RuntimeException(ex);
        }
    }

    /* (non-Javadoc)
     * @see org.tempuri.FreqHistoryWebServiceSoap#removeByFreqId(java.lang.String freqId)*
     */
    public org.tempuri.FreqHistoryOperationResponse removeByFreqId(java.lang.String freqId) { 
        LOG.info("Executing operation removeByFreqId");
        System.out.println(freqId);
        try {
            org.tempuri.FreqHistoryOperationResponse _return = null;
            return _return;
        } catch (java.lang.Exception ex) {
            ex.printStackTrace();
            throw new RuntimeException(ex);
        }
    }

    /* (non-Javadoc)
     * @see org.tempuri.FreqHistoryWebServiceSoap#removeByFreqIds(org.tempuri.ArrayOfString freqIds)*
     */
    public org.tempuri.FreqHistoryOperationResponse removeByFreqIds(org.tempuri.ArrayOfString freqIds) { 
        LOG.info("Executing operation removeByFreqIds");
        System.out.println(freqIds);
        try {
            org.tempuri.FreqHistoryOperationResponse _return = null;
            return _return;
        } catch (java.lang.Exception ex) {
            ex.printStackTrace();
            throw new RuntimeException(ex);
        }
    }

    /* (non-Javadoc)
     * @see org.tempuri.FreqHistoryWebServiceSoap#query(org.tempuri.FreqHistoryQueryRequest request)*
     */
    public org.tempuri.FreqHistoryQueryResponse query(org.tempuri.FreqHistoryQueryRequest request) { 
        LOG.info("Executing operation query");
        System.out.println(request);
        try {
            org.tempuri.FreqHistoryQueryResponse _return = null;
            return _return;
        } catch (java.lang.Exception ex) {
            ex.printStackTrace();
            throw new RuntimeException(ex);
        }
    }

    /* (non-Javadoc)
     * @see org.tempuri.FreqHistoryWebServiceSoap#helloWorld()*
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
     * @see org.tempuri.FreqHistoryWebServiceSoap#updateByFreqIds(org.tempuri.ArrayOfFreqHistoryDTO dtos)*
     */
    public org.tempuri.FreqHistoryOperationResponse updateByFreqIds(org.tempuri.ArrayOfFreqHistoryDTO dtos) { 
        LOG.info("Executing operation updateByFreqIds");
        System.out.println(dtos);
        try {
            org.tempuri.FreqHistoryOperationResponse _return = null;
            return _return;
        } catch (java.lang.Exception ex) {
            ex.printStackTrace();
            throw new RuntimeException(ex);
        }
    }

}
