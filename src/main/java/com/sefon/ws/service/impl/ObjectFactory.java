
package com.sefon.ws.service.impl;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


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

    private final static QName _QuerySelfFreqInfoByIDId_QNAME = new QName("http://impl.service.ws.sefon.com", "id");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.sefon.ws.service.impl
     * 
     */
    public ObjectFactory() {
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

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://impl.service.ws.sefon.com", name = "id", scope = QuerySelfFreqInfoByID.class)
    public JAXBElement<String> createQuerySelfFreqInfoByIDId(String value) {
        return new JAXBElement<String>(_QuerySelfFreqInfoByIDId_QNAME, String.class, QuerySelfFreqInfoByID.class, value);
    }

}
