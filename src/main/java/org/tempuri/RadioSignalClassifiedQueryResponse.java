
package org.tempuri;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>RadioSignalClassifiedQueryResponse complex type�� Java �ࡣ
 * 
 * <p>����ģʽƬ��ָ�������ڴ����е�Ԥ�����ݡ�
 * 
 * <pre>
 * &lt;complexType name="RadioSignalClassifiedQueryResponse"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="IsSucess" type="{http://www.w3.org/2001/XMLSchema}boolean"/&gt;
 *         &lt;element name="ErrorMessage" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="LstOnFreqBand" type="{http://tempuri.org/}ArrayOfSignalStaticsOnFreqBand" minOccurs="0"/&gt;
 *         &lt;element name="LstOnStation" type="{http://tempuri.org/}ArrayOfSignalStaticsOnStation" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "RadioSignalClassifiedQueryResponse", propOrder = {
    "isSucess",
    "errorMessage",
    "lstOnFreqBand",
    "lstOnStation"
})
public class RadioSignalClassifiedQueryResponse {

    @XmlElement(name = "IsSucess")
    protected boolean isSucess;
    @XmlElement(name = "ErrorMessage")
    protected String errorMessage;
    @XmlElement(name = "LstOnFreqBand")
    protected ArrayOfSignalStaticsOnFreqBand lstOnFreqBand;
    @XmlElement(name = "LstOnStation")
    protected ArrayOfSignalStaticsOnStation lstOnStation;

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
     * ��ȡlstOnFreqBand���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfSignalStaticsOnFreqBand }
     *     
     */
    public ArrayOfSignalStaticsOnFreqBand getLstOnFreqBand() {
        return lstOnFreqBand;
    }

    /**
     * ����lstOnFreqBand���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfSignalStaticsOnFreqBand }
     *     
     */
    public void setLstOnFreqBand(ArrayOfSignalStaticsOnFreqBand value) {
        this.lstOnFreqBand = value;
    }

    /**
     * ��ȡlstOnStation���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfSignalStaticsOnStation }
     *     
     */
    public ArrayOfSignalStaticsOnStation getLstOnStation() {
        return lstOnStation;
    }

    /**
     * ����lstOnStation���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfSignalStaticsOnStation }
     *     
     */
    public void setLstOnStation(ArrayOfSignalStaticsOnStation value) {
        this.lstOnStation = value;
    }

}
