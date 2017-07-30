
package com.sefon.ws.model.xsd;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

/**
 * <p>
 * StationQuerySpecInfo complex type�� Java �ࡣ
 * 
 * <p>
 * ����ģʽƬ��ָ�������ڴ����е�Ԥ�����ݡ�
 * 
 * <pre>
 * &lt;complexType name="StationQuerySpecInfo"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="areaCodes" type="{http://www.w3.org/2001/XMLSchema}string" maxOccurs="unbounded" minOccurs="0"/&gt;
 *         &lt;element name="beginDate" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="endDate" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="freqRanges" type="{http://model.ws.sefon.com/xsd}QueryFreqRangeInfo" maxOccurs="unbounded" minOccurs="0"/&gt;
 *         &lt;element name="isLoad" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/&gt;
 *         &lt;element name="signalFreq" type="{http://www.w3.org/2001/XMLSchema}double" minOccurs="0"/&gt;
 *         &lt;element name="statStatus" type="{http://www.w3.org/2001/XMLSchema}short" maxOccurs="unbounded" minOccurs="0"/&gt;
 *         &lt;element name="techSystems" type="{http://www.w3.org/2001/XMLSchema}string" maxOccurs="unbounded" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "StationQuerySpecInfo", propOrder = { "areaCodes", "beginDate", "endDate", "freqRanges", "isLoad", "signalFreq", "statStatuses",
		"techSystems" })
public class StationQuerySpecInfo {

	@XmlElement(nillable = true)
	protected List<String> areaCodes;

	@XmlElement(nillable = true)
	protected String beginDate;

	@XmlElement(nillable = true)
	protected String endDate;

	@XmlElement(nillable = true)
	protected List<QueryFreqRangeInfo> freqRanges;

	protected Boolean isLoad;

	@XmlElement(nillable = true)
	protected Double signalFreq;

	@XmlElement(name = "statStatus", nillable = true)
	protected List<Short> statStatuses;

	@XmlElement(nillable = true)
	protected List<String> techSystems;

	/**
	 * Gets the value of the areaCodes property.
	 * 
	 * <p>
	 * This accessor method returns a reference to the live list, not a
	 * snapshot. Therefore any modification you make to the returned list will
	 * be present inside the JAXB object. This is why there is not a
	 * <CODE>set</CODE> method for the areaCodes property.
	 * 
	 * <p>
	 * For example, to add a new item, do as follows:
	 * 
	 * <pre>
	 * getAreaCodes().add(newItem);
	 * </pre>
	 * 
	 * 
	 * <p>
	 * Objects of the following type(s) are allowed in the list {@link String }
	 * 
	 * 
	 */

	public List<String> getAreaCodes() {
		if (areaCodes == null) {
			areaCodes = new ArrayList<String>();
		}
		return this.areaCodes;
	}

	public void setAreaCodes(List<String> areaCodes) {
		this.areaCodes = areaCodes;
	}

	/**
	 * ��ȡbeginDate���Ե�ֵ��
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getBeginDate() {
		return beginDate;
	}

	/**
	 * ����beginDate���Ե�ֵ��
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	public void setBeginDate(String value) {
		this.beginDate = value;
	}

	/**
	 * ��ȡendDate���Ե�ֵ��
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getEndDate() {
		return endDate;
	}

	/**
	 * ����endDate���Ե�ֵ��
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	public void setEndDate(String value) {
		this.endDate = value;
	}

	/**
	 * Gets the value of the freqRanges property.
	 * 
	 * <p>
	 * This accessor method returns a reference to the live list, not a
	 * snapshot. Therefore any modification you make to the returned list will
	 * be present inside the JAXB object. This is why there is not a
	 * <CODE>set</CODE> method for the freqRanges property.
	 * 
	 * <p>
	 * For example, to add a new item, do as follows:
	 * 
	 * <pre>
	 * getFreqRanges().add(newItem);
	 * </pre>
	 * 
	 * 
	 * <p>
	 * Objects of the following type(s) are allowed in the list
	 * {@link QueryFreqRangeInfo }
	 * 
	 * 
	 */
	public List<QueryFreqRangeInfo> getFreqRanges() {
		if (freqRanges == null) {
			freqRanges = new ArrayList<QueryFreqRangeInfo>();
		}
		return this.freqRanges;
	}

	/**
	 * ��ȡisLoad���Ե�ֵ��
	 * 
	 * @return possible object is {@link Boolean }
	 * 
	 */
	public Boolean isIsLoad() {
		return isLoad;
	}

	/**
	 * ����isLoad���Ե�ֵ��
	 * 
	 * @param value
	 *            allowed object is {@link Boolean }
	 * 
	 */
	public void setIsLoad(Boolean value) {
		this.isLoad = value;
	}

	/**
	 * ��ȡsignalFreq���Ե�ֵ��
	 * 
	 * @return possible object is {@link Double }
	 * 
	 */
	public Double getSignalFreq() {
		return signalFreq;
	}

	/**
	 * ����signalFreq���Ե�ֵ��
	 * 
	 * @param value
	 *            allowed object is {@link Double }
	 * 
	 */
	public void setSignalFreq(Double value) {
		this.signalFreq = value;
	}

	/**
	 * Gets the value of the statStatuses property.
	 * 
	 * <p>
	 * This accessor method returns a reference to the live list, not a
	 * snapshot. Therefore any modification you make to the returned list will
	 * be present inside the JAXB object. This is why there is not a
	 * <CODE>set</CODE> method for the statStatuses property.
	 * 
	 * <p>
	 * For example, to add a new item, do as follows:
	 * 
	 * <pre>
	 * getStatStatuses().add(newItem);
	 * </pre>
	 * 
	 * 
	 * <p>
	 * Objects of the following type(s) are allowed in the list {@link Short }
	 * 
	 * 
	 */
	public List<Short> getStatStatuses() {
		if (statStatuses == null) {
			statStatuses = new ArrayList<Short>();
		}
		return this.statStatuses;
	}

	/**
	 * Gets the value of the techSystems property.
	 * 
	 * <p>
	 * This accessor method returns a reference to the live list, not a
	 * snapshot. Therefore any modification you make to the returned list will
	 * be present inside the JAXB object. This is why there is not a
	 * <CODE>set</CODE> method for the techSystems property.
	 * 
	 * <p>
	 * For example, to add a new item, do as follows:
	 * 
	 * <pre>
	 * getTechSystems().add(newItem);
	 * </pre>
	 * 
	 * 
	 * <p>
	 * Objects of the following type(s) are allowed in the list {@link String }
	 * 
	 * 
	 */
	public List<String> getTechSystems() {
		if (techSystems == null) {
			techSystems = new ArrayList<String>();
		}
		return this.techSystems;
	}

}
