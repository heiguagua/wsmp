package com.chinawiserv.wsmp.controller.data;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.chinawiserv.apps.util.logger.Logger;
import com.chinawiserv.wsmp.client.WebServiceSoapFactory;
import com.chinawiserv.wsmp.hbase.HbaseClient;
import com.chinawiserv.wsmp.hbase.query.OccAndMax;
import com.chinawiserv.wsmp.levellocate.socket.model.Params;
import com.chinawiserv.wsmp.model.LevelLocate;
import com.chinawiserv.wsmp.pojo.IntensiveMonitoring;
import com.chinawiserv.wsmp.pojo.Station;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.sefon.ws.model.xsd.StationInfoPagedResult;
import com.sefon.ws.model.xsd.StationQuerySpecInfo;
import com.sefon.ws.service.impl.StationService;
import de.onlinehome.geomath.jk3d.Jk2d;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.WebApplicationContext;
import org.tempuri.*;

import java.math.BigInteger;
import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.util.*;

import static java.util.stream.Collectors.toList;

@RestControllerAdvice
@RequestMapping("/data/alarm")
public class AlarmDataController {

    @Autowired
    private WebServiceSoapFactory service;

    @Autowired
    private WebApplicationContext applicationContext;

    @Autowired
    private HbaseClient hbaseClient;

    @Autowired
    private StationService stationService;

//    @Autowired
//    private com.chinawiserv.wsmp.levellocate.LevelLocate Locate;

    @Autowired
    private Jk2d jk2d;

    @Value("${upperBound.value:5000000}")
    long upperBound;

    @Value("${upperBound.value:5000000}")
    long lowerBound;

    private ObjectMapper mapper = new ObjectMapper();


    @GetMapping(path = "/secondLevelChart")
    public Object secondLevelChart(@RequestParam String beginTime, @RequestParam long centorFreq, @RequestParam String stationCode) {

        HashMap<String, Object> reslutMap = new HashMap<>();
        //
        // LocalDate loacl = LocalDate.now();
        //
        // DateTimeFormatter yearformatter =
        // DateTimeFormatter.ofPattern("yyyyMMdd");
        // String first = loacl.format(yearformatter);
        //
        // LocalDateTime time = LocalDateTime.now();
        //
        // DateTimeFormatter timeformatter =
        // DateTimeFormatter.ofPattern("HHmmss");
        // String last = timeformatter.format(time);
        // beginTime = first + last;

        String id = "52010126";
        long frequency = (long) (88.8 * 1000000);

        try {

            if (beginTime.length() == 8) {
                beginTime = beginTime.concat("000000");
            }

            OccAndMax reslutResponce = hbaseClient.queryOccHour(stationCode, beginTime, centorFreq);
            //OccAndMax reslutResponce = hbaseClient.queryOccHour(stationCode, beginTime, centorFreq);
            Map<String, Object> Max = reslutResponce.getMax();
            Map<String, Object> Occ = reslutResponce.getOcc();
            if (Occ.size() == 0) {

                HashMap<String, Object> resoluteHashMap = Maps.newHashMap();

                String[] xAxis = new String[]{};
                double[] series = new double[]{};

                resoluteHashMap.put("xAxis", xAxis);
                resoluteHashMap.put("series", series);

                reslutMap.put("dayOcc", resoluteHashMap);
                Logger.info("以天计算占用度从hbase中查询正常返回值为空 查询时间为{}，页面入参：监测站id{}，开始时间{},中心频率{}", LocalDateTime.now().toString(), stationCode, beginTime, centorFreq);
            } else {

                LinkedList<Object> xAxis = Lists.newLinkedList();
                LinkedList<Object> series = Lists.newLinkedList();
                Occ.forEach((k, v) -> {
                    xAxis.add(k);
                    series.add(v);
                });

                HashMap<String, Object> restlutdayOccHashMap = Maps.newHashMap();

                restlutdayOccHashMap.put("xAxis", xAxis);
                restlutdayOccHashMap.put("series", series);

                reslutMap.put("dayOcc", restlutdayOccHashMap);
                Logger.info("以天计算占用度从hbase中查询正常有返回值为{} ， 查询时间为{}，页面入参：监测站id{}，开始时间{},中心频率{}", Occ, LocalDateTime.now().toString(), stationCode, beginTime, centorFreq);
            }

            if (Max.size() == 0) {

                HashMap<String, Object> resoluteHashMap = Maps.newHashMap();

                String[] xAxis = new String[]{};
                double[] series = new double[]{};

                resoluteHashMap.put("xAxis", xAxis);
                resoluteHashMap.put("series", series);

                reslutMap.put("max", resoluteHashMap);
                Logger.info("以天计算峰值从hbase中查询正常返回值为空 查询时间为{}，页面入参：监测站id{}，开始时间{},中心频率{}", LocalDateTime.now().toString(), stationCode, beginTime, centorFreq);
            } else {

                LinkedList<Object> xAxis = Lists.newLinkedList();
                LinkedList<Object> series = Lists.newLinkedList();

                final double pow = Math.pow(10, 6);

                Max.forEach((k, v) -> {

                    final double key = Double.parseDouble(k) / pow;

                    xAxis.add(key);
                    series.add(v);
                });

                HashMap<String, Object> restlutHashMap = Maps.newHashMap();

                restlutHashMap.put("xAxis", xAxis);
                restlutHashMap.put("series", series);

                reslutMap.put("max", restlutHashMap);
                Logger.info("以天计算占用度从hbase中查询正常有返回值为{} ， 查询时间为{}，页面入参：监测站id{}，开始时间{},中心频率{}", Max, LocalDateTime.now().toString(), stationCode, beginTime, centorFreq);
            }

            return reslutMap;
        } catch (Exception e) {
            // TODO Auto-generated catch block
            Logger.error("天占用度和峰值从hbase中查询异常 ， 查询时间为{}，页面入参：监测站id{}，开始时间{},中心频率{},异常的信息为{}", LocalDateTime.now().toString(), stationCode, beginTime, centorFreq, e);
            HashMap<String, Object> resoluteHashMap = Maps.newHashMap();

            String[] xAxis = new String[]{};
            double[] series = new double[]{};

            resoluteHashMap.put("xAxis", xAxis);
            resoluteHashMap.put("series", series);

            reslutMap.put("max", resoluteHashMap);

            return reslutMap;

        }
    }

    @GetMapping(path = "/firstLevelChart")
    public Object firstLevelChart(@RequestParam String beginTime, @RequestParam long centorFreq, @RequestParam String stationCode) {

//        LocalDate loacl = LocalDate.now();
//
//        DateTimeFormatter yearformatter = DateTimeFormatter.ofPattern("yyyyMMdd");
//         String first = loacl.format(yearformatter);
//
//        LocalDateTime time = LocalDateTime.now();
//
//        DateTimeFormatter timeformatter = DateTimeFormatter.ofPattern("HHmmss");
//        String last = timeformatter.format(time);
//        beginTime = first + last;

        HashMap<String, Object> reslutMap = new HashMap<>();

        HashMap<String, Object> resoluteHashMap = Maps.newHashMap();

        HashMap<String, Object> occReslute = Maps.newHashMap();
        try {

            String id = "52010126";
            long frequency = (long) (88.8 * 1000000);
            //测试或者正式环境使用
//            Map<Object, Object> max = hbaseClient.queryMaxLevels(stationCode, centorFreq, upperBound, lowerBound, beginTime);
//            Map<String, Object> occ = hbaseClient.queryOccDay(stationCode, beginTime, 90, centorFreq).getOcc();

            Map<Object, Object> max = hbaseClient.queryMaxLevels(stationCode, centorFreq, upperBound, lowerBound, beginTime);
            Map<String, Object> occ = hbaseClient.queryOccDay(stationCode, beginTime, 90, frequency).getOcc();

            if (occ.size() == 0) {

                HashMap<String, Object> restlutHashMap = Maps.newHashMap();

                String[] xAxis = new String[]{};
                double[] series = new double[]{};

                restlutHashMap.put("xAxis", xAxis);
                restlutHashMap.put("series", series);

                reslutMap.put("monthOcc", restlutHashMap);
                Logger.info("以三个月计算占用度从hbase中查询正常返回值为空 查询时间为{}，页面入参：监测站id{}，开始时间{},中心频率{}", LocalDateTime.now().toString(), stationCode, beginTime, centorFreq);
            } else {

                LinkedList<Object> xAxis = Lists.newLinkedList();
                LinkedList<Object> series = Lists.newLinkedList();

                occ.forEach((k, v) -> {
                    xAxis.add(k);
                    series.add(v);
                });


                occReslute.put("xAxis", xAxis);
                occReslute.put("series", series);

                reslutMap.put("monthOcc", occReslute);
                Logger.info("以三个月计算占用度从hbase中查询正常有返回值为{} ， 查询时间为{}，页面入参：监测站id{}，开始时间{},中心频率{}", occ, LocalDateTime.now().toString(), stationCode, beginTime, centorFreq);
            }

            if (max.size() == 0) {

                String[] xAxis = new String[]{};
                double[] series = new double[]{};

                resoluteHashMap.put("xAxis", xAxis);
                resoluteHashMap.put("series", series);

                reslutMap.put("max", resoluteHashMap);
                Logger.info("以三个月计算占用度从hbase中查询正常返回值为空 查询时间为{}，页面入参：监测站id{}，开始时间{},中心频率{}", LocalDateTime.now().toString(), stationCode, beginTime, centorFreq);
                return reslutMap;
            } else {

                LinkedList<Object> xAxis = Lists.newLinkedList();
                LinkedList<Object> series = Lists.newLinkedList();

                final double pow = Math.pow(10, 6);

                max.forEach((k, v) -> {

                    final double key = Double.parseDouble(k.toString()) / pow;

                    xAxis.add(key);
                    series.add(v);
                });


                resoluteHashMap.put("xAxis", xAxis);
                resoluteHashMap.put("series", series);

                reslutMap.put("max", resoluteHashMap);
                Logger.info("以三个月计算峰值从hbase中查询正常有返回值为{} ， 查询时间为{}，页面入参：监测站id{}，开始时间{},中心频率{}", max, LocalDateTime.now().toString(), stationCode, beginTime, centorFreq);
            }

            return reslutMap;
        } catch (Exception e) {
            Logger.error("以三个月计算峰值和占用度从hbase中查询其中一个或都出现异常 ， 查询时间为{}，页面入参：监测站id{}，开始时间{},中心频率{}， 异常为{}", LocalDateTime.now().toString(), stationCode, beginTime, centorFreq, e);
            String[] xAxis = new String[]{};
            double[] series = new double[]{};

            resoluteHashMap.put("xAxis", xAxis);
            resoluteHashMap.put("series", series);

            reslutMap.put("max", resoluteHashMap);
            return reslutMap;
        }
    }

    @PostMapping(path = "/intensivemonitoring")
    public void intensivemonitoring(@RequestBody IntensiveMonitoring in) {

        if (in.getStatus() == 0) {
            // 需要取消对应的
            EntityWrapper<IntensiveMonitoring> ew = new EntityWrapper<>(in);
            ew.where("SINGAL_FREQUENCY = {0}", in.getSingalFrequency());
        }

    }

    @PostMapping(path = "/warringconfirm")
    public void warning_confirm(@RequestBody String param) throws JsonProcessingException {

        FreqWarningOperationResponse res = null;
        try {
            res = (FreqWarningOperationResponse) service.freqWarnServiceCall("update", param, FreqWarningDTO.class);
        } catch (Exception e) {

            e.printStackTrace();
            Logger.error("确认告警接口调用异常 , 操作时间{},入参:{} 异常:{}", LocalDateTime.now().toString(), param, e);
        }
        Logger.info("确认告警接口调用正常，返回信息{}", JSON.toJSONString(res));
    }

    @PostMapping(path = "/getStation")
    public @ResponseBody
    Map<String, Object> getStationPiont(@RequestBody Map<String, Object> param) {

//        long centerFreq = (long) (88.8 * 1000000);
//        String dateTime = "20170810235959";
        Params params = new Params();
//        测试或正式环境使用
//        Long frequency = Long.parseLong((String) param.get("frequency"));
//        List<LevelLocate> relate = hbaseClient.queryLevelLocate((String) param.get("beginTime"), frequency );
        List<LevelLocate> mapPoint = Collections.emptyList();
        List<Map<String, Object>> levelPoint = Lists.newLinkedList();
        try {

            final String beginTime = param.get("beginTime").toString();
            final long frequency =  Long.valueOf(param.get("frequency").toString());

            List<LevelLocate> relate = hbaseClient.queryLevelLocate(beginTime, frequency);
            Logger.info("均值查询正常返回个数为 :{}, 操作时间：{},入参：开始时间：{}，中心频率：{}", relate.size(),  LocalDateTime.now().toString(), beginTime, frequency);
            List<String> stationcode = (List<String>) param.get("stationCodes");

            mapPoint = relate.stream().filter(t -> stationcode.contains(t.getId())).collect(toList());

            int[] ids = relate.stream().mapToInt(m -> Integer.valueOf(m.getId())).toArray();

            double[] flon = relate.stream().mapToDouble(LevelLocate::getFlon).toArray();
            double[] flat = relate.stream().mapToDouble(LevelLocate::getFlat).toArray();
            double[] level = relate.stream().mapToDouble(LevelLocate::getLevel).toArray();
            //至少要八个点才能计算出来

            params.setSid("" + System.currentTimeMillis());// sid能够表该次计算的唯一标识
            params.setStype((byte) 3);// 固定3，标识计算类型为场强计算
            params.setDistanceTh(10d);// 距离门限，单位km，值是界面传递进来的
            params.setNum(relate.size());// 传感器数
            params.setIds(ids);// 所有传感器
            params.setLon(flon);// 每个传感器的经度
            params.setLat(flat);// 每个传感器的纬度
            params.setLevel(level);// 每个传感器的均值

        } catch (NumberFormatException e) {
            Logger.error("场强查询异常 ,操作时间：{},入参：开始时间：{}，中心频率：{} 异常 ：{}", LocalDateTime.now(), param.get("beginTime"), param.get("frequency"), e);
        }

        int coulm = mapPoint.size();

        double[][] p = new double[coulm][3];

        for (int index = 0; index < coulm; index++) {
            p[index][0] = mapPoint.get(index).getFlon();
            p[index][1] = mapPoint.get(index).getFlat();
            p[index][2] = mapPoint.get(index).getLevel();
        }

        double[][] t = new double[0][0];

        if (coulm > 0) {
            t = jk2d.jk2d_ret(0.5, 10, 0.5, 10, p);
        }
        int size = t.length;
        List<Map<String, Object>> kriking = Lists.newLinkedList();

        Map<String, Object> spatialReference = Maps.newHashMap();
        DecimalFormat xformart = new DecimalFormat("#.00000000");
        DecimalFormat yformart = new DecimalFormat("#.000000000");
        spatialReference.put("wkid", 4326);
        for (int index = 0; index < size; index++) {
            Map<String, Object> element = Maps.newHashMap();
            Map<String, Object> count = Maps.newHashMap();
            Map<String, Object> geometry = Maps.newLinkedHashMap();
            double val = t[index][2];
            double x = t[index][0];
            double y = t[index][1];
            geometry.put("spatialReference", spatialReference);
            geometry.put("type", "point");
            geometry.put("x", Double.valueOf(xformart.format(x * 20037508.34 / 180)));
            y = Math.log(Math.tan((90 + y) * Math.PI / 360)) / (Math.PI / 180);
            y = y * 20037508.34 / 180;
            geometry.put("y", Double.valueOf(yformart.format(y)));
            count.put("count", (int) val);
            element.put("attributes", count);
            element.put("geometry", geometry);
            kriking.add(element);
        }


        //测试或正式环境使用
        //List<String> stationcode = (List<String>) param.get("stationcode");

        try {

            for (LevelLocate levelLocate : mapPoint) {

                params.setWarningId(Integer.valueOf(levelLocate.getId()));// 告警传感器id
                // Result result = Locate.execute(params);
                Map<String, Object> mapLocate = Maps.newHashMap();

//                mapLocate.put("x", result.getOutLon());
//                mapLocate.put("y", result.getOutLat());
//                mapLocate.put("radius", result.getRangeR());
//                mapLocate.put("stationId", result.getSid());

                levelPoint.add(mapLocate);
            }

        } catch (Exception e) {
            // TODO Auto-generated catch block
            Logger.error("场强定位计算 操作时间{} 入参：{},异常 ：{}", LocalDateTime.now().toString(), param, e);
        }
        Logger.info("场强定位计算正常 操作时间{} 返回值为{}", LocalDateTime.now().toString(), JSON.toJSONString(levelPoint));
        List<Map<String, String>> stationPiont = mapPoint.stream().map(station -> {
            HashMap<String, String> element = Maps.newHashMap();
            element.put("x", station.getFlon() + "");
            element.put("y", station.getFlat() + "");
            element.put("count", Double.longBitsToDouble(station.getLevel()) + "");
            element.put("stationId", station.getId());
            return element;
        }).collect(toList());

        Map<String, Object> mapPiont = new HashMap<>();

        mapPiont.put("stationPiont", stationPiont);
        mapPiont.put("levelPoint", levelPoint);
        mapPiont.put("kriking", kriking);

        // map.put("x", "106.709177096");
        // map.put("y", "26.6299067414");
        // map.put("count", "45");
        // map.put("stationId", "oopsoo");
        return mapPiont;
    }


    @GetMapping(path = "/stationsf")
    public Object getStationBySF(@RequestParam Map<String, Object> param) {

        final Object offset = param.get("offset");
        final Object limit = param.get("limit");
        final String areaCode = (String) param.get("areaCode");
        String[] areaCodes = areaCode.split(",");
        StationQuerySpecInfo info = new StationQuerySpecInfo();

        int pageNumber = Integer.parseInt(offset.toString());
        int limitNumber = Integer.parseInt(limit.toString());

        ArrayList<String> dr = new ArrayList<>(Arrays.asList(areaCodes));
        info.setSignalFreq(Double.parseDouble((String) param.get("centorFreq")) / 1000000);
        info.setAreaCodes(dr);


        Map<String, Object> hasMap = Maps.newLinkedHashMap();

        try {
            StationInfoPagedResult reslut = stationService.getStationServiceHttpSoap11Endpoint().queryStationWithPagination(info, pageNumber, limitNumber);

            int totlal = reslut.getPageInfo().getTotalPages();

            List<Station> stations = reslut.getStations().stream().map(s -> {

                String id = s.getStationID();
                String stationName = s.getSTATName();
                String centerFreqStr = s.getFREQLC() + "";
                String bandWidth = s.getNETBand() + "";
                return new Station(id, stationName, centerFreqStr, bandWidth);

            }).collect(toList());

            hasMap.put("total", totlal);
            hasMap.put("rows", stations);
            Logger.info("四方台站webservice  StationService调用正常，操作时间{} ,入参 ：查询条件{} 当前个数{} 限制个数{}", LocalDateTime.now().toString(), info, pageNumber, limitNumber);
        } catch (Exception e) {
            Logger.error("四方台站webservice  StationService调用异常，操作时间{} ,入参 ：查询条件{} 当前个数{} 限制个数{} 异常详情 : {}", LocalDateTime.now().toString(), info, pageNumber, limitNumber, e);
        }

        return hasMap;

    }

    @PostMapping("/instersingal")
    public String insterSingal(@RequestBody Map<String, Map<String, Object>> param) throws JsonProcessingException {

        final RadioSignalOperationReponse res;
        try {
            final Map<String, Object> signal = param.get("sigal");

            final Map<String, Object> station = param.get("station");
            final FreqWarningQueryResponse response = (FreqWarningQueryResponse) service.freqWarnServiceCall("query",
                    mapper.writeValueAsString(signal.get("warmingId")), FreqWarningQueryRequest.class);

            final FreqWarningDTO t = response.getWarningInfos().getFreqWarningDTOs().size() > 0 ? response.getWarningInfos().getFreqWarningDTOs().get(0)
                    : new FreqWarningDTO();

            final BigInteger bandWidth = t.getBandWidth();
            final BigInteger centerFreq = t.getCenterFreq();

            List<Map<String, String>> ids = response.getWarningInfos().getFreqWarningDTOs().get(0).getStatList().getFreqWarningStatDTOs().stream().map(m -> {
                HashMap<String, String> map = Maps.newHashMap();
                map.put("stationNumber", m.getStationGUID());
                return map;
            }).collect(toList());

            station.put("centerFreq", centerFreq);
            station.put("bandWidth", bandWidth);

            String stationId = (String) signal.get("stationId");

            String typeCode = (String) signal.get("typeCode");

            String areaCode = stationId.substring(0, 4);

            station.put("stationKey", station.get("stationKey"));

            station.put("typeCode", typeCode);

            station.put("areaCode", areaCode);

            Map<String, Object> radioSignalAbnormalHistoryDTO = Maps.newHashMap();

            radioSignalAbnormalHistoryDTO.put("radioSignalStationDTO", ids);

            station.put("stationDTOs", radioSignalAbnormalHistoryDTO);

            res = (RadioSignalOperationReponse) service.radioSignalServiceCall("insertRadioSignal",
                    mapper.writeValueAsString(station), RadioSignalDTO.class);
            Map<String, Object> waringID = (Map<String, Object>) signal.get("warmingId");
            service.getFreqWarnService().updateStatus((String) waringID.get("id"), 1);
            Logger.info("告警生成信号成功 操作时间{} 入参:{} 返回消息{}", LocalDateTime.now().toString(), JSON.toJSONString(param), JSON.toJSONString(res));
        } catch (JsonProcessingException e) {

            Logger.error("告警生成信号异常 操作时间{} 入参:{} 返回消息{}", LocalDateTime.now().toString(), JSON.toJSONString(param), e);
        }

        return null;
    }

    @GetMapping(path = {"/StationInfo"
    })
    public Object stationList(@RequestParam Map<String, Object> map) throws JsonProcessingException {

        Map<String, Object> reslut = null;
        try {
            String index = (String) map.get("offset");
            String limit = (String) map.get("limit");
            String areaCode = (String) map.get("areaCode");
            String[] areaList = areaCode.split(",");
            List<String> areaCodeList = new ArrayList<>(Arrays.asList(areaList));


            Map<String, Object> requestParam = Maps.newLinkedHashMap();
            requestParam.put("index", index);
            requestParam.put("count", limit);

            Map<String, Object> list = Maps.newLinkedHashMap();
            list.put("string", areaCodeList);
            requestParam.put("areaCodeList", list);

            final RStatQuerySignalsResponse2 response = (RStatQuerySignalsResponse2) service.radioStationServiceCall("rStatQuerySignals",
                    mapper.writeValueAsString(requestParam), RStatQuerySignalsRequest.class);

            List<Station> reslutDtos;
            reslutDtos = response.getRStatSignalList().getRadioStationSignalDTO().stream().map((RadioStationSignalDTO t) -> {

                final RadioStationDTO radioStationDTO = t.getStation();

                final RadioFreqDTO radioFreqDTO = t.getFreq();

                int centerFre = radioFreqDTO.getCenterFreq().intValue() / 1000000;
                int tapeWidth = radioFreqDTO.getBandWidth().intValue() / 1000000;
                final String centerFreString = centerFre + "";
                final String tapeWidthString = tapeWidth + "";

                return new Station(radioStationDTO.getID(), radioStationDTO.getName(), centerFreString, tapeWidthString);
            }).collect(toList());

            reslut = Maps.newHashMap();

            reslut.put("total", response.getTotalNum());
            reslut.put("rows", reslutDtos);
            Logger.info("博创台情webservice接口调用成功 操作时间{} 返回值", LocalDateTime.now().toString(), JSON.toJSONString(reslutDtos));
        } catch (JsonProcessingException e) {
            Logger.error("博创台情webservice接口调用异常 操作时间{} 入参{} 异常:{}", LocalDateTime.now().toString(), map, e);
        }
        return reslut;
    }

//    @GetMapping("/maxlevel")
//    public Object getMaxlevel(@RequestParam String beginTime, @RequestParam long centorFreq, @RequestParam String stationCode) {
//        try {
//
//            LocalDate loacl = LocalDate.now();
//
//            DateTimeFormatter yearformatter = DateTimeFormatter.ofPattern("yyyyMMdd");
//            String first = loacl.format(yearformatter);
//
//            LocalDateTime time = LocalDateTime.now();
//
//            DateTimeFormatter timeformatter = DateTimeFormatter.ofPattern("HHmmss");
//            String last = timeformatter.format(time);
//            beginTime = first + last;
//
//            String id = "52010118";
//            long frequency = (long) (88.8 * 1000000);
//
//            Map<Object, Object> reponceReslut = hbaseClient.queryMaxLevels(stationCode, frequency, upperBound, lowerBound, beginTime);
//
//            LinkedList<Object> xAxis = Lists.newLinkedList();
//            LinkedList<Object> series = Lists.newLinkedList();
//
//            final double pow = Math.pow(10, 6);
//
//            reponceReslut.forEach((k, v) -> {
//
//                final double key = Double.parseDouble(k.toString()) / pow;
//
//                xAxis.add(key);
//                series.add(v);
//            });
//
//            HashMap<String, Object> restlutHashMap = Maps.newHashMap();
//
//            restlutHashMap.put("xAxis", xAxis);
//            restlutHashMap.put("series", series);
//
//            return restlutHashMap;
//
//        } catch (Exception e) {
//            // TODO Auto-generated catch block
//            Logger.error("峰值查询异常", e);
//        }
//
//        return Maps.newHashMap();
//    }
}
