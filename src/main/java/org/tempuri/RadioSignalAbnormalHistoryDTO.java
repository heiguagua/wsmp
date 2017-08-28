
package org.tempuri;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


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
    "freqguid",
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
    protected String freqguid;
    @XmlElement(name = "IsInvalid")
    protected boolean isInvalid;
    @XmlElement(name = "SaveDate", required = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar saveDate;
    @XmlElement(name = "InvalidDate", required = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar invalidDate;
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
     * ��ȡfreqguid���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFREQGUID() {
        return freqguid;
    }

    /**
     * ����freqguid���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFREQGUID(String value) {
        this.freqguid = value;
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
