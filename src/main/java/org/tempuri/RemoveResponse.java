
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
 *         &lt;element name="RemoveResult" type="{http://tempuri.org/}FreqWarningOperationResponse" minOccurs="0"/&gt;
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
    "removeResult"
})
@XmlRootElement(name = "RemoveResponse")
public class RemoveResponse {

    @XmlElement(name = "RemoveResult")
    protected FreqWarningOperationResponse removeResult;

    /**
     * 获取removeResult属性的值。
     * 
     * @return
     *     possible object is
     *     {@link FreqWarningOperationResponse }
     *     
     */
    public FreqWarningOperationResponse getRemoveResult() {
        return removeResult;
    }

    /**
     * 设置removeResult属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link FreqWarningOperationResponse }
     *     
     */
    public void setRemoveResult(FreqWarningOperationResponse value) {
        this.removeResult = value;
    }

}
