
package org.tempuri;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>SignalStaticsOnFreqBand complex type�� Java �ࡣ
 * 
 * <p>����ģʽƬ��ָ�������ڴ����е�Ԥ�����ݡ�
 * 
 * <pre>
 * &lt;complexType name="SignalStaticsOnFreqBand"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="Band" type="{http://tempuri.org/}FrequencyBand" minOccurs="0"/&gt;
 *         &lt;element name="SignalStaticsLst" type="{http://tempuri.org/}ArrayOfSignalStatics" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "SignalStaticsOnFreqBand", propOrder = {
    "band",
    "signalStaticsLst"
})
public class SignalStaticsOnFreqBand {

    @XmlElement(name = "Band")
    protected FrequencyBand band;
    @XmlElement(name = "SignalStaticsLst")
    protected ArrayOfSignalStatics signalStaticsLst;

    /**
     * ��ȡband���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link FrequencyBand }
     *     
     */
    public FrequencyBand getBand() {
        return band;
    }

    /**
     * ����band���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link FrequencyBand }
     *     
     */
    public void setBand(FrequencyBand value) {
        this.band = value;
    }

    /**
     * ��ȡsignalStaticsLst���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfSignalStatics }
     *     
     */
    public ArrayOfSignalStatics getSignalStaticsLst() {
        return signalStaticsLst;
    }

    /**
     * ����signalStaticsLst���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfSignalStatics }
     *     
     */
    public void setSignalStaticsLst(ArrayOfSignalStatics value) {
        this.signalStaticsLst = value;
    }

}
