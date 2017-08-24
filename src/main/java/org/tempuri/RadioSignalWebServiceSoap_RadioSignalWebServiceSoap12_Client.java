
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
 * 2017-08-24T11:33:55.581+08:00
 * Generated source version: 3.1.11
 * 
 */
public final class RadioSignalWebServiceSoap_RadioSignalWebServiceSoap12_Client {

    private static final QName SERVICE_NAME = new QName("http://tempuri.org/", "RadioSignalWebService");

    private RadioSignalWebServiceSoap_RadioSignalWebServiceSoap12_Client() {
    }

    public static void main(String args[]) throws java.lang.Exception {
        URL wsdlURL = RadioSignalWebService.WSDL_LOCATION;
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
      
        RadioSignalWebService ss = new RadioSignalWebService(wsdlURL, SERVICE_NAME);
        RadioSignalWebServiceSoap port = ss.getRadioSignalWebServiceSoap12();  
        
        {
        System.out.println("Invoking insertAbnormalHistory...");
        org.tempuri.RadioSignalAbnormalHistoryDTO _insertAbnormalHistory_request = null;
        org.tempuri.RadioSignalOperationReponse _insertAbnormalHistory__return = port.insertAbnormalHistory(_insertAbnormalHistory_request);
        System.out.println("insertAbnormalHistory.result=" + _insertAbnormalHistory__return);


        }
        {
        System.out.println("Invoking queryRadioSignal...");
        org.tempuri.RadioSignalQueryRequest _queryRadioSignal_request = null;
        org.tempuri.RadioSignalQueryResponse _queryRadioSignal__return = port.queryRadioSignal(_queryRadioSignal_request);
        System.out.println("queryRadioSignal.result=" + _queryRadioSignal__return);


        }
        {
        System.out.println("Invoking updateAbnormalHistory...");
        org.tempuri.RadioSignalAbnormalHistoryDTO _updateAbnormalHistory_info = null;
        org.tempuri.RadioSignalOperationReponse _updateAbnormalHistory__return = port.updateAbnormalHistory(_updateAbnormalHistory_info);
        System.out.println("updateAbnormalHistory.result=" + _updateAbnormalHistory__return);


        }
        {
        System.out.println("Invoking insertRadioSignal...");
        org.tempuri.RadioSignalDTO _insertRadioSignal_info = null;
        org.tempuri.RadioSignalOperationReponse _insertRadioSignal__return = port.insertRadioSignal(_insertRadioSignal_info);
        System.out.println("insertRadioSignal.result=" + _insertRadioSignal__return);


        }
        {
        System.out.println("Invoking updateRadioSignalForRequest...");
        org.tempuri.RadioSignalUpdateRequest _updateRadioSignalForRequest_request = null;
        org.tempuri.RadioSignalOperationReponse _updateRadioSignalForRequest__return = port.updateRadioSignalForRequest(_updateRadioSignalForRequest_request);
        System.out.println("updateRadioSignalForRequest.result=" + _updateRadioSignalForRequest__return);


        }
        {
        System.out.println("Invoking removeAbnormalHistory...");
        java.lang.String _removeAbnormalHistory_id = "";
        org.tempuri.RadioSignalOperationReponse _removeAbnormalHistory__return = port.removeAbnormalHistory(_removeAbnormalHistory_id);
        System.out.println("removeAbnormalHistory.result=" + _removeAbnormalHistory__return);


        }
        {
        System.out.println("Invoking helloWorld...");
        java.lang.String _helloWorld__return = port.helloWorld();
        System.out.println("helloWorld.result=" + _helloWorld__return);


        }
        {
        System.out.println("Invoking queryAbnormalHistory...");
        org.tempuri.AbnormalHistoryRequest _queryAbnormalHistory_request = null;
        org.tempuri.AbnormalHistoryQueryResponse _queryAbnormalHistory__return = port.queryAbnormalHistory(_queryAbnormalHistory_request);
        System.out.println("queryAbnormalHistory.result=" + _queryAbnormalHistory__return);


        }
        {
        System.out.println("Invoking updateRadioSignal...");
        org.tempuri.RadioSignalDTO _updateRadioSignal_info = null;
        org.tempuri.RadioSignalOperationReponse _updateRadioSignal__return = port.updateRadioSignal(_updateRadioSignal_info);
        System.out.println("updateRadioSignal.result=" + _updateRadioSignal__return);


        }
        {
        System.out.println("Invoking removeRadioSignal...");
        org.tempuri.ArrayOfString _removeRadioSignal_freqIDs = null;
        org.tempuri.RadioSignalOperationReponse _removeRadioSignal__return = port.removeRadioSignal(_removeRadioSignal_freqIDs);
        System.out.println("removeRadioSignal.result=" + _removeRadioSignal__return);


        }
        {
        System.out.println("Invoking queryRadioSignalClassified...");
        org.tempuri.RadioSignalClassifiedQueryRequest _queryRadioSignalClassified_request = null;
        org.tempuri.RadioSignalClassifiedQueryResponse _queryRadioSignalClassified__return = port.queryRadioSignalClassified(_queryRadioSignalClassified_request);
        System.out.println("queryRadioSignalClassified.result=" + _queryRadioSignalClassified__return);


        }

        System.exit(0);
    }

}
