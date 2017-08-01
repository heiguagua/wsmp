
package org.tempuri;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>FreqWarningStatQueryResponse complex type�� Java �ࡣ
 * 
 * <p>����ģʽƬ��ָ�������ڴ����е�Ԥ�����ݡ�
 * 
 * <pre>
 * &lt;complexType name="FreqWarningStatQueryResponse"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="IsSucess" type="{http://www.w3.org/2001/XMLSchema}boolean"/&gt;
 *         &lt;element name="ErrorMessage" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="WarningStatInfos" type="{http://tempuri.org/}ArrayOfFreqWarningStatDTO" minOccurs="0"/&gt;
 *         &lt;element name="TotalCount" type="{http://www.w3.org/2001/XMLSchema}long"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "FreqWarningStatQueryResponse", propOrder = {
    "isSucess",
    "errorMessage",
    "warningStatInfos",
    "totalCount"
})
public class FreqWarningStatQueryResponse {

    @XmlElement(name = "IsSucess")
    protected boolean isSucess;
    @XmlElement(name = "ErrorMessage")
    protected String errorMessage;
    @XmlElement(name = "WarningStatInfos")
    protected ArrayOfFreqWarningStatDTO warningStatInfos;
    @XmlElement(name = "TotalCount", required = true, type = Long.class, nillable = true)
    protected Long totalCount;

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
     * ��ȡwarningStatInfos���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfFreqWarningStatDTO }
     *     
     */
    public ArrayOfFreqWarningStatDTO getWarningStatInfos() {
        return warningStatInfos;
    }

    /**
     * ����warningStatInfos���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfFreqWarningStatDTO }
     *     
     */
    public void setWarningStatInfos(ArrayOfFreqWarningStatDTO value) {
        this.warningStatInfos = value;
    }

    /**
     * ��ȡtotalCount���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getTotalCount() {
        return totalCount;
    }

    /**
     * ����totalCount���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setTotalCount(Long value) {
        this.totalCount = value;
    }

}
