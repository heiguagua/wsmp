
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
 *         &lt;element name="dto" type="{http://tempuri.org/}FreqHistoryDTO" minOccurs="0"/&gt;
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
    "dto"
})
@XmlRootElement(name = "UpdateByFreqId")
public class UpdateByFreqId {

    protected FreqHistoryDTO dto;

    /**
     * ��ȡdto���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link FreqHistoryDTO }
     *     
     */
    public FreqHistoryDTO getDto() {
        return dto;
    }

    /**
     * ����dto���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link FreqHistoryDTO }
     *     
     */
    public void setDto(FreqHistoryDTO value) {
        this.dto = value;
    }

}
