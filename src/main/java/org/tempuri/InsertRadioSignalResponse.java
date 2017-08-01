
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
 *         &lt;element name="InsertRadioSignalResult" type="{http://tempuri.org/}RadioSignalOperationReponse" minOccurs="0"/&gt;
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
    "insertRadioSignalResult"
})
@XmlRootElement(name = "InsertRadioSignalResponse")
public class InsertRadioSignalResponse {

    @XmlElement(name = "InsertRadioSignalResult")
    protected RadioSignalOperationReponse insertRadioSignalResult;

    /**
     * 获取insertRadioSignalResult属性的值。
     * 
     * @return
     *     possible object is
     *     {@link RadioSignalOperationReponse }
     *     
     */
    public RadioSignalOperationReponse getInsertRadioSignalResult() {
        return insertRadioSignalResult;
    }

    /**
     * 设置insertRadioSignalResult属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link RadioSignalOperationReponse }
     *     
     */
    public void setInsertRadioSignalResult(RadioSignalOperationReponse value) {
        this.insertRadioSignalResult = value;
    }

}
