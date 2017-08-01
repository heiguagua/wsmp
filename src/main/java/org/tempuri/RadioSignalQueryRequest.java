
package org.tempuri;

import java.math.BigInteger;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>RadioSignalQueryRequest complex type�� Java �ࡣ
 * 
 * <p>����ģʽƬ��ָ�������ڴ����е�Ԥ�����ݡ�
 * 
 * <pre>
 * &lt;complexType name="RadioSignalQueryRequest"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="ID" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="BeginFreq" type="{http://www.w3.org/2001/XMLSchema}unsignedLong"/&gt;
 *         &lt;element name="EndFreq" type="{http://www.w3.org/2001/XMLSchema}unsignedLong"/&gt;
 *         &lt;element name="TypeCodes" type="{http://tempuri.org/}ArrayOfSignalTypeDTO" minOccurs="0"/&gt;
 *         &lt;element name="AreaCodes" type="{http://tempuri.org/}ArrayOfInt" minOccurs="0"/&gt;
 *         &lt;element name="StationIDs" type="{http://tempuri.org/}ArrayOfString" minOccurs="0"/&gt;
 *         &lt;element name="StartTime" type="{http://www.w3.org/2001/XMLSchema}dateTime"/&gt;
 *         &lt;element name="StopTime" type="{http://www.w3.org/2001/XMLSchema}dateTime"/&gt;
 *         &lt;element name="StartIndex" type="{http://www.w3.org/2001/XMLSchema}long"/&gt;
 *         &lt;element name="Count" type="{http://www.w3.org/2001/XMLSchema}long"/&gt;
 *         &lt;element name="IsReturnRadioStation" type="{http://www.w3.org/2001/XMLSchema}boolean"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "RadioSignalQueryRequest", propOrder = {
    "id",
    "beginFreq",
    "endFreq",
    "typeCodes",
    "areaCodes",
    "stationIDs",
    "startTime",
    "stopTime",
    "startIndex",
    "count",
    "isReturnRadioStation"
})
public class RadioSignalQueryRequest {

    @XmlElement(name = "ID")
    protected String id;
    @XmlElement(name = "BeginFreq", required = true, nillable = true)
    @XmlSchemaType(name = "unsignedLong")
    protected BigInteger beginFreq;
    @XmlElement(name = "EndFreq", required = true, nillable = true)
    @XmlSchemaType(name = "unsignedLong")
    protected BigInteger endFreq;
    @XmlElement(name = "TypeCodes")
    protected ArrayOfSignalTypeDTO typeCodes;
    @XmlElement(name = "AreaCodes")
    protected ArrayOfInt areaCodes;
    @XmlElement(name = "StationIDs")
    protected ArrayOfString stationIDs;
    @XmlElement(name = "StartTime", required = true, nillable = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar startTime;
    @XmlElement(name = "StopTime", required = true, nillable = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar stopTime;
    @XmlElement(name = "StartIndex", required = true, type = Long.class, nillable = true)
    protected Long startIndex;
    @XmlElement(name = "Count", required = true, type = Long.class, nillable = true)
    protected Long count;
    @XmlElement(name = "IsReturnRadioStation")
    protected boolean isReturnRadioStation;

    /**
     * ��ȡid���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getID() {
        return id;
    }

    /**
     * ����id���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setID(String value) {
        this.id = value;
    }

    /**
     * ��ȡbeginFreq���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getBeginFreq() {
        return beginFreq;
    }

    /**
     * ����beginFreq���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setBeginFreq(BigInteger value) {
        this.beginFreq = value;
    }

    /**
     * ��ȡendFreq���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getEndFreq() {
        return endFreq;
    }

    /**
     * ����endFreq���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setEndFreq(BigInteger value) {
        this.endFreq = value;
    }

    /**
     * ��ȡtypeCodes���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfSignalTypeDTO }
     *     
     */
    public ArrayOfSignalTypeDTO getTypeCodes() {
        return typeCodes;
    }

    /**
     * ����typeCodes���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfSignalTypeDTO }
     *     
     */
    public void setTypeCodes(ArrayOfSignalTypeDTO value) {
        this.typeCodes = value;
    }

    /**
     * ��ȡareaCodes���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfInt }
     *     
     */
    public ArrayOfInt getAreaCodes() {
        return areaCodes;
    }

    /**
     * ����areaCodes���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfInt }
     *     
     */
    public void setAreaCodes(ArrayOfInt value) {
        this.areaCodes = value;
    }

    /**
     * ��ȡstationIDs���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfString }
     *     
     */
    public ArrayOfString getStationIDs() {
        return stationIDs;
    }

    /**
     * ����stationIDs���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfString }
     *     
     */
    public void setStationIDs(ArrayOfString value) {
        this.stationIDs = value;
    }

    /**
     * ��ȡstartTime���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getStartTime() {
        return startTime;
    }

    /**
     * ����startTime���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setStartTime(XMLGregorianCalendar value) {
        this.startTime = value;
    }

    /**
     * ��ȡstopTime���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getStopTime() {
        return stopTime;
    }

    /**
     * ����stopTime���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setStopTime(XMLGregorianCalendar value) {
        this.stopTime = value;
    }

    /**
     * ��ȡstartIndex���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getStartIndex() {
        return startIndex;
    }

    /**
     * ����startIndex���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setStartIndex(Long value) {
        this.startIndex = value;
    }

    /**
     * ��ȡcount���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getCount() {
        return count;
    }

    /**
     * ����count���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setCount(Long value) {
        this.count = value;
    }

    /**
     * ��ȡisReturnRadioStation���Ե�ֵ��
     * 
     */
    public boolean isIsReturnRadioStation() {
        return isReturnRadioStation;
    }

    /**
     * ����isReturnRadioStation���Ե�ֵ��
     * 
     */
    public void setIsReturnRadioStation(boolean value) {
        this.isReturnRadioStation = value;
    }

}
