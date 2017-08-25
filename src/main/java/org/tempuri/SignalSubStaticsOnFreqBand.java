
package org.tempuri;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>SignalSubStaticsOnFreqBand complex type�� Java �ࡣ
 * 
 * <p>����ģʽƬ��ָ�������ڴ����е�Ԥ�����ݡ�
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
     * ��ȡband���Ե�ֵ��
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
     * ����band���Ե�ֵ��
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
     * ��ȡcount���Ե�ֵ��
     * 
     */
    public int getCount() {
        return count;
    }

    /**
     * ����count���Ե�ֵ��
     * 
     */
    public void setCount(int value) {
        this.count = value;
    }

}
