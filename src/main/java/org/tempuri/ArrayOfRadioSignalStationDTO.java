
package org.tempuri;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>ArrayOfRadioSignalStationDTO complex type的 Java 类。
 * 
 * <p>以下模式片段指定包含在此类中的预期内容。
 * 
 * <pre>
 * &lt;complexType name="ArrayOfRadioSignalStationDTO"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="RadioSignalStationDTO" type="{http://tempuri.org/}RadioSignalStationDTO" maxOccurs="unbounded" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ArrayOfRadioSignalStationDTO", propOrder = {
    "radioSignalStationDTO"
})
public class ArrayOfRadioSignalStationDTO {

    @XmlElement(name = "RadioSignalStationDTO", nillable = true)
    protected List<RadioSignalStationDTO> radioSignalStationDTO;

    /**
     * Gets the value of the radioSignalStationDTO property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the radioSignalStationDTO property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getRadioSignalStationDTO().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link RadioSignalStationDTO }
     * 
     * 
     */
    public List<RadioSignalStationDTO> getRadioSignalStationDTO() {
        if (radioSignalStationDTO == null) {
            radioSignalStationDTO = new ArrayList<RadioSignalStationDTO>();
        }
        return this.radioSignalStationDTO;
    }

}
