
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
import javax.xml.ws.Action;
import javax.xml.ws.RequestWrapper;
import javax.xml.ws.ResponseWrapper;

/**
 * This class was generated by Apache CXF 3.1.12
 * 2017-09-21T15:11:31.021+08:00
 * Generated source version: 3.1.12
 * 
 */
public final class IImportFreqRangeManageService_NetTcpBindingIImportFreqRangeManageService_Client {

    private static final QName SERVICE_NAME = new QName("http://tempuri.org/", "ImportFreqRangeManageService");

    private IImportFreqRangeManageService_NetTcpBindingIImportFreqRangeManageService_Client() {
    }

    public static void main(String args[]) throws java.lang.Exception {
        URL wsdlURL = ImportFreqRangeManageService.WSDL_LOCATION;
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
      
        ImportFreqRangeManageService ss = new ImportFreqRangeManageService(wsdlURL, SERVICE_NAME);
        IImportFreqRangeManageService port = ss.getNetTcpBindingIImportFreqRangeManageService();  
        
        {
        System.out.println("Invoking queryImportantMonitorFreqRange...");
        java.lang.String _queryImportantMonitorFreqRange__return = port.queryImportantMonitorFreqRange();
        System.out.println("queryImportantMonitorFreqRange.result=" + _queryImportantMonitorFreqRange__return);


        }
        {
        System.out.println("Invoking removeById...");
        java.lang.String _removeById_id = "";
        java.lang.Boolean _removeById__return = port.removeById(_removeById_id);
        System.out.println("removeById.result=" + _removeById__return);


        }
        {
        System.out.println("Invoking addImportantMonitorFreq...");
        java.lang.String _addImportantMonitorFreq_jsonStr = "";
        java.lang.Boolean _addImportantMonitorFreq__return = port.addImportantMonitorFreq(_addImportantMonitorFreq_jsonStr);
        System.out.println("addImportantMonitorFreq.result=" + _addImportantMonitorFreq__return);


        }
        {
        System.out.println("Invoking removeAllTask...");
        com.microsoft.schemas._2003._10.serialization.arrays.ArrayOfguid _removeAllTask_ids = null;
        java.lang.Boolean _removeAllTask__return = port.removeAllTask(_removeAllTask_ids);
        System.out.println("removeAllTask.result=" + _removeAllTask__return);


        }
        {
        System.out.println("Invoking updateImportantMonitorFreqRange...");
        java.lang.String _updateImportantMonitorFreqRange_jsonStr = "";
        java.lang.String _updateImportantMonitorFreqRange__return = port.updateImportantMonitorFreqRange(_updateImportantMonitorFreqRange_jsonStr);
        System.out.println("updateImportantMonitorFreqRange.result=" + _updateImportantMonitorFreqRange__return);


        }
        {
        System.out.println("Invoking findFreqByWarn...");
        java.lang.String _findFreqByWarn_warnId = "";
        java.lang.String _findFreqByWarn__return = port.findFreqByWarn(_findFreqByWarn_warnId);
        System.out.println("findFreqByWarn.result=" + _findFreqByWarn__return);


        }
        {
        System.out.println("Invoking createOrUpdate...");
        java.lang.String _createOrUpdate_jsonTaskParam = "";
        java.lang.String _createOrUpdate__return = port.createOrUpdate(_createOrUpdate_jsonTaskParam);
        System.out.println("createOrUpdate.result=" + _createOrUpdate__return);


        }
        {
        System.out.println("Invoking findAllFreq...");
        java.lang.String _findAllFreq__return = port.findAllFreq();
        System.out.println("findAllFreq.result=" + _findAllFreq__return);


        }
        {
        System.out.println("Invoking findByFreq...");
        java.lang.String _findByFreq_warnId = "";
        java.lang.Double _findByFreq_centerFreq = null;
        java.lang.String _findByFreq__return = port.findByFreq(_findByFreq_warnId, _findByFreq_centerFreq);
        System.out.println("findByFreq.result=" + _findByFreq__return);


        }
        {
        System.out.println("Invoking findAllFreqRange...");
        java.lang.String _findAllFreqRange__return = port.findAllFreqRange();
        System.out.println("findAllFreqRange.result=" + _findAllFreqRange__return);


        }

        System.exit(0);
    }

}
