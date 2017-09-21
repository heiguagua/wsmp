
package org.tempuri;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>RadioSignalSubClassifiedQueryRequest complex type�� Java �ࡣ
 * 
 * <p>����ģʽƬ��ָ�������ڴ����е�Ԥ�����ݡ�
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
     * ��ȡtype���Ե�ֵ��
     * 
     */
    public int getType() {
        return type;
    }

    /**
     * ����type���Ե�ֵ��
     * 
     */
    public void setType(int value) {
        this.type = value;
    }

    /**
     * ��ȡfreqBandList���Ե�ֵ��
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
     * ����freqBandList���Ե�ֵ��
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
     * ��ȡstationNumber���Ե�ֵ��
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
     * ����stationNumber���Ե�ֵ��
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
     * ��ȡisInValid���Ե�ֵ��
     * 
     */
    public boolean isIsInValid() {
        return isInValid;
    }

    /**
     * ����isInValid���Ե�ֵ��
     * 
     */
    public void setIsInValid(boolean value) {
        this.isInValid = value;
    }

}
