
package org.tempuri;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>anonymous complex type�� Java �ࡣ
 * 
 * <p>����ģʽƬ��ָ�������ڴ����е�Ԥ�����ݡ�
 * 
 * <pre>
 * &lt;complexType&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="station" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="freq" type="{http://tempuri.org/}RadioFreqDTO" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "station",
    "freq"
})
@XmlRootElement(name = "RStatInsertUpdateFreq")
public class RStatInsertUpdateFreq {

    protected String station;
    protected RadioFreqDTO freq;

    /**
     * ��ȡstation���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getStation() {
        return station;
    }

    /**
     * ����station���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setStation(String value) {
        this.station = value;
    }

    /**
     * ��ȡfreq���Ե�ֵ��
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
     * ����freq���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link RadioFreqDTO }
     *     
     */
    public void setFreq(RadioFreqDTO value) {
        this.freq = value;
    }

}
