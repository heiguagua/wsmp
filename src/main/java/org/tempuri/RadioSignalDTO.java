
package org.tempuri;

import java.math.BigInteger;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>RadioSignalDTO complex type的 Java 类。
 * 
 * <p>以下模式片段指定包含在此类中的预期内容。
 * 
 * <pre>
 * &lt;complexType name="RadioSignalDTO"&gt;
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
 *         &lt;element name="ExtendFields" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="InvalidDate" type="{http://www.w3.org/2001/XMLSchema}dateTime"/&gt;
 *         &lt;element name="Des" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="AppendDTOs" type="{http://tempuri.org/}ArrayOfRadioSignalAppendDTO" minOccurs="0"/&gt;
 *         &lt;element name="StationDTOs" type="{http://tempuri.org/}ArrayOfRadioSignalStationDTO" minOccurs="0"/&gt;
 *         &lt;element name="RadioStation" type="{http://tempuri.org/}RadioStationSignalDTO" minOccurs="0"/&gt;
 *         &lt;element name="AbnormalHistory" type="{http://tempuri.org/}ArrayOfRadioSignalAbnormalHistoryDTO" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "RadioSignalDTO", propOrder = {
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
    "extendFields",
    "invalidDate",
    "des",
    "appendDTOs",
    "stationDTOs",
    "radioStation",
    "abnormalHistory"
})
public class RadioSignalDTO {

    @XmlElement(name = "ID")
    protected String id;
    @XmlElement(name = "Name")
    protected String name;
    @XmlElement(name = "CenterFreq", required = true)
    @XmlSchemaType(name = "unsignedLong")
    protected BigInteger centerFreq;
    @XmlElement(name = "BandWidth")
    @XmlSchemaType(name = "unsignedInt")
    protected long bandWidth;
    @XmlElement(name = "AreaCode")
    protected int areaCode;
    @XmlElement(name = "TypeCode")
    protected int typeCode;
    @XmlElement(name = "Longitude")
    protected double longitude;
    @XmlElement(name = "Latitude")
    protected double latitude;
    @XmlElement(name = "Altitude")
    protected double altitude;
    @XmlElement(name = "StationKey")
    protected String stationKey;
    @XmlElement(name = "IsInvalid")
    protected boolean isInvalid;
    @XmlElement(name = "SaveDate", required = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar saveDate;
    @XmlElement(name = "ExtendFields")
    protected String extendFields;
    @XmlElement(name = "InvalidDate", required = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar invalidDate;
    @XmlElement(name = "Des")
    protected String des;
    @XmlElement(name = "AppendDTOs")
    protected ArrayOfRadioSignalAppendDTO appendDTOs;
    @XmlElement(name = "StationDTOs")
    protected ArrayOfRadioSignalStationDTO stationDTOs;
    @XmlElement(name = "RadioStation")
    protected RadioStationSignalDTO radioStation;
    @XmlElement(name = "AbnormalHistory")
    protected ArrayOfRadioSignalAbnormalHistoryDTO abnormalHistory;

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
     */
    public long getBandWidth() {
        return bandWidth;
    }

    /**
     * 设置bandWidth属性的值。
     * 
     */
    public void setBandWidth(long value) {
        this.bandWidth = value;
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
     * 获取typeCode属性的值。
     * 
     */
    public int getTypeCode() {
        return typeCode;
    }

    /**
     * 设置typeCode属性的值。
     * 
     */
    public void setTypeCode(int value) {
        this.typeCode = value;
    }

    /**
     * 获取longitude属性的值。
     * 
     */
    public double getLongitude() {
        return longitude;
    }

    /**
     * 设置longitude属性的值。
     * 
     */
    public void setLongitude(double value) {
        this.longitude = value;
    }

    /**
     * 获取latitude属性的值。
     * 
     */
    public double getLatitude() {
        return latitude;
    }

    /**
     * 设置latitude属性的值。
     * 
     */
    public void setLatitude(double value) {
        this.latitude = value;
    }

    /**
     * 获取altitude属性的值。
     * 
     */
    public double getAltitude() {
        return altitude;
    }

    /**
     * 设置altitude属性的值。
     * 
     */
    public void setAltitude(double value) {
        this.altitude = value;
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
     * 获取extendFields属性的值。
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
     * 设置extendFields属性的值。
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
     * 获取des属性的值。
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
     * 设置des属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDes(String value) {
        this.des = value;
    }

    /**
     * 获取appendDTOs属性的值。
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfRadioSignalAppendDTO }
     *     
     */
    public ArrayOfRadioSignalAppendDTO getAppendDTOs() {
        return appendDTOs;
    }

    /**
     * 设置appendDTOs属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfRadioSignalAppendDTO }
     *     
     */
    public void setAppendDTOs(ArrayOfRadioSignalAppendDTO value) {
        this.appendDTOs = value;
    }

    /**
     * 获取stationDTOs属性的值。
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfRadioSignalStationDTO }
     *     
     */
    public ArrayOfRadioSignalStationDTO getStationDTOs() {
        return stationDTOs;
    }

    /**
     * 设置stationDTOs属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfRadioSignalStationDTO }
     *     
     */
    public void setStationDTOs(ArrayOfRadioSignalStationDTO value) {
        this.stationDTOs = value;
    }

    /**
     * 获取radioStation属性的值。
     * 
     * @return
     *     possible object is
     *     {@link RadioStationSignalDTO }
     *     
     */
    public RadioStationSignalDTO getRadioStation() {
        return radioStation;
    }

    /**
     * 设置radioStation属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link RadioStationSignalDTO }
     *     
     */
    public void setRadioStation(RadioStationSignalDTO value) {
        this.radioStation = value;
    }

    /**
     * 获取abnormalHistory属性的值。
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfRadioSignalAbnormalHistoryDTO }
     *     
     */
    public ArrayOfRadioSignalAbnormalHistoryDTO getAbnormalHistory() {
        return abnormalHistory;
    }

    /**
     * 设置abnormalHistory属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfRadioSignalAbnormalHistoryDTO }
     *     
     */
    public void setAbnormalHistory(ArrayOfRadioSignalAbnormalHistoryDTO value) {
        this.abnormalHistory = value;
    }

}
