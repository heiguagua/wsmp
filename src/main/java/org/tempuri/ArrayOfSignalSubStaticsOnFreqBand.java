
package org.tempuri;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>ArrayOfSignalSubStaticsOnFreqBand complex type的 Java 类。
 * 
 * <p>以下模式片段指定包含在此类中的预期内容。
 * 
 * <pre>
 * &lt;complexType name="ArrayOfSignalSubStaticsOnFreqBand"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="SignalSubStaticsOnFreqBand" type="{http://tempuri.org/}SignalSubStaticsOnFreqBand" maxOccurs="unbounded" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ArrayOfSignalSubStaticsOnFreqBand", propOrder = {
    "signalSubStaticsOnFreqBand"
})
public class ArrayOfSignalSubStaticsOnFreqBand {

    @XmlElement(name = "SignalSubStaticsOnFreqBand", nillable = true)
    protected List<SignalSubStaticsOnFreqBand> signalSubStaticsOnFreqBand;

    /**
     * Gets the value of the signalSubStaticsOnFreqBand property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the signalSubStaticsOnFreqBand property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getSignalSubStaticsOnFreqBand().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link SignalSubStaticsOnFreqBand }
     * 
     * 
     */
    public List<SignalSubStaticsOnFreqBand> getSignalSubStaticsOnFreqBand() {
        if (signalSubStaticsOnFreqBand == null) {
            signalSubStaticsOnFreqBand = new ArrayList<SignalSubStaticsOnFreqBand>();
        }
        return this.signalSubStaticsOnFreqBand;
    }

}
