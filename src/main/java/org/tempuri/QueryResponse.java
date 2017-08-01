
package org.tempuri;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>anonymous complex type的 Java 类。
 * 
 * <p>以下模式片段指定包含在此类中的预期内容。
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
     * 获取queryResult属性的值。
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
     * 设置queryResult属性的值。
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
