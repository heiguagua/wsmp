
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
 *         &lt;element name="QueryRadioSignalResult" type="{http://tempuri.org/}RadioSignalQueryResponse" minOccurs="0"/&gt;
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
    "queryRadioSignalResult"
})
@XmlRootElement(name = "QueryRadioSignalResponse")
public class QueryRadioSignalResponse {

    @XmlElement(name = "QueryRadioSignalResult")
    protected RadioSignalQueryResponse queryRadioSignalResult;

    /**
     * ��ȡqueryRadioSignalResult���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link RadioSignalQueryResponse }
     *     
     */
    public RadioSignalQueryResponse getQueryRadioSignalResult() {
        return queryRadioSignalResult;
    }

    /**
     * ����queryRadioSignalResult���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link RadioSignalQueryResponse }
     *     
     */
    public void setQueryRadioSignalResult(RadioSignalQueryResponse value) {
        this.queryRadioSignalResult = value;
    }

}
