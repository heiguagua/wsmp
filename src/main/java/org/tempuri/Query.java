
package org.tempuri;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
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
 *         &lt;element name="request" type="{http://tempuri.org/}FreqWarningQueryRequest" minOccurs="0"/&gt;
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
    "request"
})
@XmlRootElement(name = "Query")
public class Query {

    protected FreqWarningQueryRequest request;

    /**
     * ��ȡrequest���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link FreqWarningQueryRequest }
     *     
     */
    public FreqWarningQueryRequest getRequest() {
        return request;
    }

    /**
     * ����request���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link FreqWarningQueryRequest }
     *     
     */
    public void setRequest(FreqWarningQueryRequest value) {
        this.request = value;
    }

}
