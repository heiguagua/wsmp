
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
 *         &lt;element name="AddImportantMonitorFreqResult" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/&gt;
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
    "addImportantMonitorFreqResult"
})
@XmlRootElement(name = "AddImportantMonitorFreqResponse")
public class AddImportantMonitorFreqResponse {

    @XmlElement(name = "AddImportantMonitorFreqResult")
    protected Boolean addImportantMonitorFreqResult;

    /**
     * 获取addImportantMonitorFreqResult属性的值。
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isAddImportantMonitorFreqResult() {
        return addImportantMonitorFreqResult;
    }

    /**
     * 设置addImportantMonitorFreqResult属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setAddImportantMonitorFreqResult(Boolean value) {
        this.addImportantMonitorFreqResult = value;
    }

}
