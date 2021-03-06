
package org.tempuri;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>anonymous complex type的 Java 类。
 * 
 * <p>以下模式片段指定包含在此类中的预期内容。
 * 
 * <pre>
 * &lt;complexType&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="dto" type="{http://tempuri.org/}FreqWarningStatDTO" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "dto"
})
@XmlRootElement(name = "Insert_Stat")
public class InsertStat {

    protected FreqWarningStatDTO dto;

    /**
     * 获取dto属性的值。
     * 
     * @return
     *     possible object is
     *     {@link FreqWarningStatDTO }
     *     
     */
    public FreqWarningStatDTO getDto() {
        return dto;
    }

    /**
     * 设置dto属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link FreqWarningStatDTO }
     *     
     */
    public void setDto(FreqWarningStatDTO value) {
        this.dto = value;
    }

}
