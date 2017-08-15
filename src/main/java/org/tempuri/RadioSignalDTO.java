
package org.tempuri;

import javax.xml.bind.annotation.*;
import javax.xml.datatype.XMLGregorianCalendar;
import java.math.BigInteger;


/**
 * <p>RadioSignalDTO complex type�� Java �ࡣ
 * 
 * <p>����ģʽƬ��ָ�������ڴ����е�Ԥ�����ݡ�
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
 *         &lt;element name="WarningFreqID" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
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
    "warningFreqID",
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
    @XmlElement(name = "WarningFreqID")
    protected String warningFreqID;
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
     */
    public long getBandWidth() {
        return bandWidth;
    }

    /**
     * ����bandWidth���Ե�ֵ��
     * 
     */
    public void setBandWidth(long value) {
        this.bandWidth = value;
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
     * ��ȡtypeCode���Ե�ֵ��
     * 
     */
    public int getTypeCode() {
        return typeCode;
    }

    /**
     * ����typeCode���Ե�ֵ��
     * 
     */
    public void setTypeCode(int value) {
        this.typeCode = value;
    }

    /**
     * ��ȡlongitude���Ե�ֵ��
     * 
     */
    public double getLongitude() {
        return longitude;
    }

    /**
     * ����longitude���Ե�ֵ��
     * 
     */
    public void setLongitude(double value) {
        this.longitude = value;
    }

    /**
     * ��ȡlatitude���Ե�ֵ��
     * 
     */
    public double getLatitude() {
        return latitude;
    }

    /**
     * ����latitude���Ե�ֵ��
     * 
     */
    public void setLatitude(double value) {
        this.latitude = value;
    }

    /**
     * ��ȡaltitude���Ե�ֵ��
     * 
     */
    public double getAltitude() {
        return altitude;
    }

    /**
     * ����altitude���Ե�ֵ��
     * 
     */
    public void setAltitude(double value) {
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

    /**
     * ��ȡappendDTOs���Ե�ֵ��
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
     * ����appendDTOs���Ե�ֵ��
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
     * ��ȡstationDTOs���Ե�ֵ��
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
     * ����stationDTOs���Ե�ֵ��
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
     * ��ȡradioStation���Ե�ֵ��
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
     * ����radioStation���Ե�ֵ��
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
     * ��ȡabnormalHistory���Ե�ֵ��
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
     * ����abnormalHistory���Ե�ֵ��
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
