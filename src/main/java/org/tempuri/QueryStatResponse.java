
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
 *         &lt;element name="Query_StatResult" type="{http://tempuri.org/}FreqWarningStatQueryResponse" minOccurs="0"/&gt;
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
    "queryStatResult"
})
@XmlRootElement(name = "Query_StatResponse")
public class QueryStatResponse {

    @XmlElement(name = "Query_StatResult")
    protected FreqWarningStatQueryResponse queryStatResult;

    /**
     * ��ȡqueryStatResult���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link FreqWarningStatQueryResponse }
     *     
     */
    public FreqWarningStatQueryResponse getQueryStatResult() {
        return queryStatResult;
    }

    /**
     * ����queryStatResult���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link FreqWarningStatQueryResponse }
     *     
     */
    public void setQueryStatResult(FreqWarningStatQueryResponse value) {
        this.queryStatResult = value;
    }

}
