
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
 * This class was generated by Apache CXF 3.1.12
 * 2017-08-23T18:45:33.057+08:00
 * Generated source version: 3.1.12
 * 
 */
public final class FreqWarningWebServiceSoap_FreqWarningWebServiceSoap12_Client {

    private static final QName SERVICE_NAME = new QName("http://tempuri.org/", "FreqWarningWebService");

    private FreqWarningWebServiceSoap_FreqWarningWebServiceSoap12_Client() {
    }

    public static void main(String args[]) throws java.lang.Exception {
        URL wsdlURL = FreqWarningWebService.WSDL_LOCATION;
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
      
        FreqWarningWebService ss = new FreqWarningWebService(wsdlURL, SERVICE_NAME);
        FreqWarningWebServiceSoap port = ss.getFreqWarningWebServiceSoap12();  
        
        {
        System.out.println("Invoking queryStat...");
        org.tempuri.FreqWarningStatQueryRequest _queryStat_request = null;
        org.tempuri.FreqWarningStatQueryResponse _queryStat__return = port.queryStat(_queryStat_request);
        System.out.println("queryStat.result=" + _queryStat__return);


        }
        {
        System.out.println("Invoking removeStat...");
        org.tempuri.ArrayOfString _removeStat_statIds = null;
        org.tempuri.FreqWarningOperationResponse _removeStat__return = port.removeStat(_removeStat_statIds);
        System.out.println("removeStat.result=" + _removeStat__return);


        }
        {
        System.out.println("Invoking updateStatus...");
        java.lang.String _updateStatus_freqId = "";
        int _updateStatus_status = 0;
        org.tempuri.FreqWarningOperationResponse _updateStatus__return = port.updateStatus(_updateStatus_freqId, _updateStatus_status);
        System.out.println("updateStatus.result=" + _updateStatus__return);


        }
        {
        System.out.println("Invoking updateStat...");
        org.tempuri.FreqWarningStatDTO _updateStat_dto = null;
        org.tempuri.FreqWarningOperationResponse _updateStat__return = port.updateStat(_updateStat_dto);
        System.out.println("updateStat.result=" + _updateStat__return);


        }
        {
        System.out.println("Invoking query...");
        org.tempuri.FreqWarningQueryRequest _query_request = null;
        org.tempuri.FreqWarningQueryResponse _query__return = port.query(_query_request);
        System.out.println("query.result=" + _query__return);


        }
        {
        System.out.println("Invoking remove...");
        org.tempuri.ArrayOfString _remove_freqIds = null;
        org.tempuri.FreqWarningOperationResponse _remove__return = port.remove(_remove_freqIds);
        System.out.println("remove.result=" + _remove__return);


        }
        {
        System.out.println("Invoking updateKeyword...");
        java.lang.String _updateKeyword_freqId = "";
        java.lang.String _updateKeyword_keyword = "";
        org.tempuri.FreqWarningOperationResponse _updateKeyword__return = port.updateKeyword(_updateKeyword_freqId, _updateKeyword_keyword);
        System.out.println("updateKeyword.result=" + _updateKeyword__return);


        }
        {
        System.out.println("Invoking helloWorld...");
        java.lang.String _helloWorld__return = port.helloWorld();
        System.out.println("helloWorld.result=" + _helloWorld__return);


        }
        {
        System.out.println("Invoking updateSelected...");
        java.lang.String _updateSelected_freqId = "";
        java.lang.Integer _updateSelected_status = null;
        javax.xml.datatype.XMLGregorianCalendar _updateSelected_lastTimeDate = null;
        java.lang.String _updateSelected_des = "";
        org.tempuri.FreqWarningOperationResponse _updateSelected__return = port.updateSelected(_updateSelected_freqId, _updateSelected_status, _updateSelected_lastTimeDate, _updateSelected_des);
        System.out.println("updateSelected.result=" + _updateSelected__return);


        }
        {
        System.out.println("Invoking insertStat...");
        org.tempuri.FreqWarningStatDTO _insertStat_dto = null;
        org.tempuri.FreqWarningOperationResponse _insertStat__return = port.insertStat(_insertStat_dto);
        System.out.println("insertStat.result=" + _insertStat__return);


        }
        {
        System.out.println("Invoking insert...");
        org.tempuri.FreqWarningDTO _insert_dto = null;
        org.tempuri.FreqWarningOperationResponse _insert__return = port.insert(_insert_dto);
        System.out.println("insert.result=" + _insert__return);


        }
        {
        System.out.println("Invoking update...");
        org.tempuri.FreqWarningDTO _update_dto = null;
        org.tempuri.FreqWarningOperationResponse _update__return = port.update(_update_dto);
        System.out.println("update.result=" + _update__return);


        }

        System.exit(0);
    }

}
