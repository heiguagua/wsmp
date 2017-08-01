
package com.sefon.ws.service.impl;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import com.sefon.ws.model.freq.xsd.FrequencyRangeQuerySpec;


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
 *         &lt;element name="spec" type="{http://freq.model.ws.sefon.com/xsd}FrequencyRangeQuerySpec" minOccurs="0"/&gt;
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
    "spec"
})
@XmlRootElement(name = "QueryFrequencyRange")
public class QueryFrequencyRange {

    @XmlElement(nillable = true)
    protected FrequencyRangeQuerySpec spec;

    /**
     * ��ȡspec���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link FrequencyRangeQuerySpec }
     *     
     */
    public FrequencyRangeQuerySpec getSpec() {
        return spec;
    }

    /**
     * ����spec���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link FrequencyRangeQuerySpec }
     *     
     */
    public void setSpec(FrequencyRangeQuerySpec value) {
        this.spec = value;
    }

}
