
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
 * <p>StationInfo complex type�� Java �ࡣ
 * 
 * <p>����ģʽƬ��ָ�������ڴ����е�Ԥ�����ݡ�
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
     * ��ȡantGain���Ե�ֵ��
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
     * ����antGain���Ե�ֵ��
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
     * ��ȡantHight���Ե�ֵ��
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
     * ����antHight���Ե�ֵ��
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
     * ��ȡequauth���Ե�ֵ��
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
     * ����equauth���Ե�ֵ��
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
     * ��ȡequModel���Ե�ֵ��
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
     * ����equModel���Ե�ֵ��
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
     * ��ȡfeedLose���Ե�ֵ��
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
     * ����feedLose���Ե�ֵ��
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
     * ��ȡfeeguid���Ե�ֵ��
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
     * ����feeguid���Ե�ֵ��
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
     * ��ȡfreqCode���Ե�ֵ��
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
     * ����freqCode���Ե�ֵ��
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
     * ��ȡfreqefb���Ե�ֵ��
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
     * ����freqefb���Ե�ֵ��
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
     * ��ȡfreqefe���Ե�ֵ��
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
     * ����freqefe���Ե�ֵ��
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
     * ��ȡfreqeBand���Ե�ֵ��
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
     * ����freqeBand���Ե�ֵ��
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
     * ��ȡfreqeBandStr���Ե�ֵ��
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
     * ����freqeBandStr���Ե�ֵ��
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
     * ��ȡfreqlc���Ե�ֵ��
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
     * ����freqlc���Ե�ֵ��
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
     * ��ȡfreqmb���Ե�ֵ��
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
     * ����freqmb���Ե�ֵ��
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
     * ��ȡfreqmod���Ե�ֵ��
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
     * ����freqmod���Ե�ֵ��
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
     * ��ȡfreqmodStr���Ե�ֵ��
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
     * ����freqmodStr���Ե�ֵ��
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
     * ��ȡfreqType���Ե�ֵ��
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
     * ����freqType���Ե�ֵ��
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
     * ��ȡfreqTypeStr���Ե�ֵ��
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
     * ����freqTypeStr���Ե�ֵ��
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
     * ��ȡfrequc���Ե�ֵ��
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
     * ����frequc���Ե�ֵ��
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
     * ��ȡftfreqepow���Ե�ֵ��
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
     * ����ftfreqepow���Ե�ֵ��
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
     * ��ȡftfreqpowflag���Ե�ֵ��
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
     * ����ftfreqpowflag���Ե�ֵ��
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
     * ��ȡid���Ե�ֵ��
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
     * ����id���Ե�ֵ��
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
     * ��ȡmemo���Ե�ֵ��
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
     * ����memo���Ե�ֵ��
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
     * ��ȡnetArea���Ե�ֵ��
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
     * ����netArea���Ե�ֵ��
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
     * ��ȡnetAreaStr���Ե�ֵ��
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
     * ����netAreaStr���Ե�ֵ��
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
     * ��ȡnetBand���Ե�ֵ��
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
     * ����netBand���Ե�ֵ��
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
     * ��ȡnetBandStr���Ե�ֵ��
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
     * ����netBandStr���Ե�ֵ��
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
     * ��ȡnetConfirmDate���Ե�ֵ��
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
     * ����netConfirmDate���Ե�ֵ��
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
     * ��ȡnetConfirmDateStr���Ե�ֵ��
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
     * ����netConfirmDateStr���Ե�ֵ��
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
     * ��ȡnetExpiredDate���Ե�ֵ��
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
     * ����netExpiredDate���Ե�ֵ��
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
     * ��ȡnetExpiredDateStr���Ե�ֵ��
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
     * ����netExpiredDateStr���Ե�ֵ��
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
     * ��ȡnetsp���Ե�ֵ��
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
     * ����netsp���Ե�ֵ��
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
     * ��ȡnetspStr���Ե�ֵ��
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
     * ����netspStr���Ե�ֵ��
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
     * ��ȡnetsvn���Ե�ֵ��
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
     * ����netsvn���Ե�ֵ��
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
     * ��ȡnetStartDate���Ե�ֵ��
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
     * ����netStartDate���Ե�ֵ��
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
     * ��ȡnetStartDateStr���Ե�ֵ��
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
     * ����netStartDateStr���Ե�ֵ��
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
     * ��ȡnetts���Ե�ֵ��
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
     * ����netts���Ե�ֵ��
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
     * ��ȡnettsStr���Ե�ֵ��
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
     * ����nettsStr���Ե�ֵ��
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
     * ��ȡstataddr���Ե�ֵ��
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
     * ����stataddr���Ե�ֵ��
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
     * ��ȡstatat���Ե�ֵ��
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
     * ����statat���Ե�ֵ��
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
     * ��ȡstatAreaCode���Ե�ֵ��
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
     * ����statAreaCode���Ե�ֵ��
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
     * ��ȡstatDateStart���Ե�ֵ��
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
     * ����statDateStart���Ե�ֵ��
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
     * ��ȡstatDateStartStr���Ե�ֵ��
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
     * ����statDateStartStr���Ե�ֵ��
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
     * ��ȡstatequsum���Ե�ֵ��
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
     * ����statequsum���Ե�ֵ��
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
     * ��ȡstatla���Ե�ֵ��
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
     * ����statla���Ե�ֵ��
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
     * ��ȡstatlg���Ե�ֵ��
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
     * ����statlg���Ե�ֵ��
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
     * ��ȡstatName���Ե�ֵ��
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
     * ����statName���Ե�ֵ��
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
     * ��ȡstatStatus���Ե�ֵ��
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
     * ����statStatus���Ե�ֵ��
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
     * ��ȡstatStatusStr���Ե�ֵ��
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
     * ����statStatusStr���Ե�ֵ��
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
     * ��ȡstatType���Ե�ֵ��
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
     * ����statType���Ե�ֵ��
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
     * ��ȡstatTypeStr���Ե�ֵ��
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
     * ����statTypeStr���Ե�ֵ��
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
     * ��ȡstatWork���Ե�ֵ��
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
     * ����statWork���Ե�ֵ��
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
     * ��ȡuserName���Ե�ֵ��
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
     * ����userName���Ե�ֵ��
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
     * ��ȡbInitInfo���Ե�ֵ��
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
     * ����bInitInfo���Ե�ֵ��
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
     * ��ȡbeginFreq���Ե�ֵ��
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
     * ����beginFreq���Ե�ֵ��
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
     * ��ȡcenterFreq���Ե�ֵ��
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
     * ����centerFreq���Ե�ֵ��
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
     * ��ȡcenterFreqStr���Ե�ֵ��
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
     * ����centerFreqStr���Ե�ֵ��
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
     * ��ȡendFreq���Ե�ֵ��
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
     * ����endFreq���Ե�ֵ��
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
     * ��ȡfreqID���Ե�ֵ��
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
     * ����freqID���Ե�ֵ��
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
     * ��ȡimagePath���Ե�ֵ��
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
     * ����imagePath���Ե�ֵ��
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
     * ��ȡisLoad���Ե�ֵ��
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
     * ����isLoad���Ե�ֵ��
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
     * ��ȡnetSVNStr���Ե�ֵ��
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
     * ����netSVNStr���Ե�ֵ��
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
     * ��ȡstationFreqID���Ե�ֵ��
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
     * ����stationFreqID���Ե�ֵ��
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
     * ��ȡstationFreqIdHash���Ե�ֵ��
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
     * ����stationFreqIdHash���Ե�ֵ��
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
     * ��ȡstationID���Ե�ֵ��
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
     * ����stationID���Ե�ֵ��
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
