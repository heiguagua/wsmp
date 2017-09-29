
package org.tempuri;

import java.math.BigInteger;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>FreqWarningDTO complex type的 Java 类。
 * 
 * <p>以下模式片段指定包含在此类中的预期内容。
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
 *         &lt;element name="ELevel" type="{http://www.w3.org/2001/XMLSchema}long"/&gt;
 *         &lt;element name="StationKey" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="SaveDate" type="{http://www.w3.org/2001/XMLSchema}dateTime"/&gt;
 *         &lt;element name="LastTimeDate" type="{http://www.w3.org/2001/XMLSchema}dateTime"/&gt;
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
    "lastTimeDate",
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
    @XmlElement(name = "ELevel")
    protected long eLevel;
    @XmlElement(name = "StationKey")
    protected String stationKey;
    @XmlElement(name = "SaveDate", required = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar saveDate;
    @XmlElement(name = "LastTimeDate", required = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar lastTimeDate;
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
     * 获取id属性的值。
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
     * 设置id属性的值。
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
     * 获取centerFreq属性的值。
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
     * 设置centerFreq属性的值。
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
     * 获取bandWidth属性的值。
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
     * 设置bandWidth属性的值。
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
     * 获取status属性的值。
     * 
     */
    public int getStatus() {
        return status;
    }

    /**
     * 设置status属性的值。
     * 
     */
    public void setStatus(int value) {
        this.status = value;
    }

    /**
     * 获取areaCode属性的值。
     * 
     */
    public int getAreaCode() {
        return areaCode;
    }

    /**
     * 设置areaCode属性的值。
     * 
     */
    public void setAreaCode(int value) {
        this.areaCode = value;
    }

    /**
     * 获取keyword属性的值。
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
     * 设置keyword属性的值。
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
     * 获取eLevel属性的值。
     * 
     */
    public long getELevel() {
        return eLevel;
    }

    /**
     * 设置eLevel属性的值。
     * 
     */
    public void setELevel(long value) {
        this.eLevel = value;
    }

    /**
     * 获取stationKey属性的值。
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
     * 设置stationKey属性的值。
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
     * 获取saveDate属性的值。
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
     * 设置saveDate属性的值。
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
     * 获取lastTimeDate属性的值。
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getLastTimeDate() {
        return lastTimeDate;
    }

    /**
     * 设置lastTimeDate属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setLastTimeDate(XMLGregorianCalendar value) {
        this.lastTimeDate = value;
    }

    /**
     * 获取isInvalid属性的值。
     * 
     */
    public boolean isIsInvalid() {
        return isInvalid;
    }

    /**
     * 设置isInvalid属性的值。
     * 
     */
    public void setIsInvalid(boolean value) {
        this.isInvalid = value;
    }

    /**
     * 获取invalidDate属性的值。
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
     * 设置invalidDate属性的值。
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
     * 获取description属性的值。
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
     * 设置description属性的值。
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
     * 获取statList属性的值。
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
     * 设置statList属性的值。
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
