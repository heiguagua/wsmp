
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
 *         &lt;element name="RStatQuerySignalsResult" type="{http://tempuri.org/}RStatQuerySignalsResponse" minOccurs="0"/&gt;
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
    "rStatQuerySignalsResult"
})
@XmlRootElement(name = "RStatQuerySignalsResponse")
public class RStatQuerySignalsResponse {

    @XmlElement(name = "RStatQuerySignalsResult")
    protected RStatQuerySignalsResponse2 rStatQuerySignalsResult;

    /**
     * ��ȡrStatQuerySignalsResult���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link RStatQuerySignalsResponse2 }
     *     
     */
    public RStatQuerySignalsResponse2 getRStatQuerySignalsResult() {
        return rStatQuerySignalsResult;
    }

    /**
     * ����rStatQuerySignalsResult���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link RStatQuerySignalsResponse2 }
     *     
     */
    public void setRStatQuerySignalsResult(RStatQuerySignalsResponse2 value) {
        this.rStatQuerySignalsResult = value;
    }

}
