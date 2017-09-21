package com.chinawiserv.wsmp.cache;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.annotation.PostConstruct;
import javax.imageio.ImageIO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.EncodedResource;
import org.springframework.core.io.support.PropertiesLoaderUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.chinawiserv.apps.util.logger.Logger;
import com.chinawiserv.wsmp.pojo.AlarmDealed;
import com.chinawiserv.wsmp.pojo.AlarmUnDealed;
import com.chinawiserv.wsmp.pojo.BandStatusTable;
import com.chinawiserv.wsmp.pojo.RedioType;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

@Configuration
public class CacheConfig {

	public final static String MAP_DATA = "mapData";

	public final static String USR_DATA = "userData";

	public final static String DATA_LIST = "dataList";

	public final static String COLOR_LIST = "color";

	@Value("${config.location:classpath:}")
	private String configHome;

	@Autowired
	private ApplicationContext def;

	// 实时警告处理表头
	@Bean
	public AlarmDealed getAlarmDealed() throws IOException {

		final Resource resource = this.def.getResource(configHome.trim().concat("table_column/alarm_dealed.properties"));
		final EncodedResource encodedResource = new EncodedResource(resource, Charset.forName("utf-8"));
		final Properties p = PropertiesLoaderUtils.loadProperties(encodedResource);
		final String radio = p.getProperty("radio", "频率");
		final String firstTime = p.getProperty("first_appear_time", "首次出现时间");
		final String lastingTime = p.getProperty("lasting_time", "持续时间");
		final String radioType = p.getProperty("radio_type", "类型");
		final String station = p.getProperty("station", "监测站");
		final String radioStatus = p.getProperty("radio_status", "状态");
		final String mark = p.getProperty("mark", "备注");
		return new AlarmDealed(radio, firstTime, lastingTime, radioType, station, radioStatus, mark);
	}

	// 实时警告未处理表头
	@Bean
	public AlarmUnDealed getAlarmUnDealed() throws IOException {

		final Resource resource = this.def.getResource(configHome.trim().concat("table_column/alarm_undealed.properties"));
		EncodedResource encodedResource = new EncodedResource(resource, Charset.forName("utf-8"));
		final Properties p = PropertiesLoaderUtils.loadProperties(encodedResource);
		final String radio = p.getProperty("radio", "频率");
		final String firstTime = p.getProperty("first_appear_time", "首次出现时间");
		final String lastingTime = p.getProperty("lasting_time", "持续时间");
		final String radioType = p.getProperty("radio_type", "类型");
		final String station = p.getProperty("station", "监测站");
		final String radioStatus = p.getProperty("radio_status", "状态");
		final String mark = p.getProperty("mark", "备注");
		return new AlarmUnDealed(radio, firstTime, lastingTime, radioType, station, radioStatus, mark);
	}

	@Bean
	public BandStatusTable getBandStatusTable() throws IOException {

		// DefaultResourceLoader loader = new DefaultResourceLoader();
		final Resource resource = this.def.getResource(configHome.trim().concat("table_column/band_status.properties"));
		final EncodedResource encodedResource = new EncodedResource(resource, Charset.forName("utf-8"));
		final Properties p = PropertiesLoaderUtils.loadProperties(encodedResource);
		final String radioName = p.getProperty("radio_name", "频段名称");
		final String legalStation = p.getProperty("legal_station", "合法正常台站");
		final String illegalStation = p.getProperty("illegal_station", "合法违规台站");
		final String legalSignal = p.getProperty("legal_signal", "已知信号");
		final String illegalSignal = p.getProperty("illegal_signal", "不明信号");
		final String unknownSignal = p.getProperty("unknown_signal", "非法信号");
		return new BandStatusTable(radioName, legalStation, illegalStation, legalSignal, unknownSignal, illegalSignal);
	}

	@Bean
	public RedioType getRedioType() throws IOException {

		final Resource resource = this.def.getResource(configHome.trim().concat("checkbox/RedioType.properties"));
		final EncodedResource encodedResource = new EncodedResource(resource, Charset.forName("utf-8"));
		final Properties p = PropertiesLoaderUtils.loadProperties(encodedResource);
		final String legalNormalStation = p.getProperty("legalNormalStation", "合法台站正常");
		final String illegalNormalStation = p.getProperty("legalUnNormalStation", "合法台站违规");
		final String llegalStation = p.getProperty("legalStation", "合法台站");
		final String illegalSignal = p.getProperty("illegalSignal", "非法信号");
		final String unKonw = p.getProperty("unKonw", "不明信号");
		return new RedioType(legalNormalStation, illegalNormalStation, llegalStation, illegalSignal, unKonw);
	}

	@Bean(name = MAP_DATA)
	public List<Object> mapData() throws IOException {

		final Resource resource = this.def.getResource(configHome.trim().concat("boundary/5100.json"));
		final File file = resource.getFile();
		final Type type = new TypeReference<LinkedHashMap<String, Object>>() {}.getType();

		try (InputStream is = Files.newInputStream(file.toPath())) {

			final LinkedHashMap<String, Object> map = JSON.parseObject(is, type);

			Map<String, Object> boundary = (Map<String, Object>) map.get("5101");
			List<Object> regions = (List<Object>) boundary.get("boundary");

			return regions;
		}
	}

	@Bean(name = "kringGraid")
	public List<Map<String, Object>> graidData() throws IOException {

		final Resource resource = this.def.getResource(configHome.trim().concat("geoJson/5101.json"));
		final File file = resource.getFile();
		final Type type = new TypeReference<LinkedHashMap<String, Object>>() {}.getType();

		try (InputStream is = Files.newInputStream(file.toPath())) {

			final LinkedHashMap<String, Object> map = JSON.parseObject(is, type);
			List<Map<String, Object>> reslute = (List<Map<String, Object>>) map.get("point");
			return reslute;
		}
		// double start = System.currentTimeMillis();
		// final Resource resource =
		// this.def.getResource(configHome.concat("geoJson/5101.json"));
		// Path path =
		// Paths.get("G:/ideaSpace/wsmp/src/main/resources/boundary/5100.json");
		// final File file = path.toFile();
		// final Type type = new TypeReference<ConcurrentMap<String, Object>>()
		// {
		// }.getType();
		// List<List<BigDecimal>> regions;
		// try (InputStream is = Files.newInputStream(file.toPath())) {
		//
		// final Map<String, Object> map = JSON.parseObject(is, type);
		// Map<String, Object> boundary = (Map<String, Object>) map.get("5101");
		// regions = (List<List<BigDecimal>>) boundary.get("boundary");
		//
		//
		// }
		//
		// List<BigDecimal> xmax = regions.stream().max((t1, t2) ->
		// t1.get(0).doubleValue() > t2.get(0).doubleValue() ? 1 : -1).get();
		// List<BigDecimal> xmin = regions.stream().min((t1, t2) ->
		// t1.get(0).doubleValue() > t2.get(0).doubleValue() ? 1 : -1).get();
		// List<BigDecimal> ymax = regions.stream().max((t1, t2) ->
		// t1.get(1).doubleValue() > t2.get(1).doubleValue() ? 1 : -1).get();
		// List<BigDecimal> ymin = regions.stream().min((t1, t2) ->
		// t1.get(1).doubleValue() > t2.get(1).doubleValue() ? 1 : -1).get();
		// double xlimit = xmax.get(0).doubleValue();
		// System.out.println("xmax " + xmax.get(0).doubleValue());
		// System.out.println("xmin " + xmin.get(0).doubleValue());
		// System.out.println("ymax " + ymax.get(1).doubleValue());
		// System.out.println("ymin " + ymin.get(1).doubleValue());
		// final int row = regions.size();
		// final int columm = 2;
		// final double[][] transArry = new double[row][columm];
		//
		// for (int i = 0; i < row; i++) {
		// List<BigDecimal> element = regions.get(i);
		//
		// double x = element.get(0).doubleValue();
		// double y = element.get(1).doubleValue();
		//
		//// y = Math.log(Math.tan((90 + y) * Math.PI / 360)) / (Math.PI / 180);
		//// y = y * 20037508.34 / 180;
		// //* 20037508.34 / 180;
		//
		// transArry[i][0] = x;
		// transArry[i][1] = y;
		//
		// }
		//
		// int yMuner = 100;
		// double xStart = xmin.get(0).doubleValue();
		// double ymaxSart = ymax.get(1).doubleValue();
		// double yminSart = ymin.get(1).doubleValue();
		// double[][] point = transArry;
		// double[][] poly = transArry;
		// double yStep = (ymaxSart - yminSart) / yMuner;
		// double[][] stratPoint = new double[yMuner][2];
		// double currentY = ymaxSart;
		// for (int i = 0; i < yMuner; i++) {
		// stratPoint[i][0] = xStart;
		// stratPoint[i][1] = currentY;
		// currentY -= yStep;
		// }
		//
		// double xStep = yStep;
		// // LinkedBlockingDeque<Map<String,Double>> deque = new
		// LinkedBlockingDeque<>();
		// LinkedList<Map<String, Object>> deque = new LinkedList<>();
		// Stream.of(stratPoint).forEach((t) -> {
		// double x = t[0];
		// double y = t[1];
		// while (true) {
		// // System.out.println("x :"+x +" "+"y :"+y);
		// x = x + xStep;
		//
		// if (x > xlimit) {
		// break;
		// }
		//
		// if (isInSide(x, y, poly)) {
		// Map<String, Object> map = Maps.newHashMap();
		// map.put("x", x);
		// map.put("y", y);
		// deque.add(map);
		// }
		// }
		// });
		// double end = System.currentTimeMillis();
		// System.out.println("耗时 : " + (end - start) / 1000 + " " + "个数 :" +
		// deque.size());
		// // System.out.println(deque);
		// return deque;

		// System.out.println(JSON.toJSONString(deque));

	}

	public static Boolean isInSide(double pointX, double pointY, double[][] poly) {
		double px = pointX, py = pointY;
		boolean flag = false;

		for (int i = 0, l = poly.length, j = l - 1; i < l; j = i, i++) {
			double sx = poly[i][0], sy = poly[i][1], tx = poly[j][0], ty = poly[j][1];

			// 点与多边形顶点重合
			if ((sx == px && sy == py) || (tx == px && ty == py)) { return true; }

			// 判断线段两端点是否在射线两侧
			if ((sy < py && ty >= py) || (sy >= py && ty < py)) {
				// 线段上与射线 Y 坐标相同的点的 X 坐标
				double x = sx + (py - sy) * (tx - sx) / (ty - sy);

				// 点在多边形的边上
				if (x == px) { return true; }

				// 射线穿过多边形的边界
				if (x > px) {
					flag = !flag;
				}
			}
		}

		// 射线穿过多边形边界的次数为奇数时点在多边形内
		return flag;
	}

	@Bean(name = DATA_LIST)
	public Object stationList() {
		// final Resource resource =
		// this.def.getResource(configHome.concat("geoJson/station-list.json"));
		// final File file = resource.getFile();
		// final Type type = new TypeReference<List<Map<String, String>>>()
		// {}.getType();
		//
		// try (InputStream is = Files.newInputStream(file.toPath())) {
		//
		// final LinkedHashMap<String, Object> map = JSON.parseObject(is, type);
		// return map;
		// }

		HashMap<String, String> map = Maps.newHashMap();
		map.put("Num", "52010001");
		map.put("Name", "监测站");

		HashMap<String, String> map2 = Maps.newHashMap();
		map2.put("Num", "52040003");
		map2.put("Name", "监测站安顺");

		List<HashMap<String, String>> list = Lists.newLinkedList();
		list.add(map);
		list.add(map2);

		return list;
	}

	// @Bean(name = USR_DATA)
	public Object userData() throws IOException {

		final Resource resource = this.def.getResource("classpath:geoJson/user-info.json");
		final File file = resource.getFile();
		final Type type = new TypeReference<LinkedHashMap<String, Object>>() {}.getType();

		try (InputStream is = Files.newInputStream(file.toPath())) {

			final LinkedHashMap<String, Object> map = JSON.parseObject(is, type);
			return map;
		}
	}

	@Bean(name = COLOR_LIST)
	public  List<int[]> getColors() {
		//File file = new File("E:\\qzother\\wsmps\\src\\main\\java\\com\\chinawiserv\\wsmp\\a.png"
		BufferedImage bi = null;
		try {
			final Resource resource = this.def.getResource(configHome.trim().concat("image/a.png"));
			final File file = resource.getFile();
			bi = ImageIO.read(file);
		} catch (Exception e) {
			e.printStackTrace();
		}
		int width = bi.getWidth();
		int minx = bi.getMinX();
		List<int[]> colors =new ArrayList<int[]>();
		for (int i = minx; i < width; i++) {
			int[] rgb = new int[3];
			// 下面三行代码将一个数字转换为RGB数字
            int pixel =  bi.getRGB(i, 0);
            rgb[0] = (pixel & 0xff0000) >> 16;
            rgb[1] = (pixel & 0xff00) >> 8;
            rgb[2] = (pixel & 0xff);
            colors.add(rgb) ;
		}

		return colors;
	}

	@PostConstruct
	public void init() throws FileNotFoundException {

		this.configHome = this.configHome.trim();
		Logger.info("config home is : {} ", this.configHome);
	}
}
