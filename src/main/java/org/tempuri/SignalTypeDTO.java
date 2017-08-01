
package org.tempuri;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>SignalTypeDTO complex type�� Java �ࡣ
 * 
 * <p>����ģʽƬ��ָ�������ڴ����е�Ԥ�����ݡ�
 * 
 * <pre>
 * &lt;complexType name="SignalTypeDTO"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="isInvalid" type="{http://www.w3.org/2001/XMLSchema}boolean"/&gt;
 *         &lt;element name="SignalType" type="{http://www.w3.org/2001/XMLSchema}int"/&gt;
 *         &lt;element name="AbnormalTypes" type="{http://tempuri.org/}ArrayOfSignalTypeDTO" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "SignalTypeDTO", propOrder = {
    "isInvalid",
    "signalType",
    "abnormalTypes"
})
public class SignalTypeDTO {

    @XmlElement(required = true, type = Boolean.class, nillable = true)
    protected Boolean isInvalid;
    @XmlElement(name = "SignalType")
    protected int signalType;
    @XmlElement(name = "AbnormalTypes")
    protected ArrayOfSignalTypeDTO abnormalTypes;

    /**
     * ��ȡisInvalid���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isIsInvalid() {
        return isInvalid;
    }

    /**
     * ����isInvalid���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setIsInvalid(Boolean value) {
        this.isInvalid = value;
    }

    /**
     * ��ȡsignalType���Ե�ֵ��
     * 
     */
    public int getSignalType() {
        return signalType;
    }

    /**
     * ����signalType���Ե�ֵ��
     * 
     */
    public void setSignalType(int value) {
        this.signalType = value;
    }

    /**
     * ��ȡabnormalTypes���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfSignalTypeDTO }
     *     
     */
    public ArrayOfSignalTypeDTO getAbnormalTypes() {
        return abnormalTypes;
    }

    /**
     * ����abnormalTypes���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfSignalTypeDTO }
     *     
     */
    public void setAbnormalTypes(ArrayOfSignalTypeDTO value) {
        this.abnormalTypes = value;
    }

}
