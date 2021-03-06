
package org.tempuri;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>ArrayOfSignalTypeDTO complex type�� Java �ࡣ
 * 
 * <p>����ģʽƬ��ָ�������ڴ����е�Ԥ�����ݡ�
 * 
 * <pre>
 * &lt;complexType name="ArrayOfSignalTypeDTO"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="SignalTypeDTO" type="{http://tempuri.org/}SignalTypeDTO" maxOccurs="unbounded" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ArrayOfSignalTypeDTO", propOrder = {
    "signalTypeDTO"
})
public class ArrayOfSignalTypeDTO {

    @XmlElement(name = "SignalTypeDTO", nillable = true)
    protected List<SignalTypeDTO> signalTypeDTO;

    public void setSignalTypeDTO(List<SignalTypeDTO> signalTypeDTO) {
		this.signalTypeDTO = signalTypeDTO;
	}

	/**
     * Gets the value of the signalTypeDTO property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the signalTypeDTO property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getSignalTypeDTO().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link SignalTypeDTO }
     * 
     * 
     */
    public List<SignalTypeDTO> getSignalTypeDTO() {
        if (signalTypeDTO == null) {
            signalTypeDTO = new ArrayList<SignalTypeDTO>();
        }
        return this.signalTypeDTO;
    }

}
