//
//package org.tempuri;
//
//import javax.xml.ws.Endpoint;
//
///**
// * This class was generated by Apache CXF 3.1.11
// * 2017-07-25T13:35:44.705+08:00
// * Generated source version: 3.1.11
// *
// */
//
//public class FreqWarningWebServiceSoap_FreqWarningWebServiceSoap_Server{
//
//    protected FreqWarningWebServiceSoap_FreqWarningWebServiceSoap_Server() throws java.lang.Exception {
//        System.out.println("Starting Server");
//        Object implementor = new FreqWarningWebServiceSoapImpl();
//        String address = "http://192.168.21.2:8000/FreqWarningWebService.asmx";
//        Endpoint.publish(address, implementor);
//    }
//
//    public static void main(String args[]) throws java.lang.Exception {
//        new FreqWarningWebServiceSoap_FreqWarningWebServiceSoap_Server();
//        System.out.println("Server ready...");
//
//        Thread.sleep(5 * 60 * 1000);
//        System.out.println("Server exiting");
//        System.exit(0);
//    }
//}
