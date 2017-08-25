
package org.tempuri;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>SignalSubStaticsOnFreqBand complex type的 Java 类。
 * 
 * <p>以下模式片段指定包含在此类中的预期内容。
 * 
 * <pre>
 * &lt;complexType name="SignalSubStaticsOnFreqBand"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="Band" type="{http://tempuri.org/}FrequencyBand" minOccurs="0"/&gt;
 *         &lt;element name="Count" type="{http://www.w3.org/2001/XMLSchema}int"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "SignalSubStaticsOnFreqBand", propOrder = {
    "band",
    "count"
})
public class SignalSubStaticsOnFreqBand {

    @XmlElement(name = "Band")
    protected FrequencyBand band;
    @XmlElement(name = "Count")
    protected int count;

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
     * 获取count属性的值。
     * 
     */
    public int getCount() {
        return count;
    }

    /**
     * 设置count属性的值。
     * 
     */
    public void setCount(int value) {
        this.count = value;
    }

}
