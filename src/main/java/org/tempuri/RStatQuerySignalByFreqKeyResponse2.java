
package org.tempuri;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>RStatQuerySignalByFreqKeyResponse complex type的 Java 类。
 * 
 * <p>以下模式片段指定包含在此类中的预期内容。
 * 
 * <pre>
 * &lt;complexType name="RStatQuerySignalByFreqKeyResponse"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="RadioStationSignal" type="{http://tempuri.org/}RadioStationSignalDTO" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "RStatQuerySignalByFreqKeyResponse", propOrder = {
    "radioStationSignal"
})
public class RStatQuerySignalByFreqKeyResponse2 {

    @XmlElement(name = "RadioStationSignal")
    protected RadioStationSignalDTO radioStationSignal;

    /**
     * 获取radioStationSignal属性的值。
     * 
     * @return
     *     possible object is
     *     {@link RadioStationSignalDTO }
     *     
     */
    public RadioStationSignalDTO getRadioStationSignal() {
        return radioStationSignal;
    }

    /**
     * 设置radioStationSignal属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link RadioStationSignalDTO }
     *     
     */
    public void setRadioStationSignal(RadioStationSignalDTO value) {
        this.radioStationSignal = value;
    }

}
