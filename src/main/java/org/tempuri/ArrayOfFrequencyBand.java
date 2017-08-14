
package org.tempuri;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


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
    "frequencyBand"
})
public class ArrayOfFrequencyBand {

    @XmlElement(name = "FrequencyBand", nillable = true)
    protected List<FrequencyBand> frequencyBand;

    public void setFrequencyBand(List<FrequencyBand> frequencyBand) {
		this.frequencyBand = frequencyBand;
	}

	/**
     * Gets the value of the frequencyBand property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the frequencyBand property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getFrequencyBand().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link FrequencyBand }
     * 
     * 
     */
    public List<FrequencyBand> getFrequencyBand() {
        if (frequencyBand == null) {
            frequencyBand = new ArrayList<FrequencyBand>();
        }
        return this.frequencyBand;
    }

}
