
package org.tempuri;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>FreqWarningStatQueryRequest complex type的 Java 类。
 * 
 * <p>以下模式片段指定包含在此类中的预期内容。
 * 
 * <pre>
 * &lt;complexType name="FreqWarningStatQueryRequest"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="FREQ_GUID" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="Station_GUID" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="StartIndex" type="{http://www.w3.org/2001/XMLSchema}long"/&gt;
 *         &lt;element name="Count" type="{http://www.w3.org/2001/XMLSchema}long"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "FreqWarningStatQueryRequest", propOrder = {
    "freqguid",
    "stationGUID",
    "startIndex",
    "count"
})
public class FreqWarningStatQueryRequest {

    @XmlElement(name = "FREQ_GUID")
    protected String freqguid;
    @XmlElement(name = "Station_GUID")
    protected String stationGUID;
    @XmlElement(name = "StartIndex", required = true, type = Long.class, nillable = true)
    protected Long startIndex;
    @XmlElement(name = "Count", required = true, type = Long.class, nillable = true)
    protected Long count;

    /**
     * 获取freqguid属性的值。
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
     * 设置freqguid属性的值。
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
     * 获取stationGUID属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getStationGUID() {
        return stationGUID;
    }

    /**
     * 设置stationGUID属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setStationGUID(String value) {
        this.stationGUID = value;
    }

    /**
     * 获取startIndex属性的值。
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getStartIndex() {
        return startIndex;
    }

    /**
     * 设置startIndex属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setStartIndex(Long value) {
        this.startIndex = value;
    }

    /**
     * 获取count属性的值。
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getCount() {
        return count;
    }

    /**
     * 设置count属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setCount(Long value) {
        this.count = value;
    }

}
