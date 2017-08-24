
package org.tempuri;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>ArrayOfSignalStaticsOnStation complex type的 Java 类。
 * 
 * <p>以下模式片段指定包含在此类中的预期内容。
 * 
 * <pre>
 * &lt;complexType name="ArrayOfSignalStaticsOnStation"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="SignalStaticsOnStation" type="{http://tempuri.org/}SignalStaticsOnStation" maxOccurs="unbounded" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ArrayOfSignalStaticsOnStation", propOrder = {
    "signalStaticsOnStations"
})
public class ArrayOfSignalStaticsOnStation {

    @XmlElement(name = "SignalStaticsOnStation", nillable = true)
    protected List<SignalStaticsOnStation> signalStaticsOnStations;

    /**
     * Gets the value of the signalStaticsOnStations property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the signalStaticsOnStations property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getSignalStaticsOnStations().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link SignalStaticsOnStation }
     * 
     * 
     */
    public List<SignalStaticsOnStation> getSignalStaticsOnStations() {
        if (signalStaticsOnStations == null) {
            signalStaticsOnStations = new ArrayList<SignalStaticsOnStation>();
        }
        return this.signalStaticsOnStations;
    }

}
