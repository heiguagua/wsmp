
package org.tempuri;

import java.util.Calendar;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import org.w3._2001.xmlschema.Adapter1;


/**
 * <p>RadioSignalClassifiedQueryRequest complex type�� Java �ࡣ
 * 
 * <p>����ģʽƬ��ָ�������ڴ����е�Ԥ�����ݡ�
 * 
 * <pre>
 * &lt;complexType name="RadioSignalClassifiedQueryRequest"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="FreqBandList" type="{http://tempuri.org/}ArrayOfFrequencyBand" minOccurs="0"/&gt;
 *         &lt;element name="StationNumber" type="{http://tempuri.org/}ArrayOfString" minOccurs="0"/&gt;
 *         &lt;element name="AreaCodes" type="{http://tempuri.org/}ArrayOfInt" minOccurs="0"/&gt;
 *         &lt;element name="StartTime" type="{http://www.w3.org/2001/XMLSchema}dateTime"/&gt;
 *         &lt;element name="StopTime" type="{http://www.w3.org/2001/XMLSchema}dateTime"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "RadioSignalClassifiedQueryRequest", propOrder = {
    "freqBandList",
    "stationNumber",
    "areaCodes",
    "startTime",
    "stopTime"
})
public class RadioSignalClassifiedQueryRequest {

    @XmlElement(name = "FreqBandList")
    protected ArrayOfFrequencyBand freqBandList;
    @XmlElement(name = "StationNumber")
    protected ArrayOfString stationNumber;
    @XmlElement(name = "AreaCodes")
    protected ArrayOfInt areaCodes;
    @XmlElement(name = "StartTime", required = true, type = String.class, nillable = true)
    @XmlJavaTypeAdapter(Adapter1 .class)
    @XmlSchemaType(name = "dateTime")
    protected Calendar startTime;
    @XmlElement(name = "StopTime", required = true, type = String.class, nillable = true)
    @XmlJavaTypeAdapter(Adapter1 .class)
    @XmlSchemaType(name = "dateTime")
    protected Calendar stopTime;

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
     *     {@link String }
     *     
     */
    public Calendar getStartTime() {
        return startTime;
    }

    /**
     * ����startTime���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setStartTime(Calendar value) {
        this.startTime = value;
    }

    /**
     * ��ȡstopTime���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public Calendar getStopTime() {
        return stopTime;
    }

    /**
     * ����stopTime���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setStopTime(Calendar value) {
        this.stopTime = value;
    }

}
