
package org.tempuri;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>RadioSignalFromWarningIDQueryResponse complex type�� Java �ࡣ
 * 
 * <p>����ģʽƬ��ָ�������ڴ����е�Ԥ�����ݡ�
 * 
 * <pre>
 * &lt;complexType name="RadioSignalFromWarningIDQueryResponse"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="IsSucess" type="{http://www.w3.org/2001/XMLSchema}boolean"/&gt;
 *         &lt;element name="ErrorMessage" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="RadioSignalInfo" type="{http://tempuri.org/}ArrayOfRadioSignalDTO" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "RadioSignalFromWarningIDQueryResponse", propOrder = {
    "isSucess",
    "errorMessage",
    "radioSignalInfo"
})
public class RadioSignalFromWarningIDQueryResponse {

    @XmlElement(name = "IsSucess")
    protected boolean isSucess;
    @XmlElement(name = "ErrorMessage")
    protected String errorMessage;
    @XmlElement(name = "RadioSignalInfo")
    protected ArrayOfRadioSignalDTO radioSignalInfo;

    /**
     * ��ȡisSucess���Ե�ֵ��
     * 
     */
    public boolean isIsSucess() {
        return isSucess;
    }

    /**
     * ����isSucess���Ե�ֵ��
     * 
     */
    public void setIsSucess(boolean value) {
        this.isSucess = value;
    }

    /**
     * ��ȡerrorMessage���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getErrorMessage() {
        return errorMessage;
    }

    /**
     * ����errorMessage���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setErrorMessage(String value) {
        this.errorMessage = value;
    }

    /**
     * ��ȡradioSignalInfo���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfRadioSignalDTO }
     *     
     */
    public ArrayOfRadioSignalDTO getRadioSignalInfo() {
        return radioSignalInfo;
    }

    /**
     * ����radioSignalInfo���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfRadioSignalDTO }
     *     
     */
    public void setRadioSignalInfo(ArrayOfRadioSignalDTO value) {
        this.radioSignalInfo = value;
    }

}
