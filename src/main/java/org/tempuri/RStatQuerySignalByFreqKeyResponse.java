
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
 *         &lt;element name="RStatQuerySignalByFreqKeyResult" type="{http://tempuri.org/}RStatQuerySignalByFreqKeyResponse" minOccurs="0"/&gt;
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
    "rStatQuerySignalByFreqKeyResult"
})
@XmlRootElement(name = "RStatQuerySignalByFreqKeyResponse")
public class RStatQuerySignalByFreqKeyResponse {

    @XmlElement(name = "RStatQuerySignalByFreqKeyResult")
    protected RStatQuerySignalByFreqKeyResponse2 rStatQuerySignalByFreqKeyResult;

    /**
     * ��ȡrStatQuerySignalByFreqKeyResult���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link RStatQuerySignalByFreqKeyResponse2 }
     *     
     */
    public RStatQuerySignalByFreqKeyResponse2 getRStatQuerySignalByFreqKeyResult() {
        return rStatQuerySignalByFreqKeyResult;
    }

    /**
     * ����rStatQuerySignalByFreqKeyResult���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link RStatQuerySignalByFreqKeyResponse2 }
     *     
     */
    public void setRStatQuerySignalByFreqKeyResult(RStatQuerySignalByFreqKeyResponse2 value) {
        this.rStatQuerySignalByFreqKeyResult = value;
    }

}
