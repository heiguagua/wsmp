
package org.tempuri;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import java.util.ArrayList;
import java.util.List;


/**
 * <p>ArrayOfSignalStaticsOnFreqBand complex type�� Java �ࡣ
 * 
 * <p>����ģʽƬ��ָ�������ڴ����е�Ԥ�����ݡ�
 * 
 * <pre>
 * &lt;complexType name="ArrayOfSignalStaticsOnFreqBand"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="SignalStaticsOnFreqBand" type="{http://tempuri.org/}SignalStaticsOnFreqBand" maxOccurs="unbounded" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ArrayOfSignalStaticsOnFreqBand", propOrder = {
    "signalStaticsOnFreqBands"
})
public class ArrayOfSignalStaticsOnFreqBand {

    @XmlElement(name = "SignalStaticsOnFreqBand", nillable = true)
    protected List<SignalStaticsOnFreqBand> signalStaticsOnFreqBands;

    /**
     * Gets the value of the signalStaticsOnFreqBands property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the signalStaticsOnFreqBands property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getSignalStaticsOnFreqBands().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link SignalStaticsOnFreqBand }
     * 
     * 
     */
    public List<SignalStaticsOnFreqBand> getSignalStaticsOnFreqBands() {
        if (signalStaticsOnFreqBands == null) {
            signalStaticsOnFreqBands = new ArrayList<SignalStaticsOnFreqBand>();
        }
        return this.signalStaticsOnFreqBands;
    }

}
