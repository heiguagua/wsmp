
package org.tempuri;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import java.util.ArrayList;
import java.util.List;


/**
 * <p>ArrayOfFreqWarningStatDTO complex type�� Java �ࡣ
 * 
 * <p>����ģʽƬ��ָ�������ڴ����е�Ԥ�����ݡ�
 * 
 * <pre>
 * &lt;complexType name="ArrayOfFreqWarningStatDTO"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="FreqWarningStatDTO" type="{http://tempuri.org/}FreqWarningStatDTO" maxOccurs="unbounded" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ArrayOfFreqWarningStatDTO", propOrder = {
    "freqWarningStatDTOs"
})
public class ArrayOfFreqWarningStatDTO {

    @XmlElement(name = "FreqWarningStatDTO", nillable = true)
    protected List<FreqWarningStatDTO> freqWarningStatDTOs;

    /**
     * Gets the value of the freqWarningStatDTOs property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the freqWarningStatDTOs property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getFreqWarningStatDTOs().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link FreqWarningStatDTO }
     * 
     * 
     */
    public List<FreqWarningStatDTO> getFreqWarningStatDTOs() {
        if (freqWarningStatDTOs == null) {
            freqWarningStatDTOs = new ArrayList<FreqWarningStatDTO>();
        }
        return this.freqWarningStatDTOs;
    }

}
