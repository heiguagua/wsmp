
package org.tempuri;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>RadioSignalSubClassifiedQueryRequest complex type的 Java 类。
 * 
 * <p>以下模式片段指定包含在此类中的预期内容。
 * 
 * <pre>
 * &lt;complexType name="RadioSignalSubClassifiedQueryRequest"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="Type" type="{http://www.w3.org/2001/XMLSchema}int"/&gt;
 *         &lt;element name="FreqBandList" type="{http://tempuri.org/}ArrayOfFrequencyBand" minOccurs="0"/&gt;
 *         &lt;element name="StationNumber" type="{http://tempuri.org/}ArrayOfString" minOccurs="0"/&gt;
 *         &lt;element name="AreaCodes" type="{http://tempuri.org/}ArrayOfInt" minOccurs="0"/&gt;
 *         &lt;element name="StartTime" type="{http://www.w3.org/2001/XMLSchema}dateTime"/&gt;
 *         &lt;element name="StopTime" type="{http://www.w3.org/2001/XMLSchema}dateTime"/&gt;
 *         &lt;element name="IsInValid" type="{http://www.w3.org/2001/XMLSchema}boolean"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "RadioSignalSubClassifiedQueryRequest", propOrder = {
    "type",
    "freqBandList",
    "stationNumber",
    "areaCodes",
    "startTime",
    "stopTime",
    "isInValid"
})
public class RadioSignalSubClassifiedQueryRequest {

    @XmlElement(name = "Type")
    protected int type;
    @XmlElement(name = "FreqBandList")
    protected ArrayOfFrequencyBand freqBandList;
    @XmlElement(name = "StationNumber")
    protected ArrayOfString stationNumber;
    @XmlElement(name = "AreaCodes")
    protected ArrayOfInt areaCodes;
    @XmlElement(name = "StartTime", required = true, nillable = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar startTime;
    @XmlElement(name = "StopTime", required = true, nillable = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar stopTime;
    @XmlElement(name = "IsInValid")
    protected boolean isInValid;

    /**
     * 获取type属性的值。
     * 
     */
    public int getType() {
        return type;
    }

    /**
     * 设置type属性的值。
     * 
     */
    public void setType(int value) {
        this.type = value;
    }

    /**
     * 获取freqBandList属性的值。
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfFrequencyBand }
     *     
     */
    public ArrayOfFrequencyBand getFreqBandList() {
        return freqBandList;
    }

    /**
     * 设置freqBandList属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfFrequencyBand }
     *     
     */
    public void setFreqBandList(ArrayOfFrequencyBand value) {
        this.freqBandList = value;
    }

    /**
     * 获取stationNumber属性的值。
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfString }
     *     
     */
    public ArrayOfString getStationNumber() {
        return stationNumber;
    }

    /**
     * 设置stationNumber属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfString }
     *     
     */
    public void setStationNumber(ArrayOfString value) {
        this.stationNumber = value;
    }

    /**
     * 获取areaCodes属性的值。
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
     * 设置areaCodes属性的值。
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
     * 获取isInValid属性的值。
     * 
     */
    public boolean isIsInValid() {
        return isInValid;
    }

    /**
     * 设置isInValid属性的值。
     * 
     */
    public void setIsInValid(boolean value) {
        this.isInValid = value;
    }

}
