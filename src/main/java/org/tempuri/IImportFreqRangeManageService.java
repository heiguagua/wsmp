package org.tempuri;

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
 * 2017-08-05T13:14:31.852+08:00
 * Generated source version: 3.1.12
 * 
 */
@WebService(targetNamespace = "http://tempuri.org/", name = "IImportFreqRangeManageService")
@XmlSeeAlso({ObjectFactory.class})
public interface IImportFreqRangeManageService {

    @WebMethod(operationName = "QueryImportantMonitorFreqRange", action = "http://tempuri.org/IImportFreqRangeManageService/QueryImportantMonitorFreqRange")
    @Action(input = "http://tempuri.org/IImportFreqRangeManageService/QueryImportantMonitorFreqRange", output = "http://tempuri.org/IImportFreqRangeManageService/QueryImportantMonitorFreqRangeResponse")
    @RequestWrapper(localName = "QueryImportantMonitorFreqRange", targetNamespace = "http://tempuri.org/", className = "org.tempuri.QueryImportantMonitorFreqRange")
    @ResponseWrapper(localName = "QueryImportantMonitorFreqRangeResponse", targetNamespace = "http://tempuri.org/", className = "org.tempuri.QueryImportantMonitorFreqRangeResponse")
    @WebResult(name = "QueryImportantMonitorFreqRangeResult", targetNamespace = "http://tempuri.org/")
    public java.lang.String queryImportantMonitorFreqRange();

    @WebMethod(operationName = "RemoveById", action = "http://tempuri.org/IImportFreqRangeManageService/RemoveById")
    @Action(input = "http://tempuri.org/IImportFreqRangeManageService/RemoveById", output = "http://tempuri.org/IImportFreqRangeManageService/RemoveByIdResponse")
    @RequestWrapper(localName = "RemoveById", targetNamespace = "http://tempuri.org/", className = "org.tempuri.RemoveById")
    @ResponseWrapper(localName = "RemoveByIdResponse", targetNamespace = "http://tempuri.org/", className = "org.tempuri.RemoveByIdResponse")
    @WebResult(name = "RemoveByIdResult", targetNamespace = "http://tempuri.org/")
    public java.lang.Boolean removeById(
        @WebParam(name = "id", targetNamespace = "http://tempuri.org/")
        java.lang.String id
    );

    @WebMethod(operationName = "AddImportantMonitorFreq", action = "http://tempuri.org/IImportFreqRangeManageService/AddImportantMonitorFreq")
    @Action(input = "http://tempuri.org/IImportFreqRangeManageService/AddImportantMonitorFreq", output = "http://tempuri.org/IImportFreqRangeManageService/AddImportantMonitorFreqResponse")
    @RequestWrapper(localName = "AddImportantMonitorFreq", targetNamespace = "http://tempuri.org/", className = "org.tempuri.AddImportantMonitorFreq")
    @ResponseWrapper(localName = "AddImportantMonitorFreqResponse", targetNamespace = "http://tempuri.org/", className = "org.tempuri.AddImportantMonitorFreqResponse")
    @WebResult(name = "AddImportantMonitorFreqResult", targetNamespace = "http://tempuri.org/")
    public java.lang.Boolean addImportantMonitorFreq(
        @WebParam(name = "jsonStr", targetNamespace = "http://tempuri.org/")
        java.lang.String jsonStr
    );

    @WebMethod(operationName = "UpdateImportantMonitorFreqRange", action = "http://tempuri.org/IImportFreqRangeManageService/UpdateImportantMonitorFreqRange")
    @Action(input = "http://tempuri.org/IImportFreqRangeManageService/UpdateImportantMonitorFreqRange", output = "http://tempuri.org/IImportFreqRangeManageService/UpdateImportantMonitorFreqRangeResponse")
    @RequestWrapper(localName = "UpdateImportantMonitorFreqRange", targetNamespace = "http://tempuri.org/", className = "org.tempuri.UpdateImportantMonitorFreqRange")
    @ResponseWrapper(localName = "UpdateImportantMonitorFreqRangeResponse", targetNamespace = "http://tempuri.org/", className = "org.tempuri.UpdateImportantMonitorFreqRangeResponse")
    @WebResult(name = "UpdateImportantMonitorFreqRangeResult", targetNamespace = "http://tempuri.org/")
    public java.lang.String updateImportantMonitorFreqRange(
        @WebParam(name = "jsonStr", targetNamespace = "http://tempuri.org/")
        java.lang.String jsonStr
    );

    @WebMethod(operationName = "FindFreqByWarn", action = "http://tempuri.org/IImportFreqRangeManageService/FindFreqByWarn")
    @Action(input = "http://tempuri.org/IImportFreqRangeManageService/FindFreqByWarn", output = "http://tempuri.org/IImportFreqRangeManageService/FindFreqByWarnResponse")
    @RequestWrapper(localName = "FindFreqByWarn", targetNamespace = "http://tempuri.org/", className = "org.tempuri.FindFreqByWarn")
    @ResponseWrapper(localName = "FindFreqByWarnResponse", targetNamespace = "http://tempuri.org/", className = "org.tempuri.FindFreqByWarnResponse")
    @WebResult(name = "FindFreqByWarnResult", targetNamespace = "http://tempuri.org/")
    public java.lang.String findFreqByWarn(
        @WebParam(name = "warnId", targetNamespace = "http://tempuri.org/")
        java.lang.String warnId
    );

    @WebMethod(operationName = "CreateOrUpdate", action = "http://tempuri.org/IImportFreqRangeManageService/CreateOrUpdate")
    @Action(input = "http://tempuri.org/IImportFreqRangeManageService/CreateOrUpdate", output = "http://tempuri.org/IImportFreqRangeManageService/CreateOrUpdateResponse")
    @RequestWrapper(localName = "CreateOrUpdate", targetNamespace = "http://tempuri.org/", className = "org.tempuri.CreateOrUpdate")
    @ResponseWrapper(localName = "CreateOrUpdateResponse", targetNamespace = "http://tempuri.org/", className = "org.tempuri.CreateOrUpdateResponse")
    @WebResult(name = "CreateOrUpdateResult", targetNamespace = "http://tempuri.org/")
    public java.lang.String createOrUpdate(
        @WebParam(name = "jsonTaskParam", targetNamespace = "http://tempuri.org/")
        java.lang.String jsonTaskParam
    );

    @WebMethod(operationName = "FindAllFreq", action = "http://tempuri.org/IImportFreqRangeManageService/FindAllFreq")
    @Action(input = "http://tempuri.org/IImportFreqRangeManageService/FindAllFreq", output = "http://tempuri.org/IImportFreqRangeManageService/FindAllFreqResponse")
    @RequestWrapper(localName = "FindAllFreq", targetNamespace = "http://tempuri.org/", className = "org.tempuri.FindAllFreq")
    @ResponseWrapper(localName = "FindAllFreqResponse", targetNamespace = "http://tempuri.org/", className = "org.tempuri.FindAllFreqResponse")
    @WebResult(name = "FindAllFreqResult", targetNamespace = "http://tempuri.org/")
    public java.lang.String findAllFreq();

    @WebMethod(operationName = "FindAllFreqRange", action = "http://tempuri.org/IImportFreqRangeManageService/FindAllFreqRange")
    @Action(input = "http://tempuri.org/IImportFreqRangeManageService/FindAllFreqRange", output = "http://tempuri.org/IImportFreqRangeManageService/FindAllFreqRangeResponse")
    @RequestWrapper(localName = "FindAllFreqRange", targetNamespace = "http://tempuri.org/", className = "org.tempuri.FindAllFreqRange")
    @ResponseWrapper(localName = "FindAllFreqRangeResponse", targetNamespace = "http://tempuri.org/", className = "org.tempuri.FindAllFreqRangeResponse")
    @WebResult(name = "FindAllFreqRangeResult", targetNamespace = "http://tempuri.org/")
    public java.lang.String findAllFreqRange();
}