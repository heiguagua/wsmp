
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
 *         &lt;element name="UpdateByFreqIdResult" type="{http://tempuri.org/}FreqHistoryOperationResponse" minOccurs="0"/&gt;
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
    "updateByFreqIdResult"
})
@XmlRootElement(name = "UpdateByFreqIdResponse")
public class UpdateByFreqIdResponse {

    @XmlElement(name = "UpdateByFreqIdResult")
    protected FreqHistoryOperationResponse updateByFreqIdResult;

    /**
     * ��ȡupdateByFreqIdResult���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link FreqHistoryOperationResponse }
     *     
     */
    public FreqHistoryOperationResponse getUpdateByFreqIdResult() {
        return updateByFreqIdResult;
    }

    /**
     * ����updateByFreqIdResult���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link FreqHistoryOperationResponse }
     *     
     */
    public void setUpdateByFreqIdResult(FreqHistoryOperationResponse value) {
        this.updateByFreqIdResult = value;
    }

}
