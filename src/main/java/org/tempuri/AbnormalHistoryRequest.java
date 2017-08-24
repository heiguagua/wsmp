
package org.tempuri;

import org.w3._2001.xmlschema.Adapter1;

import javax.xml.bind.annotation.*;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.util.Calendar;


/**
 * <p>AbnormalHistoryRequest complex type�� Java �ࡣ
 * 
 * <p>����ģʽƬ��ָ�������ڴ����е�Ԥ�����ݡ�
 * 
 * <pre>
 * &lt;complexType name="AbnormalHistoryRequest"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="ID" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="TypeCodes" type="{http://tempuri.org/}ArrayOfSignalTypeDTO" minOccurs="0"/&gt;
 *         &lt;element name="StartTime" type="{http://www.w3.org/2001/XMLSchema}dateTime"/&gt;
 *         &lt;element name="StopTime" type="{http://www.w3.org/2001/XMLSchema}dateTime"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "AbnormalHistoryRequest", propOrder = {
    "id",
    "typeCodes",
    "startTime",
    "stopTime"
})
public class AbnormalHistoryRequest {

    @XmlElement(name = "ID")
    protected String id;
    @XmlElement(name = "TypeCodes")
    protected ArrayOfSignalTypeDTO typeCodes;
    @XmlElement(name = "StartTime", required = true, type = String.class, nillable = true)
    @XmlJavaTypeAdapter(Adapter1 .class)
    @XmlSchemaType(name = "dateTime")
    protected Calendar startTime;
    @XmlElement(name = "StopTime", required = true, type = String.class, nillable = true)
    @XmlJavaTypeAdapter(Adapter1 .class)
    @XmlSchemaType(name = "dateTime")
    protected Calendar stopTime;

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
     * ��ȡtypeCodes���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfSignalTypeDTO }
     *     
     */
    public ArrayOfSignalTypeDTO getTypeCodes() {
        return typeCodes;
    }

    /**
     * ����typeCodes���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfSignalTypeDTO }
     *     
     */
    public void setTypeCodes(ArrayOfSignalTypeDTO value) {
        this.typeCodes = value;
    }

    /**
     * ��ȡstartTime���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public Calendar getStartTime() {
        return startTime;
    }

    /**
     * ����startTime���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setStartTime(Calendar value) {
        this.startTime = value;
    }

    /**
     * ��ȡstopTime���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public Calendar getStopTime() {
        return stopTime;
    }

    /**
     * ����stopTime���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setStopTime(Calendar value) {
        this.stopTime = value;
    }

}
