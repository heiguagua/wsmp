
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
 *         &lt;element name="QueryRadioSignalSubClassifiedResult" type="{http://tempuri.org/}RadioSignalSubClassifiedQueryResponse" minOccurs="0"/&gt;
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
    "queryRadioSignalSubClassifiedResult"
})
@XmlRootElement(name = "QueryRadioSignalSubClassifiedResponse")
public class QueryRadioSignalSubClassifiedResponse {

    @XmlElement(name = "QueryRadioSignalSubClassifiedResult")
    protected RadioSignalSubClassifiedQueryResponse queryRadioSignalSubClassifiedResult;

    /**
     * ��ȡqueryRadioSignalSubClassifiedResult���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link RadioSignalSubClassifiedQueryResponse }
     *     
     */
    public RadioSignalSubClassifiedQueryResponse getQueryRadioSignalSubClassifiedResult() {
        return queryRadioSignalSubClassifiedResult;
    }

    /**
     * ����queryRadioSignalSubClassifiedResult���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link RadioSignalSubClassifiedQueryResponse }
     *     
     */
    public void setQueryRadioSignalSubClassifiedResult(RadioSignalSubClassifiedQueryResponse value) {
        this.queryRadioSignalSubClassifiedResult = value;
    }

}
