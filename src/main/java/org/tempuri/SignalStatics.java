
package org.tempuri;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>SignalStatics complex type�� Java �ࡣ
 * 
 * <p>����ģʽƬ��ָ�������ڴ����е�Ԥ�����ݡ�
 * 
 * <pre>
 * &lt;complexType name="SignalStatics"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="SignalType" type="{http://www.w3.org/2001/XMLSchema}int"/&gt;
 *         &lt;element name="Count" type="{http://www.w3.org/2001/XMLSchema}int"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "SignalStatics", propOrder = {
    "signalType",
    "count"
})
public class SignalStatics {

    @XmlElement(name = "SignalType")
    protected int signalType;
    @XmlElement(name = "Count")
    protected int count;

    /**
     * ��ȡsignalType���Ե�ֵ��
     * 
     */
    public int getSignalType() {
        return signalType;
    }

    /**
     * ����signalType���Ե�ֵ��
     * 
     */
    public void setSignalType(int value) {
        this.signalType = value;
    }

    /**
     * ��ȡcount���Ե�ֵ��
     * 
     */
    public int getCount() {
        return count;
    }

    /**
     * ����count���Ե�ֵ��
     * 
     */
    public void setCount(int value) {
        this.count = value;
    }

}
