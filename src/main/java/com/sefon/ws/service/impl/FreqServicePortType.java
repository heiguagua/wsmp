package com.sefon.ws.service.impl;

import javax.jws.Oneway;
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
 * 2017-07-27T14:33:55.679+08:00
 * Generated source version: 3.1.12
 * 
 */
@WebService(targetNamespace = "http://impl.service.ws.sefon.com", name = "FreqServicePortType")
@XmlSeeAlso({ObjectFactory.class, com.sefon.ws.model.xsd.ObjectFactory.class, com.sefon.ws.model.electric.level.xsd.ObjectFactory.class})
public interface FreqServicePortType {

    @WebMethod(operationName = "QueryFreqRangeDateOcc", action = "urn:QueryFreqRangeDateOcc")
    @Action(input = "urn:QueryFreqRangeDateOcc", output = "urn:QueryFreqRangeDateOccResponse")
    @RequestWrapper(localName = "QueryFreqRangeDateOcc", targetNamespace = "http://impl.service.ws.sefon.com", className = "com.sefon.ws.service.impl.QueryFreqRangeDateOcc")
    @ResponseWrapper(localName = "QueryFreqRangeDateOccResponse", targetNamespace = "http://impl.service.ws.sefon.com", className = "com.sefon.ws.service.impl.QueryFreqRangeDateOccResponse")
    @WebResult(name = "return", targetNamespace = "http://impl.service.ws.sefon.com")
    public java.util.List<com.sefon.ws.model.electric.level.xsd.DateOccLevelDataInfo> queryFreqRangeDateOcc(
        @WebParam(name = "spec", targetNamespace = "http://impl.service.ws.sefon.com")
        com.sefon.ws.model.electric.level.xsd.FreqRangeQuerySpec spec
    );

    @WebMethod(operationName = "QueryContinuousFreqncyRange", action = "urn:QueryContinuousFreqncyRange")
    @Action(input = "urn:QueryContinuousFreqncyRange", output = "urn:QueryContinuousFreqncyRangeResponse")
    @RequestWrapper(localName = "QueryContinuousFreqncyRange", targetNamespace = "http://impl.service.ws.sefon.com", className = "com.sefon.ws.service.impl.QueryContinuousFreqncyRange")
    @ResponseWrapper(localName = "QueryContinuousFreqncyRangeResponse", targetNamespace = "http://impl.service.ws.sefon.com", className = "com.sefon.ws.service.impl.QueryContinuousFreqncyRangeResponse")
    @WebResult(name = "return", targetNamespace = "http://impl.service.ws.sefon.com")
    public java.util.List<com.sefon.ws.model.freq.xsd.FrequencyRangeInfo> queryContinuousFreqncyRange(
        @WebParam(name = "spec", targetNamespace = "http://impl.service.ws.sefon.com")
        com.sefon.ws.model.freq.xsd.FrequencyRangeQuerySpec spec
    );

    @WebMethod(action = "urn:main")
    @Action(input = "urn:main")
    @Oneway
    @RequestWrapper(localName = "main", targetNamespace = "http://impl.service.ws.sefon.com", className = "com.sefon.ws.service.impl.Main")
    public void main(
        @WebParam(name = "args", targetNamespace = "http://impl.service.ws.sefon.com")
        java.util.List<java.lang.String> args
    );

    @WebMethod(operationName = "QueryFrequencyRange", action = "urn:QueryFrequencyRange")
    @Action(input = "urn:QueryFrequencyRange", output = "urn:QueryFrequencyRangeResponse")
    @RequestWrapper(localName = "QueryFrequencyRange", targetNamespace = "http://impl.service.ws.sefon.com", className = "com.sefon.ws.service.impl.QueryFrequencyRange")
    @ResponseWrapper(localName = "QueryFrequencyRangeResponse", targetNamespace = "http://impl.service.ws.sefon.com", className = "com.sefon.ws.service.impl.QueryFrequencyRangeResponse")
    @WebResult(name = "return", targetNamespace = "http://impl.service.ws.sefon.com")
    public java.util.List<com.sefon.ws.model.freq.xsd.FrequencyRangeInfo> queryFrequencyRange(
        @WebParam(name = "spec", targetNamespace = "http://impl.service.ws.sefon.com")
        com.sefon.ws.model.freq.xsd.FrequencyRangeQuerySpec spec
    );
}
