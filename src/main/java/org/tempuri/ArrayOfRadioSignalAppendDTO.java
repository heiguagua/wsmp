
package org.tempuri;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>ArrayOfRadioSignalAppendDTO complex type�� Java �ࡣ
 * 
 * <p>����ģʽƬ��ָ�������ڴ����е�Ԥ�����ݡ�
 * 
 * <pre>
 * &lt;complexType name="ArrayOfRadioSignalAppendDTO"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="RadioSignalAppendDTO" type="{http://tempuri.org/}RadioSignalAppendDTO" maxOccurs="unbounded" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ArrayOfRadioSignalAppendDTO", propOrder = {
    "radioSignalAppendDTO"
})
public class ArrayOfRadioSignalAppendDTO {

    @XmlElement(name = "RadioSignalAppendDTO", nillable = true)
    protected List<RadioSignalAppendDTO> radioSignalAppendDTO;

    /**
     * Gets the value of the radioSignalAppendDTO property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the radioSignalAppendDTO property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getRadioSignalAppendDTO().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link RadioSignalAppendDTO }
     * 
     * 
     */
    public List<RadioSignalAppendDTO> getRadioSignalAppendDTO() {
        if (radioSignalAppendDTO == null) {
            radioSignalAppendDTO = new ArrayList<RadioSignalAppendDTO>();
        }
        return this.radioSignalAppendDTO;
    }

}
