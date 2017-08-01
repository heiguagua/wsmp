
package com.sefon.ws.model.xsd;

import javax.xml.bind.annotation.XmlRegistry;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the com.sefon.ws.model.xsd package. 
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
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.sefon.ws.model.xsd
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link StationQuerySpecInfo }
     * 
     */
    public StationQuerySpecInfo createStationQuerySpecInfo() {
        return new StationQuerySpecInfo();
    }

    /**
     * Create an instance of {@link StationInfoPagedResult }
     * 
     */
    public StationInfoPagedResult createStationInfoPagedResult() {
        return new StationInfoPagedResult();
    }

    /**
     * Create an instance of {@link StationInfo }
     * 
     */
    public StationInfo createStationInfo() {
        return new StationInfo();
    }

    /**
     * Create an instance of {@link QueryFreqRangeInfo }
     * 
     */
    public QueryFreqRangeInfo createQueryFreqRangeInfo() {
        return new QueryFreqRangeInfo();
    }

    /**
     * Create an instance of {@link PaginationInfo }
     * 
     */
    public PaginationInfo createPaginationInfo() {
        return new PaginationInfo();
    }

}
