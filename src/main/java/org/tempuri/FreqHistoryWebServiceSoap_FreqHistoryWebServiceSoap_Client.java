
package org.tempuri;

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
import javax.xml.ws.RequestWrapper;
import javax.xml.ws.ResponseWrapper;

/**
 * This class was generated by Apache CXF 3.1.11
 * 2017-07-14T13:59:22.373+08:00
 * Generated source version: 3.1.11
 * 
 */
public final class FreqHistoryWebServiceSoap_FreqHistoryWebServiceSoap_Client {

    private static final QName SERVICE_NAME = new QName("http://tempuri.org/", "FreqHistoryWebService");

    private FreqHistoryWebServiceSoap_FreqHistoryWebServiceSoap_Client() {
    }

    public static void main(String args[]) throws java.lang.Exception {
        URL wsdlURL = FreqHistoryWebService.WSDL_LOCATION;
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
      
        FreqHistoryWebService ss = new FreqHistoryWebService(wsdlURL, SERVICE_NAME);
        FreqHistoryWebServiceSoap port = ss.getFreqHistoryWebServiceSoap();  
        
        {
        System.out.println("Invoking insertFreq...");
        org.tempuri.FreqHistoryDTO _insertFreq_dto = null;
        org.tempuri.FreqHistoryOperationResponse _insertFreq__return = port.insertFreq(_insertFreq_dto);
        System.out.println("insertFreq.result=" + _insertFreq__return);


        }
        {
        System.out.println("Invoking insertFreqs...");
        org.tempuri.ArrayOfFreqHistoryDTO _insertFreqs_dtos = null;
        org.tempuri.FreqHistoryOperationResponse _insertFreqs__return = port.insertFreqs(_insertFreqs_dtos);
        System.out.println("insertFreqs.result=" + _insertFreqs__return);


        }
        {
        System.out.println("Invoking updateByFreqId...");
        org.tempuri.FreqHistoryDTO _updateByFreqId_dto = null;
        org.tempuri.FreqHistoryOperationResponse _updateByFreqId__return = port.updateByFreqId(_updateByFreqId_dto);
        System.out.println("updateByFreqId.result=" + _updateByFreqId__return);


        }
        {
        System.out.println("Invoking removeByFreqId...");
        java.lang.String _removeByFreqId_freqId = "";
        org.tempuri.FreqHistoryOperationResponse _removeByFreqId__return = port.removeByFreqId(_removeByFreqId_freqId);
        System.out.println("removeByFreqId.result=" + _removeByFreqId__return);


        }
        {
        System.out.println("Invoking removeByFreqIds...");
        org.tempuri.ArrayOfString _removeByFreqIds_freqIds = null;
        org.tempuri.FreqHistoryOperationResponse _removeByFreqIds__return = port.removeByFreqIds(_removeByFreqIds_freqIds);
        System.out.println("removeByFreqIds.result=" + _removeByFreqIds__return);


        }
        {
        System.out.println("Invoking query...");
        org.tempuri.FreqHistoryQueryRequest _query_request = null;
        org.tempuri.FreqHistoryQueryResponse _query__return = port.query(_query_request);
        System.out.println("query.result=" + _query__return);


        }
        {
        System.out.println("Invoking helloWorld...");
        java.lang.String _helloWorld__return = port.helloWorld();
        System.out.println("helloWorld.result=" + _helloWorld__return);


        }
        {
        System.out.println("Invoking updateByFreqIds...");
        org.tempuri.ArrayOfFreqHistoryDTO _updateByFreqIds_dtos = null;
        org.tempuri.FreqHistoryOperationResponse _updateByFreqIds__return = port.updateByFreqIds(_updateByFreqIds_dtos);
        System.out.println("updateByFreqIds.result=" + _updateByFreqIds__return);


        }

        System.exit(0);
    }

}
