
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
 *         &lt;element name="QueryRadioSignalClassifiedResult" type="{http://tempuri.org/}RadioSignalClassifiedQueryResponse" minOccurs="0"/&gt;
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
    "queryRadioSignalClassifiedResult"
})
@XmlRootElement(name = "QueryRadioSignalClassifiedResponse")
public class QueryRadioSignalClassifiedResponse {

    @XmlElement(name = "QueryRadioSignalClassifiedResult")
    protected RadioSignalClassifiedQueryResponse queryRadioSignalClassifiedResult;

    /**
     * ��ȡqueryRadioSignalClassifiedResult���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link RadioSignalClassifiedQueryResponse }
     *     
     */
    public RadioSignalClassifiedQueryResponse getQueryRadioSignalClassifiedResult() {
        return queryRadioSignalClassifiedResult;
    }

    /**
     * ����queryRadioSignalClassifiedResult���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link RadioSignalClassifiedQueryResponse }
     *     
     */
    public void setQueryRadioSignalClassifiedResult(RadioSignalClassifiedQueryResponse value) {
        this.queryRadioSignalClassifiedResult = value;
    }

}
