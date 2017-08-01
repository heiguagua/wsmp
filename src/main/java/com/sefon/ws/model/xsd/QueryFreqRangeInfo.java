
package com.sefon.ws.model.xsd;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>QueryFreqRangeInfo complex type的 Java 类。
 * 
 * <p>以下模式片段指定包含在此类中的预期内容。
 * 
 * <pre>
 * &lt;complexType name="QueryFreqRangeInfo"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="beginFreq" type="{http://www.w3.org/2001/XMLSchema}double" minOccurs="0"/&gt;
 *         &lt;element name="endFreq" type="{http://www.w3.org/2001/XMLSchema}double" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "QueryFreqRangeInfo", propOrder = {
    "beginFreq",
    "endFreq"
})
public class QueryFreqRangeInfo {

    @XmlElement(nillable = true)
    protected Double beginFreq;
    @XmlElement(nillable = true)
    protected Double endFreq;

    /**
     * 获取beginFreq属性的值。
     * 
     * @return
     *     possible object is
     *     {@link Double }
     *     
     */
    public Double getBeginFreq() {
        return beginFreq;
    }

    /**
     * 设置beginFreq属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link Double }
     *     
     */
    public void setBeginFreq(Double value) {
        this.beginFreq = value;
    }

    /**
     * 获取endFreq属性的值。
     * 
     * @return
     *     possible object is
     *     {@link Double }
     *     
     */
    public Double getEndFreq() {
        return endFreq;
    }

    /**
     * 设置endFreq属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link Double }
     *     
     */
    public void setEndFreq(Double value) {
        this.endFreq = value;
    }

}
