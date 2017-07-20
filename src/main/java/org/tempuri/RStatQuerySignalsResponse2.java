
package org.tempuri;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>RStatQuerySignalsResponse complex type�� Java �ࡣ
 * 
 * <p>����ģʽƬ��ָ�������ڴ����е�Ԥ�����ݡ�
 * 
 * <pre>
 * &lt;complexType name="RStatQuerySignalsResponse"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="TotalNum" type="{http://www.w3.org/2001/XMLSchema}int"/&gt;
 *         &lt;element name="RStatSignalList" type="{http://tempuri.org/}ArrayOfRadioStationSignalDTO" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "RStatQuerySignalsResponse", propOrder = {
    "totalNum",
    "rStatSignalList"
})
public class RStatQuerySignalsResponse2 {

    @XmlElement(name = "TotalNum")
    protected int totalNum;
    @XmlElement(name = "RStatSignalList")
    protected ArrayOfRadioStationSignalDTO rStatSignalList;

    /**
     * ��ȡtotalNum���Ե�ֵ��
     * 
     */
    public int getTotalNum() {
        return totalNum;
    }

    /**
     * ����totalNum���Ե�ֵ��
     * 
     */
    public void setTotalNum(int value) {
        this.totalNum = value;
    }

    /**
     * ��ȡrStatSignalList���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfRadioStationSignalDTO }
     *     
     */
    public ArrayOfRadioStationSignalDTO getRStatSignalList() {
        return rStatSignalList;
    }

    /**
     * ����rStatSignalList���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfRadioStationSignalDTO }
     *     
     */
    public void setRStatSignalList(ArrayOfRadioStationSignalDTO value) {
        this.rStatSignalList = value;
    }

}
