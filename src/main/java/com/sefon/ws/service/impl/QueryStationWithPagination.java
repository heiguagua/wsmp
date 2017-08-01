
package com.sefon.ws.service.impl;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import com.sefon.ws.model.xsd.StationQuerySpecInfo;


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
     * ��ȡspec���Ե�ֵ��
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
     * ����spec���Ե�ֵ��
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
     * ��ȡpageNum���Ե�ֵ��
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
     * ����pageNum���Ե�ֵ��
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
     * ��ȡpageSize���Ե�ֵ��
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
     * ����pageSize���Ե�ֵ��
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
