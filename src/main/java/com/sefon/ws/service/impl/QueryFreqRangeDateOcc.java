
package com.sefon.ws.service.impl;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import com.sefon.ws.model.electric.level.xsd.FreqRangeQuerySpec;


/**
 * <p>anonymous complex type的 Java 类。
 * 
 * <p>以下模式片段指定包含在此类中的预期内容。
 * 
 * <pre>
 * &lt;complexType&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="spec" type="{http://level.electric.model.ws.sefon.com/xsd}FreqRangeQuerySpec" minOccurs="0"/&gt;
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
@XmlRootElement(name = "QueryFreqRangeDateOcc")
public class QueryFreqRangeDateOcc {

    @XmlElement(nillable = true)
    protected FreqRangeQuerySpec spec;

    /**
     * 获取spec属性的值。
     * 
     * @return
     *     possible object is
     *     {@link FreqRangeQuerySpec }
     *     
     */
    public FreqRangeQuerySpec getSpec() {
        return spec;
    }

    /**
     * 设置spec属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link FreqRangeQuerySpec }
     *     
     */
    public void setSpec(FreqRangeQuerySpec value) {
        this.spec = value;
    }

}
