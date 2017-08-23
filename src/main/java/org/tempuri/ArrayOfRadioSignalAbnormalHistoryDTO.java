
package org.tempuri;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>ArrayOfRadioSignalAbnormalHistoryDTO complex type的 Java 类。
 * 
 * <p>以下模式片段指定包含在此类中的预期内容。
 * 
 * <pre>
 * &lt;complexType name="ArrayOfRadioSignalAbnormalHistoryDTO"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="RadioSignalAbnormalHistoryDTO" type="{http://tempuri.org/}RadioSignalAbnormalHistoryDTO" maxOccurs="unbounded" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ArrayOfRadioSignalAbnormalHistoryDTO", propOrder = {
    "radioSignalAbnormalHistoryDTOs"
})
public class ArrayOfRadioSignalAbnormalHistoryDTO {

    @XmlElement(name = "RadioSignalAbnormalHistoryDTO", nillable = true)
    protected List<RadioSignalAbnormalHistoryDTO> radioSignalAbnormalHistoryDTOs;

    /**
     * Gets the value of the radioSignalAbnormalHistoryDTOs property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the radioSignalAbnormalHistoryDTOs property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getRadioSignalAbnormalHistoryDTOs().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link RadioSignalAbnormalHistoryDTO }
     * 
     * 
     */
    public List<RadioSignalAbnormalHistoryDTO> getRadioSignalAbnormalHistoryDTOs() {
        if (radioSignalAbnormalHistoryDTOs == null) {
            radioSignalAbnormalHistoryDTOs = new ArrayList<RadioSignalAbnormalHistoryDTO>();
        }
        return this.radioSignalAbnormalHistoryDTOs;
    }

}
