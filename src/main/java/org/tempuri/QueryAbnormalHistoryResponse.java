
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
 *         &lt;element name="QueryAbnormalHistoryResult" type="{http://tempuri.org/}AbnormalHistoryQueryResponse" minOccurs="0"/&gt;
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
    "queryAbnormalHistoryResult"
})
@XmlRootElement(name = "QueryAbnormalHistoryResponse")
public class QueryAbnormalHistoryResponse {

    @XmlElement(name = "QueryAbnormalHistoryResult")
    protected AbnormalHistoryQueryResponse queryAbnormalHistoryResult;

    /**
     * ��ȡqueryAbnormalHistoryResult���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link AbnormalHistoryQueryResponse }
     *     
     */
    public AbnormalHistoryQueryResponse getQueryAbnormalHistoryResult() {
        return queryAbnormalHistoryResult;
    }

    /**
     * ����queryAbnormalHistoryResult���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link AbnormalHistoryQueryResponse }
     *     
     */
    public void setQueryAbnormalHistoryResult(AbnormalHistoryQueryResponse value) {
        this.queryAbnormalHistoryResult = value;
    }

}
