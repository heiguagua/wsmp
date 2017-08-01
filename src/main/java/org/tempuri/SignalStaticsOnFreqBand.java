
package org.tempuri;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>SignalStaticsOnFreqBand complex type的 Java 类。
 * 
 * <p>以下模式片段指定包含在此类中的预期内容。
 * 
 * <pre>
 * &lt;complexType name="SignalStaticsOnFreqBand"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="Band" type="{http://tempuri.org/}FrequencyBand" minOccurs="0"/&gt;
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
@XmlType(name = "SignalStaticsOnFreqBand", propOrder = {
    "band",
    "signalStaticsLst"
})
public class SignalStaticsOnFreqBand {

    @XmlElement(name = "Band")
    protected FrequencyBand band;
    @XmlElement(name = "SignalStaticsLst")
    protected ArrayOfSignalStatics signalStaticsLst;

    /**
     * 获取band属性的值。
     * 
     * @return
     *     possible object is
     *     {@link FrequencyBand }
     *     
     */
    public FrequencyBand getBand() {
        return band;
    }

    /**
     * 设置band属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link FrequencyBand }
     *     
     */
    public void setBand(FrequencyBand value) {
        this.band = value;
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
