
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
 *         &lt;element name="dtos" type="{http://tempuri.org/}ArrayOfFreqHistoryDTO" minOccurs="0"/&gt;
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
    "dtos"
})
@XmlRootElement(name = "UpdateByFreqIds")
public class UpdateByFreqIds {

    protected ArrayOfFreqHistoryDTO dtos;

    /**
     * ��ȡdtos���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfFreqHistoryDTO }
     *     
     */
    public ArrayOfFreqHistoryDTO getDtos() {
        return dtos;
    }

    /**
     * ����dtos���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfFreqHistoryDTO }
     *     
     */
    public void setDtos(ArrayOfFreqHistoryDTO value) {
        this.dtos = value;
    }

}