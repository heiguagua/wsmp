
package org.tempuri;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;
import com.microsoft.schemas._2003._10.serialization.arrays.ArrayOfguid;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the org.tempuri package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _QueryImportantMonitorFreqRangeResponseQueryImportantMonitorFreqRangeResult_QNAME = new QName("http://tempuri.org/", "QueryImportantMonitorFreqRangeResult");
    private final static QName _UpdateImportantMonitorFreqRangeJsonStr_QNAME = new QName("http://tempuri.org/", "jsonStr");
    private final static QName _UpdateImportantMonitorFreqRangeResponseUpdateImportantMonitorFreqRangeResult_QNAME = new QName("http://tempuri.org/", "UpdateImportantMonitorFreqRangeResult");
    private final static QName _FindAllFreqResponseFindAllFreqResult_QNAME = new QName("http://tempuri.org/", "FindAllFreqResult");
    private final static QName _FindAllFreqRangeResponseFindAllFreqRangeResult_QNAME = new QName("http://tempuri.org/", "FindAllFreqRangeResult");
    private final static QName _FindByFreqWarnId_QNAME = new QName("http://tempuri.org/", "warnId");
    private final static QName _FindByFreqResponseFindByFreqResult_QNAME = new QName("http://tempuri.org/", "FindByFreqResult");
    private final static QName _FindFreqByWarnResponseFindFreqByWarnResult_QNAME = new QName("http://tempuri.org/", "FindFreqByWarnResult");
    private final static QName _CreateOrUpdateJsonTaskParam_QNAME = new QName("http://tempuri.org/", "jsonTaskParam");
    private final static QName _CreateOrUpdateResponseCreateOrUpdateResult_QNAME = new QName("http://tempuri.org/", "CreateOrUpdateResult");
    private final static QName _RemoveAllTaskIds_QNAME = new QName("http://tempuri.org/", "ids");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: org.tempuri
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link QueryImportantMonitorFreqRange }
     * 
     */
    public QueryImportantMonitorFreqRange createQueryImportantMonitorFreqRange() {
        return new QueryImportantMonitorFreqRange();
    }

    /**
     * Create an instance of {@link QueryImportantMonitorFreqRangeResponse }
     * 
     */
    public QueryImportantMonitorFreqRangeResponse createQueryImportantMonitorFreqRangeResponse() {
        return new QueryImportantMonitorFreqRangeResponse();
    }

    /**
     * Create an instance of {@link UpdateImportantMonitorFreqRange }
     * 
     */
    public UpdateImportantMonitorFreqRange createUpdateImportantMonitorFreqRange() {
        return new UpdateImportantMonitorFreqRange();
    }

    /**
     * Create an instance of {@link UpdateImportantMonitorFreqRangeResponse }
     * 
     */
    public UpdateImportantMonitorFreqRangeResponse createUpdateImportantMonitorFreqRangeResponse() {
        return new UpdateImportantMonitorFreqRangeResponse();
    }

    /**
     * Create an instance of {@link AddImportantMonitorFreq }
     * 
     */
    public AddImportantMonitorFreq createAddImportantMonitorFreq() {
        return new AddImportantMonitorFreq();
    }

    /**
     * Create an instance of {@link AddImportantMonitorFreqResponse }
     * 
     */
    public AddImportantMonitorFreqResponse createAddImportantMonitorFreqResponse() {
        return new AddImportantMonitorFreqResponse();
    }

    /**
     * Create an instance of {@link FindAllFreq }
     * 
     */
    public FindAllFreq createFindAllFreq() {
        return new FindAllFreq();
    }

    /**
     * Create an instance of {@link FindAllFreqResponse }
     * 
     */
    public FindAllFreqResponse createFindAllFreqResponse() {
        return new FindAllFreqResponse();
    }

    /**
     * Create an instance of {@link FindAllFreqRange }
     * 
     */
    public FindAllFreqRange createFindAllFreqRange() {
        return new FindAllFreqRange();
    }

    /**
     * Create an instance of {@link FindAllFreqRangeResponse }
     * 
     */
    public FindAllFreqRangeResponse createFindAllFreqRangeResponse() {
        return new FindAllFreqRangeResponse();
    }

    /**
     * Create an instance of {@link FindByFreq }
     * 
     */
    public FindByFreq createFindByFreq() {
        return new FindByFreq();
    }

    /**
     * Create an instance of {@link FindByFreqResponse }
     * 
     */
    public FindByFreqResponse createFindByFreqResponse() {
        return new FindByFreqResponse();
    }

    /**
     * Create an instance of {@link FindFreqByWarn }
     * 
     */
    public FindFreqByWarn createFindFreqByWarn() {
        return new FindFreqByWarn();
    }

    /**
     * Create an instance of {@link FindFreqByWarnResponse }
     * 
     */
    public FindFreqByWarnResponse createFindFreqByWarnResponse() {
        return new FindFreqByWarnResponse();
    }

    /**
     * Create an instance of {@link CreateOrUpdate }
     * 
     */
    public CreateOrUpdate createCreateOrUpdate() {
        return new CreateOrUpdate();
    }

    /**
     * Create an instance of {@link CreateOrUpdateResponse }
     * 
     */
    public CreateOrUpdateResponse createCreateOrUpdateResponse() {
        return new CreateOrUpdateResponse();
    }

    /**
     * Create an instance of {@link RemoveAllTask }
     * 
     */
    public RemoveAllTask createRemoveAllTask() {
        return new RemoveAllTask();
    }

    /**
     * Create an instance of {@link RemoveAllTaskResponse }
     * 
     */
    public RemoveAllTaskResponse createRemoveAllTaskResponse() {
        return new RemoveAllTaskResponse();
    }

    /**
     * Create an instance of {@link RemoveById }
     * 
     */
    public RemoveById createRemoveById() {
        return new RemoveById();
    }

    /**
     * Create an instance of {@link RemoveByIdResponse }
     * 
     */
    public RemoveByIdResponse createRemoveByIdResponse() {
        return new RemoveByIdResponse();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://tempuri.org/", name = "QueryImportantMonitorFreqRangeResult", scope = QueryImportantMonitorFreqRangeResponse.class)
    public JAXBElement<String> createQueryImportantMonitorFreqRangeResponseQueryImportantMonitorFreqRangeResult(String value) {
        return new JAXBElement<String>(_QueryImportantMonitorFreqRangeResponseQueryImportantMonitorFreqRangeResult_QNAME, String.class, QueryImportantMonitorFreqRangeResponse.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://tempuri.org/", name = "jsonStr", scope = UpdateImportantMonitorFreqRange.class)
    public JAXBElement<String> createUpdateImportantMonitorFreqRangeJsonStr(String value) {
        return new JAXBElement<String>(_UpdateImportantMonitorFreqRangeJsonStr_QNAME, String.class, UpdateImportantMonitorFreqRange.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://tempuri.org/", name = "UpdateImportantMonitorFreqRangeResult", scope = UpdateImportantMonitorFreqRangeResponse.class)
    public JAXBElement<String> createUpdateImportantMonitorFreqRangeResponseUpdateImportantMonitorFreqRangeResult(String value) {
        return new JAXBElement<String>(_UpdateImportantMonitorFreqRangeResponseUpdateImportantMonitorFreqRangeResult_QNAME, String.class, UpdateImportantMonitorFreqRangeResponse.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://tempuri.org/", name = "jsonStr", scope = AddImportantMonitorFreq.class)
    public JAXBElement<String> createAddImportantMonitorFreqJsonStr(String value) {
        return new JAXBElement<String>(_UpdateImportantMonitorFreqRangeJsonStr_QNAME, String.class, AddImportantMonitorFreq.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://tempuri.org/", name = "FindAllFreqResult", scope = FindAllFreqResponse.class)
    public JAXBElement<String> createFindAllFreqResponseFindAllFreqResult(String value) {
        return new JAXBElement<String>(_FindAllFreqResponseFindAllFreqResult_QNAME, String.class, FindAllFreqResponse.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://tempuri.org/", name = "FindAllFreqRangeResult", scope = FindAllFreqRangeResponse.class)
    public JAXBElement<String> createFindAllFreqRangeResponseFindAllFreqRangeResult(String value) {
        return new JAXBElement<String>(_FindAllFreqRangeResponseFindAllFreqRangeResult_QNAME, String.class, FindAllFreqRangeResponse.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://tempuri.org/", name = "warnId", scope = FindByFreq.class)
    public JAXBElement<String> createFindByFreqWarnId(String value) {
        return new JAXBElement<String>(_FindByFreqWarnId_QNAME, String.class, FindByFreq.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://tempuri.org/", name = "FindByFreqResult", scope = FindByFreqResponse.class)
    public JAXBElement<String> createFindByFreqResponseFindByFreqResult(String value) {
        return new JAXBElement<String>(_FindByFreqResponseFindByFreqResult_QNAME, String.class, FindByFreqResponse.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://tempuri.org/", name = "warnId", scope = FindFreqByWarn.class)
    public JAXBElement<String> createFindFreqByWarnWarnId(String value) {
        return new JAXBElement<String>(_FindByFreqWarnId_QNAME, String.class, FindFreqByWarn.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://tempuri.org/", name = "FindFreqByWarnResult", scope = FindFreqByWarnResponse.class)
    public JAXBElement<String> createFindFreqByWarnResponseFindFreqByWarnResult(String value) {
        return new JAXBElement<String>(_FindFreqByWarnResponseFindFreqByWarnResult_QNAME, String.class, FindFreqByWarnResponse.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://tempuri.org/", name = "jsonTaskParam", scope = CreateOrUpdate.class)
    public JAXBElement<String> createCreateOrUpdateJsonTaskParam(String value) {
        return new JAXBElement<String>(_CreateOrUpdateJsonTaskParam_QNAME, String.class, CreateOrUpdate.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://tempuri.org/", name = "CreateOrUpdateResult", scope = CreateOrUpdateResponse.class)
    public JAXBElement<String> createCreateOrUpdateResponseCreateOrUpdateResult(String value) {
        return new JAXBElement<String>(_CreateOrUpdateResponseCreateOrUpdateResult_QNAME, String.class, CreateOrUpdateResponse.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ArrayOfguid }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://tempuri.org/", name = "ids", scope = RemoveAllTask.class)
    public JAXBElement<ArrayOfguid> createRemoveAllTaskIds(ArrayOfguid value) {
        return new JAXBElement<ArrayOfguid>(_RemoveAllTaskIds_QNAME, ArrayOfguid.class, RemoveAllTask.class, value);
    }

}
