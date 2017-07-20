
package org.tempuri;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>SignalTypeDTO complex type的 Java 类。
 * 
 * <p>以下模式片段指定包含在此类中的预期内容。
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
     * 获取isInvalid属性的值。
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
     * 设置isInvalid属性的值。
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
     * 获取signalType属性的值。
     * 
     */
    public int getSignalType() {
        return signalType;
    }

    /**
     * 设置signalType属性的值。
     * 
     */
    public void setSignalType(int value) {
        this.signalType = value;
    }

    /**
     * 获取abnormalTypes属性的值。
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
     * 设置abnormalTypes属性的值。
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
