
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
 *         &lt;element name="QueryResult" type="{http://tempuri.org/}FreqWarningQueryResponse" minOccurs="0"/&gt;
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
    "queryResult"
})
@XmlRootElement(name = "QueryResponse")
public class QueryResponse {

    @XmlElement(name = "QueryResult")
    protected FreqWarningQueryResponse queryResult;

    /**
     * ��ȡqueryResult���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link FreqWarningQueryResponse }
     *     
     */
    public FreqWarningQueryResponse getQueryResult() {
        return queryResult;
    }

    /**
     * ����queryResult���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link FreqWarningQueryResponse }
     *     
     */
    public void setQueryResult(FreqWarningQueryResponse value) {
        this.queryResult = value;
    }

}
