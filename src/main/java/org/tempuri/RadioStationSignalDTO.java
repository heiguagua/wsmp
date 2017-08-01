
package org.tempuri;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>RadioStationSignalDTO complex type的 Java 类。
 * 
 * <p>以下模式片段指定包含在此类中的预期内容。
 * 
 * <pre>
 * &lt;complexType name="RadioStationSignalDTO"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="Station" type="{http://tempuri.org/}RadioStationDTO" minOccurs="0"/&gt;
 *         &lt;element name="Freq" type="{http://tempuri.org/}RadioFreqDTO" minOccurs="0"/&gt;
 *         &lt;element name="IsUnRegistered" type="{http://www.w3.org/2001/XMLSchema}boolean"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "RadioStationSignalDTO", propOrder = {
    "station",
    "freq",
    "isUnRegistered"
})
public class RadioStationSignalDTO {

    @XmlElement(name = "Station")
    protected RadioStationDTO station;
    @XmlElement(name = "Freq")
    protected RadioFreqDTO freq;
    @XmlElement(name = "IsUnRegistered")
    protected boolean isUnRegistered;

    /**
     * 获取station属性的值。
     * 
     * @return
     *     possible object is
     *     {@link RadioStationDTO }
     *     
     */
    public RadioStationDTO getStation() {
        return station;
    }

    /**
     * 设置station属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link RadioStationDTO }
     *     
     */
    public void setStation(RadioStationDTO value) {
        this.station = value;
    }

    /**
     * 获取freq属性的值。
     * 
     * @return
     *     possible object is
     *     {@link RadioFreqDTO }
     *     
     */
    public RadioFreqDTO getFreq() {
        return freq;
    }

    /**
     * 设置freq属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link RadioFreqDTO }
     *     
     */
    public void setFreq(RadioFreqDTO value) {
        this.freq = value;
    }

    /**
     * 获取isUnRegistered属性的值。
     * 
     */
    public boolean isIsUnRegistered() {
        return isUnRegistered;
    }

    /**
     * 设置isUnRegistered属性的值。
     * 
     */
    public void setIsUnRegistered(boolean value) {
        this.isUnRegistered = value;
    }

}
