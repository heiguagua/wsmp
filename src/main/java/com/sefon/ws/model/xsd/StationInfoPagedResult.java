
package com.sefon.ws.model.xsd;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>StationInfoPagedResult complex type�� Java �ࡣ
 * 
 * <p>����ģʽƬ��ָ�������ڴ����е�Ԥ�����ݡ�
 * 
 * <pre>
 * &lt;complexType name="StationInfoPagedResult"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="pageInfo" type="{http://model.ws.sefon.com/xsd}PaginationInfo" minOccurs="0"/&gt;
 *         &lt;element name="stations" type="{http://model.ws.sefon.com/xsd}StationInfo" maxOccurs="unbounded" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "StationInfoPagedResult", propOrder = {
    "pageInfo",
    "stations"
})
public class StationInfoPagedResult {

    @XmlElement(nillable = true)
    protected PaginationInfo pageInfo;
    @XmlElement(nillable = true)
    protected List<StationInfo> stations;

    /**
     * ��ȡpageInfo���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link PaginationInfo }
     *     
     */
    public PaginationInfo getPageInfo() {
        return pageInfo;
    }

    /**
     * ����pageInfo���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link PaginationInfo }
     *     
     */
    public void setPageInfo(PaginationInfo value) {
        this.pageInfo = value;
    }

    /**
     * Gets the value of the stations property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the stations property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getStations().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link StationInfo }
     * 
     * 
     */
    public List<StationInfo> getStations() {
        if (stations == null) {
            stations = new ArrayList<StationInfo>();
        }
        return this.stations;
    }

}
