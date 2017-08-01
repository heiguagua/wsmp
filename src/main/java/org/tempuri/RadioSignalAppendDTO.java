
package org.tempuri;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>RadioSignalAppendDTO complex type的 Java 类。
 * 
 * <p>以下模式片段指定包含在此类中的预期内容。
 * 
 * <pre>
 * &lt;complexType name="RadioSignalAppendDTO"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="ID" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="SignalID" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="AppendData" type="{http://www.w3.org/2001/XMLSchema}base64Binary" minOccurs="0"/&gt;
 *         &lt;element name="AppendType" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
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
@XmlType(name = "RadioSignalAppendDTO", propOrder = {
    "id",
    "signalID",
    "appendData",
    "appendType",
    "des"
})
public class RadioSignalAppendDTO {

    @XmlElement(name = "ID")
    protected String id;
    @XmlElement(name = "SignalID")
    protected String signalID;
    @XmlElement(name = "AppendData")
    protected byte[] appendData;
    @XmlElement(name = "AppendType")
    protected String appendType;
    @XmlElement(name = "Des")
    protected String des;

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
     * 获取signalID属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSignalID() {
        return signalID;
    }

    /**
     * 设置signalID属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSignalID(String value) {
        this.signalID = value;
    }

    /**
     * 获取appendData属性的值。
     * 
     * @return
     *     possible object is
     *     byte[]
     */
    public byte[] getAppendData() {
        return appendData;
    }

    /**
     * 设置appendData属性的值。
     * 
     * @param value
     *     allowed object is
     *     byte[]
     */
    public void setAppendData(byte[] value) {
        this.appendData = value;
    }

    /**
     * 获取appendType属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAppendType() {
        return appendType;
    }

    /**
     * 设置appendType属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAppendType(String value) {
        this.appendType = value;
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

}
