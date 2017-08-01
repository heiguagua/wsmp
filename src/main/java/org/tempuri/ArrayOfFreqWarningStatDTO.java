
package org.tempuri;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


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
    "freqWarningStatDTO"
})
public class ArrayOfFreqWarningStatDTO {

    @XmlElement(name = "FreqWarningStatDTO", nillable = true)
    protected List<FreqWarningStatDTO> freqWarningStatDTO;

    /**
     * Gets the value of the freqWarningStatDTO property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the freqWarningStatDTO property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getFreqWarningStatDTO().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link FreqWarningStatDTO }
     * 
     * 
     */
    public List<FreqWarningStatDTO> getFreqWarningStatDTO() {
        if (freqWarningStatDTO == null) {
            freqWarningStatDTO = new ArrayList<FreqWarningStatDTO>();
        }
        return this.freqWarningStatDTO;
    }

}
