
package org.tempuri;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>ArrayOfFreqHistoryDTO complex type�� Java �ࡣ
 * 
 * <p>����ģʽƬ��ָ�������ڴ����е�Ԥ�����ݡ�
 * 
 * <pre>
 * &lt;complexType name="ArrayOfFreqHistoryDTO"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="FreqHistoryDTO" type="{http://tempuri.org/}FreqHistoryDTO" maxOccurs="unbounded" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ArrayOfFreqHistoryDTO", propOrder = {
    "freqHistoryDTO"
})
public class ArrayOfFreqHistoryDTO {

    @XmlElement(name = "FreqHistoryDTO", nillable = true)
    protected List<FreqHistoryDTO> freqHistoryDTO;

    /**
     * Gets the value of the freqHistoryDTO property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the freqHistoryDTO property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getFreqHistoryDTO().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link FreqHistoryDTO }
     * 
     * 
     */
    public List<FreqHistoryDTO> getFreqHistoryDTO() {
        if (freqHistoryDTO == null) {
            freqHistoryDTO = new ArrayList<FreqHistoryDTO>();
        }
        return this.freqHistoryDTO;
    }

}
