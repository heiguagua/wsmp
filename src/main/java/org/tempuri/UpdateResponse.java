
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
 *         &lt;element name="UpdateResult" type="{http://tempuri.org/}FreqWarningOperationResponse" minOccurs="0"/&gt;
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
    "updateResult"
})
@XmlRootElement(name = "UpdateResponse")
public class UpdateResponse {

    @XmlElement(name = "UpdateResult")
    protected FreqWarningOperationResponse updateResult;

    /**
     * 获取updateResult属性的值。
     * 
     * @return
     *     possible object is
     *     {@link FreqWarningOperationResponse }
     *     
     */
    public FreqWarningOperationResponse getUpdateResult() {
        return updateResult;
    }

    /**
     * 设置updateResult属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link FreqWarningOperationResponse }
     *     
     */
    public void setUpdateResult(FreqWarningOperationResponse value) {
        this.updateResult = value;
    }

}
