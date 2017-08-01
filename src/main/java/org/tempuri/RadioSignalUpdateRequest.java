
package org.tempuri;

import java.math.BigInteger;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>RadioSignalUpdateRequest complex type�� Java �ࡣ
 * 
 * <p>����ģʽƬ��ָ�������ڴ����е�Ԥ�����ݡ�
 * 
 * <pre>
 * &lt;complexType name="RadioSignalUpdateRequest"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="ID" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="Name" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="CenterFreq" type="{http://www.w3.org/2001/XMLSchema}unsignedLong"/&gt;
 *         &lt;element name="BandWidth" type="{http://www.w3.org/2001/XMLSchema}unsignedInt"/&gt;
 *         &lt;element name="AreaCode" type="{http://www.w3.org/2001/XMLSchema}int"/&gt;
 *         &lt;element name="TypeCode" type="{http://www.w3.org/2001/XMLSchema}int"/&gt;
 *         &lt;element name="Longitude" type="{http://www.w3.org/2001/XMLSchema}double"/&gt;
 *         &lt;element name="Latitude" type="{http://www.w3.org/2001/XMLSchema}double"/&gt;
 *         &lt;element name="Altitude" type="{http://www.w3.org/2001/XMLSchema}double"/&gt;
 *         &lt;element name="StationKey" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="IsInvalid" type="{http://www.w3.org/2001/XMLSchema}boolean"/&gt;
 *         &lt;element name="SaveDate" type="{http://www.w3.org/2001/XMLSchema}dateTime"/&gt;
 *         &lt;element name="InvalidDate" type="{http://www.w3.org/2001/XMLSchema}dateTime"/&gt;
 *         &lt;element name="WarningFreqID" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="ExtendFields" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="Des" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "RadioSignalUpdateRequest", propOrder = {
    "id",
    "name",
    "centerFreq",
    "bandWidth",
    "areaCode",
    "typeCode",
    "longitude",
    "latitude",
    "altitude",
    "stationKey",
    "isInvalid",
    "saveDate",
    "invalidDate",
    "warningFreqID",
    "extendFields",
    "des"
})
public class RadioSignalUpdateRequest {

    @XmlElement(name = "ID")
    protected String id;
    @XmlElement(name = "Name")
    protected String name;
    @XmlElement(name = "CenterFreq", required = true, nillable = true)
    @XmlSchemaType(name = "unsignedLong")
    protected BigInteger centerFreq;
    @XmlElement(name = "BandWidth", required = true, type = Long.class, nillable = true)
    @XmlSchemaType(name = "unsignedInt")
    protected Long bandWidth;
    @XmlElement(name = "AreaCode", required = true, type = Integer.class, nillable = true)
    protected Integer areaCode;
    @XmlElement(name = "TypeCode", required = true, type = Integer.class, nillable = true)
    protected Integer typeCode;
    @XmlElement(name = "Longitude", required = true, type = Double.class, nillable = true)
    protected Double longitude;
    @XmlElement(name = "Latitude", required = true, type = Double.class, nillable = true)
    protected Double latitude;
    @XmlElement(name = "Altitude", required = true, type = Double.class, nillable = true)
    protected Double altitude;
    @XmlElement(name = "StationKey")
    protected String stationKey;
    @XmlElement(name = "IsInvalid", required = true, type = Boolean.class, nillable = true)
    protected Boolean isInvalid;
    @XmlElement(name = "SaveDate", required = true, nillable = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar saveDate;
    @XmlElement(name = "InvalidDate", required = true, nillable = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar invalidDate;
    @XmlElement(name = "WarningFreqID")
    protected String warningFreqID;
    @XmlElement(name = "ExtendFields")
    protected String extendFields;
    @XmlElement(name = "Des")
    protected String des;

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
     * ��ȡname���Ե�ֵ��
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
     * ����name���Ե�ֵ��
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
     *     {@link Long }
     *     
     */
    public Long getBandWidth() {
        return bandWidth;
    }

    /**
     * ����bandWidth���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setBandWidth(Long value) {
        this.bandWidth = value;
    }

    /**
     * ��ȡareaCode���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getAreaCode() {
        return areaCode;
    }

    /**
     * ����areaCode���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setAreaCode(Integer value) {
        this.areaCode = value;
    }

    /**
     * ��ȡtypeCode���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getTypeCode() {
        return typeCode;
    }

    /**
     * ����typeCode���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setTypeCode(Integer value) {
        this.typeCode = value;
    }

    /**
     * ��ȡlongitude���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link Double }
     *     
     */
    public Double getLongitude() {
        return longitude;
    }

    /**
     * ����longitude���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link Double }
     *     
     */
    public void setLongitude(Double value) {
        this.longitude = value;
    }

    /**
     * ��ȡlatitude���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link Double }
     *     
     */
    public Double getLatitude() {
        return latitude;
    }

    /**
     * ����latitude���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link Double }
     *     
     */
    public void setLatitude(Double value) {
        this.latitude = value;
    }

    /**
     * ��ȡaltitude���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link Double }
     *     
     */
    public Double getAltitude() {
        return altitude;
    }

    /**
     * ����altitude���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link Double }
     *     
     */
    public void setAltitude(Double value) {
        this.altitude = value;
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
     * ��ȡisInvalid���Ե�ֵ��
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
     * ����isInvalid���Ե�ֵ��
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
     * ��ȡwarningFreqID���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getWarningFreqID() {
        return warningFreqID;
    }

    /**
     * ����warningFreqID���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setWarningFreqID(String value) {
        this.warningFreqID = value;
    }

    /**
     * ��ȡextendFields���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getExtendFields() {
        return extendFields;
    }

    /**
     * ����extendFields���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setExtendFields(String value) {
        this.extendFields = value;
    }

    /**
     * ��ȡdes���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDes() {
        return des;
    }

    /**
     * ����des���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDes(String value) {
        this.des = value;
    }

}
