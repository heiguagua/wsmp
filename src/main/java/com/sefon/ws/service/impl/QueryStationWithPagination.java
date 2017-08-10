
package com.sefon.ws.service.impl;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import com.sefon.ws.model.xsd.StationQuerySpecInfo;


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
 *         &lt;element name="spec" type="{http://model.ws.sefon.com/xsd}StationQuerySpecInfo" minOccurs="0"/&gt;
 *         &lt;element name="pageNum" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/&gt;
 *         &lt;element name="pageSize" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/&gt;
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
    "spec",
    "pageNum",
    "pageSize"
})
@XmlRootElement(name = "QueryStationWithPagination")
public class QueryStationWithPagination {

    @XmlElement(nillable = true)
    protected StationQuerySpecInfo spec;
    protected Integer pageNum;
    protected Integer pageSize;

    /**
     * 获取spec属性的值。
     * 
     * @return
     *     possible object is
     *     {@link StationQuerySpecInfo }
     *     
     */
    public StationQuerySpecInfo getSpec() {
        return spec;
    }

    /**
     * 设置spec属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link StationQuerySpecInfo }
     *     
     */
    public void setSpec(StationQuerySpecInfo value) {
        this.spec = value;
    }

    /**
     * 获取pageNum属性的值。
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getPageNum() {
        return pageNum;
    }

    /**
     * 设置pageNum属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setPageNum(Integer value) {
        this.pageNum = value;
    }

    /**
     * 获取pageSize属性的值。
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getPageSize() {
        return pageSize;
    }

    /**
     * 设置pageSize属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setPageSize(Integer value) {
        this.pageSize = value;
    }

}
