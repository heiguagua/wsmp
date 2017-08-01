
package com.sefon.ws.model.electric.level.xsd;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>FreqRangeQuerySpec complex type�� Java �ࡣ
 * 
 * <p>����ģʽƬ��ָ�������ڴ����е�Ԥ�����ݡ�
 * 
 * <pre>
 * &lt;complexType name="FreqRangeQuerySpec"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="areaCodes" type="{http://www.w3.org/2001/XMLSchema}short" maxOccurs="unbounded" minOccurs="0"/&gt;
 *         &lt;element name="beginDate" type="{http://www.w3.org/2001/XMLSchema}date" minOccurs="0"/&gt;
 *         &lt;element name="beginFreq" type="{http://www.w3.org/2001/XMLSchema}long" minOccurs="0"/&gt;
 *         &lt;element name="dataNodeIdList" type="{http://www.w3.org/2001/XMLSchema}string" maxOccurs="unbounded" minOccurs="0"/&gt;
 *         &lt;element name="dataNodeIdNameList" type="{http://www.w3.org/2001/XMLSchema}string" maxOccurs="unbounded" minOccurs="0"/&gt;
 *         &lt;element name="dataType" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/&gt;
 *         &lt;element name="endDate" type="{http://www.w3.org/2001/XMLSchema}date" minOccurs="0"/&gt;
 *         &lt;element name="endFreq" type="{http://www.w3.org/2001/XMLSchema}long" minOccurs="0"/&gt;
 *         &lt;element name="queryDateType" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "FreqRangeQuerySpec", propOrder = {
    "areaCodes",
    "beginDate",
    "beginFreq",
    "dataNodeIdLists",
    "dataNodeIdNameLists",
    "dataType",
    "endDate",
    "endFreq",
    "queryDateType"
})
public class FreqRangeQuerySpec {

    @XmlElement(nillable = true)
    protected List<Short> areaCodes;
    @XmlElement(nillable = true)
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar beginDate;
    @XmlElement(nillable = true)
    protected Long beginFreq;
    @XmlElement(name = "dataNodeIdList", nillable = true)
    protected List<String> dataNodeIdLists;
    @XmlElement(name = "dataNodeIdNameList", nillable = true)
    protected List<String> dataNodeIdNameLists;
    @XmlElement(nillable = true)
    protected Integer dataType;
    @XmlElement(nillable = true)
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar endDate;
    @XmlElement(nillable = true)
    protected Long endFreq;
    @XmlElement(nillable = true)
    protected Integer queryDateType;

    /**
     * Gets the value of the areaCodes property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the areaCodes property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getAreaCodes().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Short }
     * 
     * 
     */
    public List<Short> getAreaCodes() {
        if (areaCodes == null) {
            areaCodes = new ArrayList<Short>();
        }
        return this.areaCodes;
    }

    /**
     * ��ȡbeginDate���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getBeginDate() {
        return beginDate;
    }

    /**
     * ����beginDate���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setBeginDate(XMLGregorianCalendar value) {
        this.beginDate = value;
    }

    /**
     * ��ȡbeginFreq���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getBeginFreq() {
        return beginFreq;
    }

    /**
     * ����beginFreq���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setBeginFreq(Long value) {
        this.beginFreq = value;
    }

    /**
     * Gets the value of the dataNodeIdLists property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the dataNodeIdLists property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getDataNodeIdLists().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link String }
     * 
     * 
     */
    public List<String> getDataNodeIdLists() {
        if (dataNodeIdLists == null) {
            dataNodeIdLists = new ArrayList<String>();
        }
        return this.dataNodeIdLists;
    }

    /**
     * Gets the value of the dataNodeIdNameLists property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the dataNodeIdNameLists property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getDataNodeIdNameLists().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link String }
     * 
     * 
     */
    public List<String> getDataNodeIdNameLists() {
        if (dataNodeIdNameLists == null) {
            dataNodeIdNameLists = new ArrayList<String>();
        }
        return this.dataNodeIdNameLists;
    }

    /**
     * ��ȡdataType���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getDataType() {
        return dataType;
    }

    /**
     * ����dataType���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setDataType(Integer value) {
        this.dataType = value;
    }

    /**
     * ��ȡendDate���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getEndDate() {
        return endDate;
    }

    /**
     * ����endDate���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setEndDate(XMLGregorianCalendar value) {
        this.endDate = value;
    }

    /**
     * ��ȡendFreq���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getEndFreq() {
        return endFreq;
    }

    /**
     * ����endFreq���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setEndFreq(Long value) {
        this.endFreq = value;
    }

    /**
     * ��ȡqueryDateType���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getQueryDateType() {
        return queryDateType;
    }

    /**
     * ����queryDateType���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setQueryDateType(Integer value) {
        this.queryDateType = value;
    }

}
