
package org.tempuri;

/**
 * Please modify this class to meet your needs
 * This class is not complete
 */

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;

import javax.xml.namespace.QName;

/**
 * This class was generated by Apache CXF 3.1.11
 * 2017-07-14T14:25:17.924+08:00
 * Generated source version: 3.1.11
 * 
 */
public final class RadioStationWebServiceSoap_RadioStationWebServiceSoap_Client {

    private static final QName SERVICE_NAME = new QName("http://tempuri.org/", "RadioStationWebService");

    private RadioStationWebServiceSoap_RadioStationWebServiceSoap_Client() {
    }

    public static void main(String args[]) throws java.lang.Exception {
        URL wsdlURL = RadioStationWebService.WSDL_LOCATION;
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
      
        RadioStationWebService ss = new RadioStationWebService(wsdlURL, SERVICE_NAME);
        RadioStationWebServiceSoap port = ss.getRadioStationWebServiceSoap();  
        
        {
        System.out.println("Invoking rStatQuerySignalByFreqKey...");
        org.tempuri.RStatQuerySignalByFreqKeyRequest _rStatQuerySignalByFreqKey_key = null;
        org.tempuri.RStatQuerySignalByFreqKeyResponse2 _rStatQuerySignalByFreqKey__return = port.rStatQuerySignalByFreqKey(_rStatQuerySignalByFreqKey_key);
        System.out.println("rStatQuerySignalByFreqKey.result=" + _rStatQuerySignalByFreqKey__return);


        }
        {
        System.out.println("Invoking rStatInsertUpdateFreq...");
        java.lang.String _rStatInsertUpdateFreq_station = "";
        org.tempuri.RadioFreqDTO _rStatInsertUpdateFreq_freq = null;
        java.lang.String _rStatInsertUpdateFreq__return = port.rStatInsertUpdateFreq(_rStatInsertUpdateFreq_station, _rStatInsertUpdateFreq_freq);
        System.out.println("rStatInsertUpdateFreq.result=" + _rStatInsertUpdateFreq__return);


        }
        {
        System.out.println("Invoking rStatQueryAreas...");
        org.tempuri.ArrayOfString _rStatQueryAreas__return = port.rStatQueryAreas();
        System.out.println("rStatQueryAreas.result=" + _rStatQueryAreas__return);


        }
        {
        System.out.println("Invoking rStatInsertUpdateStation...");
        org.tempuri.RadioStationDTO _rStatInsertUpdateStation_station = null;
        java.lang.String _rStatInsertUpdateStation__return = port.rStatInsertUpdateStation(_rStatInsertUpdateStation_station);
        System.out.println("rStatInsertUpdateStation.result=" + _rStatInsertUpdateStation__return);


        }
        {
        System.out.println("Invoking rStatDeleteFreq...");
        org.tempuri.ArrayOfString _rStatDeleteFreq_freqs = null;
        port.rStatDeleteFreq(_rStatDeleteFreq_freqs);


        }
        {
        System.out.println("Invoking rStatQueryStatName...");
        org.tempuri.RStatQueryStatNameRequest _rStatQueryStatName_request = null;
        org.tempuri.ArrayOfString _rStatQueryStatName__return = port.rStatQueryStatName(_rStatQueryStatName_request);
        System.out.println("rStatQueryStatName.result=" + _rStatQueryStatName__return);


        }
        {
        System.out.println("Invoking helloWorld...");
        java.lang.String _helloWorld__return = port.helloWorld();
        System.out.println("helloWorld.result=" + _helloWorld__return);


        }
        {
        System.out.println("Invoking rStatQuerySignals...");
        org.tempuri.RStatQuerySignalsRequest _rStatQuerySignals_request = null;
        org.tempuri.RStatQuerySignalsResponse2 _rStatQuerySignals__return = port.rStatQuerySignals(_rStatQuerySignals_request);
        System.out.println("rStatQuerySignals.result=" + _rStatQuerySignals__return);


        }
        {
        System.out.println("Invoking rStatDeleteStation...");
        org.tempuri.ArrayOfString _rStatDeleteStation_stations = null;
        port.rStatDeleteStation(_rStatDeleteStation_stations);


        }

        System.exit(0);
    }

}
