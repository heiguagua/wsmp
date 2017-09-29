
package org.tempuri;

import java.math.BigInteger;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>FreqWarningStandardQueryRequest complex type的 Java 类。
 * 
 * <p>以下模式片段指定包含在此类中的预期内容。
 * 
 * <pre>
 * &lt;complexType name="FreqWarningStandardQueryRequest"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="ID" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="BeginFreq" type="{http://www.w3.org/2001/XMLSchema}unsignedLong"/&gt;
 *         &lt;element name="EndFreq" type="{http://www.w3.org/2001/XMLSchema}unsignedLong"/&gt;
 *         &lt;element name="Status" type="{http://www.w3.org/2001/XMLSchema}int"/&gt;
 *         &lt;element name="IsInvalid" type="{http://www.w3.org/2001/XMLSchema}boolean"/&gt;
 *         &lt;element name="AreaCode" type="{http://www.w3.org/2001/XMLSchema}int"/&gt;
 *         &lt;element name="StartTime" type="{http://www.w3.org/2001/XMLSchema}dateTime"/&gt;
 *         &lt;element name="StopTime" type="{http://www.w3.org/2001/XMLSchema}dateTime"/&gt;
 *         &lt;element name="KeyWord" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="StartIndex" type="{http://www.w3.org/2001/XMLSchema}long"/&gt;
 *         &lt;element name="Count" type="{http://www.w3.org/2001/XMLSchema}long"/&gt;
 *         &lt;element name="StationIDs" type="{http://tempuri.org/}ArrayOfString" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "FreqWarningStandardQueryRequest", propOrder = {
    "id",
    "beginFreq",
    "endFreq",
    "status",
    "isInvalid",
    "areaCode",
    "startTime",
    "stopTime",
    "keyWord",
    "startIndex",
    "count",
    "stationIDs"
})
public class FreqWarningStandardQueryRequest {

    @XmlElement(name = "ID")
    protected String id;
    @XmlElement(name = "BeginFreq", required = true, nillable = true)
    @XmlSchemaType(name = "unsignedLong")
    protected BigInteger beginFreq;
    @XmlElement(name = "EndFreq", required = true, nillable = true)
    @XmlSchemaType(name = "unsignedLong")
    protected BigInteger endFreq;
    @XmlElement(name = "Status", required = true, type = Integer.class, nillable = true)
    protected Integer status;
    @XmlElement(name = "IsInvalid", required = true, type = Boolean.class, nillable = true)
    protected Boolean isInvalid;
    @XmlElement(name = "AreaCode", required = true, type = Integer.class, nillable = true)
    protected Integer areaCode;
    @XmlElement(name = "StartTime", required = true, nillable = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar startTime;
    @XmlElement(name = "StopTime", required = true, nillable = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar stopTime;
    @XmlElement(name = "KeyWord")
    protected String keyWord;
    @XmlElement(name = "StartIndex", required = true, type = Long.class, nillable = true)
    protected Long startIndex;
    @XmlElement(name = "Count", required = true, type = Long.class, nillable = true)
    protected Long count;
    @XmlElement(name = "StationIDs")
    protected ArrayOfString stationIDs;

    /**
     * 获取id属性的值。
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
     * 设置id属性的值。
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
     * 获取beginFreq属性的值。
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
     * 设置beginFreq属性的值。
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
     * 获取endFreq属性的值。
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
     * 设置endFreq属性的值。
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
     * 获取status属性的值。
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getStatus() {
        return status;
    }

    /**
     * 设置status属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setStatus(Integer value) {
        this.status = value;
    }

    /**
     * 获取isInvalid属性的值。
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isIsInvalid() {
        return isInvalid;
    }

    /**
     * 设置isInvalid属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setIsInvalid(Boolean value) {
        this.isInvalid = value;
    }

    /**
     * 获取areaCode属性的值。
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getAreaCode() {
        return areaCode;
    }

    /**
     * 设置areaCode属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setAreaCode(Integer value) {
        this.areaCode = value;
    }

    /**
     * 获取startTime属性的值。
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
     * 设置startTime属性的值。
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
     * 获取stopTime属性的值。
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
     * 设置stopTime属性的值。
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
     * 获取keyWord属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getKeyWord() {
        return keyWord;
    }

    /**
     * 设置keyWord属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setKeyWord(String value) {
        this.keyWord = value;
    }

    /**
     * 获取startIndex属性的值。
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
     * 设置startIndex属性的值。
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
     * 获取count属性的值。
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
     * 设置count属性的值。
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
     * 获取stationIDs属性的值。
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
     * 设置stationIDs属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfString }
     *     
     */
    public void setStationIDs(ArrayOfString value) {
        this.stationIDs = value;
    }

}
