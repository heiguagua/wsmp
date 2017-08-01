
package org.tempuri;

import java.math.BigInteger;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>FrequencyBand complex type�� Java �ࡣ
 * 
 * <p>����ģʽƬ��ָ�������ڴ����е�Ԥ�����ݡ�
 * 
 * <pre>
 * &lt;complexType name="FrequencyBand"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="FreqMin" type="{http://www.w3.org/2001/XMLSchema}unsignedLong"/&gt;
 *         &lt;element name="FreqMax" type="{http://www.w3.org/2001/XMLSchema}unsignedLong"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "FrequencyBand", propOrder = {
    "freqMin",
    "freqMax"
})
public class FrequencyBand {

    @XmlElement(name = "FreqMin", required = true)
    @XmlSchemaType(name = "unsignedLong")
    protected BigInteger freqMin;
    @XmlElement(name = "FreqMax", required = true)
    @XmlSchemaType(name = "unsignedLong")
    protected BigInteger freqMax;

    /**
     * ��ȡfreqMin���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getFreqMin() {
        return freqMin;
    }

    /**
     * ����freqMin���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setFreqMin(BigInteger value) {
        this.freqMin = value;
    }

    /**
     * ��ȡfreqMax���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getFreqMax() {
        return freqMax;
    }

    /**
     * ����freqMax���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setFreqMax(BigInteger value) {
        this.freqMax = value;
    }

}
