
package org.tempuri;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>SignalStaticsOnStation complex type的 Java 类。
 * 
 * <p>以下模式片段指定包含在此类中的预期内容。
 * 
 * <pre>
 * &lt;complexType name="SignalStaticsOnStation"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="StationNumber" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="SignalStaticsLst" type="{http://tempuri.org/}ArrayOfSignalStatics" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "SignalStaticsOnStation", propOrder = {
    "stationNumber",
    "signalStaticsLst"
})
public class SignalStaticsOnStation {

    @XmlElement(name = "StationNumber")
    protected String stationNumber;
    @XmlElement(name = "SignalStaticsLst")
    protected ArrayOfSignalStatics signalStaticsLst;

    /**
     * 获取stationNumber属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getStationNumber() {
        return stationNumber;
    }

    /**
     * 设置stationNumber属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setStationNumber(String value) {
        this.stationNumber = value;
    }

    /**
     * 获取signalStaticsLst属性的值。
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfSignalStatics }
     *     
     */
    public ArrayOfSignalStatics getSignalStaticsLst() {
        return signalStaticsLst;
    }

    /**
     * 设置signalStaticsLst属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfSignalStatics }
     *     
     */
    public void setSignalStaticsLst(ArrayOfSignalStatics value) {
        this.signalStaticsLst = value;
    }

}
