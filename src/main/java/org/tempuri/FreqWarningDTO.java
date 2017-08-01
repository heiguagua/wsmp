
package org.tempuri;

import java.math.BigInteger;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>FreqWarningDTO complex type�� Java �ࡣ
 * 
 * <p>����ģʽƬ��ָ�������ڴ����е�Ԥ�����ݡ�
 * 
 * <pre>
 * &lt;complexType name="FreqWarningDTO"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="ID" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="CenterFreq" type="{http://www.w3.org/2001/XMLSchema}unsignedLong"/&gt;
 *         &lt;element name="BandWidth" type="{http://www.w3.org/2001/XMLSchema}unsignedLong"/&gt;
 *         &lt;element name="Status" type="{http://www.w3.org/2001/XMLSchema}int"/&gt;
 *         &lt;element name="AreaCode" type="{http://www.w3.org/2001/XMLSchema}int"/&gt;
 *         &lt;element name="Keyword" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="ELevel" type="{http://www.w3.org/2001/XMLSchema}unsignedLong"/&gt;
 *         &lt;element name="StationKey" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="SaveDate" type="{http://www.w3.org/2001/XMLSchema}dateTime"/&gt;
 *         &lt;element name="IsInvalid" type="{http://www.w3.org/2001/XMLSchema}boolean"/&gt;
 *         &lt;element name="InvalidDate" type="{http://www.w3.org/2001/XMLSchema}dateTime"/&gt;
 *         &lt;element name="Description" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="StatList" type="{http://tempuri.org/}ArrayOfFreqWarningStatDTO" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "FreqWarningDTO", propOrder = {
    "id",
    "centerFreq",
    "bandWidth",
    "status",
    "areaCode",
    "keyword",
    "eLevel",
    "stationKey",
    "saveDate",
    "isInvalid",
    "invalidDate",
    "description",
    "statList"
})
public class FreqWarningDTO {

    @XmlElement(name = "ID")
    protected String id;
    @XmlElement(name = "CenterFreq", required = true)
    @XmlSchemaType(name = "unsignedLong")
    protected BigInteger centerFreq;
    @XmlElement(name = "BandWidth", required = true)
    @XmlSchemaType(name = "unsignedLong")
    protected BigInteger bandWidth;
    @XmlElement(name = "Status")
    protected int status;
    @XmlElement(name = "AreaCode")
    protected int areaCode;
    @XmlElement(name = "Keyword")
    protected String keyword;
    @XmlElement(name = "ELevel", required = true)
    @XmlSchemaType(name = "unsignedLong")
    protected BigInteger eLevel;
    @XmlElement(name = "StationKey")
    protected String stationKey;
    @XmlElement(name = "SaveDate", required = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar saveDate;
    @XmlElement(name = "IsInvalid")
    protected boolean isInvalid;
    @XmlElement(name = "InvalidDate", required = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar invalidDate;
    @XmlElement(name = "Description")
    protected String description;
    @XmlElement(name = "StatList")
    protected ArrayOfFreqWarningStatDTO statList;

    /**
     * ��ȡid���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getID() {
        return id;
    }

    /**
     * ����id���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setID(String value) {
        this.id = value;
    }

    /**
     * ��ȡcenterFreq���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getCenterFreq() {
        return centerFreq;
    }

    /**
     * ����centerFreq���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setCenterFreq(BigInteger value) {
        this.centerFreq = value;
    }

    /**
     * ��ȡbandWidth���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getBandWidth() {
        return bandWidth;
    }

    /**
     * ����bandWidth���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setBandWidth(BigInteger value) {
        this.bandWidth = value;
    }

    /**
     * ��ȡstatus���Ե�ֵ��
     * 
     */
    public int getStatus() {
        return status;
    }

    /**
     * ����status���Ե�ֵ��
     * 
     */
    public void setStatus(int value) {
        this.status = value;
    }

    /**
     * ��ȡareaCode���Ե�ֵ��
     * 
     */
    public int getAreaCode() {
        return areaCode;
    }

    /**
     * ����areaCode���Ե�ֵ��
     * 
     */
    public void setAreaCode(int value) {
        this.areaCode = value;
    }

    /**
     * ��ȡkeyword���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getKeyword() {
        return keyword;
    }

    /**
     * ����keyword���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setKeyword(String value) {
        this.keyword = value;
    }

    /**
     * ��ȡeLevel���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getELevel() {
        return eLevel;
    }

    /**
     * ����eLevel���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setELevel(BigInteger value) {
        this.eLevel = value;
    }

    /**
     * ��ȡstationKey���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getStationKey() {
        return stationKey;
    }

    /**
     * ����stationKey���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setStationKey(String value) {
        this.stationKey = value;
    }

    /**
     * ��ȡsaveDate���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getSaveDate() {
        return saveDate;
    }

    /**
     * ����saveDate���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setSaveDate(XMLGregorianCalendar value) {
        this.saveDate = value;
    }

    /**
     * ��ȡisInvalid���Ե�ֵ��
     * 
     */
    public boolean isIsInvalid() {
        return isInvalid;
    }

    /**
     * ����isInvalid���Ե�ֵ��
     * 
     */
    public void setIsInvalid(boolean value) {
        this.isInvalid = value;
    }

    /**
     * ��ȡinvalidDate���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getInvalidDate() {
        return invalidDate;
    }

    /**
     * ����invalidDate���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setInvalidDate(XMLGregorianCalendar value) {
        this.invalidDate = value;
    }

    /**
     * ��ȡdescription���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDescription() {
        return description;
    }

    /**
     * ����description���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDescription(String value) {
        this.description = value;
    }

    /**
     * ��ȡstatList���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfFreqWarningStatDTO }
     *     
     */
    public ArrayOfFreqWarningStatDTO getStatList() {
        return statList;
    }

    /**
     * ����statList���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfFreqWarningStatDTO }
     *     
     */
    public void setStatList(ArrayOfFreqWarningStatDTO value) {
        this.statList = value;
    }

}
