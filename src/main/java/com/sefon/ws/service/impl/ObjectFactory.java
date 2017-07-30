
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
     * Create an instance of {@link QueryStationWithPagination }
     * 
     */
    public QueryStationWithPagination createQueryStationWithPagination() {
        return new QueryStationWithPagination();
    }

    /**
     * Create an instance of {@link QueryStationWithPaginationResponse }
     * 
     */
    public QueryStationWithPaginationResponse createQueryStationWithPaginationResponse() {
        return new QueryStationWithPaginationResponse();
    }

    /**
     * Create an instance of {@link GetStationById }
     * 
     */
    public GetStationById createGetStationById() {
        return new GetStationById();
    }

    /**
     * Create an instance of {@link GetStationByIdResponse }
     * 
     */
    public GetStationByIdResponse createGetStationByIdResponse() {
        return new GetStationByIdResponse();
    }

    /**
     * Create an instance of {@link QueryStation }
     * 
     */
    public QueryStation createQueryStation() {
        return new QueryStation();
    }

    /**
     * Create an instance of {@link QueryStationResponse }
     * 
     */
    public QueryStationResponse createQueryStationResponse() {
        return new QueryStationResponse();
    }

}
