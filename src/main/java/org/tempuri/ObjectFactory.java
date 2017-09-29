
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

     public Query createQuery() {
        return new Query();
    }

    /**
     * Create an instance of {@link FreqWarningQueryRequest }
     * 
     */
    public FreqWarningQueryRequest createFreqWarningQueryRequest() {
        return new FreqWarningQueryRequest();
    }

    /**
     * Create an instance of {@link QueryResponse }
     * 
     */
    public QueryResponse createQueryResponse() {
        return new QueryResponse();
    }

    /**
     * Create an instance of {@link FreqWarningQueryResponse }
     * 
     */
    public FreqWarningQueryResponse createFreqWarningQueryResponse() {
        return new FreqWarningQueryResponse();
    }

    /**
     * Create an instance of {@link QueryStandard }
     * 
     */
    public QueryStandard createQueryStandard() {
        return new QueryStandard();
    }

    /**
     * Create an instance of {@link FreqWarningStandardQueryRequest }
     * 
     */
    public FreqWarningStandardQueryRequest createFreqWarningStandardQueryRequest() {
        return new FreqWarningStandardQueryRequest();
    }

    /**
     * Create an instance of {@link QueryStandardResponse }
     * 
     */
    public QueryStandardResponse createQueryStandardResponse() {
        return new QueryStandardResponse();
    }

    /**
     * Create an instance of {@link Insert }
     * 
     */
    public Insert createInsert() {
        return new Insert();
    }

    /**
     * Create an instance of {@link FreqWarningDTO }
     * 
     */
    public FreqWarningDTO createFreqWarningDTO() {
        return new FreqWarningDTO();
    }

    /**
     * Create an instance of {@link InsertResponse }
     * 
     */
    public InsertResponse createInsertResponse() {
        return new InsertResponse();
    }

    /**
     * Create an instance of {@link FreqWarningOperationResponse }
     * 
     */
    public FreqWarningOperationResponse createFreqWarningOperationResponse() {
        return new FreqWarningOperationResponse();
    }

    /**
     * Create an instance of {@link Remove }
     * 
     */
    public Remove createRemove() {
        return new Remove();
    }

    /**
     * Create an instance of {@link RemoveResponse }
     * 
     */
    public RemoveResponse createRemoveResponse() {
        return new RemoveResponse();
    }

    /**
     * Create an instance of {@link Update }
     * 
     */
    public Update createUpdate() {
        return new Update();
    }

    /**
     * Create an instance of {@link UpdateResponse }
     * 
     */
    public UpdateResponse createUpdateResponse() {
        return new UpdateResponse();
    }

    /**
     * Create an instance of {@link UpdateKeyword }
     * 
     */
    public UpdateKeyword createUpdateKeyword() {
        return new UpdateKeyword();
    }

    /**
     * Create an instance of {@link UpdateKeywordResponse }
     * 
     */
    public UpdateKeywordResponse createUpdateKeywordResponse() {
        return new UpdateKeywordResponse();
    }

    /**
     * Create an instance of {@link UpdateStatus }
     * 
     */
    public UpdateStatus createUpdateStatus() {
        return new UpdateStatus();
    }

    /**
     * Create an instance of {@link UpdateStatusResponse }
     * 
     */
    public UpdateStatusResponse createUpdateStatusResponse() {
        return new UpdateStatusResponse();
    }

    /**
     * Create an instance of {@link UpdateSelected }
     * 
     */
    public UpdateSelected createUpdateSelected() {
        return new UpdateSelected();
    }

    /**
     * Create an instance of {@link UpdateSelectedResponse }
     * 
     */
    public UpdateSelectedResponse createUpdateSelectedResponse() {
        return new UpdateSelectedResponse();
    }

    /**
     * Create an instance of {@link InsertStat }
     * 
     */
    public InsertStat createInsertStat() {
        return new InsertStat();
    }

    /**
     * Create an instance of {@link FreqWarningStatDTO }
     * 
     */
    public FreqWarningStatDTO createFreqWarningStatDTO() {
        return new FreqWarningStatDTO();
    }

    /**
     * Create an instance of {@link InsertStatResponse }
     * 
     */
    public InsertStatResponse createInsertStatResponse() {
        return new InsertStatResponse();
    }

    /**
     * Create an instance of {@link UpdateStat }
     * 
     */
    public UpdateStat createUpdateStat() {
        return new UpdateStat();
    }

    /**
     * Create an instance of {@link UpdateStatResponse }
     * 
     */
    public UpdateStatResponse createUpdateStatResponse() {
        return new UpdateStatResponse();
    }

    /**
     * Create an instance of {@link RemoveStat }
     * 
     */
    public RemoveStat createRemoveStat() {
        return new RemoveStat();
    }

    /**
     * Create an instance of {@link RemoveStatResponse }
     * 
     */
    public RemoveStatResponse createRemoveStatResponse() {
        return new RemoveStatResponse();
    }

    /**
     * Create an instance of {@link QueryStat }
     * 
     */
    public QueryStat createQueryStat() {
        return new QueryStat();
    }

    /**
     * Create an instance of {@link FreqWarningStatQueryRequest }
     * 
     */
    public FreqWarningStatQueryRequest createFreqWarningStatQueryRequest() {
        return new FreqWarningStatQueryRequest();
    }

    /**
     * Create an instance of {@link QueryStatResponse }
     * 
     */
    public QueryStatResponse createQueryStatResponse() {
        return new QueryStatResponse();
    }

    /**
     * Create an instance of {@link FreqWarningStatQueryResponse }
     * 
     */
    public FreqWarningStatQueryResponse createFreqWarningStatQueryResponse() {
        return new FreqWarningStatQueryResponse();
    }

    /**
     * Create an instance of {@link ArrayOfFreqWarningDTO }
     * 
     */
    public ArrayOfFreqWarningDTO createArrayOfFreqWarningDTO() {
        return new ArrayOfFreqWarningDTO();
    }

    /**
     * Create an instance of {@link ArrayOfFreqWarningStatDTO }
     * 
     */
    public ArrayOfFreqWarningStatDTO createArrayOfFreqWarningStatDTO() {
        return new ArrayOfFreqWarningStatDTO();
    }
    public QueryRadioSignal createQueryRadioSignal() {
        return new QueryRadioSignal();
    }

    /**
     * Create an instance of {@link RadioSignalQueryRequest }
     * 
     */
    public RadioSignalQueryRequest createRadioSignalQueryRequest() {
        return new RadioSignalQueryRequest();
    }

    /**
     * Create an instance of {@link QueryRadioSignalResponse }
     * 
     */
    public QueryRadioSignalResponse createQueryRadioSignalResponse() {
        return new QueryRadioSignalResponse();
    }

    /**
     * Create an instance of {@link RadioSignalQueryResponse }
     * 
     */
    public RadioSignalQueryResponse createRadioSignalQueryResponse() {
        return new RadioSignalQueryResponse();
    }

    /**
     * Create an instance of {@link QueryRadioSignalClassified }
     * 
     */
    public QueryRadioSignalClassified createQueryRadioSignalClassified() {
        return new QueryRadioSignalClassified();
    }

    /**
     * Create an instance of {@link RadioSignalClassifiedQueryRequest }
     * 
     */
    public RadioSignalClassifiedQueryRequest createRadioSignalClassifiedQueryRequest() {
        return new RadioSignalClassifiedQueryRequest();
    }

    /**
     * Create an instance of {@link QueryRadioSignalClassifiedResponse }
     * 
     */
    public QueryRadioSignalClassifiedResponse createQueryRadioSignalClassifiedResponse() {
        return new QueryRadioSignalClassifiedResponse();
    }

    /**
     * Create an instance of {@link RadioSignalClassifiedQueryResponse }
     * 
     */
    public RadioSignalClassifiedQueryResponse createRadioSignalClassifiedQueryResponse() {
        return new RadioSignalClassifiedQueryResponse();
    }

    /**
     * Create an instance of {@link QueryRadioSignalSubClassified }
     * 
     */
    public QueryRadioSignalSubClassified createQueryRadioSignalSubClassified() {
        return new QueryRadioSignalSubClassified();
    }

    /**
     * Create an instance of {@link RadioSignalSubClassifiedQueryRequest }
     * 
     */
    public RadioSignalSubClassifiedQueryRequest createRadioSignalSubClassifiedQueryRequest() {
        return new RadioSignalSubClassifiedQueryRequest();
    }

    /**
     * Create an instance of {@link QueryRadioSignalSubClassifiedResponse }
     * 
     */
    public QueryRadioSignalSubClassifiedResponse createQueryRadioSignalSubClassifiedResponse() {
        return new QueryRadioSignalSubClassifiedResponse();
    }

    /**
     * Create an instance of {@link RadioSignalSubClassifiedQueryResponse }
     * 
     */
    public RadioSignalSubClassifiedQueryResponse createRadioSignalSubClassifiedQueryResponse() {
        return new RadioSignalSubClassifiedQueryResponse();
    }

    /**
     * Create an instance of {@link QueryAbnormalHistory }
     * 
     */
    public QueryAbnormalHistory createQueryAbnormalHistory() {
        return new QueryAbnormalHistory();
    }

    /**
     * Create an instance of {@link AbnormalHistoryRequest }
     * 
     */
    public AbnormalHistoryRequest createAbnormalHistoryRequest() {
        return new AbnormalHistoryRequest();
    }

    /**
     * Create an instance of {@link QueryAbnormalHistoryResponse }
     * 
     */
    public QueryAbnormalHistoryResponse createQueryAbnormalHistoryResponse() {
        return new QueryAbnormalHistoryResponse();
    }

    /**
     * Create an instance of {@link AbnormalHistoryQueryResponse }
     * 
     */
    public AbnormalHistoryQueryResponse createAbnormalHistoryQueryResponse() {
        return new AbnormalHistoryQueryResponse();
    }

    /**
     * Create an instance of {@link QuerySignalFromWarningID }
     * 
     */
    public QuerySignalFromWarningID createQuerySignalFromWarningID() {
        return new QuerySignalFromWarningID();
    }

    /**
     * Create an instance of {@link QuerySignalFromWarningIDResponse }
     * 
     */
    public QuerySignalFromWarningIDResponse createQuerySignalFromWarningIDResponse() {
        return new QuerySignalFromWarningIDResponse();
    }

    /**
     * Create an instance of {@link RadioSignalFromWarningIDQueryResponse }
     * 
     */
    public RadioSignalFromWarningIDQueryResponse createRadioSignalFromWarningIDQueryResponse() {
        return new RadioSignalFromWarningIDQueryResponse();
    }

    /**
     * Create an instance of {@link InsertRadioSignal }
     * 
     */
    public InsertRadioSignal createInsertRadioSignal() {
        return new InsertRadioSignal();
    }

    /**
     * Create an instance of {@link RadioSignalDTO }
     * 
     */
    public RadioSignalDTO createRadioSignalDTO() {
        return new RadioSignalDTO();
    }

    /**
     * Create an instance of {@link InsertRadioSignalResponse }
     * 
     */
    public InsertRadioSignalResponse createInsertRadioSignalResponse() {
        return new InsertRadioSignalResponse();
    }

    /**
     * Create an instance of {@link RadioSignalOperationReponse }
     * 
     */
    public RadioSignalOperationReponse createRadioSignalOperationReponse() {
        return new RadioSignalOperationReponse();
    }

    /**
     * Create an instance of {@link InsertAbnormalHistory }
     * 
     */
    public InsertAbnormalHistory createInsertAbnormalHistory() {
        return new InsertAbnormalHistory();
    }

    /**
     * Create an instance of {@link RadioSignalAbnormalHistoryDTO }
     * 
     */
    public RadioSignalAbnormalHistoryDTO createRadioSignalAbnormalHistoryDTO() {
        return new RadioSignalAbnormalHistoryDTO();
    }

    /**
     * Create an instance of {@link InsertAbnormalHistoryResponse }
     * 
     */
    public InsertAbnormalHistoryResponse createInsertAbnormalHistoryResponse() {
        return new InsertAbnormalHistoryResponse();
    }

    /**
     * Create an instance of {@link UpdateRadioSignal }
     * 
     */
    public UpdateRadioSignal createUpdateRadioSignal() {
        return new UpdateRadioSignal();
    }

    /**
     * Create an instance of {@link UpdateRadioSignalResponse }
     * 
     */
    public UpdateRadioSignalResponse createUpdateRadioSignalResponse() {
        return new UpdateRadioSignalResponse();
    }

    /**
     * Create an instance of {@link UpdateRadioSignalForRequest }
     * 
     */
    public UpdateRadioSignalForRequest createUpdateRadioSignalForRequest() {
        return new UpdateRadioSignalForRequest();
    }

    /**
     * Create an instance of {@link RadioSignalUpdateRequest }
     * 
     */
    public RadioSignalUpdateRequest createRadioSignalUpdateRequest() {
        return new RadioSignalUpdateRequest();
    }

    /**
     * Create an instance of {@link UpdateRadioSignalForRequestResponse }
     * 
     */
    public UpdateRadioSignalForRequestResponse createUpdateRadioSignalForRequestResponse() {
        return new UpdateRadioSignalForRequestResponse();
    }

    /**
     * Create an instance of {@link UpdateAbnormalHistory }
     * 
     */
    public UpdateAbnormalHistory createUpdateAbnormalHistory() {
        return new UpdateAbnormalHistory();
    }

    /**
     * Create an instance of {@link UpdateAbnormalHistoryResponse }
     * 
     */
    public UpdateAbnormalHistoryResponse createUpdateAbnormalHistoryResponse() {
        return new UpdateAbnormalHistoryResponse();
    }

    /**
     * Create an instance of {@link RemoveRadioSignal }
     * 
     */
    public RemoveRadioSignal createRemoveRadioSignal() {
        return new RemoveRadioSignal();
    }

    /**
     * Create an instance of {@link ArrayOfString }
     * 
     */
    public ArrayOfString createArrayOfString() {
        return new ArrayOfString();
    }

    /**
     * Create an instance of {@link RemoveRadioSignalResponse }
     * 
     */
    public RemoveRadioSignalResponse createRemoveRadioSignalResponse() {
        return new RemoveRadioSignalResponse();
    }

    /**
     * Create an instance of {@link RemoveAbnormalHistory }
     * 
     */
    public RemoveAbnormalHistory createRemoveAbnormalHistory() {
        return new RemoveAbnormalHistory();
    }

    /**
     * Create an instance of {@link RemoveAbnormalHistoryResponse }
     * 
     */
    public RemoveAbnormalHistoryResponse createRemoveAbnormalHistoryResponse() {
        return new RemoveAbnormalHistoryResponse();
    }

    /**
     * Create an instance of {@link ArrayOfSignalTypeDTO }
     * 
     */
    public ArrayOfSignalTypeDTO createArrayOfSignalTypeDTO() {
        return new ArrayOfSignalTypeDTO();
    }

    /**
     * Create an instance of {@link SignalTypeDTO }
     * 
     */
    public SignalTypeDTO createSignalTypeDTO() {
        return new SignalTypeDTO();
    }

    /**
     * Create an instance of {@link ArrayOfInt }
     * 
     */
    public ArrayOfInt createArrayOfInt() {
        return new ArrayOfInt();
    }

    /**
     * Create an instance of {@link ArrayOfRadioSignalDTO }
     * 
     */
    public ArrayOfRadioSignalDTO createArrayOfRadioSignalDTO() {
        return new ArrayOfRadioSignalDTO();
    }

    /**
     * Create an instance of {@link ArrayOfRadioSignalAppendDTO }
     * 
     */
    public ArrayOfRadioSignalAppendDTO createArrayOfRadioSignalAppendDTO() {
        return new ArrayOfRadioSignalAppendDTO();
    }

    /**
     * Create an instance of {@link RadioSignalAppendDTO }
     * 
     */
    public RadioSignalAppendDTO createRadioSignalAppendDTO() {
        return new RadioSignalAppendDTO();
    }

    /**
     * Create an instance of {@link ArrayOfRadioSignalStationDTO }
     * 
     */
    public ArrayOfRadioSignalStationDTO createArrayOfRadioSignalStationDTO() {
        return new ArrayOfRadioSignalStationDTO();
    }

    /**
     * Create an instance of {@link RadioSignalStationDTO }
     * 
     */
    public RadioSignalStationDTO createRadioSignalStationDTO() {
        return new RadioSignalStationDTO();
    }

    /**
     * Create an instance of {@link RadioStationSignalDTO }
     * 
     */
    public RadioStationSignalDTO createRadioStationSignalDTO() {
        return new RadioStationSignalDTO();
    }

    /**
     * Create an instance of {@link RadioStationDTO }
     * 
     */
    public RadioStationDTO createRadioStationDTO() {
        return new RadioStationDTO();
    }

    /**
     * Create an instance of {@link RadioFreqDTO }
     * 
     */
    public RadioFreqDTO createRadioFreqDTO() {
        return new RadioFreqDTO();
    }

    /**
     * Create an instance of {@link ArrayOfRadioSignalAbnormalHistoryDTO }
     * 
     */
    public ArrayOfRadioSignalAbnormalHistoryDTO createArrayOfRadioSignalAbnormalHistoryDTO() {
        return new ArrayOfRadioSignalAbnormalHistoryDTO();
    }

    /**
     * Create an instance of {@link ArrayOfFrequencyBand }
     * 
     */
    public ArrayOfFrequencyBand createArrayOfFrequencyBand() {
        return new ArrayOfFrequencyBand();
    }

    /**
     * Create an instance of {@link FrequencyBand }
     * 
     */
    public FrequencyBand createFrequencyBand() {
        return new FrequencyBand();
    }

    /**
     * Create an instance of {@link ArrayOfSignalStaticsOnFreqBand }
     * 
     */
    public ArrayOfSignalStaticsOnFreqBand createArrayOfSignalStaticsOnFreqBand() {
        return new ArrayOfSignalStaticsOnFreqBand();
    }

    /**
     * Create an instance of {@link SignalStaticsOnFreqBand }
     * 
     */
    public SignalStaticsOnFreqBand createSignalStaticsOnFreqBand() {
        return new SignalStaticsOnFreqBand();
    }

    /**
     * Create an instance of {@link ArrayOfSignalStatics }
     * 
     */
    public ArrayOfSignalStatics createArrayOfSignalStatics() {
        return new ArrayOfSignalStatics();
    }

    /**
     * Create an instance of {@link SignalStatics }
     * 
     */
    public SignalStatics createSignalStatics() {
        return new SignalStatics();
    }

    /**
     * Create an instance of {@link ArrayOfSignalStaticsOnStation }
     * 
     */
    public ArrayOfSignalStaticsOnStation createArrayOfSignalStaticsOnStation() {
        return new ArrayOfSignalStaticsOnStation();
    }

    /**
     * Create an instance of {@link SignalStaticsOnStation }
     * 
     */
    public SignalStaticsOnStation createSignalStaticsOnStation() {
        return new SignalStaticsOnStation();
    }

    /**
     * Create an instance of {@link ArrayOfSignalSubStaticsOnFreqBand }
     * 
     */
    public ArrayOfSignalSubStaticsOnFreqBand createArrayOfSignalSubStaticsOnFreqBand() {
        return new ArrayOfSignalSubStaticsOnFreqBand();
    }

    /**
     * Create an instance of {@link SignalSubStaticsOnFreqBand }
     * 
     */
    public SignalSubStaticsOnFreqBand createSignalSubStaticsOnFreqBand() {
        return new SignalSubStaticsOnFreqBand();
    }

    /**
     * Create an instance of {@link ArrayOfSignalSubStaticsOnStation }
     * 
     */
    public ArrayOfSignalSubStaticsOnStation createArrayOfSignalSubStaticsOnStation() {
        return new ArrayOfSignalSubStaticsOnStation();
    }

    /**
     * Create an instance of {@link SignalSubStaticsOnStation }
     * 
     */
    public SignalSubStaticsOnStation createSignalSubStaticsOnStation() {
        return new SignalSubStaticsOnStation();
    }

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
