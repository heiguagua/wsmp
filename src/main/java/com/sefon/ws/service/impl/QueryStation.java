
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
 *         &lt;element name="removeSame" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/&gt;
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
    "removeSame"
})
@XmlRootElement(name = "QueryStation")
public class QueryStation {

    @XmlElement(nillable = true)
    protected StationQuerySpecInfo spec;
    protected Boolean removeSame;

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
     * ��ȡremoveSame���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isRemoveSame() {
        return removeSame;
    }

    /**
     * ����removeSame���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setRemoveSame(Boolean value) {
        this.removeSame = value;
    }

}
