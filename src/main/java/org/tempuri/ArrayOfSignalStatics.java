
package org.tempuri;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>ArrayOfSignalStatics complex type�� Java �ࡣ
 * 
 * <p>����ģʽƬ��ָ�������ڴ����е�Ԥ�����ݡ�
 * 
 * <pre>
 * &lt;complexType name="ArrayOfSignalStatics"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="SignalStatics" type="{http://tempuri.org/}SignalStatics" maxOccurs="unbounded" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ArrayOfSignalStatics", propOrder = {
    "signalStatics"
})
public class ArrayOfSignalStatics {

    @XmlElement(name = "SignalStatics", nillable = true)
    protected List<SignalStatics> signalStatics;

    /**
     * Gets the value of the signalStatics property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the signalStatics property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getSignalStatics().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link SignalStatics }
     * 
     * 
     */
    public List<SignalStatics> getSignalStatics() {
        if (signalStatics == null) {
            signalStatics = new ArrayList<SignalStatics>();
        }
        return this.signalStatics;
    }

}
