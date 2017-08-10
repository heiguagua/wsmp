
package com.sefon.ws.model.xsd;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>StationInfo complex type的 Java 类。
 * 
 * <p>以下模式片段指定包含在此类中的预期内容。
 * 
 * <pre>
 * &lt;complexType name="StationInfo"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="ANT_Gain" type="{http://www.w3.org/2001/XMLSchema}double" minOccurs="0"/&gt;
 *         &lt;element name="ANT_Hight" type="{http://www.w3.org/2001/XMLSchema}double" minOccurs="0"/&gt;
 *         &lt;element name="EQU_AUTH" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="EQU_Model" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="FEED_Lose" type="{http://www.w3.org/2001/XMLSchema}double" minOccurs="0"/&gt;
 *         &lt;element name="FEE_GUID" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="FREQ_Code" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="FREQ_EFB" type="{http://www.w3.org/2001/XMLSchema}double" minOccurs="0"/&gt;
 *         &lt;element name="FREQ_EFE" type="{http://www.w3.org/2001/XMLSchema}double" minOccurs="0"/&gt;
 *         &lt;element name="FREQ_E_Band" type="{http://www.w3.org/2001/XMLSchema}double" minOccurs="0"/&gt;
 *         &lt;element name="FREQ_E_BandStr" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="FREQ_LC" type="{http://www.w3.org/2001/XMLSchema}double" minOccurs="0"/&gt;
 *         &lt;element name="FREQ_MB" type="{http://www.w3.org/2001/XMLSchema}byte" minOccurs="0"/&gt;
 *         &lt;element name="FREQ_MOD" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="FREQ_MODStr" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="FREQ_Type" type="{http://www.w3.org/2001/XMLSchema}byte" minOccurs="0"/&gt;
 *         &lt;element name="FREQ_TypeStr" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="FREQ_UC" type="{http://www.w3.org/2001/XMLSchema}double" minOccurs="0"/&gt;
 *         &lt;element name="FT_FREQ_EPOW" type="{http://www.w3.org/2001/XMLSchema}double" minOccurs="0"/&gt;
 *         &lt;element name="FT_FREQ_POWFLAG" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="ID" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/&gt;
 *         &lt;element name="MEMO" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="NET_Area" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="NET_AreaStr" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="NET_Band" type="{http://www.w3.org/2001/XMLSchema}double" minOccurs="0"/&gt;
 *         &lt;element name="NET_BandStr" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="NET_Confirm_Date" type="{http://www.w3.org/2001/XMLSchema}date" minOccurs="0"/&gt;
 *         &lt;element name="NET_Confirm_DateStr" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="NET_Expired_Date" type="{http://www.w3.org/2001/XMLSchema}date" minOccurs="0"/&gt;
 *         &lt;element name="NET_Expired_DateStr" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="NET_SP" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="NET_SPStr" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="NET_SVN" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="NET_Start_Date" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="NET_Start_DateStr" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="NET_TS" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="NET_TSStr" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="STAT_ADDR" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="STAT_AT" type="{http://www.w3.org/2001/XMLSchema}double" minOccurs="0"/&gt;
 *         &lt;element name="STAT_Area_Code" type="{http://www.w3.org/2001/XMLSchema}short" minOccurs="0"/&gt;
 *         &lt;element name="STAT_Date_Start" type="{http://www.w3.org/2001/XMLSchema}date" minOccurs="0"/&gt;
 *         &lt;element name="STAT_Date_StartStr" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="STAT_EQU_SUM" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/&gt;
 *         &lt;element name="STAT_LA" type="{http://www.w3.org/2001/XMLSchema}double" minOccurs="0"/&gt;
 *         &lt;element name="STAT_LG" type="{http://www.w3.org/2001/XMLSchema}double" minOccurs="0"/&gt;
 *         &lt;element name="STAT_Name" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="STAT_Status" type="{http://www.w3.org/2001/XMLSchema}byte" minOccurs="0"/&gt;
 *         &lt;element name="STAT_StatusStr" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="STAT_Type" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="STAT_TypeStr" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="STAT_Work" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="USER_Name" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="_bInitInfo" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/&gt;
 *         &lt;element name="beginFreq" type="{http://www.w3.org/2001/XMLSchema}double" minOccurs="0"/&gt;
 *         &lt;element name="centerFreq" type="{http://www.w3.org/2001/XMLSchema}double" minOccurs="0"/&gt;
 *         &lt;element name="centerFreqStr" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="endFreq" type="{http://www.w3.org/2001/XMLSchema}double" minOccurs="0"/&gt;
 *         &lt;element name="freqID" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="imagePath" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="isLoad" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/&gt;
 *         &lt;element name="net_SVNStr" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="node_MaxLevels" type="{http://www.w3.org/2001/XMLSchema}byte" maxOccurs="unbounded" minOccurs="0"/&gt;
 *         &lt;element name="node_MinLevels" type="{http://www.w3.org/2001/XMLSchema}byte" maxOccurs="unbounded" minOccurs="0"/&gt;
 *         &lt;element name="stationFreqID" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="stationFreqIdHash" type="{http://www.w3.org/2001/XMLSchema}long" minOccurs="0"/&gt;
 *         &lt;element name="stationID" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "StationInfo", propOrder = {
    "antGain",
    "antHight",
    "equauth",
    "equModel",
    "feedLose",
    "feeguid",
    "freqCode",
    "freqefb",
    "freqefe",
    "freqeBand",
    "freqeBandStr",
    "freqlc",
    "freqmb",
    "freqmod",
    "freqmodStr",
    "freqType",
    "freqTypeStr",
    "frequc",
    "ftfreqepow",
    "ftfreqpowflag",
    "id",
    "memo",
    "netArea",
    "netAreaStr",
    "netBand",
    "netBandStr",
    "netConfirmDate",
    "netConfirmDateStr",
    "netExpiredDate",
    "netExpiredDateStr",
    "netsp",
    "netspStr",
    "netsvn",
    "netStartDate",
    "netStartDateStr",
    "netts",
    "nettsStr",
    "stataddr",
    "statat",
    "statAreaCode",
    "statDateStart",
    "statDateStartStr",
    "statequsum",
    "statla",
    "statlg",
    "statName",
    "statStatus",
    "statStatusStr",
    "statType",
    "statTypeStr",
    "statWork",
    "userName",
    "bInitInfo",
    "beginFreq",
    "centerFreq",
    "centerFreqStr",
    "endFreq",
    "freqID",
    "imagePath",
    "isLoad",
    "netSVNStr",
    "nodeMaxLevels",
    "nodeMinLevels",
    "stationFreqID",
    "stationFreqIdHash",
    "stationID"
})
public class StationInfo {

    @XmlElement(name = "ANT_Gain", nillable = true)
    protected Double antGain;
    @XmlElement(name = "ANT_Hight", nillable = true)
    protected Double antHight;
    @XmlElement(name = "EQU_AUTH", nillable = true)
    protected String equauth;
    @XmlElement(name = "EQU_Model", nillable = true)
    protected String equModel;
    @XmlElement(name = "FEED_Lose", nillable = true)
    protected Double feedLose;
    @XmlElement(name = "FEE_GUID", nillable = true)
    protected String feeguid;
    @XmlElement(name = "FREQ_Code", nillable = true)
    protected String freqCode;
    @XmlElement(name = "FREQ_EFB", nillable = true)
    protected Double freqefb;
    @XmlElement(name = "FREQ_EFE", nillable = true)
    protected Double freqefe;
    @XmlElement(name = "FREQ_E_Band", nillable = true)
    protected Double freqeBand;
    @XmlElement(name = "FREQ_E_BandStr", nillable = true)
    protected String freqeBandStr;
    @XmlElement(name = "FREQ_LC", nillable = true)
    protected Double freqlc;
    @XmlElement(name = "FREQ_MB", nillable = true)
    protected Byte freqmb;
    @XmlElement(name = "FREQ_MOD", nillable = true)
    protected String freqmod;
    @XmlElement(name = "FREQ_MODStr", nillable = true)
    protected String freqmodStr;
    @XmlElement(name = "FREQ_Type", nillable = true)
    protected Byte freqType;
    @XmlElement(name = "FREQ_TypeStr", nillable = true)
    protected String freqTypeStr;
    @XmlElement(name = "FREQ_UC", nillable = true)
    protected Double frequc;
    @XmlElement(name = "FT_FREQ_EPOW", nillable = true)
    protected Double ftfreqepow;
    @XmlElement(name = "FT_FREQ_POWFLAG", nillable = true)
    protected String ftfreqpowflag;
    @XmlElement(name = "ID", nillable = true)
    protected Integer id;
    @XmlElement(name = "MEMO", nillable = true)
    protected String memo;
    @XmlElement(name = "NET_Area", nillable = true)
    protected String netArea;
    @XmlElement(name = "NET_AreaStr", nillable = true)
    protected String netAreaStr;
    @XmlElement(name = "NET_Band", nillable = true)
    protected Double netBand;
    @XmlElement(name = "NET_BandStr", nillable = true)
    protected String netBandStr;
    @XmlElement(name = "NET_Confirm_Date", nillable = true)
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar netConfirmDate;
    @XmlElement(name = "NET_Confirm_DateStr", nillable = true)
    protected String netConfirmDateStr;
    @XmlElement(name = "NET_Expired_Date", nillable = true)
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar netExpiredDate;
    @XmlElement(name = "NET_Expired_DateStr", nillable = true)
    protected String netExpiredDateStr;
    @XmlElement(name = "NET_SP", nillable = true)
    protected String netsp;
    @XmlElement(name = "NET_SPStr", nillable = true)
    protected String netspStr;
    @XmlElement(name = "NET_SVN", nillable = true)
    protected String netsvn;
    @XmlElement(name = "NET_Start_Date", nillable = true)
    protected String netStartDate;
    @XmlElement(name = "NET_Start_DateStr", nillable = true)
    protected String netStartDateStr;
    @XmlElement(name = "NET_TS", nillable = true)
    protected String netts;
    @XmlElement(name = "NET_TSStr", nillable = true)
    protected String nettsStr;
    @XmlElement(name = "STAT_ADDR", nillable = true)
    protected String stataddr;
    @XmlElement(name = "STAT_AT", nillable = true)
    protected Double statat;
    @XmlElement(name = "STAT_Area_Code", nillable = true)
    protected Short statAreaCode;
    @XmlElement(name = "STAT_Date_Start", nillable = true)
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar statDateStart;
    @XmlElement(name = "STAT_Date_StartStr", nillable = true)
    protected String statDateStartStr;
    @XmlElement(name = "STAT_EQU_SUM", nillable = true)
    protected Integer statequsum;
    @XmlElement(name = "STAT_LA", nillable = true)
    protected Double statla;
    @XmlElement(name = "STAT_LG", nillable = true)
    protected Double statlg;
    @XmlElement(name = "STAT_Name", nillable = true)
    protected String statName;
    @XmlElement(name = "STAT_Status", nillable = true)
    protected Byte statStatus;
    @XmlElement(name = "STAT_StatusStr", nillable = true)
    protected String statStatusStr;
    @XmlElement(name = "STAT_Type", nillable = true)
    protected String statType;
    @XmlElement(name = "STAT_TypeStr", nillable = true)
    protected String statTypeStr;
    @XmlElement(name = "STAT_Work", nillable = true)
    protected String statWork;
    @XmlElement(name = "USER_Name", nillable = true)
    protected String userName;
    @XmlElement(name = "_bInitInfo", nillable = true)
    protected Boolean bInitInfo;
    @XmlElement(nillable = true)
    protected Double beginFreq;
    @XmlElement(nillable = true)
    protected Double centerFreq;
    @XmlElement(nillable = true)
    protected String centerFreqStr;
    @XmlElement(nillable = true)
    protected Double endFreq;
    @XmlElement(nillable = true)
    protected String freqID;
    @XmlElement(nillable = true)
    protected String imagePath;
    @XmlElement(nillable = true)
    protected Boolean isLoad;
    @XmlElement(name = "net_SVNStr", nillable = true)
    protected String netSVNStr;
    @XmlElement(name = "node_MaxLevels", nillable = true)
    protected List<Byte> nodeMaxLevels;
    @XmlElement(name = "node_MinLevels", nillable = true)
    protected List<Byte> nodeMinLevels;
    @XmlElement(nillable = true)
    protected String stationFreqID;
    @XmlElement(nillable = true)
    protected Long stationFreqIdHash;
    @XmlElement(nillable = true)
    protected String stationID;

    /**
     * 获取antGain属性的值。
     * 
     * @return
     *     possible object is
     *     {@link Double }
     *     
     */
    public Double getANTGain() {
        return antGain;
    }

    /**
     * 设置antGain属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link Double }
     *     
     */
    public void setANTGain(Double value) {
        this.antGain = value;
    }

    /**
     * 获取antHight属性的值。
     * 
     * @return
     *     possible object is
     *     {@link Double }
     *     
     */
    public Double getANTHight() {
        return antHight;
    }

    /**
     * 设置antHight属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link Double }
     *     
     */
    public void setANTHight(Double value) {
        this.antHight = value;
    }

    /**
     * 获取equauth属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEQUAUTH() {
        return equauth;
    }

    /**
     * 设置equauth属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEQUAUTH(String value) {
        this.equauth = value;
    }

    /**
     * 获取equModel属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEQUModel() {
        return equModel;
    }

    /**
     * 设置equModel属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEQUModel(String value) {
        this.equModel = value;
    }

    /**
     * 获取feedLose属性的值。
     * 
     * @return
     *     possible object is
     *     {@link Double }
     *     
     */
    public Double getFEEDLose() {
        return feedLose;
    }

    /**
     * 设置feedLose属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link Double }
     *     
     */
    public void setFEEDLose(Double value) {
        this.feedLose = value;
    }

    /**
     * 获取feeguid属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFEEGUID() {
        return feeguid;
    }

    /**
     * 设置feeguid属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFEEGUID(String value) {
        this.feeguid = value;
    }

    /**
     * 获取freqCode属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFREQCode() {
        return freqCode;
    }

    /**
     * 设置freqCode属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFREQCode(String value) {
        this.freqCode = value;
    }

    /**
     * 获取freqefb属性的值。
     * 
     * @return
     *     possible object is
     *     {@link Double }
     *     
     */
    public Double getFREQEFB() {
        return freqefb;
    }

    /**
     * 设置freqefb属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link Double }
     *     
     */
    public void setFREQEFB(Double value) {
        this.freqefb = value;
    }

    /**
     * 获取freqefe属性的值。
     * 
     * @return
     *     possible object is
     *     {@link Double }
     *     
     */
    public Double getFREQEFE() {
        return freqefe;
    }

    /**
     * 设置freqefe属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link Double }
     *     
     */
    public void setFREQEFE(Double value) {
        this.freqefe = value;
    }

    /**
     * 获取freqeBand属性的值。
     * 
     * @return
     *     possible object is
     *     {@link Double }
     *     
     */
    public Double getFREQEBand() {
        return freqeBand;
    }

    /**
     * 设置freqeBand属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link Double }
     *     
     */
    public void setFREQEBand(Double value) {
        this.freqeBand = value;
    }

    /**
     * 获取freqeBandStr属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFREQEBandStr() {
        return freqeBandStr;
    }

    /**
     * 设置freqeBandStr属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFREQEBandStr(String value) {
        this.freqeBandStr = value;
    }

    /**
     * 获取freqlc属性的值。
     * 
     * @return
     *     possible object is
     *     {@link Double }
     *     
     */
    public Double getFREQLC() {
        return freqlc;
    }

    /**
     * 设置freqlc属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link Double }
     *     
     */
    public void setFREQLC(Double value) {
        this.freqlc = value;
    }

    /**
     * 获取freqmb属性的值。
     * 
     * @return
     *     possible object is
     *     {@link Byte }
     *     
     */
    public Byte getFREQMB() {
        return freqmb;
    }

    /**
     * 设置freqmb属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link Byte }
     *     
     */
    public void setFREQMB(Byte value) {
        this.freqmb = value;
    }

    /**
     * 获取freqmod属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFREQMOD() {
        return freqmod;
    }

    /**
     * 设置freqmod属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFREQMOD(String value) {
        this.freqmod = value;
    }

    /**
     * 获取freqmodStr属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFREQMODStr() {
        return freqmodStr;
    }

    /**
     * 设置freqmodStr属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFREQMODStr(String value) {
        this.freqmodStr = value;
    }

    /**
     * 获取freqType属性的值。
     * 
     * @return
     *     possible object is
     *     {@link Byte }
     *     
     */
    public Byte getFREQType() {
        return freqType;
    }

    /**
     * 设置freqType属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link Byte }
     *     
     */
    public void setFREQType(Byte value) {
        this.freqType = value;
    }

    /**
     * 获取freqTypeStr属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFREQTypeStr() {
        return freqTypeStr;
    }

    /**
     * 设置freqTypeStr属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFREQTypeStr(String value) {
        this.freqTypeStr = value;
    }

    /**
     * 获取frequc属性的值。
     * 
     * @return
     *     possible object is
     *     {@link Double }
     *     
     */
    public Double getFREQUC() {
        return frequc;
    }

    /**
     * 设置frequc属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link Double }
     *     
     */
    public void setFREQUC(Double value) {
        this.frequc = value;
    }

    /**
     * 获取ftfreqepow属性的值。
     * 
     * @return
     *     possible object is
     *     {@link Double }
     *     
     */
    public Double getFTFREQEPOW() {
        return ftfreqepow;
    }

    /**
     * 设置ftfreqepow属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link Double }
     *     
     */
    public void setFTFREQEPOW(Double value) {
        this.ftfreqepow = value;
    }

    /**
     * 获取ftfreqpowflag属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFTFREQPOWFLAG() {
        return ftfreqpowflag;
    }

    /**
     * 设置ftfreqpowflag属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFTFREQPOWFLAG(String value) {
        this.ftfreqpowflag = value;
    }

    /**
     * 获取id属性的值。
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getID() {
        return id;
    }

    /**
     * 设置id属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setID(Integer value) {
        this.id = value;
    }

    /**
     * 获取memo属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMEMO() {
        return memo;
    }

    /**
     * 设置memo属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMEMO(String value) {
        this.memo = value;
    }

    /**
     * 获取netArea属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNETArea() {
        return netArea;
    }

    /**
     * 设置netArea属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNETArea(String value) {
        this.netArea = value;
    }

    /**
     * 获取netAreaStr属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNETAreaStr() {
        return netAreaStr;
    }

    /**
     * 设置netAreaStr属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNETAreaStr(String value) {
        this.netAreaStr = value;
    }

    /**
     * 获取netBand属性的值。
     * 
     * @return
     *     possible object is
     *     {@link Double }
     *     
     */
    public Double getNETBand() {
        return netBand;
    }

    /**
     * 设置netBand属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link Double }
     *     
     */
    public void setNETBand(Double value) {
        this.netBand = value;
    }

    /**
     * 获取netBandStr属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNETBandStr() {
        return netBandStr;
    }

    /**
     * 设置netBandStr属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNETBandStr(String value) {
        this.netBandStr = value;
    }

    /**
     * 获取netConfirmDate属性的值。
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getNETConfirmDate() {
        return netConfirmDate;
    }

    /**
     * 设置netConfirmDate属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setNETConfirmDate(XMLGregorianCalendar value) {
        this.netConfirmDate = value;
    }

    /**
     * 获取netConfirmDateStr属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNETConfirmDateStr() {
        return netConfirmDateStr;
    }

    /**
     * 设置netConfirmDateStr属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNETConfirmDateStr(String value) {
        this.netConfirmDateStr = value;
    }

    /**
     * 获取netExpiredDate属性的值。
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getNETExpiredDate() {
        return netExpiredDate;
    }

    /**
     * 设置netExpiredDate属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setNETExpiredDate(XMLGregorianCalendar value) {
        this.netExpiredDate = value;
    }

    /**
     * 获取netExpiredDateStr属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNETExpiredDateStr() {
        return netExpiredDateStr;
    }

    /**
     * 设置netExpiredDateStr属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNETExpiredDateStr(String value) {
        this.netExpiredDateStr = value;
    }

    /**
     * 获取netsp属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNETSP() {
        return netsp;
    }

    /**
     * 设置netsp属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNETSP(String value) {
        this.netsp = value;
    }

    /**
     * 获取netspStr属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNETSPStr() {
        return netspStr;
    }

    /**
     * 设置netspStr属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNETSPStr(String value) {
        this.netspStr = value;
    }

    /**
     * 获取netsvn属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNETSVN() {
        return netsvn;
    }

    /**
     * 设置netsvn属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNETSVN(String value) {
        this.netsvn = value;
    }

    /**
     * 获取netStartDate属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNETStartDate() {
        return netStartDate;
    }

    /**
     * 设置netStartDate属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNETStartDate(String value) {
        this.netStartDate = value;
    }

    /**
     * 获取netStartDateStr属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNETStartDateStr() {
        return netStartDateStr;
    }

    /**
     * 设置netStartDateStr属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNETStartDateStr(String value) {
        this.netStartDateStr = value;
    }

    /**
     * 获取netts属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNETTS() {
        return netts;
    }

    /**
     * 设置netts属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNETTS(String value) {
        this.netts = value;
    }

    /**
     * 获取nettsStr属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNETTSStr() {
        return nettsStr;
    }

    /**
     * 设置nettsStr属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNETTSStr(String value) {
        this.nettsStr = value;
    }

    /**
     * 获取stataddr属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSTATADDR() {
        return stataddr;
    }

    /**
     * 设置stataddr属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSTATADDR(String value) {
        this.stataddr = value;
    }

    /**
     * 获取statat属性的值。
     * 
     * @return
     *     possible object is
     *     {@link Double }
     *     
     */
    public Double getSTATAT() {
        return statat;
    }

    /**
     * 设置statat属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link Double }
     *     
     */
    public void setSTATAT(Double value) {
        this.statat = value;
    }

    /**
     * 获取statAreaCode属性的值。
     * 
     * @return
     *     possible object is
     *     {@link Short }
     *     
     */
    public Short getSTATAreaCode() {
        return statAreaCode;
    }

    /**
     * 设置statAreaCode属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link Short }
     *     
     */
    public void setSTATAreaCode(Short value) {
        this.statAreaCode = value;
    }

    /**
     * 获取statDateStart属性的值。
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getSTATDateStart() {
        return statDateStart;
    }

    /**
     * 设置statDateStart属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setSTATDateStart(XMLGregorianCalendar value) {
        this.statDateStart = value;
    }

    /**
     * 获取statDateStartStr属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSTATDateStartStr() {
        return statDateStartStr;
    }

    /**
     * 设置statDateStartStr属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSTATDateStartStr(String value) {
        this.statDateStartStr = value;
    }

    /**
     * 获取statequsum属性的值。
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getSTATEQUSUM() {
        return statequsum;
    }

    /**
     * 设置statequsum属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setSTATEQUSUM(Integer value) {
        this.statequsum = value;
    }

    /**
     * 获取statla属性的值。
     * 
     * @return
     *     possible object is
     *     {@link Double }
     *     
     */
    public Double getSTATLA() {
        return statla;
    }

    /**
     * 设置statla属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link Double }
     *     
     */
    public void setSTATLA(Double value) {
        this.statla = value;
    }

    /**
     * 获取statlg属性的值。
     * 
     * @return
     *     possible object is
     *     {@link Double }
     *     
     */
    public Double getSTATLG() {
        return statlg;
    }

    /**
     * 设置statlg属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link Double }
     *     
     */
    public void setSTATLG(Double value) {
        this.statlg = value;
    }

    /**
     * 获取statName属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSTATName() {
        return statName;
    }

    /**
     * 设置statName属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSTATName(String value) {
        this.statName = value;
    }

    /**
     * 获取statStatus属性的值。
     * 
     * @return
     *     possible object is
     *     {@link Byte }
     *     
     */
    public Byte getSTATStatus() {
        return statStatus;
    }

    /**
     * 设置statStatus属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link Byte }
     *     
     */
    public void setSTATStatus(Byte value) {
        this.statStatus = value;
    }

    /**
     * 获取statStatusStr属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSTATStatusStr() {
        return statStatusStr;
    }

    /**
     * 设置statStatusStr属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSTATStatusStr(String value) {
        this.statStatusStr = value;
    }

    /**
     * 获取statType属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSTATType() {
        return statType;
    }

    /**
     * 设置statType属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSTATType(String value) {
        this.statType = value;
    }

    /**
     * 获取statTypeStr属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSTATTypeStr() {
        return statTypeStr;
    }

    /**
     * 设置statTypeStr属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSTATTypeStr(String value) {
        this.statTypeStr = value;
    }

    /**
     * 获取statWork属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSTATWork() {
        return statWork;
    }

    /**
     * 设置statWork属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSTATWork(String value) {
        this.statWork = value;
    }

    /**
     * 获取userName属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUSERName() {
        return userName;
    }

    /**
     * 设置userName属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUSERName(String value) {
        this.userName = value;
    }

    /**
     * 获取bInitInfo属性的值。
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isBInitInfo() {
        return bInitInfo;
    }

    /**
     * 设置bInitInfo属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setBInitInfo(Boolean value) {
        this.bInitInfo = value;
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
     * 获取centerFreq属性的值。
     * 
     * @return
     *     possible object is
     *     {@link Double }
     *     
     */
    public Double getCenterFreq() {
        return centerFreq;
    }

    /**
     * 设置centerFreq属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link Double }
     *     
     */
    public void setCenterFreq(Double value) {
        this.centerFreq = value;
    }

    /**
     * 获取centerFreqStr属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCenterFreqStr() {
        return centerFreqStr;
    }

    /**
     * 设置centerFreqStr属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCenterFreqStr(String value) {
        this.centerFreqStr = value;
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
     * 获取freqID属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFreqID() {
        return freqID;
    }

    /**
     * 设置freqID属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFreqID(String value) {
        this.freqID = value;
    }

    /**
     * 获取imagePath属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getImagePath() {
        return imagePath;
    }

    /**
     * 设置imagePath属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setImagePath(String value) {
        this.imagePath = value;
    }

    /**
     * 获取isLoad属性的值。
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isIsLoad() {
        return isLoad;
    }

    /**
     * 设置isLoad属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setIsLoad(Boolean value) {
        this.isLoad = value;
    }

    /**
     * 获取netSVNStr属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNetSVNStr() {
        return netSVNStr;
    }

    /**
     * 设置netSVNStr属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNetSVNStr(String value) {
        this.netSVNStr = value;
    }

    /**
     * Gets the value of the nodeMaxLevels property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the nodeMaxLevels property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getNodeMaxLevels().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Byte }
     * 
     * 
     */
    public List<Byte> getNodeMaxLevels() {
        if (nodeMaxLevels == null) {
            nodeMaxLevels = new ArrayList<Byte>();
        }
        return this.nodeMaxLevels;
    }

    /**
     * Gets the value of the nodeMinLevels property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the nodeMinLevels property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getNodeMinLevels().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Byte }
     * 
     * 
     */
    public List<Byte> getNodeMinLevels() {
        if (nodeMinLevels == null) {
            nodeMinLevels = new ArrayList<Byte>();
        }
        return this.nodeMinLevels;
    }

    /**
     * 获取stationFreqID属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getStationFreqID() {
        return stationFreqID;
    }

    /**
     * 设置stationFreqID属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setStationFreqID(String value) {
        this.stationFreqID = value;
    }

    /**
     * 获取stationFreqIdHash属性的值。
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getStationFreqIdHash() {
        return stationFreqIdHash;
    }

    /**
     * 设置stationFreqIdHash属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setStationFreqIdHash(Long value) {
        this.stationFreqIdHash = value;
    }

    /**
     * 获取stationID属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getStationID() {
        return stationID;
    }

    /**
     * 设置stationID属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setStationID(String value) {
        this.stationID = value;
    }

}
