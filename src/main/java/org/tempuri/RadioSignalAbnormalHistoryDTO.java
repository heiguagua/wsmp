
package org.tempuri;

import org.w3._2001.xmlschema.Adapter1;

import javax.xml.bind.annotation.*;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.util.Calendar;


/**
 * <p>RadioSignalAbnormalHistoryDTO complex type�� Java �ࡣ
 * 
 * <p>����ģʽƬ��ָ�������ڴ����е�Ԥ�����ݡ�
 * 
 * <pre>
 * &lt;complexType name="RadioSignalAbnormalHistoryDTO"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="ID" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="FREQ_GUID" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="IsInvalid" type="{http://www.w3.org/2001/XMLSchema}boolean"/&gt;
 *         &lt;element name="SaveDate" type="{http://www.w3.org/2001/XMLSchema}dateTime"/&gt;
 *         &lt;element name="InvalidDate" type="{http://www.w3.org/2001/XMLSchema}dateTime"/&gt;
 *         &lt;element name="HistoryType" type="{http://www.w3.org/2001/XMLSchema}int"/&gt;
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
@XmlType(name = "RadioSignalAbnormalHistoryDTO", propOrder = {
    "id",
    "freq_GUID",
    "isInvalid",
    "saveDate",
    "invalidDate",
    "historyType",
    "des"
})
public class RadioSignalAbnormalHistoryDTO {

    @XmlElement(name = "ID")
    protected String id;
    @XmlElement(name = "FREQ_GUID")
    protected String freq_GUID;
    @XmlElement(name = "IsInvalid")
    protected boolean isInvalid;
    @XmlElement(name = "SaveDate", required = true, type = String.class)
    @XmlJavaTypeAdapter(Adapter1 .class)
    @XmlSchemaType(name = "dateTime")
    protected Calendar saveDate;
    @XmlElement(name = "InvalidDate", required = true, type = String.class)
    @XmlJavaTypeAdapter(Adapter1 .class)
    @XmlSchemaType(name = "dateTime")
    protected Calendar invalidDate;
    @XmlElement(name = "HistoryType")
    protected int historyType;
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
     * ��ȡfreq_GUID���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFREQ_GUID() {
        return freq_GUID;
    }

    /**
     * ����freq_GUID���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFREQ_GUID(String value) {
        this.freq_GUID = value;
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
     *     {@link String }
     *     
     */
    public Calendar getSaveDate() {
        return saveDate;
    }

    /**
     * ����saveDate���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSaveDate(Calendar value) {
        this.saveDate = value;
    }

    /**
     * ��ȡinvalidDate���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public Calendar getInvalidDate() {
        return invalidDate;
    }

    /**
     * ����invalidDate���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setInvalidDate(Calendar value) {
        this.invalidDate = value;
    }

    /**
     * ��ȡhistoryType���Ե�ֵ��
     * 
     */
    public int getHistoryType() {
        return historyType;
    }

    /**
     * ����historyType���Ե�ֵ��
     * 
     */
    public void setHistoryType(int value) {
        this.historyType = value;
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
