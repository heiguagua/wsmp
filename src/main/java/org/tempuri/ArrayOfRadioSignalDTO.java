
package org.tempuri;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import java.util.ArrayList;
import java.util.List;


/**
 * <p>ArrayOfRadioSignalDTO complex type�� Java �ࡣ
 * 
 * <p>����ģʽƬ��ָ�������ڴ����е�Ԥ�����ݡ�
 * 
 * <pre>
 * &lt;complexType name="ArrayOfRadioSignalDTO"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="RadioSignalDTO" type="{http://tempuri.org/}RadioSignalDTO" maxOccurs="unbounded" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ArrayOfRadioSignalDTO", propOrder = {
    "radioSignalDTO"
})
public class ArrayOfRadioSignalDTO {

    @XmlElement(name = "RadioSignalDTO", nillable = true)
    protected List<RadioSignalDTO> radioSignalDTO;

    /**
     * Gets the value of the radioSignalDTO property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the radioSignalDTO property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getRadioSignalDTO().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link RadioSignalDTO }
     * 
     * 
     */
    public List<RadioSignalDTO> getRadioSignalDTO() {
        if (radioSignalDTO == null) {
            radioSignalDTO = new ArrayList<RadioSignalDTO>();
        }
        return this.radioSignalDTO;
    }

}
