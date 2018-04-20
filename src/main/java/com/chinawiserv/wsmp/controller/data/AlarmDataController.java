package com.chinawiserv.wsmp.controller.data;

import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toMap;

import java.math.BigInteger;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.function.BinaryOperator;
import java.util.stream.Collectors;

import com.chinawiserv.wsmp.pojo.request.EstimateRequest;
import com.chinawiserv.wsmp.pojo.request.StationPositionInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.tempuri.FreqWarningDTO;
import org.tempuri.FreqWarningOperationResponse;
import org.tempuri.FreqWarningQueryRequest;
import org.tempuri.FreqWarningQueryResponse;
import org.tempuri.RStatQuerySignalsRequest;
import org.tempuri.RStatQuerySignalsResponse2;
import org.tempuri.RadioFreqDTO;
import org.tempuri.RadioSignalDTO;
import org.tempuri.RadioSignalOperationReponse;
import org.tempuri.RadioStationDTO;
import org.tempuri.RadioStationSignalDTO;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.chinawiserv.apps.util.logger.Logger;
import com.chinawiserv.wsmp.IDW.IDWMain;
import com.chinawiserv.wsmp.IDW.IDWPoint;
import com.chinawiserv.wsmp.client.HttpServiceConfig;
import com.chinawiserv.wsmp.client.WebServiceSoapFactory;
import com.chinawiserv.wsmp.hbase.HbaseClient;
import com.chinawiserv.wsmp.hbase.query.OccAndMax;
import com.chinawiserv.wsmp.javatoc.LocateSignalCompute;
import com.chinawiserv.wsmp.javatoc.model.SignalResult;
import com.chinawiserv.wsmp.kriging.Interpolation;
import com.chinawiserv.wsmp.model.LevelLocate;
import com.chinawiserv.wsmp.pojo.IntensiveMonitoring;
import com.chinawiserv.wsmp.pojo.Station;
import com.chinawiserv.wsmp.util.Distance;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.sefon.ws.model.xsd.QueryFreqRangeInfo;
import com.sefon.ws.model.xsd.StationInfo;
import com.sefon.ws.model.xsd.StationInfoPagedResult;
import com.sefon.ws.model.xsd.StationQuerySpecInfo;
import com.sefon.ws.service.impl.StationService;

@RestControllerAdvice
@RequestMapping("/data/alarm")
public class AlarmDataController {

    @Autowired
    @Qualifier(value = "kringGraid")
    List<Map<String, Object>> kringGraid;

    @Autowired
    private WebServiceSoapFactory service;


    @Autowired
    private HbaseClient hbaseClient;

    @Autowired
    private StationService stationService;
    
    @Value("${kring.url}")
	private String kringUrl;

    private Interpolation kri = new Interpolation();
    //@Autowired
    // private com.chinawiserv.wsmp.levellocate.LevelLocate locate;

//    @Autowired
//    private Jk3d jk3d;

    @Value("${upperBound.value:5000000}")
    long upperBound;

    @Value("${upperBound.value:5000000}")
    long lowerBound;

    private ObjectMapper mapper = new ObjectMapper();

    @Value("${asio.formatter:yyyyMMddHHmmss}")
    DateTimeFormatter formatter;

    @Value("${kriking.value}")
    private int intKrikingValue;

    private DecimalFormat df = new DecimalFormat("0.00");

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

        try {

            if (beginTime.length() == 8) {
                beginTime = beginTime.concat("000000");
            }

            OccAndMax reslutResponce = hbaseClient.queryOccHour(stationCode, beginTime, centorFreq);
            //OccAndMax reslutResponce = hbaseClient.queryOccHour(stationCode, beginTime, centorFreq);
            Map<String, Object> Max = reslutResponce.getMax();
            Map<String, Object> Occ = reslutResponce.getOcc();

            Map<String, Object> temple = Maps.newLinkedHashMap();

            for (int beginHour = 0; beginHour < 24; beginHour++) {
                temple.put("" + beginHour, 0);
            }

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
                LinkedList<Double> series = Lists.newLinkedList();

                for (Map.Entry<String, Object> entry : temple.entrySet()) {
                    if (StringUtils.isEmpty(Occ.get(entry.getKey()))) {
                        Occ.put(entry.getKey(), -100);
                    }
                }

                Occ = Occ.entrySet().stream().sorted((c1, c2) -> Integer.parseInt(c1.getKey()) > Integer.parseInt(c2.getKey()) ? 1 : -1)
                        .collect(toMap(Map.Entry::getKey, Map.Entry::getValue, throwingMerger(), LinkedHashMap::new));

                Occ.forEach((k, v) -> {
                    xAxis.add(k);
                    Double value = Double.parseDouble(v.toString());

                    if (value==-100){
                        series.add(null);
                    }else{
                        series.add(value);
                    }
                });

                HashMap<String, Object> restlutdayOccHashMap = Maps.newHashMap();
                List<Double> zeroSeriesList  = Lists.newLinkedList();
                List<Double> noneZeroSeriesList  = Lists.newLinkedList();
                series.forEach((t)->{
//                    if (t==null||t.doubleValue()==0){
                    if (t==null){
                        zeroSeriesList.add(t);
                        noneZeroSeriesList.add(null);
                    }else{
                        zeroSeriesList.add(null);
                        noneZeroSeriesList.add(t);
                    }
                });

                restlutdayOccHashMap.put("zeroSeries", zeroSeriesList);
                restlutdayOccHashMap.put("noneZeroSeries", noneZeroSeriesList);

                restlutdayOccHashMap.put("xAxis", xAxis);

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

                for (int beginHour = 0; beginHour < 24; beginHour++) {
                    temple.put("" + beginHour, 0);
                }

                LinkedList<Object> xAxis = Lists.newLinkedList();
                LinkedList<Object> series = Lists.newLinkedList();

                for (Map.Entry<String, Object> entry : temple.entrySet()) {
                    if (StringUtils.isEmpty(Max.get(entry.getKey()))) {
                        Max.put(entry.getKey(), -100);
                    }
                }

                Max = Max.entrySet().stream().sorted((c1, c2) -> Integer.parseInt(c1.getKey()) > Integer.parseInt(c2.getKey()) ? 1 : -1
                ).collect(toMap(Map.Entry::getKey, Map.Entry::getValue, throwingMerger(), LinkedHashMap::new));

                Max.forEach((k, v) -> {
                    final double key = Integer.parseInt(k);
                    double value = Double.parseDouble(v.toString());
                    if (value==-100){
                        series.add(null);
                    }else{
                        series.add(v);
                    }
                    xAxis.add(key);
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

    @SuppressWarnings("unchecked")
    @PostMapping(path = "/estimate")
    public @ResponseBody
    Map<String, Object> estimate(@RequestBody EstimateRequest param) {

        JSONObject kriking3=null;
        String beginTime = param.getTime();
        List<StationPositionInfo> stationsList = param.getStations();
        try {
            String[] stations=new String[stationsList.size()];
            for(int i=0;i<stations.length;i++){
                stations[i]=stationsList.get(i).getId()+"";
            }
            if(param.getType()==null){
                param.setType("day");
            }
            Map<String, Object> reMap = hbaseClient.queryPower(param.getType(), beginTime, stations);



            //构造克里金二维数组参数
            double [][] kringParam= new double[stationsList.size()][3];
            for(int i = 0; i < stationsList.size(); i++){
                StationPositionInfo info=stationsList.get(i);
                String key=info.getId()+"";
                if(reMap.containsKey(key)){
                    kringParam[i][0] = info.getFlon();
                    kringParam[i][1] = info.getFlat();
                    if(reMap.get(key)!=null){
                        Float level=(Float)(reMap.get(key));
                        if(!Float.isNaN((level))){
                            DecimalFormat dFormat=new DecimalFormat("#.0");
                            String temp=dFormat.format(level);
                            info.setLevel(Double.valueOf(temp)+i);
                            kringParam[i][2] = info.getLevel();
                        }else{
                            info.setLevel(0);
                            kringParam[i][2] = 0;
                        }
                    }else{
                        info.setLevel(0);
                        kringParam[i][2] = 0;
                    }

                }
            }
//            String ss="[[30.628000259399414,104.00900268554688,3.0],[30.528000259399414,103.90900268554688,4.0],[30.729568481445312,103.97245788574219,0.0],[30.75,103.88999938964844,-2.0],[30.628000259399414,104.00900268554688,3.0]]";

            List<double[]> list = Arrays.asList(kringParam);
//            list=JSONObject.parseArray(ss,double[].class);
//            for(int i = 0; i < stationsList.size(); i++){
//                stationsList.get(i).setFlat(list.get(i)[1]);
//                stationsList.get(i).setFlon(list.get(i)[0]);
//                stationsList.get(i).setLevel(list.get(i)[2]);
//            }
            Logger.info("参数1{}",JSON.toJSONString(list));
            List<double[]> collect = list.stream().filter(ds -> ds[0] < 53.55 && ds[0] > 3.86 && ds[1] < 135.05 && ds[1] > 73.66 ).collect(toList());
            int size=collect.size();
            int radis=100;
            if(size==3){//只是用于测试环境  因为测试环境只有三个可用数据 所以要构建一个 弄成4个点
                double [] temp= new double[3];
                temp[0]=list.get(1)[0]+0.05;
                temp[1]=list.get(1)[1]+0.1;
                temp[2]=0;
                collect.add(temp);
                radis=75;
            }
            String string = HttpServiceConfig.httpclient(collect.toArray(new double[collect.size()][3]), kringUrl, false);
            kriking3 = JSONObject.parseObject(string);

            Logger.info("场强定位计算正常 操作时间{} 返回值为{}", LocalDateTime.now().toString(),kriking3);
        } catch (NumberFormatException e) {
            Logger.error("场强定位计算 ,操作异常 ：{}", e);
        }

        int coulm = stationsList.size();
        String electrCoverage = "0";

        if (coulm > 0) {
            Object object = kriking3.get("result");
            if(!"".equals(object.toString())){
                List<Integer[]> list = JSONObject.parseArray(object.toString(), Integer[].class);
                if(list!=null&&!list.isEmpty()) {
                    double numerator = list.stream().filter((e) -> e[2] >= intKrikingValue).count();
                    int denominator = list.size()+coulm;
                    electrCoverage = df.format(denominator > 0 ? numerator / denominator : 0);
                }
            }
        }

        List<Map<String, String>> stationPiont = stationsList.stream().map(station -> {
            HashMap<String, String> element = Maps.newHashMap();
            element.put("x", station.getFlat()+ "");
            element.put("y", station.getFlon()  + "");
            element.put("count", (station.getLevel()) + "");
            element.put("stationId", station.getId()+"");
            return element;
        }).collect(toList());

        Map<String, Object> mapPiont = new HashMap<>();
        mapPiont.put("stationPiont", stationPiont);
        mapPiont.put("kriking3", kriking3);
        mapPiont.put("electrCoverage", electrCoverage);

        return mapPiont;
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

//            long frequency = (long) (88.8 * 1000000);
            //测试或者正式环境使用
            //            Map<Object, Object> max = hbaseClient.queryMaxLevels(stationCode, centorFreq, upperBound, lowerBound, beginTime);
            //            Map<String, Object> occ = hbaseClient.queryOccDay(stationCode, beginTime, 90, centorFreq).getOcc();

            Map<Object, Object> max = hbaseClient.queryMaxLevels(stationCode, centorFreq, upperBound, lowerBound, LocalDateTime.now().format(formatter));
            Map<String, Object> occ = hbaseClient.queryOccDay(stationCode, LocalDateTime.now().format(formatter), 90, centorFreq).getOcc();

            if (occ.size() == 0) {

                HashMap<String, Object> restlutHashMap = Maps.newHashMap();

                String[] xAxis = new String[]{};
                double[] series = new double[]{};

                restlutHashMap.put("xAxis", xAxis);
                restlutHashMap.put("series", series);
                reslutMap.put("monthOcc", restlutHashMap);
                Logger.info("以三个月计算占用度从hbase中查询正常返回值为空 查询时间为{}，页面入参：监测站id{}，开始时间{},中心频率{}", LocalDateTime.now().toString(), stationCode, beginTime, centorFreq);
            } else {


                LinkedList<Integer> xAxis = Lists.newLinkedList();
                LinkedList<Double> series = Lists.newLinkedList();


                ZonedDateTime temple = LocalDateTime.now().atZone(ZoneId.systemDefault());
                LinkedHashMap<String, Object> reslute = Maps.newLinkedHashMap();
                occReslute.put("xAxis", xAxis);

                for (int i = 0; i < 90; i++) {
                    String date = temple.toString();
                    date = date.replaceAll(":", "").replaceAll("T", "").replaceAll("-", "").substring(0, 8);
                    reslute.put(date, -100);
                    temple = temple.plusDays(-1);
                }

                occ.forEach((k, v) -> {
                    reslute.replace(k, v);
                });

                occ = reslute;
                occ = occ.entrySet().stream().sorted((c1, c2) -> Integer.parseInt(c1.getKey()) > Integer.parseInt(c2.getKey()) ? 1 : -1)
                        .collect(toMap(Map.Entry::getKey, Map.Entry::getValue, throwingMerger(), LinkedHashMap::new));
                occ.forEach((k, v) -> {

                    int i = Integer.parseInt(k);
                    double value = Double.parseDouble(v.toString());
                    xAxis.add(i);

                    if (value==-100){
                        series.add(null);
                    }else{
                        series.add(value);
                    }
                });
                List<Double> zeroSeriesList  = Lists.newLinkedList();
                List<Double> noneZeroSeriesList  = Lists.newLinkedList();
                series.forEach((t)->{
                    if (t==null||t.doubleValue()==0){
                        zeroSeriesList.add(t);
                        noneZeroSeriesList.add(null);
                    }else{
                        zeroSeriesList.add(null);
                        noneZeroSeriesList.add(t);
                    }
                });
                occReslute.put("zeroSeries", zeroSeriesList);
                occReslute.put("noneZeroSeries", noneZeroSeriesList);
                reslutMap.put("monthOcc", occReslute);
                Logger.info("以三个月计算占用度从hbase中查询正常有返回值为{} ， 查询时间为{}，页面入参：监测站id{}，开始时间{},中心频率{}", occ, LocalDateTime.now().toString(), stationCode, beginTime, centorFreq);
            }

            if (max.size() == 0) {

                String[] xAxis = new String[]{};
                double[] series = new double[]{};

                resoluteHashMap.put("xAxis", xAxis);
                resoluteHashMap.put("series", series);

                reslutMap.put("max", resoluteHashMap);
                Logger.info("以三个月计算峰值度从hbase中查询正常返回值为空 查询时间为{}，页面入参：监测站id{}，开始时间{},中心频率{}", LocalDateTime.now().toString(), stationCode, beginTime, centorFreq);
                return reslutMap;
            } else {

                LinkedList<Double> xAxis = Lists.newLinkedList();
                LinkedList<Object> series = Lists.newLinkedList();

                final double pow = Math.pow(10, 6);

                max = max.entrySet().stream().sorted((c1, c2) -> Integer.parseInt(c1.getKey().toString()) > Integer.parseInt(c2.getKey().toString()) ? 1 : -1
                ).collect(toMap(Map.Entry::getKey, Map.Entry::getValue, throwingMerger(), LinkedHashMap::new));
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

    //        测试或正式环境使用
    @PostMapping(path = "/getStation")
    public @ResponseBody
    Map<String, Object> getStationPiont(@RequestBody Map<String, Object> param) {

//        long centerFreq = (long) (88.8 * 1000000);
//        String dateTime = "20170810235959";
//        Long frequency = Long.parseLong((String) param.get("frequency"));
//        List<LevelLocate> relate = hbaseClient.queryLevelLocate((String) param.get("beginTime"), frequency );
        List<LevelLocate> mapPoint = Collections.emptyList();
        List<Map<String, Object>> levelPoint = Lists.newLinkedList();
        Object kriking3=null;
        try {

            final long frequency = Long.valueOf(param.get("frequency").toString());

            List<LevelLocate> relate = hbaseClient.queryLevelLocate(LocalDateTime.now().format(formatter), frequency);
            Logger.info("均值查询正常返回个数为 :{}, 操作时间：{},入参：开始时间：{}，中心频率：{}", relate.size(), LocalDateTime.now().toString(), LocalDateTime.now().format(formatter), frequency);
            List<String> stationcode = (List<String>) param.get("stationCodes");

            mapPoint = relate.stream().filter(t -> stationcode.contains(t.getId())).collect(toList());

            Logger.info("地图上显示的点 信息为{}", JSON.toJSONString(mapPoint));

            int[] ids = mapPoint.stream().mapToInt(m -> Integer.valueOf(m.getId())).toArray();
            //构造克里金二维数组参数
            double [][] kringParam= new double[mapPoint.size()][3];
            for (int i = 0; i < ids.length; i++) {
				kringParam[i][0] = mapPoint.get(i).getFlat();
				kringParam[i][1] = mapPoint.get(i).getFlon();
				kringParam[i][2] = mapPoint.get(i).getLevel();
			}
            
            List<double[]> list =new ArrayList<double[]>( Arrays.asList(kringParam));
            Iterator<double[]> ite = list.iterator();
            while (ite.hasNext()) {
            	double[] ds = ite.next();
				if(ds[0]<53.55&&ds[0]>3.86&&ds[1]<135.05&&ds[1]>73.66){
				}else {
					ite.remove();
				}
			}
            
            
            String string = HttpServiceConfig.httpclient(list.toArray(new double[list.size()][3]), kringUrl,false);
            kriking3 = JSONObject.parseObject(string);
//            double[] flon = mapPoint.stream().mapToDouble(LevelLocate::getFlon).toArray();
//            double[] flat = mapPoint.stream().mapToDouble(LevelLocate::getFlat).toArray();
//            double[] level = mapPoint.stream().mapToDouble(LevelLocate::getLevel).toArray();
//            int[] waringsensorid = mapPoint.stream().mapToInt((LevelLocate t) -> Integer.parseInt(t.getId())
//            ).toArray();

            //少要八个点才能计算出来

//            LevelResult result = LevelCompute.levelCompute(ids,flon,flat,level, ids.length,10, waringsensorid);
//            int size = result.getOangeR().size();
//            for (int index = 0;index < size;index++){
//
//                Map<String, Object> mapLocate = Maps.newHashMap();
//
//                mapLocate.put("x", result.getOutLon().get(index));
//                mapLocate.put("y", result.getOutLat().get(index));
//                mapLocate.put("radius",  result.getOangeR().get(index));
//                levelPoint.add(mapLocate);
//            }
            Logger.info("场强定位计算正常 操作时间{} 返回值为{}", LocalDateTime.now().toString(), JSON.toJSONString(levelPoint));
        } catch (NumberFormatException e) {
            Logger.error("场强定位计算 ,操作时间：{},入参：开始时间：{}，中心频率：{} 异常 ：{}", LocalDateTime.now(), param.get("beginTime"), param.get("frequency"), e);
        }

        int coulm = mapPoint.size();
//        Random random = new Random();
       // List<DataInfo> dataOuts = Lists.newLinkedList();
        //List<DataInfo> temple = Lists.newLinkedList()
        List<IDWPoint> inData = Lists.newLinkedList();
     
//        p[2][0] = xMin + (xMax - xMin) / 2 ;
//        p[2][1] = yMin + (yMax - yMin) / 2 ;
//        p[2][2] = 17;


//        inPutData.add(new DataInfo(106.779815,27.230648,20));
//        inPutData.add(new DataInfo(106.606183,26.840808,10));
//        inPutData.add(new DataInfo(106.688752,26.335002,25));
//        inPutData.add(new DataInfo(106.220286,26.817141,2));

        List<IDWPoint> outData = Lists.newLinkedList();
        double[][] t1 = new double[kringGraid.size()][3];
        int beginIndex = 0;
        for (Map<String, Object> dataOut : kringGraid) {
            double x = Double.parseDouble(dataOut.get("x").toString());
            double y = Double.parseDouble(dataOut.get("y").toString());
            t1[beginIndex][0] = x;
            t1[beginIndex][1] = y;

            outData.add(new IDWPoint(x, y));

            //t1[beginIndex][2] = 4.5;
            beginIndex++;
//            dataOuts.add(new DataInfo(x,y,0));
//            temple.add(new DataInfo(x,y,5));
        }

//        temple.addAll(dataOuts);
//
//        temple.add(new DataInfo(106.779815, 27.230648, 10));

        //inPutData.add(new DataInfo(106.779815,27.230648,10));


        List<Map<String, Object>> kriking2 = Lists.newLinkedList();
        List<Map<String, Object>> kriking = Lists.newLinkedList();

        Map<String, Object> spatialReference = Maps.newHashMap();
        spatialReference.put("wkid", 4326);
//        Random random = new Random();
//        for (int index = 0; index < size; index++) {
//            Map<String, Object> element = Maps.newHashMap();
//            Map<String, Object> count = Maps.newHashMap();
//            Map<String, Object> geometry = Maps.newLinkedHashMap();
//            double val = t[index][2];
//            double x = t[index][0];
//            double y = t[index][1];
//            geometry.put("spatialReference", spatialReference);
//            geometry.put("type", "point");
//            //geometry.put("x", x);
//            geometry.put("x", x * 20037508.34 / 180);
//            y = Math.log(Math.tan((90 + y) * Math.PI / 360)) / (Math.PI / 180);
//            y = y * 20037508.34 / 180;
//            geometry.put("y", y);
//
//            if (val>0){
//                val =  random.nextInt(200);
//            }
//
//            count.put("count",val );
//            element.put("attributes", count);
//            element.put("geometry", geometry);
//            kriking.add(element);
//        }

//        for (int index = 0; index < size; index++) {
//            Map<String, Object> element = Maps.newHashMap();
//            Map<String, Object> count = Maps.newHashMap();
//            Map<String, Object> geometry = Maps.newLinkedHashMap();
//            double val = t[index][2];
//            double x = t[index][0];
//            double y = t[index][1];
//            geometry.put("spatialReference", spatialReference);
//            geometry.put("type", "point");
//            geometry.put("x", x );
//            geometry.put("y", y);
//            count.put("count", val);
//            element.put("attributes", count);
//            element.put("geometry", geometry);
//            kriking.add(element);
//        }

        String electrCoverage = "0";

        if (coulm > 0) {

//            Arrays.stream(p).forEach(e -> System.out.println(Arrays.toString(e)));
//            t2 = jk3d.typeOk(p,t1);
//            kri.InitCal(inPutData, dataOuts);
//            kri.OkrigingCal();
//            dataOuts = kri.CopyResults();
        	IDWMain idw = new IDWMain();
            idw.getRes(inData, outData);
            double numerator = outData.stream().filter((e) -> e.getZ() > intKrikingValue).count();
            int denominator = kringGraid.size()+coulm;
            electrCoverage = df.format(denominator > 0 ? numerator / denominator : 0);
            for (IDWPoint info : outData) {
                Map<String, Object> element = Maps.newHashMap();
                Map<String, Object> count = Maps.newHashMap();
                Map<String, Object> geometry = Maps.newLinkedHashMap();
                double val = info.getZ();
                double x = info.getX();
                double y = info.getY();
                geometry.put("spatialReference", spatialReference);
                geometry.put("type", "point");
                geometry.put("x", x * 20037508.34 / 180);
                //geometry.put("x", x );
                y = Math.log(Math.tan((90 + y) * Math.PI / 360)) / (Math.PI / 180);
                y = y * 20037508.34 / 180;
                geometry.put("y", y);
                count.put("count", val);
                element.put("attributes", count);
                element.put("geometry", geometry);
                kriking.add(element);
            }
        }


        //System.out.println(kriking);

        //测试或正式环境使用
        //List<String> stationcode = (List<String>) param.get("stationcode");


        List<Map<String, String>> stationPiont = mapPoint.stream().map(station -> {
            HashMap<String, String> element = Maps.newHashMap();
            element.put("x", station.getFlon() + "");
            element.put("y", station.getFlat() + "");
            element.put("count", (station.getLevel()) + "");
            element.put("stationId", station.getId());
            return element;
        }).collect(toList());

//        HashMap<String,String> tempMap2 = Maps.newHashMap();
//        tempMap2.put("x",106.779815+"");
//        tempMap2.put("y",27.230648+"" );
//        tempMap2.put("count",20+"");
//        tempMap2.put("stationId", "44");
//
//        HashMap<String,String> tempMap1 = Maps.newHashMap();
//        tempMap1.put("x",106.606183+"");
//        tempMap1.put("y",26.840808+"" );
//        tempMap1.put("count",10+"");
//        tempMap1.put("stationId", "44");
//
//        HashMap<String,String> tempMap3 = Maps.newHashMap();
//        tempMap3.put("x",106.688752+"");
//        tempMap3.put("y",26.335002+"" );
//        tempMap3.put("count",25+"");
//        tempMap3.put("stationId", "44");
//
//        HashMap<String,String> tempMap4 = Maps.newHashMap();
//        tempMap4.put("x",106.220286+"");
//        tempMap4.put("y",26.817141+"" );
//        tempMap4.put("count",2+"");
//        tempMap4.put("stationId", "44");
//
//        stationPiont.add(tempMap1);
//        stationPiont.add(tempMap2);
//        stationPiont.add(tempMap3);
//        stationPiont.add(tempMap4);

        Map<String, Object> mapPiont = new HashMap<>();

        mapPiont.put("stationPiont", stationPiont);
        mapPiont.put("levelPoint", levelPoint);
        mapPiont.put("kriking", kriking);
        mapPiont.put("kriking2", kriking2);
        mapPiont.put("kriking3", kriking3);
        mapPiont.put("electrCoverage", electrCoverage);

        // map.put("x", "106.709177096");
        // map.put("y", "26.6299067414");
        // map.put("count", "45");
        // map.put("stationId", "oopsoo");
        return mapPiont;
    }
    //场强定位
    @PostMapping(path = "/getFieldStrengthPosition")
    public @ResponseBody
    SignalResult getFieldStrengthPosition(@RequestBody Map<String, Object> param) {
    	Logger.info("参数param{} ", param);
    	List<Map> list=(List<Map>)param.get("stationPiont");
//    	element.put("x", station.getFlon() + "");
//		element.put("y", station.getFlat() + "");
//		element.put("count", (station.getLevel()) + "");
//		element.put("stationId", station.getId());
//    	List<Map<String, Object>> levelPoint = Lists.newLinkedList();
    	double[] flon = list.stream().mapToDouble(map->Double.parseDouble(map.get("x").toString())).toArray();
        double[] flat = list.stream().mapToDouble(map->Double.parseDouble(map.get("y").toString())).toArray();
        double[] level = list.stream().mapToDouble(map->Double.parseDouble(map.get("count").toString())).toArray();
        int[] ids = list.stream().mapToInt(map -> Integer.parseInt(map.get("stationId").toString())).toArray();

        //少要八个点才能计算出来

//        LevelResult result = LevelCompute.levelCompute(ids,flon,flat,level, ids.length,Double.valueOf(threshold), ids);
        SignalResult result  = LocateSignalCompute.locateSignalCompute(ids.length,flon,flat,level);
//        int size = result.getOangeR().size();
//        for (int index = 0;index < size;index++){
//
//            Map<String, Object> mapLocate = Maps.newHashMap();
//
//            mapLocate.put("x", result.getOutLon().get(index));
//            mapLocate.put("y", result.getOutLat().get(index));
//            mapLocate.put("radius",  result.getOangeR().get(index));
//            levelPoint.add(mapLocate);
//        }
        Logger.info("场强定位计算正常 操作时间{} 返回值为{}", LocalDateTime.now().toString(), result.getLon(),result.getLat(),result.getRangeR());
    	return result;
    }
    @SuppressWarnings("unchecked")
	@PostMapping(path = "/getStationVersion2")
    public @ResponseBody
    Map<String, Object> getStationVersion2(@RequestBody Map<String, Object> param) {
    	List<LevelLocate> mapPoint = Collections.emptyList();
    	JSONObject kriking3=null;
    	JSONObject kriking3new = new JSONObject();

    	try {
    		
    		final long frequency = Long.valueOf(param.get("frequency").toString());
    		
    		//查询电频均值
    		List<LevelLocate> relate = Collections.emptyList();
    		for(int i=0;relate.size() == 0&&i<180;i++) {
    			relate = hbaseClient.queryLevelLocate(LocalDateTime.of(LocalDate.now().plusDays(0-i), LocalTime.now()).format(formatter), frequency);//LocalDateTime.of(LocalDate.of(2017, 10, 23), LocalTime.now()).format(formatter)	
    		}
    		 
    		Logger.info("均值查询正常返回个数为 :{}, 操作时间：{},入参：开始时间：{}，中心频率：{}", relate.size(), LocalDateTime.now().toString(), LocalDateTime.now().format(formatter), frequency);
    		List<String> stationcode = (List<String>) param.get("stationCodes");
    		
    		mapPoint = relate.stream().filter(t -> stationcode.contains(t.getId())).collect(toList());

//            LevelLocate ee=new LevelLocate();
//            ee.setFlat(103.96566009521484);
//            ee.setFlon(30.746593475341797);
//            ee.setLevel((byte)41);
//            ee.setId("51010024");
//            mapPoint.add(ee);
            //由于克里金算法那边 必须传入4个以上的数据进去 得出来的结果才是正确的
            //如果是三个点的那么第三个值就是一个定值  显示数据就会不对
            //所以在不足四个的时候我们给他补足到4个 然后返回的时候 再将补充添加的数据去掉即在地图上不显示
            Boolean isAdut=false;
            if(mapPoint.size()<4){
                int size=mapPoint.size();
                for(int i=0;i<4-size;i++){
                    LevelLocate ss=new LevelLocate();
                    ss.setFlat(mapPoint.get(0).getFlat()+i*0.01);
                    ss.setFlon(mapPoint.get(0).getFlon()+i*0.01);
                    ss.setLevel((byte)(mapPoint.get(0).getLevel()+i));
                    ss.setId("0");
                    mapPoint.add(ss);
                }
                isAdut=true;
            }
    		Logger.info("地图上显示的点 信息为{}", JSON.toJSONString(mapPoint));

            int[] ids = mapPoint.stream().mapToInt(m -> Integer.valueOf(m.getId())).toArray();
    		//构造克里金二维数组参数
    		double [][] kringParam= new double[mapPoint.size()][3];
    		for (int i = 0; i < ids.length; i++) {

    			kringParam[i][0] = mapPoint.get(i).getFlat();
                kringParam[i][1] = mapPoint.get(i).getFlon();
    			kringParam[i][2] = mapPoint.get(i).getLevel();
    		}
    		 List<double[]> list = Arrays.asList(kringParam);
    		 Logger.info("参数1{}",JSON.toJSONString(list));
    		 List<double[]> collect = list.stream().filter(ds -> ds[0]<53.55&&ds[0]>3.86&&ds[1]<135.05&&ds[1]>73.66).collect(toList());
//    		List<double[]> list =new ArrayList<double[]>( Arrays.asList(kringParam));
//    		Iterator<double[]> ite = list.iterator();
//    		while (ite.hasNext()) {
//    			double[] ds = ite.next();
//    			if(ds[0]<53.55&&ds[0]>3.86&&ds[1]<135.05&&ds[1]>73.66){
//    			}else {
//    				ite.remove();
//    			}
//    		}
    		Logger.info("参数2{}",JSON.toJSONString(collect));
    		String string = HttpServiceConfig.httpclient(collect.toArray(new double[collect.size()][3]), kringUrl,isAdut);
    		kriking3 = JSONObject.parseObject(string);
    		//过滤kriking点
    		Object object = kriking3.get("result");
        	List<double[]> krikinglist = JSONObject.parseArray(object.toString(), double[].class);
//        	//过滤距离
//			Integer r = 30;
//			if(krikinglist != null) {
//			List<double[]> krikinglistFiletered = krikinglist.stream().filter(e -> {
//                for (int i = 0; i < kringParam.length; i++) {
//                    if (Distance.getDistance(e, kringParam[i]) < r)
//                        return true;
//                }
//                return true;
//            }).collect(Collectors.toList());
			kriking3new.put("result", krikinglist);
//			}
    		
    		Logger.info("场强定位计算正常 操作时间{} 返回值为{}", LocalDateTime.now().toString(),kriking3);
    	} catch (NumberFormatException e) {
    		Logger.error("场强定位计算 ,操作时间：{},入参：开始时间：{}，中心频率：{} 异常 ：{}", LocalDateTime.now(), param.get("beginTime"), param.get("frequency"), e);
    	}
    	
    	int coulm = mapPoint.size();
    	String electrCoverage = "0";
    	
    	if (coulm > 0) {
    		Object object = kriking3.get("result");
    		List<Integer[]> list = JSONObject.parseArray(object.toString(), Integer[].class);
    		if(list!=null&&!list.isEmpty()) {
	    		double numerator = list.stream().filter((e) -> e[2] >= intKrikingValue).count();
	    		int denominator = list.size()+coulm;
	    		electrCoverage = df.format(denominator > 0 ? numerator / denominator : 0);
	    		
    		}
    	}
    	
    	List<Map<String, String>> stationPiont = mapPoint.stream().map(station -> {
            HashMap<String, String> element = Maps.newHashMap();
            element.put("x", station.getFlon() + "");
            element.put("y", station.getFlat() + "");
            element.put("count", station.getLevel() + "");
            element.put("stationId", station.getId());
            return element;
        }).filter(e -> !e.get("stationId").equals("0")).collect(toList());

        Map<String, Object> mapPiont = new HashMap<>();
    	mapPiont.put("stationPiont", stationPiont);
    	mapPiont.put("kriking3", kriking3new);
    	mapPiont.put("electrCoverage", electrCoverage);
    	
    	return mapPiont;
    }


    @GetMapping(path = "/stationsf")
    public Object getStationBySF(@RequestParam Map<String, Object> param) {
        String sortName = (String) param.get("sort");
        String order = (String) param.get("order");
        final Object offset = param.get("offset");
        final Object limit = param.get("limit");
//        final String areaCode = (String) param.get("areaCode");
//        String[] areaCodes = areaCode.split(",");
        StationQuerySpecInfo info = new StationQuerySpecInfo();
        List<QueryFreqRangeInfo> rangeInfos = Lists.newLinkedList();
        QueryFreqRangeInfo rangeInfo = new QueryFreqRangeInfo();

        double centorFreqDouble = Double.parseDouble((String) param.get("centorFreq")) / 1000000;
        double upFreqDouble = centorFreqDouble + 5;
        double downFreqDouble = centorFreqDouble - 5;

        rangeInfo.setBeginFreq(downFreqDouble);
        rangeInfo.setEndFreq(upFreqDouble);
        rangeInfos.add(rangeInfo);

        int pageNumber = Integer.parseInt(offset.toString());
        int limitNumber = Integer.parseInt(limit.toString());

//        ArrayList<String> dr = new ArrayList<>(Arrays.asList(areaCodes));
        info.setFreqRanges(rangeInfos);
        //info.setAreaCodes(dr);
        //info.setSignalFreq(Double.parseDouble((String) param.get("centorFreq")));

        Map<String, Object> hasMap = Maps.newLinkedHashMap();

        try {
             long start = LocalDateTime.now().toInstant(ZoneOffset.UTC).toEpochMilli();
            StationInfoPagedResult reslut = stationService.getStationServiceHttpSoap11Endpoint().queryStationWithPagination(info, pageNumber, limitNumber);
            long end = LocalDateTime.now().toInstant(ZoneOffset.UTC).toEpochMilli();
            double totalTime = (end-start)/1000;
            int totlal = reslut.getPageInfo().getTotalPages();
            List<Station> stations = Collections.emptyList();
            if (sortName == null) {
                stations = reslut.getStations().stream().map(s -> {
                    String id = s.getStationID();
                    String stationName = s.getSTATName()== null ? "-" :s.getSTATName();
                    String centerFreqStr = s.getFREQEFB() == null ? "-" : s.getFREQEFB().toString();
                    String bandWidth = s.getNETBand() == null ? "-" : s.getNETBand().toString();
                    return new Station(id, stationName, centerFreqStr, bandWidth);

                }).collect(toList());
            } else if ("centerFrequency".equals(sortName) && "desc".equals(order)) {
                stations = reslut.getStations().stream().sorted((StationInfo c1, StationInfo c2) -> c1.getFREQEFB() < c2.getFREQEFB() ? 1 : -1).map(s -> {

                    String id = s.getStationID();
                    String stationName = s.getSTATName()== null ? "-" :s.getSTATName();
                    String centerFreqStr = s.getFREQEFB() == null ? "-" : s.getFREQEFB().toString();
                    String bandWidth = s.getNETBand() == null ? "-" : s.getNETBand().toString();
                    return new Station(id, stationName, centerFreqStr, bandWidth);

                }).collect(toList());
            } else if ("centerFrequency".equals(sortName) && "asc".equals(order)) {
                stations = reslut.getStations().stream().sorted((StationInfo c1, StationInfo c2) -> c1.getFREQEFB() > c2.getFREQEFB() ? 1 : -1).map(s -> {

                    String id = s.getStationID();
                    String stationName = s.getSTATName()== null ? "-" :s.getSTATName();
                    String centerFreqStr = s.getFREQEFB() == null ? "-" : s.getFREQEFB().toString();
                    String bandWidth = s.getNETBand() == null ? "-" : s.getNETBand().toString();
                    return new Station(id, stationName, centerFreqStr, bandWidth);

                }).collect(toList());


            } else if ("tapeWidth".equals(sortName) && "asc".equals(order)) {
                stations = reslut.getStations().stream().sorted((StationInfo c1, StationInfo c2) -> c1.getNETBand() > c2.getNETBand() ? 1 : -1).map(s -> {

                    String id = s.getStationID();
                    String stationName = s.getSTATName()== null ? "-" :s.getSTATName();
                    String centerFreqStr = s.getFREQEFB() == null ? "-" : s.getFREQEFB().toString();
                    String bandWidth = s.getNETBand() == null ? "-" : s.getNETBand().toString();
                    return new Station(id, stationName, centerFreqStr, bandWidth);

                }).collect(toList());
            } else if ("tapeWidth".equals(sortName) && "desc".equals(order)) {
                stations = reslut.getStations().stream().sorted((StationInfo c1, StationInfo c2) -> c1.getNETBand() < c2.getNETBand() ? 1 : -1).map(s -> {

                    String id = s.getStationID();
                    String stationName = s.getSTATName()== null ? "-" :s.getSTATName();
                    String centerFreqStr = s.getFREQEFB() == null ? "-" : s.getFREQEFB().toString();
                    String bandWidth = s.getNETBand() == null ? "-" : s.getNETBand().toString();
                    return new Station(id, stationName, centerFreqStr, bandWidth);

                }).collect(toList());
            }

            hasMap.put("total", totlal);
            hasMap.put("rows", stations);

            Logger.info("四方台站webservice  StationService调用正常，操作时间{} ,入参 ：查询条件{} 当前个数{} 限制个数{} 查询时间{}", LocalDateTime.now().toString(), info, pageNumber, limitNumber,totalTime);
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
            station.put("isManualInsert", true);
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

            res = (RadioSignalOperationReponse) service.radioSignalServiceCall("insertRadioSignal",
                    mapper.writeValueAsString(station), RadioSignalDTO.class);
            Map<String, Object> waringID = (Map<String, Object>) signal.get("warmingId");
            //service.getFreqWarnService().updateStatus((String) waringID.get("id"), 1);
            service.getFreqWarnService().updateSelected((String) waringID.get("id"), 1, null, (String) station.get("des"), (String) station.get("stationKey"));

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
            String sortName = (String) map.get("sort");
            String order = (String) map.get("order");
            String index = (String) map.get("offset");
            String limit = (String) map.get("limit");
//            String areaCode = (String) map.get("areaCode");
//            String[] areaList = areaCode.split(",");
//            List<String> areaCodeList = new ArrayList<>(Arrays.asList(areaList));

            int centorFreqInt = Integer.parseInt((String) map.get("centorFreq"));

            int upFreqInt = centorFreqInt + 5000000;
            int downFreqInt = centorFreqInt - 5000000;

            Map<String, Object> requestParam = Maps.newLinkedHashMap();
            requestParam.put("index", index);
            requestParam.put("count", 10);
            List<Station> reslutDtos = Collections.emptyList();
//            list.put("string", areaCodeList);
            //requestParam.put("areaCodeList", list);
            requestParam.put("beginFreq", downFreqInt);
            requestParam.put("endFreq", upFreqInt);

            final RStatQuerySignalsResponse2 response = (RStatQuerySignalsResponse2) service.radioStationServiceCall("rStatQuerySignals",
                    mapper.writeValueAsString(requestParam), RStatQuerySignalsRequest.class);
            if (sortName == null) {
            	  reslutDtos = response.getRStatSignalList().getRadioStationSignalDTO().stream().map((RadioStationSignalDTO t) -> {

                      final RadioStationDTO radioStationDTO = t.getStation();

                      final RadioFreqDTO radioFreqDTO = t.getFreq();

                      double centerFre = radioFreqDTO.getCenterFreq().doubleValue() / 1000000;
                      double tapeWidth = radioFreqDTO.getBandWidth().doubleValue() / 1000000;
                      final String centerFreString = centerFre + "";
                      final String tapeWidthString = tapeWidth + "";

                      return new Station(radioStationDTO.getID(), radioStationDTO.getName(), centerFreString, tapeWidthString);
                  }).collect(toList());

            }else if ("centerFrequency".equals(sortName) && "desc".equals(order)) {
                reslutDtos = response.getRStatSignalList().getRadioStationSignalDTO().stream().sorted((c1, c2) -> c1.getFreq().getCenterFreq().intValue() < c2.getFreq().getCenterFreq().intValue() ? 1 : -1).map((RadioStationSignalDTO t) -> {

                    final RadioStationDTO radioStationDTO = t.getStation();

                    final RadioFreqDTO radioFreqDTO = t.getFreq();

                    double centerFre = radioFreqDTO.getCenterFreq().doubleValue() / 1000000;
                    double tapeWidth = radioFreqDTO.getBandWidth().doubleValue() / 1000000;
                    final String centerFreString = centerFre + "";
                    final String tapeWidthString = tapeWidth + "";

                    return new Station(radioStationDTO.getID(), radioStationDTO.getName(), centerFreString, tapeWidthString);
                }).collect(toList());

            } else if ("centerFrequency".equals(sortName) && "asc".equals(order)) {
                reslutDtos = response.getRStatSignalList().getRadioStationSignalDTO().stream().sorted((c1, c2) -> c1.getFreq().getCenterFreq().intValue() > c2.getFreq().getCenterFreq().intValue() ? 1 : -1).map((RadioStationSignalDTO t) -> {

                    final RadioStationDTO radioStationDTO = t.getStation();

                    final RadioFreqDTO radioFreqDTO = t.getFreq();

                    double centerFre = radioFreqDTO.getCenterFreq().doubleValue() / 1000000;
                    double tapeWidth = radioFreqDTO.getBandWidth().doubleValue() / 1000000;
                    final String centerFreString = centerFre + "";
                    final String tapeWidthString = tapeWidth + "";

                    return new Station(radioStationDTO.getID(), radioStationDTO.getName(), centerFreString, tapeWidthString);
                }).collect(toList());

            } else if ("tapeWidth".equals(sortName) && "desc".equals(order)) {
                reslutDtos = response.getRStatSignalList().getRadioStationSignalDTO().stream().sorted((c1, c2) -> c1.getFreq().getBandWidth().intValue() < c2.getFreq().getBandWidth().intValue() ? 1 : -1).map((RadioStationSignalDTO t) -> {

                    final RadioStationDTO radioStationDTO = t.getStation();

                    final RadioFreqDTO radioFreqDTO = t.getFreq();

                    double centerFre = radioFreqDTO.getCenterFreq().doubleValue() / 1000000;
                    double tapeWidth = radioFreqDTO.getBandWidth().doubleValue() / 1000000;
                    final String centerFreString = centerFre + "";
                    final String tapeWidthString = tapeWidth + "";

                    return new Station(radioStationDTO.getID(), radioStationDTO.getName(), centerFreString, tapeWidthString);
                }).collect(toList());

            } else if ("tapeWidth".equals(sortName) && "asc".equals(order)) {
                reslutDtos = response.getRStatSignalList().getRadioStationSignalDTO().stream().sorted((c1, c2) -> c1.getFreq().getBandWidth().intValue() > c2.getFreq().getBandWidth().intValue() ? 1 : -1).map((RadioStationSignalDTO t) -> {

                    final RadioStationDTO radioStationDTO = t.getStation();

                    final RadioFreqDTO radioFreqDTO = t.getFreq();

                    double centerFre = radioFreqDTO.getCenterFreq().doubleValue() / 1000000;
                    double tapeWidth = radioFreqDTO.getBandWidth().doubleValue() / 1000000;
                    final String centerFreString = centerFre + "";
                    final String tapeWidthString = tapeWidth + "";

                    return new Station(radioStationDTO.getID(), radioStationDTO.getName(), centerFreString, tapeWidthString);
                }).collect(toList());

            }


            reslut = Maps.newHashMap();

            reslut.put("total", response.getTotalNum());
            reslut.put("rows", reslutDtos);
            Logger.info("博创台情webservice接口调用成功 操作时间{} 返回值", LocalDateTime.now().toString(), JSON.toJSONString(reslutDtos));
        } catch (JsonProcessingException e) {
            Logger.error("博创台情webservice接口调用异常 操作时间{} 入参{} 异常:{}", LocalDateTime.now().toString(), map, e);
        }
        return reslut;
    }

    private static <T> BinaryOperator<T> throwingMerger() {
        return (u, v) -> {
            throw new IllegalStateException(String.format("Duplicate key %s", u));
        };
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
//    @RequestMapping(path = {"/getAlarmingType"})
//    public int getAlarmTypeCode(@RequestParam Map<String,String> request) throws JsonProcessingException {
//        RadioSignalFromWarningIDQueryResponse response = (RadioSignalFromWarningIDQueryResponse) service.radioSignalServiceCall("querySignalFromWarningID",JSON.toJSONString(request),String.class);
//        RadioSignalDTO radioSignalDTO = response.getRadioSignalInfo().getRadioSignalDTO().stream().findFirst().orElseGet(()->{
//            RadioSignalDTO r =   new RadioSignalDTO();
//            r.setTypeCode(-1);
//            return  r;
//        });
//        radioSignalDTO.getAbnormalHistory().getRadioSignalAbnormalHistoryDTO().stream().findFirst();
//        return radioSignalDTO.getTypeCode();
//    }

}
