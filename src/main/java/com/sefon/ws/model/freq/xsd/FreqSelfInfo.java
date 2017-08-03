
package com.sefon.ws.model.freq.xsd;

import java.math.BigDecimal;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>FreqSelfInfo complex type�� Java �ࡣ
 * 
 * <p>����ģʽƬ��ָ�������ڴ����е�Ԥ�����ݡ�
 * 
 * <pre>
 * &lt;complexType name="FreqSelfInfo"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="channelBandwidth" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="deadTime" type="{http://www.w3.org/2001/XMLSchema}date" minOccurs="0"/&gt;
 *         &lt;element name="effectiveTime" type="{http://www.w3.org/2001/XMLSchema}date" minOccurs="0"/&gt;
 *         &lt;element name="freqDesc" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="freqMax" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/&gt;
 *         &lt;element name="freqMin" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/&gt;
 *         &lt;element name="freqSection" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="id" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="isManuallyAdd" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="isSeries" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="parentId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="serviceName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="st" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "FreqSelfInfo", propOrder = {
    "channelBandwidth",
    "deadTime",
    "effectiveTime",
    "freqDesc",
    "freqMax",
    "freqMin",
    "freqSection",
    "id",
    "isManuallyAdd",
    "isSeries",
    "parentId",
    "serviceName",
    "st"
})
public class FreqSelfInfo {

    @XmlElementRef(name = "channelBandwidth", namespace = "http://freq.model.ws.sefon.com/xsd", type = JAXBElement.class)
    protected JAXBElement<String> channelBandwidth;
    @XmlElementRef(name = "deadTime", namespace = "http://freq.model.ws.sefon.com/xsd", type = JAXBElement.class)
    protected JAXBElement<XMLGregorianCalendar> deadTime;
    @XmlElementRef(name = "effectiveTime", namespace = "http://freq.model.ws.sefon.com/xsd", type = JAXBElement.class)
    protected JAXBElement<XMLGregorianCalendar> effectiveTime;
    @XmlElementRef(name = "freqDesc", namespace = "http://freq.model.ws.sefon.com/xsd", type = JAXBElement.class)
    protected JAXBElement<String> freqDesc;
    @XmlElementRef(name = "freqMax", namespace = "http://freq.model.ws.sefon.com/xsd", type = JAXBElement.class)
    protected JAXBElement<BigDecimal> freqMax;
    @XmlElementRef(name = "freqMin", namespace = "http://freq.model.ws.sefon.com/xsd", type = JAXBElement.class)
    protected JAXBElement<BigDecimal> freqMin;
    @XmlElementRef(name = "freqSection", namespace = "http://freq.model.ws.sefon.com/xsd", type = JAXBElement.class)
    protected JAXBElement<String> freqSection;
    @XmlElementRef(name = "id", namespace = "http://freq.model.ws.sefon.com/xsd", type = JAXBElement.class)
    protected JAXBElement<String> id;
    @XmlElementRef(name = "isManuallyAdd", namespace = "http://freq.model.ws.sefon.com/xsd", type = JAXBElement.class)
    protected JAXBElement<String> isManuallyAdd;
    @XmlElementRef(name = "isSeries", namespace = "http://freq.model.ws.sefon.com/xsd", type = JAXBElement.class)
    protected JAXBElement<String> isSeries;
    @XmlElementRef(name = "parentId", namespace = "http://freq.model.ws.sefon.com/xsd", type = JAXBElement.class)
    protected JAXBElement<String> parentId;
    @XmlElementRef(name = "serviceName", namespace = "http://freq.model.ws.sefon.com/xsd", type = JAXBElement.class)
    protected JAXBElement<String> serviceName;
    @XmlElementRef(name = "st", namespace = "http://freq.model.ws.sefon.com/xsd", type = JAXBElement.class)
    protected JAXBElement<String> st;

    /**
     * ��ȡchannelBandwidth���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getChannelBandwidth() {
        return channelBandwidth;
    }

    /**
     * ����channelBandwidth���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setChannelBandwidth(JAXBElement<String> value) {
        this.channelBandwidth = value;
    }

    /**
     * ��ȡdeadTime���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link XMLGregorianCalendar }{@code >}
     *     
     */
    public JAXBElement<XMLGregorianCalendar> getDeadTime() {
        return deadTime;
    }

    /**
     * ����deadTime���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link XMLGregorianCalendar }{@code >}
     *     
     */
    public void setDeadTime(JAXBElement<XMLGregorianCalendar> value) {
        this.deadTime = value;
    }

    /**
     * ��ȡeffectiveTime���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link XMLGregorianCalendar }{@code >}
     *     
     */
    public JAXBElement<XMLGregorianCalendar> getEffectiveTime() {
        return effectiveTime;
    }

    /**
     * ����effectiveTime���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link XMLGregorianCalendar }{@code >}
     *     
     */
    public void setEffectiveTime(JAXBElement<XMLGregorianCalendar> value) {
        this.effectiveTime = value;
    }

    /**
     * ��ȡfreqDesc���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getFreqDesc() {
        return freqDesc;
    }

    /**
     * ����freqDesc���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setFreqDesc(JAXBElement<String> value) {
        this.freqDesc = value;
    }

    /**
     * ��ȡfreqMax���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link BigDecimal }{@code >}
     *     
     */
    public JAXBElement<BigDecimal> getFreqMax() {
        return freqMax;
    }

    /**
     * ����freqMax���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link BigDecimal }{@code >}
     *     
     */
    public void setFreqMax(JAXBElement<BigDecimal> value) {
        this.freqMax = value;
    }

    /**
     * ��ȡfreqMin���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link BigDecimal }{@code >}
     *     
     */
    public JAXBElement<BigDecimal> getFreqMin() {
        return freqMin;
    }

    /**
     * ����freqMin���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link BigDecimal }{@code >}
     *     
     */
    public void setFreqMin(JAXBElement<BigDecimal> value) {
        this.freqMin = value;
    }

    /**
     * ��ȡfreqSection���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getFreqSection() {
        return freqSection;
    }

    /**
     * ����freqSection���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setFreqSection(JAXBElement<String> value) {
        this.freqSection = value;
    }

    /**
     * ��ȡid���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getId() {
        return id;
    }

    /**
     * ����id���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setId(JAXBElement<String> value) {
        this.id = value;
    }

    /**
     * ��ȡisManuallyAdd���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getIsManuallyAdd() {
        return isManuallyAdd;
    }

    /**
     * ����isManuallyAdd���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setIsManuallyAdd(JAXBElement<String> value) {
        this.isManuallyAdd = value;
    }

    /**
     * ��ȡisSeries���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getIsSeries() {
        return isSeries;
    }

    /**
     * ����isSeries���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setIsSeries(JAXBElement<String> value) {
        this.isSeries = value;
    }

    /**
     * ��ȡparentId���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getParentId() {
        return parentId;
    }

    /**
     * ����parentId���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setParentId(JAXBElement<String> value) {
        this.parentId = value;
    }

    /**
     * ��ȡserviceName���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getServiceName() {
        return serviceName;
    }

    /**
     * ����serviceName���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setServiceName(JAXBElement<String> value) {
        this.serviceName = value;
    }

    /**
     * ��ȡst���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getSt() {
        return st;
    }

    /**
     * ����st���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setSt(JAXBElement<String> value) {
        this.st = value;
    }

}
