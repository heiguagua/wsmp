
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
 * 2017-07-25T13:35:44.659+08:00
 * Generated source version: 3.1.11
 * 
 */

@javax.jws.WebService(
                      serviceName = "FreqWarningWebService",
                      portName = "FreqWarningWebServiceSoap12",
                      targetNamespace = "http://tempuri.org/",
                      wsdlLocation = "http://192.168.21.2:8000/FreqWarningWebService.asmx?wsdl",
                      endpointInterface = "org.tempuri.FreqWarningWebServiceSoap")
                      
public class FreqWarningWebServiceSoap12Impl implements FreqWarningWebServiceSoap {

    private static final Logger LOG = Logger.getLogger(FreqWarningWebServiceSoap12Impl.class.getName());

    /* (non-Javadoc)
     * @see org.tempuri.FreqWarningWebServiceSoap#updateStat(org.tempuri.FreqWarningStatDTO dto)*
     */
    public org.tempuri.FreqWarningOperationResponse updateStat(org.tempuri.FreqWarningStatDTO dto) { 
        LOG.info("Executing operation updateStat");
        System.out.println(dto);
        try {
            org.tempuri.FreqWarningOperationResponse _return = null;
            return _return;
        } catch (java.lang.Exception ex) {
            ex.printStackTrace();
            throw new RuntimeException(ex);
        }
    }

    /* (non-Javadoc)
     * @see org.tempuri.FreqWarningWebServiceSoap#queryStat(org.tempuri.FreqWarningStatQueryRequest request)*
     */
    public org.tempuri.FreqWarningStatQueryResponse queryStat(org.tempuri.FreqWarningStatQueryRequest request) { 
        LOG.info("Executing operation queryStat");
        System.out.println(request);
        try {
            org.tempuri.FreqWarningStatQueryResponse _return = null;
            return _return;
        } catch (java.lang.Exception ex) {
            ex.printStackTrace();
            throw new RuntimeException(ex);
        }
    }

    /* (non-Javadoc)
     * @see org.tempuri.FreqWarningWebServiceSoap#query(org.tempuri.FreqWarningQueryRequest request)*
     */
    public org.tempuri.FreqWarningQueryResponse query(org.tempuri.FreqWarningQueryRequest request) { 
        LOG.info("Executing operation query");
        System.out.println(request);
        try {
            org.tempuri.FreqWarningQueryResponse _return = null;
            return _return;
        } catch (java.lang.Exception ex) {
            ex.printStackTrace();
            throw new RuntimeException(ex);
        }
    }

    /* (non-Javadoc)
     * @see org.tempuri.FreqWarningWebServiceSoap#removeStat(org.tempuri.ArrayOfString statIds)*
     */
    public org.tempuri.FreqWarningOperationResponse removeStat(org.tempuri.ArrayOfString statIds) { 
        LOG.info("Executing operation removeStat");
        System.out.println(statIds);
        try {
            org.tempuri.FreqWarningOperationResponse _return = null;
            return _return;
        } catch (java.lang.Exception ex) {
            ex.printStackTrace();
            throw new RuntimeException(ex);
        }
    }

    /* (non-Javadoc)
     * @see org.tempuri.FreqWarningWebServiceSoap#remove(org.tempuri.ArrayOfString freqIds)*
     */
    public org.tempuri.FreqWarningOperationResponse remove(org.tempuri.ArrayOfString freqIds) { 
        LOG.info("Executing operation remove");
        System.out.println(freqIds);
        try {
            org.tempuri.FreqWarningOperationResponse _return = null;
            return _return;
        } catch (java.lang.Exception ex) {
            ex.printStackTrace();
            throw new RuntimeException(ex);
        }
    }

    /* (non-Javadoc)
     * @see org.tempuri.FreqWarningWebServiceSoap#updateKeyword(java.lang.String freqId, java.lang.String keyword)*
     */
    public org.tempuri.FreqWarningOperationResponse updateKeyword(java.lang.String freqId, java.lang.String keyword) { 
        LOG.info("Executing operation updateKeyword");
        System.out.println(freqId);
        System.out.println(keyword);
        try {
            org.tempuri.FreqWarningOperationResponse _return = null;
            return _return;
        } catch (java.lang.Exception ex) {
            ex.printStackTrace();
            throw new RuntimeException(ex);
        }
    }

    /* (non-Javadoc)
     * @see org.tempuri.FreqWarningWebServiceSoap#helloWorld()*
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
     * @see org.tempuri.FreqWarningWebServiceSoap#insertStat(org.tempuri.FreqWarningStatDTO dto)*
     */
    public org.tempuri.FreqWarningOperationResponse insertStat(org.tempuri.FreqWarningStatDTO dto) { 
        LOG.info("Executing operation insertStat");
        System.out.println(dto);
        try {
            org.tempuri.FreqWarningOperationResponse _return = null;
            return _return;
        } catch (java.lang.Exception ex) {
            ex.printStackTrace();
            throw new RuntimeException(ex);
        }
    }

    /* (non-Javadoc)
     * @see org.tempuri.FreqWarningWebServiceSoap#insert(org.tempuri.FreqWarningDTO dto)*
     */
    public org.tempuri.FreqWarningOperationResponse insert(org.tempuri.FreqWarningDTO dto) { 
        LOG.info("Executing operation insert");
        System.out.println(dto);
        try {
            org.tempuri.FreqWarningOperationResponse _return = null;
            return _return;
        } catch (java.lang.Exception ex) {
            ex.printStackTrace();
            throw new RuntimeException(ex);
        }
    }

    /* (non-Javadoc)
     * @see org.tempuri.FreqWarningWebServiceSoap#update(org.tempuri.FreqWarningDTO dto)*
     */
    public org.tempuri.FreqWarningOperationResponse update(org.tempuri.FreqWarningDTO dto) { 
        LOG.info("Executing operation update");
        System.out.println(dto);
        try {
            org.tempuri.FreqWarningOperationResponse _return = null;
            return _return;
        } catch (java.lang.Exception ex) {
            ex.printStackTrace();
            throw new RuntimeException(ex);
        }
    }

}
