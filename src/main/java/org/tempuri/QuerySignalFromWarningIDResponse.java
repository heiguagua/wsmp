
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
 *         &lt;element name="QuerySignalFromWarningIDResult" type="{http://tempuri.org/}RadioSignalFromWarningIDQueryResponse" minOccurs="0"/&gt;
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
    "querySignalFromWarningIDResult"
})
@XmlRootElement(name = "QuerySignalFromWarningIDResponse")
public class QuerySignalFromWarningIDResponse {

    @XmlElement(name = "QuerySignalFromWarningIDResult")
    protected RadioSignalFromWarningIDQueryResponse querySignalFromWarningIDResult;

    /**
     * 获取querySignalFromWarningIDResult属性的值。
     * 
     * @return
     *     possible object is
     *     {@link RadioSignalFromWarningIDQueryResponse }
     *     
     */
    public RadioSignalFromWarningIDQueryResponse getQuerySignalFromWarningIDResult() {
        return querySignalFromWarningIDResult;
    }

    /**
     * 设置querySignalFromWarningIDResult属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link RadioSignalFromWarningIDQueryResponse }
     *     
     */
    public void setQuerySignalFromWarningIDResult(RadioSignalFromWarningIDQueryResponse value) {
        this.querySignalFromWarningIDResult = value;
    }

}
