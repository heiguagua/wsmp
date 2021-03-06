package org.tempuri;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.ws.RequestWrapper;
import javax.xml.ws.ResponseWrapper;

/**
 * This class was generated by Apache CXF 3.1.12
 * 2017-09-29T11:39:33.538+08:00
 * Generated source version: 3.1.12
 * 
 */
@WebService(targetNamespace = "http://tempuri.org/", name = "RadioSignalWebServiceSoap")
@XmlSeeAlso({ObjectFactory.class})
public interface RadioSignalWebServiceSoap {

    @WebMethod(operationName = "UpdateRadioSignalForRequest", action = "http://tempuri.org/UpdateRadioSignalForRequest")
    @RequestWrapper(localName = "UpdateRadioSignalForRequest", targetNamespace = "http://tempuri.org/", className = "org.tempuri.UpdateRadioSignalForRequest")
    @ResponseWrapper(localName = "UpdateRadioSignalForRequestResponse", targetNamespace = "http://tempuri.org/", className = "org.tempuri.UpdateRadioSignalForRequestResponse")
    @WebResult(name = "UpdateRadioSignalForRequestResult", targetNamespace = "http://tempuri.org/")
    public org.tempuri.RadioSignalOperationReponse updateRadioSignalForRequest(
        @WebParam(name = "request", targetNamespace = "http://tempuri.org/")
        org.tempuri.RadioSignalUpdateRequest request
    );

    @WebMethod(operationName = "RemoveAbnormalHistory", action = "http://tempuri.org/RemoveAbnormalHistory")
    @RequestWrapper(localName = "RemoveAbnormalHistory", targetNamespace = "http://tempuri.org/", className = "org.tempuri.RemoveAbnormalHistory")
    @ResponseWrapper(localName = "RemoveAbnormalHistoryResponse", targetNamespace = "http://tempuri.org/", className = "org.tempuri.RemoveAbnormalHistoryResponse")
    @WebResult(name = "RemoveAbnormalHistoryResult", targetNamespace = "http://tempuri.org/")
    public org.tempuri.RadioSignalOperationReponse removeAbnormalHistory(
        @WebParam(name = "ID", targetNamespace = "http://tempuri.org/")
        java.lang.String id
    );

    @WebMethod(operationName = "QueryRadioSignalSubClassified", action = "http://tempuri.org/QueryRadioSignalSubClassified")
    @RequestWrapper(localName = "QueryRadioSignalSubClassified", targetNamespace = "http://tempuri.org/", className = "org.tempuri.QueryRadioSignalSubClassified")
    @ResponseWrapper(localName = "QueryRadioSignalSubClassifiedResponse", targetNamespace = "http://tempuri.org/", className = "org.tempuri.QueryRadioSignalSubClassifiedResponse")
    @WebResult(name = "QueryRadioSignalSubClassifiedResult", targetNamespace = "http://tempuri.org/")
    public org.tempuri.RadioSignalSubClassifiedQueryResponse queryRadioSignalSubClassified(
        @WebParam(name = "request", targetNamespace = "http://tempuri.org/")
        org.tempuri.RadioSignalSubClassifiedQueryRequest request
    );

    @WebMethod(operationName = "UpdateRadioSignal", action = "http://tempuri.org/UpdateRadioSignal")
    @RequestWrapper(localName = "UpdateRadioSignal", targetNamespace = "http://tempuri.org/", className = "org.tempuri.UpdateRadioSignal")
    @ResponseWrapper(localName = "UpdateRadioSignalResponse", targetNamespace = "http://tempuri.org/", className = "org.tempuri.UpdateRadioSignalResponse")
    @WebResult(name = "UpdateRadioSignalResult", targetNamespace = "http://tempuri.org/")
    public org.tempuri.RadioSignalOperationReponse updateRadioSignal(
        @WebParam(name = "info", targetNamespace = "http://tempuri.org/")
        org.tempuri.RadioSignalDTO info
    );

    @WebMethod(operationName = "RemoveRadioSignal", action = "http://tempuri.org/RemoveRadioSignal")
    @RequestWrapper(localName = "RemoveRadioSignal", targetNamespace = "http://tempuri.org/", className = "org.tempuri.RemoveRadioSignal")
    @ResponseWrapper(localName = "RemoveRadioSignalResponse", targetNamespace = "http://tempuri.org/", className = "org.tempuri.RemoveRadioSignalResponse")
    @WebResult(name = "RemoveRadioSignalResult", targetNamespace = "http://tempuri.org/")
    public org.tempuri.RadioSignalOperationReponse removeRadioSignal(
        @WebParam(name = "freqIDs", targetNamespace = "http://tempuri.org/")
        org.tempuri.ArrayOfString freqIDs
    );

    @WebMethod(operationName = "InsertAbnormalHistory", action = "http://tempuri.org/InsertAbnormalHistory")
    @RequestWrapper(localName = "InsertAbnormalHistory", targetNamespace = "http://tempuri.org/", className = "org.tempuri.InsertAbnormalHistory")
    @ResponseWrapper(localName = "InsertAbnormalHistoryResponse", targetNamespace = "http://tempuri.org/", className = "org.tempuri.InsertAbnormalHistoryResponse")
    @WebResult(name = "InsertAbnormalHistoryResult", targetNamespace = "http://tempuri.org/")
    public org.tempuri.RadioSignalOperationReponse insertAbnormalHistory(
        @WebParam(name = "request", targetNamespace = "http://tempuri.org/")
        org.tempuri.RadioSignalAbnormalHistoryDTO request
    );

    @WebMethod(operationName = "QueryRadioSignal", action = "http://tempuri.org/QueryRadioSignal")
    @RequestWrapper(localName = "QueryRadioSignal", targetNamespace = "http://tempuri.org/", className = "org.tempuri.QueryRadioSignal")
    @ResponseWrapper(localName = "QueryRadioSignalResponse", targetNamespace = "http://tempuri.org/", className = "org.tempuri.QueryRadioSignalResponse")
    @WebResult(name = "QueryRadioSignalResult", targetNamespace = "http://tempuri.org/")
    public org.tempuri.RadioSignalQueryResponse queryRadioSignal(
        @WebParam(name = "request", targetNamespace = "http://tempuri.org/")
        org.tempuri.RadioSignalQueryRequest request
    );

    @WebMethod(operationName = "QuerySignalFromWarningID", action = "http://tempuri.org/QuerySignalFromWarningID")
    @RequestWrapper(localName = "QuerySignalFromWarningID", targetNamespace = "http://tempuri.org/", className = "org.tempuri.QuerySignalFromWarningID")
    @ResponseWrapper(localName = "QuerySignalFromWarningIDResponse", targetNamespace = "http://tempuri.org/", className = "org.tempuri.QuerySignalFromWarningIDResponse")
    @WebResult(name = "QuerySignalFromWarningIDResult", targetNamespace = "http://tempuri.org/")
    public org.tempuri.RadioSignalFromWarningIDQueryResponse querySignalFromWarningID(
        @WebParam(name = "warningID", targetNamespace = "http://tempuri.org/")
        java.lang.String warningID
    );

    @WebMethod(operationName = "UpdateAbnormalHistory", action = "http://tempuri.org/UpdateAbnormalHistory")
    @RequestWrapper(localName = "UpdateAbnormalHistory", targetNamespace = "http://tempuri.org/", className = "org.tempuri.UpdateAbnormalHistory")
    @ResponseWrapper(localName = "UpdateAbnormalHistoryResponse", targetNamespace = "http://tempuri.org/", className = "org.tempuri.UpdateAbnormalHistoryResponse")
    @WebResult(name = "UpdateAbnormalHistoryResult", targetNamespace = "http://tempuri.org/")
    public org.tempuri.RadioSignalOperationReponse updateAbnormalHistory(
        @WebParam(name = "info", targetNamespace = "http://tempuri.org/")
        org.tempuri.RadioSignalAbnormalHistoryDTO info
    );

    @WebMethod(operationName = "InsertRadioSignal", action = "http://tempuri.org/InsertRadioSignal")
    @RequestWrapper(localName = "InsertRadioSignal", targetNamespace = "http://tempuri.org/", className = "org.tempuri.InsertRadioSignal")
    @ResponseWrapper(localName = "InsertRadioSignalResponse", targetNamespace = "http://tempuri.org/", className = "org.tempuri.InsertRadioSignalResponse")
    @WebResult(name = "InsertRadioSignalResult", targetNamespace = "http://tempuri.org/")
    public org.tempuri.RadioSignalOperationReponse insertRadioSignal(
        @WebParam(name = "Info", targetNamespace = "http://tempuri.org/")
        org.tempuri.RadioSignalDTO info
    );

    @WebMethod(operationName = "QueryAbnormalHistory", action = "http://tempuri.org/QueryAbnormalHistory")
    @RequestWrapper(localName = "QueryAbnormalHistory", targetNamespace = "http://tempuri.org/", className = "org.tempuri.QueryAbnormalHistory")
    @ResponseWrapper(localName = "QueryAbnormalHistoryResponse", targetNamespace = "http://tempuri.org/", className = "org.tempuri.QueryAbnormalHistoryResponse")
    @WebResult(name = "QueryAbnormalHistoryResult", targetNamespace = "http://tempuri.org/")
    public org.tempuri.AbnormalHistoryQueryResponse queryAbnormalHistory(
        @WebParam(name = "request", targetNamespace = "http://tempuri.org/")
        org.tempuri.AbnormalHistoryRequest request
    );

    @WebMethod(operationName = "HelloWorld", action = "http://tempuri.org/HelloWorld")
    @RequestWrapper(localName = "HelloWorld", targetNamespace = "http://tempuri.org/", className = "org.tempuri.HelloWorld")
    @ResponseWrapper(localName = "HelloWorldResponse", targetNamespace = "http://tempuri.org/", className = "org.tempuri.HelloWorldResponse")
    @WebResult(name = "HelloWorldResult", targetNamespace = "http://tempuri.org/")
    public java.lang.String helloWorld();

    @WebMethod(operationName = "QueryRadioSignalClassified", action = "http://tempuri.org/QueryRadioSignalClassified")
    @RequestWrapper(localName = "QueryRadioSignalClassified", targetNamespace = "http://tempuri.org/", className = "org.tempuri.QueryRadioSignalClassified")
    @ResponseWrapper(localName = "QueryRadioSignalClassifiedResponse", targetNamespace = "http://tempuri.org/", className = "org.tempuri.QueryRadioSignalClassifiedResponse")
    @WebResult(name = "QueryRadioSignalClassifiedResult", targetNamespace = "http://tempuri.org/")
    public org.tempuri.RadioSignalClassifiedQueryResponse queryRadioSignalClassified(
        @WebParam(name = "request", targetNamespace = "http://tempuri.org/")
        org.tempuri.RadioSignalClassifiedQueryRequest request
    );
}
