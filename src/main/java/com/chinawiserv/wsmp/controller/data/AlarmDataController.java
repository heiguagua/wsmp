package com.chinawiserv.wsmp.controller.data;

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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.WebApplicationContext;
import org.tempuri.*;

import java.math.BigInteger;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

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

    @Autowired
    private com.chinawiserv.wsmp.levellocate.LevelLocate Locate;

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

            OccAndMax reslutResponce = hbaseClient.queryOccHour(id, beginTime, frequency);
            Map<String, Object> Max = reslutResponce.getMax();
            Map<String, Object> Occ = reslutResponce.getOcc();
            if (Occ.size() == 0) {

                HashMap<String, Object> resoluteHashMap = Maps.newHashMap();

                String[] xAxis = new String[]{};
                double[] series = new double[]{};

                resoluteHashMap.put("xAxis", xAxis);
                resoluteHashMap.put("series", series);

                reslutMap.put("dayOcc", resoluteHashMap);
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
            }

            if (Max.size() == 0) {

                HashMap<String, Object> resoluteHashMap = Maps.newHashMap();

                String[] xAxis = new String[]{};
                double[] series = new double[]{};

                resoluteHashMap.put("xAxis", xAxis);
                resoluteHashMap.put("series", series);

                reslutMap.put("max", resoluteHashMap);

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

            }

            return reslutMap;
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();

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

        LocalDate loacl = LocalDate.now();

        DateTimeFormatter yearformatter = DateTimeFormatter.ofPattern("yyyyMMdd");
        String first = loacl.format(yearformatter);

        LocalDateTime time = LocalDateTime.now();

        DateTimeFormatter timeformatter = DateTimeFormatter.ofPattern("HHmmss");
        String last = timeformatter.format(time);
        beginTime = first + last;

        HashMap<String, Object> reslutMap = new HashMap<>();

        HashMap<String, Object> resoluteHashMap = Maps.newHashMap();

        HashMap<String, Object> occReslute = Maps.newHashMap();
        try {

            String id = "52010126";
            long frequency = (long) (88.8 * 1000000);

            Map<Object, Object> max = hbaseClient.queryMaxLevels(id, frequency, upperBound, lowerBound, beginTime);
            Map<String, Object> occ = hbaseClient.queryOccDay(id, beginTime, 90, frequency).getOcc();
            if (occ.size() == 0) {

                HashMap<String, Object> restlutHashMap = Maps.newHashMap();

                String[] xAxis = new String[]{};
                double[] series = new double[]{};

                restlutHashMap.put("xAxis", xAxis);
                restlutHashMap.put("series", series);

                reslutMap.put("monthOcc", restlutHashMap);
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
            }

            if (max.size() == 0) {

                String[] xAxis = new String[]{};
                double[] series = new double[]{};

                resoluteHashMap.put("xAxis", xAxis);
                resoluteHashMap.put("series", series);

                reslutMap.put("max", resoluteHashMap);
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

            }

            return reslutMap;
        } catch (Exception e) {
            e.printStackTrace();
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

        FreqWarningOperationResponse res = (FreqWarningOperationResponse) service.freqWarnServiceCall("update", param, FreqWarningDTO.class);
        ObjectMapper mapper = new ObjectMapper();

        Logger.info(mapper.writeValueAsString(res));
    }

    @PostMapping(path = "/getStation")
    public @ResponseBody
    Map<String, Object> getStationPiont(@RequestBody Map<String, Object> param) {

        long centerFreq = (long) (88.8 * 1000000);
        String dateTime = "20170803000000";

        List<LevelLocate> relate = hbaseClient.queryLevelLocate(dateTime, centerFreq);

        relate.get(0).setFlon(105.080555555556);
        relate.get(0).setFlat(26.6838888888889);

        relate.get(1).setFlon(106.098333333333);
        relate.get(1).setFlat(26.6636111111111);

        //List<String> stationcode = (List<String>) param.get("stationcode");

        List<String> stationcode = Lists.newLinkedList();

        stationcode.add("52010120");
        stationcode.add("52010126");

        List<LevelLocate> mapPoint = relate.stream().filter(t -> stationcode.contains(t.getId())).collect(toList());

        List<Map<String, Object>> levelPoint = Lists.newLinkedList();

        int[] ids = relate.stream().mapToInt(m -> Integer.parseInt(m.getId())).toArray();

        Params params = new Params();

        double[] flon = relate.stream().mapToDouble(LevelLocate::getFlon).toArray();
        double[] flat = relate.stream().mapToDouble(LevelLocate::getFlat).toArray();
        double[] level = relate.stream().mapToDouble(LevelLocate::getLevel).toArray();

        params.setSid("" + System.currentTimeMillis());// sid能够表该次计算的唯一标识
        params.setStype((byte) 3);// 固定3，标识计算类型为场强计算
        params.setDistanceTh(10d);// 距离门限，单位km，值是界面传递进来的
        params.setNum(relate.size());// 传感器数

        params.setIds(ids);// 所有传感器

        params.setLon(flon);// 每个传感器的经度
        params.setLat(flat);// 每个传感器的纬度
        params.setLevel(level);// 每个传感器的均值

        Map<String, Object> map = Maps.newHashMap();

        map.put("x", 105.080555555556);
        map.put("y", 27.6838888888889);
        map.put("radius",10000);
        map.put("stationId", "xxxxx");

        Map<String, Object> map1 = Maps.newHashMap();

        map1.put("x", 105.080555555556);
        map1.put("y", 25.6838888888889);
        map1.put("radius",40000);
        map1.put("stationId", "xxxxx");

        levelPoint.add(map);
        levelPoint.add(map1);

//        try {
//
//            for (LevelLocate levelLocate : mapPoint) {
//
//                params.setWarningId(Integer.parseInt(levelLocate.getId()));// 告警传感器id
//                Result result = Locate.execute(params);
//
//                System.out.println(JSON.toJSON(result));
//
//                Map<String, Object> map = Maps.newHashMap();
//
//                map.put("x", result.getOutLon());
//                map.put("y", result.getOutLat());
//                map.put("radius", result.getRangeR());
//                map.put("stationId", result.getSid());
//
//                levelPoint.add(map);
//            }
//
//
//        } catch (Exception e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        }

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
        final Object areaCode = param.get("areaCode");

        StationQuerySpecInfo info = new StationQuerySpecInfo();

        int pageNumber = Integer.parseInt(offset.toString());
        int limitNumber = Integer.parseInt(limit.toString());

        List<String> areaCodesList = Lists.newLinkedList();

        if (areaCode != null) {
            areaCodesList.add(areaCode.toString());
        }

        info.setAreaCodes(areaCodesList);

        StationInfoPagedResult reslut = stationService.getStationServiceHttpSoap11Endpoint().queryStationWithPagination(info, pageNumber, limitNumber);

        Map<String, Object> hasMap = Maps.newLinkedHashMap();
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

        return hasMap;

    }

    @PostMapping("/instersingal")
    public String insterSingal(@RequestBody Map<String, Map<String, Object>> param) throws JsonProcessingException {

        final Map<String, Object> signal = param.get("sigal");

        final Map<String, Object> station = param.get("station");
        final FreqWarningQueryResponse response = (FreqWarningQueryResponse) service.freqWarnServiceCall("query",
                mapper.writeValueAsString(signal.get("warmingId")), FreqWarningQueryRequest.class);

        final FreqWarningDTO t = response.getWarningInfos().getFreqWarningDTO().size() > 0 ? response.getWarningInfos().getFreqWarningDTO().get(0)
                : new FreqWarningDTO();

        final BigInteger bandWidth = t.getBandWidth();
        final BigInteger centerFreq = t.getCenterFreq();

        List<Map<String, String>> ids = response.getWarningInfos().getFreqWarningDTO().get(0).getStatList().getFreqWarningStatDTO().stream().map(m -> {
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

        final RadioSignalOperationReponse res = (RadioSignalOperationReponse) service.radioSignalServiceCall("insertRadioSignal",
                mapper.writeValueAsString(station), RadioSignalDTO.class);

        Logger.info(mapper.writeValueAsString(res));
        return null;
    }

    @GetMapping(path = {"/StationInfo"
    })
    public Object stationList(@RequestParam Map<String, Object> map) throws JsonProcessingException {

        String index = (String) map.get("offset");
        String limit = (String) map.get("limit");
        String areaCode = (String) map.get("areaCode");

        List<String> areaCodeList = Lists.newLinkedList();
        areaCodeList.add(areaCode);

        Map<String, Object> requestParam = Maps.newLinkedHashMap();
        requestParam.put("index", index);
        requestParam.put("count", limit);

        Map<String, Object> list = Maps.newLinkedHashMap();
        list.put("string", areaCodeList);
        requestParam.put("areaCodeList", list);

        final RStatQuerySignalsResponse2 response = (RStatQuerySignalsResponse2) service.radioStationServiceCall("rStatQuerySignals",
                mapper.writeValueAsString(requestParam), RStatQuerySignalsRequest.class);

        List<Station> reslutDtos = response.getRStatSignalList().getRadioStationSignalDTO().stream().map(t -> {

            final RadioStationDTO radioStationDTO = t.getStation();

            final RadioFreqDTO radioFreqDTO = t.getFreq();

            int centerFre = radioFreqDTO.getCenterFreq().intValue() / 1000000;
            int tapeWidth = radioFreqDTO.getBandWidth().intValue() / 1000000;
            final String centerFreString = centerFre + "";
            final String tapeWidthString = tapeWidth + "";

            return new Station(radioStationDTO.getID(), radioStationDTO.getName(), centerFreString, tapeWidthString);
        }).collect(toList());

        Map<String, Object> reslut = Maps.newHashMap();

        reslut.put("total", response.getTotalNum());
        reslut.put("rows", reslutDtos);
        return reslut;
    }

    @GetMapping("/maxlevel")
    public Object getMaxlevel(@RequestParam String beginTime, @RequestParam long centorFreq, @RequestParam String stationCode) {
        try {

            LocalDate loacl = LocalDate.now();

            DateTimeFormatter yearformatter = DateTimeFormatter.ofPattern("yyyyMMdd");
            String first = loacl.format(yearformatter);

            LocalDateTime time = LocalDateTime.now();

            DateTimeFormatter timeformatter = DateTimeFormatter.ofPattern("HHmmss");
            String last = timeformatter.format(time);
            beginTime = first + last;

            String id = "52010118";
            long frequency = (long) (88.8 * 1000000);

            Map<Object, Object> reponceReslut = hbaseClient.queryMaxLevels(stationCode, frequency, upperBound, lowerBound, beginTime);

            LinkedList<Object> xAxis = Lists.newLinkedList();
            LinkedList<Object> series = Lists.newLinkedList();

            final double pow = Math.pow(10, 6);

            reponceReslut.forEach((k, v) -> {

                final double key = Double.parseDouble(k.toString()) / pow;

                xAxis.add(key);
                series.add(v);
            });

            HashMap<String, Object> restlutHashMap = Maps.newHashMap();

            restlutHashMap.put("xAxis", xAxis);
            restlutHashMap.put("series", series);

            return restlutHashMap;

        } catch (Exception e) {
            // TODO Auto-generated catch block
            Logger.error("峰值查询异常", e);
        }

        return Maps.newHashMap();
    }
}
