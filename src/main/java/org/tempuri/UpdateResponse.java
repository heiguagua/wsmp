
package org.tempuri;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
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
     * ��ȡupdateResult���Ե�ֵ��
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
     * ����updateResult���Ե�ֵ��
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
