
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
 *         &lt;element name="UpdateKeywordResult" type="{http://tempuri.org/}FreqWarningOperationResponse" minOccurs="0"/&gt;
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
    "updateKeywordResult"
})
@XmlRootElement(name = "UpdateKeywordResponse")
public class UpdateKeywordResponse {

    @XmlElement(name = "UpdateKeywordResult")
    protected FreqWarningOperationResponse updateKeywordResult;

    /**
     * ��ȡupdateKeywordResult���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link FreqWarningOperationResponse }
     *     
     */
    public FreqWarningOperationResponse getUpdateKeywordResult() {
        return updateKeywordResult;
    }

    /**
     * ����updateKeywordResult���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link FreqWarningOperationResponse }
     *     
     */
    public void setUpdateKeywordResult(FreqWarningOperationResponse value) {
        this.updateKeywordResult = value;
    }

}
