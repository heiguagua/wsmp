
package org.tempuri;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import java.util.ArrayList;
import java.util.List;


/**
 * <p>ArrayOfFrequencyBand complex type�� Java �ࡣ
 * 
 * <p>����ģʽƬ��ָ�������ڴ����е�Ԥ�����ݡ�
 * 
 * <pre>
 * &lt;complexType name="ArrayOfFrequencyBand"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="FrequencyBand" type="{http://tempuri.org/}FrequencyBand" maxOccurs="unbounded" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ArrayOfFrequencyBand", propOrder = {
    "frequencyBands"
})
public class ArrayOfFrequencyBand {

    public void setFrequencyBands(List<FrequencyBand> frequencyBands) {
        this.frequencyBands = frequencyBands;
    }

    @XmlElement(name = "FrequencyBand", nillable = true)
    protected List<FrequencyBand> frequencyBands;

    /**
     * Gets the value of the frequencyBands property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the frequencyBands property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getFrequencyBands().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link FrequencyBand }
     * 
     * 
     */
    public List<FrequencyBand> getFrequencyBands() {
        if (frequencyBands == null) {
            frequencyBands = new ArrayList<FrequencyBand>();
        }
        return this.frequencyBands;
    }

}
