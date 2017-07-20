
package org.tempuri;

import java.math.BigInteger;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>RStatQueryStatNameRequest complex type的 Java 类。
 * 
 * <p>以下模式片段指定包含在此类中的预期内容。
 * 
 * <pre>
 * &lt;complexType name="RStatQueryStatNameRequest"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="StatName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="AreaCodeList" type="{http://tempuri.org/}ArrayOfString" minOccurs="0"/&gt;
 *         &lt;element name="BeginFreq" type="{http://www.w3.org/2001/XMLSchema}unsignedLong"/&gt;
 *         &lt;element name="EndFreq" type="{http://www.w3.org/2001/XMLSchema}unsignedLong"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "RStatQueryStatNameRequest", propOrder = {
    "statName",
    "areaCodeList",
    "beginFreq",
    "endFreq"
})
public class RStatQueryStatNameRequest {

    @XmlElement(name = "StatName")
    protected String statName;
    @XmlElement(name = "AreaCodeList")
    protected ArrayOfString areaCodeList;
    @XmlElement(name = "BeginFreq", required = true)
    @XmlSchemaType(name = "unsignedLong")
    protected BigInteger beginFreq;
    @XmlElement(name = "EndFreq", required = true)
    @XmlSchemaType(name = "unsignedLong")
    protected BigInteger endFreq;

    /**
     * 获取statName属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getStatName() {
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
    public void setStatName(String value) {
        this.statName = value;
    }

    /**
     * 获取areaCodeList属性的值。
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfString }
     *     
     */
    public ArrayOfString getAreaCodeList() {
        return areaCodeList;
    }

    /**
     * 设置areaCodeList属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfString }
     *     
     */
    public void setAreaCodeList(ArrayOfString value) {
        this.areaCodeList = value;
    }

    /**
     * 获取beginFreq属性的值。
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getBeginFreq() {
        return beginFreq;
    }

    /**
     * 设置beginFreq属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setBeginFreq(BigInteger value) {
        this.beginFreq = value;
    }

    /**
     * 获取endFreq属性的值。
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getEndFreq() {
        return endFreq;
    }

    /**
     * 设置endFreq属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setEndFreq(BigInteger value) {
        this.endFreq = value;
    }

}
