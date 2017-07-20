
package org.tempuri;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>RadioSignalClassifiedQueryResponse complex type的 Java 类。
 * 
 * <p>以下模式片段指定包含在此类中的预期内容。
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
     * 获取isSucess属性的值。
     * 
     */
    public boolean isIsSucess() {
        return isSucess;
    }

    /**
     * 设置isSucess属性的值。
     * 
     */
    public void setIsSucess(boolean value) {
        this.isSucess = value;
    }

    /**
     * 获取errorMessage属性的值。
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
     * 设置errorMessage属性的值。
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
     * 获取lstOnFreqBand属性的值。
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
     * 设置lstOnFreqBand属性的值。
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
     * 获取lstOnStation属性的值。
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
     * 设置lstOnStation属性的值。
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
