
package org.tempuri;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>RadioSignalQueryResponse complex type的 Java 类。
 * 
 * <p>以下模式片段指定包含在此类中的预期内容。
 * 
 * <pre>
 * &lt;complexType name="RadioSignalQueryResponse"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="IsSucess" type="{http://www.w3.org/2001/XMLSchema}boolean"/&gt;
 *         &lt;element name="ErrorMessage" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="RadioSignals" type="{http://tempuri.org/}ArrayOfRadioSignalDTO" minOccurs="0"/&gt;
 *         &lt;element name="TotalCount" type="{http://www.w3.org/2001/XMLSchema}long"/&gt;
 *         &lt;element name="StartIndex" type="{http://www.w3.org/2001/XMLSchema}long"/&gt;
 *         &lt;element name="StopIndex" type="{http://www.w3.org/2001/XMLSchema}long"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "RadioSignalQueryResponse", propOrder = {
    "isSucess",
    "errorMessage",
    "radioSignals",
    "totalCount",
    "startIndex",
    "stopIndex"
})
public class RadioSignalQueryResponse {

    @XmlElement(name = "IsSucess")
    protected boolean isSucess;
    @XmlElement(name = "ErrorMessage")
    protected String errorMessage;
    @XmlElement(name = "RadioSignals")
    protected ArrayOfRadioSignalDTO radioSignals;
    @XmlElement(name = "TotalCount")
    protected long totalCount;
    @XmlElement(name = "StartIndex")
    protected long startIndex;
    @XmlElement(name = "StopIndex")
    protected long stopIndex;

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
     * 获取radioSignals属性的值。
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfRadioSignalDTO }
     *     
     */
    public ArrayOfRadioSignalDTO getRadioSignals() {
        return radioSignals;
    }

    /**
     * 设置radioSignals属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfRadioSignalDTO }
     *     
     */
    public void setRadioSignals(ArrayOfRadioSignalDTO value) {
        this.radioSignals = value;
    }

    /**
     * 获取totalCount属性的值。
     * 
     */
    public long getTotalCount() {
        return totalCount;
    }

    /**
     * 设置totalCount属性的值。
     * 
     */
    public void setTotalCount(long value) {
        this.totalCount = value;
    }

    /**
     * 获取startIndex属性的值。
     * 
     */
    public long getStartIndex() {
        return startIndex;
    }

    /**
     * 设置startIndex属性的值。
     * 
     */
    public void setStartIndex(long value) {
        this.startIndex = value;
    }

    /**
     * 获取stopIndex属性的值。
     * 
     */
    public long getStopIndex() {
        return stopIndex;
    }

    /**
     * 设置stopIndex属性的值。
     * 
     */
    public void setStopIndex(long value) {
        this.stopIndex = value;
    }

}
