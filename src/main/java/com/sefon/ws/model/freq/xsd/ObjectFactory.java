
package com.sefon.ws.model.freq.xsd;

import java.math.BigDecimal;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.datatype.XMLGregorianCalendar;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the com.sefon.ws.model.freq.xsd package. 
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

    private final static QName _FreqSelfInfoChannelBandwidth_QNAME = new QName("http://freq.model.ws.sefon.com/xsd", "channelBandwidth");
    private final static QName _FreqSelfInfoDeadTime_QNAME = new QName("http://freq.model.ws.sefon.com/xsd", "deadTime");
    private final static QName _FreqSelfInfoEffectiveTime_QNAME = new QName("http://freq.model.ws.sefon.com/xsd", "effectiveTime");
    private final static QName _FreqSelfInfoFreqDesc_QNAME = new QName("http://freq.model.ws.sefon.com/xsd", "freqDesc");
    private final static QName _FreqSelfInfoFreqMax_QNAME = new QName("http://freq.model.ws.sefon.com/xsd", "freqMax");
    private final static QName _FreqSelfInfoFreqMin_QNAME = new QName("http://freq.model.ws.sefon.com/xsd", "freqMin");
    private final static QName _FreqSelfInfoFreqSection_QNAME = new QName("http://freq.model.ws.sefon.com/xsd", "freqSection");
    private final static QName _FreqSelfInfoId_QNAME = new QName("http://freq.model.ws.sefon.com/xsd", "id");
    private final static QName _FreqSelfInfoIsManuallyAdd_QNAME = new QName("http://freq.model.ws.sefon.com/xsd", "isManuallyAdd");
    private final static QName _FreqSelfInfoIsSeries_QNAME = new QName("http://freq.model.ws.sefon.com/xsd", "isSeries");
    private final static QName _FreqSelfInfoParentId_QNAME = new QName("http://freq.model.ws.sefon.com/xsd", "parentId");
    private final static QName _FreqSelfInfoServiceName_QNAME = new QName("http://freq.model.ws.sefon.com/xsd", "serviceName");
    private final static QName _FreqSelfInfoSt_QNAME = new QName("http://freq.model.ws.sefon.com/xsd", "st");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.sefon.ws.model.freq.xsd
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link FreqSelfInfo }
     * 
     */
    public FreqSelfInfo createFreqSelfInfo() {
        return new FreqSelfInfo();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://freq.model.ws.sefon.com/xsd", name = "channelBandwidth", scope = FreqSelfInfo.class)
    public JAXBElement<String> createFreqSelfInfoChannelBandwidth(String value) {
        return new JAXBElement<String>(_FreqSelfInfoChannelBandwidth_QNAME, String.class, FreqSelfInfo.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link XMLGregorianCalendar }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://freq.model.ws.sefon.com/xsd", name = "deadTime", scope = FreqSelfInfo.class)
    public JAXBElement<XMLGregorianCalendar> createFreqSelfInfoDeadTime(XMLGregorianCalendar value) {
        return new JAXBElement<XMLGregorianCalendar>(_FreqSelfInfoDeadTime_QNAME, XMLGregorianCalendar.class, FreqSelfInfo.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link XMLGregorianCalendar }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://freq.model.ws.sefon.com/xsd", name = "effectiveTime", scope = FreqSelfInfo.class)
    public JAXBElement<XMLGregorianCalendar> createFreqSelfInfoEffectiveTime(XMLGregorianCalendar value) {
        return new JAXBElement<XMLGregorianCalendar>(_FreqSelfInfoEffectiveTime_QNAME, XMLGregorianCalendar.class, FreqSelfInfo.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://freq.model.ws.sefon.com/xsd", name = "freqDesc", scope = FreqSelfInfo.class)
    public JAXBElement<String> createFreqSelfInfoFreqDesc(String value) {
        return new JAXBElement<String>(_FreqSelfInfoFreqDesc_QNAME, String.class, FreqSelfInfo.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link BigDecimal }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://freq.model.ws.sefon.com/xsd", name = "freqMax", scope = FreqSelfInfo.class)
    public JAXBElement<BigDecimal> createFreqSelfInfoFreqMax(BigDecimal value) {
        return new JAXBElement<BigDecimal>(_FreqSelfInfoFreqMax_QNAME, BigDecimal.class, FreqSelfInfo.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link BigDecimal }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://freq.model.ws.sefon.com/xsd", name = "freqMin", scope = FreqSelfInfo.class)
    public JAXBElement<BigDecimal> createFreqSelfInfoFreqMin(BigDecimal value) {
        return new JAXBElement<BigDecimal>(_FreqSelfInfoFreqMin_QNAME, BigDecimal.class, FreqSelfInfo.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://freq.model.ws.sefon.com/xsd", name = "freqSection", scope = FreqSelfInfo.class)
    public JAXBElement<String> createFreqSelfInfoFreqSection(String value) {
        return new JAXBElement<String>(_FreqSelfInfoFreqSection_QNAME, String.class, FreqSelfInfo.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://freq.model.ws.sefon.com/xsd", name = "id", scope = FreqSelfInfo.class)
    public JAXBElement<String> createFreqSelfInfoId(String value) {
        return new JAXBElement<String>(_FreqSelfInfoId_QNAME, String.class, FreqSelfInfo.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://freq.model.ws.sefon.com/xsd", name = "isManuallyAdd", scope = FreqSelfInfo.class)
    public JAXBElement<String> createFreqSelfInfoIsManuallyAdd(String value) {
        return new JAXBElement<String>(_FreqSelfInfoIsManuallyAdd_QNAME, String.class, FreqSelfInfo.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://freq.model.ws.sefon.com/xsd", name = "isSeries", scope = FreqSelfInfo.class)
    public JAXBElement<String> createFreqSelfInfoIsSeries(String value) {
        return new JAXBElement<String>(_FreqSelfInfoIsSeries_QNAME, String.class, FreqSelfInfo.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://freq.model.ws.sefon.com/xsd", name = "parentId", scope = FreqSelfInfo.class)
    public JAXBElement<String> createFreqSelfInfoParentId(String value) {
        return new JAXBElement<String>(_FreqSelfInfoParentId_QNAME, String.class, FreqSelfInfo.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://freq.model.ws.sefon.com/xsd", name = "serviceName", scope = FreqSelfInfo.class)
    public JAXBElement<String> createFreqSelfInfoServiceName(String value) {
        return new JAXBElement<String>(_FreqSelfInfoServiceName_QNAME, String.class, FreqSelfInfo.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://freq.model.ws.sefon.com/xsd", name = "st", scope = FreqSelfInfo.class)
    public JAXBElement<String> createFreqSelfInfoSt(String value) {
        return new JAXBElement<String>(_FreqSelfInfoSt_QNAME, String.class, FreqSelfInfo.class, value);
    }

}
