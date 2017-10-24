package com.chinawiserv.wsmp.util;

public class Distance {
	private static final double EARTH_RADIUS = 6378.137;

	private static double rad(double d) {
		return d * Math.PI / 180.0;
	}

	public static double getDistance(double lat1, double lng1, double lat2, double lng2) {
		double radLat1 = rad(lat1);
		double radLat2 = rad(lat2);
		double a = radLat1 - radLat2;
		double b = rad(lng1) - rad(lng2);
		double s = 2 * Math.asin(Math.sqrt(
				Math.pow(Math.sin(a / 2), 2) + Math.cos(radLat1) * Math.cos(radLat2) * Math.pow(Math.sin(b / 2), 2)));
		s = s * EARTH_RADIUS;
		// s = Math.round(s * 10000) / 10000;
		return s;
	}

	public static double getDistance(double[] point1, double[] point2) {
		return getDistance(point1[0], point1[1], point2[0], point2[1]);
	}

	public static void main(String[] args) {
		System.out.println(getDistance(30.729, 104.079, 30.6584, 104.065));
	}
}
