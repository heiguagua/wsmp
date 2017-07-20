
package org.tempuri;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


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
 *         &lt;element name="RStatQuerySignalsResult" type="{http://tempuri.org/}RStatQuerySignalsResponse" minOccurs="0"/&gt;
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
    "rStatQuerySignalsResult"
})
@XmlRootElement(name = "RStatQuerySignalsResponse")
public class RStatQuerySignalsResponse {

    @XmlElement(name = "RStatQuerySignalsResult")
    protected RStatQuerySignalsResponse2 rStatQuerySignalsResult;

    /**
     * 获取rStatQuerySignalsResult属性的值。
     * 
     * @return
     *     possible object is
     *     {@link RStatQuerySignalsResponse2 }
     *     
     */
    public RStatQuerySignalsResponse2 getRStatQuerySignalsResult() {
        return rStatQuerySignalsResult;
    }

    /**
     * 设置rStatQuerySignalsResult属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link RStatQuerySignalsResponse2 }
     *     
     */
    public void setRStatQuerySignalsResult(RStatQuerySignalsResponse2 value) {
        this.rStatQuerySignalsResult = value;
    }

}
