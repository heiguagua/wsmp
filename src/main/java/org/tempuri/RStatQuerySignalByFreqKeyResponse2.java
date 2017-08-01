
package org.tempuri;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>RStatQuerySignalByFreqKeyResponse complex type�� Java �ࡣ
 * 
 * <p>����ģʽƬ��ָ�������ڴ����е�Ԥ�����ݡ�
 * 
 * <pre>
 * &lt;complexType name="RStatQuerySignalByFreqKeyResponse"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="RadioStationSignal" type="{http://tempuri.org/}RadioStationSignalDTO" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "RStatQuerySignalByFreqKeyResponse", propOrder = {
    "radioStationSignal"
})
public class RStatQuerySignalByFreqKeyResponse2 {

    @XmlElement(name = "RadioStationSignal")
    protected RadioStationSignalDTO radioStationSignal;

    /**
     * ��ȡradioStationSignal���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link RadioStationSignalDTO }
     *     
     */
    public RadioStationSignalDTO getRadioStationSignal() {
        return radioStationSignal;
    }

    /**
     * ����radioStationSignal���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link RadioStationSignalDTO }
     *     
     */
    public void setRadioStationSignal(RadioStationSignalDTO value) {
        this.radioStationSignal = value;
    }

}
