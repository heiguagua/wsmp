
package com.sefon.ws.model.freq.xsd;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>FrequencyRangeInfo complex type的 Java 类。
 * 
 * <p>以下模式片段指定包含在此类中的预期内容。
 * 
 * <pre>
 * &lt;complexType name="FrequencyRangeInfo"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="ID" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="beginFreq" type="{http://www.w3.org/2001/XMLSchema}double" minOccurs="0"/&gt;
 *         &lt;element name="description" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="endFreq" type="{http://www.w3.org/2001/XMLSchema}double" minOccurs="0"/&gt;
 *         &lt;element name="isContinue" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/&gt;
 *         &lt;element name="isLocal" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/&gt;
 *         &lt;element name="level" type="{http://www.w3.org/2001/XMLSchema}byte" minOccurs="0"/&gt;
 *         &lt;element name="name" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="oprType" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="parentId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="startUsingDate" type="{http://www.w3.org/2001/XMLSchema}date" minOccurs="0"/&gt;
 *         &lt;element name="stopUsingDate" type="{http://www.w3.org/2001/XMLSchema}date" minOccurs="0"/&gt;
 *         &lt;element name="updateTime" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "FrequencyRangeInfo", propOrder = {
    "id",
    "beginFreq",
    "description",
    "endFreq",
    "isContinue",
    "isLocal",
    "level",
    "name",
    "oprType",
    "parentId",
    "startUsingDate",
    "stopUsingDate",
    "updateTime"
})
public class FrequencyRangeInfo {

    @XmlElement(name = "ID", nillable = true)
    protected String id;
    @XmlElement(nillable = true)
    protected Double beginFreq;
    @XmlElement(nillable = true)
    protected String description;
    @XmlElement(nillable = true)
    protected Double endFreq;
    @XmlElement(nillable = true)
    protected Boolean isContinue;
    @XmlElement(nillable = true)
    protected Boolean isLocal;
    @XmlElement(nillable = true)
    protected Byte level;
    @XmlElement(nillable = true)
    protected String name;
    @XmlElement(nillable = true)
    protected String oprType;
    @XmlElement(nillable = true)
    protected String parentId;
    @XmlElement(nillable = true)
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar startUsingDate;
    @XmlElement(nillable = true)
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar stopUsingDate;
    @XmlElement(nillable = true)
    protected String updateTime;

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
     * 获取beginFreq属性的值。
     * 
     * @return
     *     possible object is
     *     {@link Double }
     *     
     */
    public Double getBeginFreq() {
        return beginFreq;
    }

    /**
     * 设置beginFreq属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link Double }
     *     
     */
    public void setBeginFreq(Double value) {
        this.beginFreq = value;
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
     * 获取endFreq属性的值。
     * 
     * @return
     *     possible object is
     *     {@link Double }
     *     
     */
    public Double getEndFreq() {
        return endFreq;
    }

    /**
     * 设置endFreq属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link Double }
     *     
     */
    public void setEndFreq(Double value) {
        this.endFreq = value;
    }

    /**
     * 获取isContinue属性的值。
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isIsContinue() {
        return isContinue;
    }

    /**
     * 设置isContinue属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setIsContinue(Boolean value) {
        this.isContinue = value;
    }

    /**
     * 获取isLocal属性的值。
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isIsLocal() {
        return isLocal;
    }

    /**
     * 设置isLocal属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setIsLocal(Boolean value) {
        this.isLocal = value;
    }

    /**
     * 获取level属性的值。
     * 
     * @return
     *     possible object is
     *     {@link Byte }
     *     
     */
    public Byte getLevel() {
        return level;
    }

    /**
     * 设置level属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link Byte }
     *     
     */
    public void setLevel(Byte value) {
        this.level = value;
    }

    /**
     * 获取name属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getName() {
        return name;
    }

    /**
     * 设置name属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setName(String value) {
        this.name = value;
    }

    /**
     * 获取oprType属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOprType() {
        return oprType;
    }

    /**
     * 设置oprType属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOprType(String value) {
        this.oprType = value;
    }

    /**
     * 获取parentId属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getParentId() {
        return parentId;
    }

    /**
     * 设置parentId属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setParentId(String value) {
        this.parentId = value;
    }

    /**
     * 获取startUsingDate属性的值。
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getStartUsingDate() {
        return startUsingDate;
    }

    /**
     * 设置startUsingDate属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setStartUsingDate(XMLGregorianCalendar value) {
        this.startUsingDate = value;
    }

    /**
     * 获取stopUsingDate属性的值。
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getStopUsingDate() {
        return stopUsingDate;
    }

    /**
     * 设置stopUsingDate属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setStopUsingDate(XMLGregorianCalendar value) {
        this.stopUsingDate = value;
    }

    /**
     * 获取updateTime属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUpdateTime() {
        return updateTime;
    }

    /**
     * 设置updateTime属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUpdateTime(String value) {
        this.updateTime = value;
    }

}
