
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
 *         &lt;element name="QueryStandardResult" type="{http://tempuri.org/}FreqWarningQueryResponse" minOccurs="0"/&gt;
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
    "queryStandardResult"
})
@XmlRootElement(name = "QueryStandardResponse")
public class QueryStandardResponse {

    @XmlElement(name = "QueryStandardResult")
    protected FreqWarningQueryResponse queryStandardResult;

    /**
     * 获取queryStandardResult属性的值。
     * 
     * @return
     *     possible object is
     *     {@link FreqWarningQueryResponse }
     *     
     */
    public FreqWarningQueryResponse getQueryStandardResult() {
        return queryStandardResult;
    }

    /**
     * 设置queryStandardResult属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link FreqWarningQueryResponse }
     *     
     */
    public void setQueryStandardResult(FreqWarningQueryResponse value) {
        this.queryStandardResult = value;
    }

}
