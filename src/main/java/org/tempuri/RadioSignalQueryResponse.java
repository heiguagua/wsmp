
package org.tempuri;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>RadioSignalQueryResponse complex type�� Java �ࡣ
 * 
 * <p>����ģʽƬ��ָ�������ڴ����е�Ԥ�����ݡ�
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
     * ��ȡradioSignals���Ե�ֵ��
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
     * ����radioSignals���Ե�ֵ��
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
     * ��ȡtotalCount���Ե�ֵ��
     * 
     */
    public long getTotalCount() {
        return totalCount;
    }

    /**
     * ����totalCount���Ե�ֵ��
     * 
     */
    public void setTotalCount(long value) {
        this.totalCount = value;
    }

    /**
     * ��ȡstartIndex���Ե�ֵ��
     * 
     */
    public long getStartIndex() {
        return startIndex;
    }

    /**
     * ����startIndex���Ե�ֵ��
     * 
     */
    public void setStartIndex(long value) {
        this.startIndex = value;
    }

    /**
     * ��ȡstopIndex���Ե�ֵ��
     * 
     */
    public long getStopIndex() {
        return stopIndex;
    }

    /**
     * ����stopIndex���Ե�ֵ��
     * 
     */
    public void setStopIndex(long value) {
        this.stopIndex = value;
    }

}
