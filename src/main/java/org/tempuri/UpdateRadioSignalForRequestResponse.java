
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
 *         &lt;element name="UpdateRadioSignalForRequestResult" type="{http://tempuri.org/}RadioSignalOperationReponse" minOccurs="0"/&gt;
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
    "updateRadioSignalForRequestResult"
})
@XmlRootElement(name = "UpdateRadioSignalForRequestResponse")
public class UpdateRadioSignalForRequestResponse {

    @XmlElement(name = "UpdateRadioSignalForRequestResult")
    protected RadioSignalOperationReponse updateRadioSignalForRequestResult;

    /**
     * ��ȡupdateRadioSignalForRequestResult���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link RadioSignalOperationReponse }
     *     
     */
    public RadioSignalOperationReponse getUpdateRadioSignalForRequestResult() {
        return updateRadioSignalForRequestResult;
    }

    /**
     * ����updateRadioSignalForRequestResult���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link RadioSignalOperationReponse }
     *     
     */
    public void setUpdateRadioSignalForRequestResult(RadioSignalOperationReponse value) {
        this.updateRadioSignalForRequestResult = value;
    }

}
