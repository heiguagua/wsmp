
package com.sefon.ws.service.impl;

import javax.xml.bind.annotation.XmlRegistry;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the com.sefon.ws.service.impl package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {


    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.sefon.ws.service.impl
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link QuerySelfFreqInfoByPID }
     * 
     */
    public QuerySelfFreqInfoByPID createQuerySelfFreqInfoByPID() {
        return new QuerySelfFreqInfoByPID();
    }

    /**
     * Create an instance of {@link QuerySelfFreqInfoByPIDResponse }
     * 
     */
    public QuerySelfFreqInfoByPIDResponse createQuerySelfFreqInfoByPIDResponse() {
        return new QuerySelfFreqInfoByPIDResponse();
    }

    /**
     * Create an instance of {@link QuerySelfFreqInfoByID }
     * 
     */
    public QuerySelfFreqInfoByID createQuerySelfFreqInfoByID() {
        return new QuerySelfFreqInfoByID();
    }

    /**
     * Create an instance of {@link QuerySelfFreqInfoByIDResponse }
     * 
     */
    public QuerySelfFreqInfoByIDResponse createQuerySelfFreqInfoByIDResponse() {
        return new QuerySelfFreqInfoByIDResponse();
    }

}
