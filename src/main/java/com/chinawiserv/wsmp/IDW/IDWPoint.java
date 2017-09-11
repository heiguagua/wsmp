package com.chinawiserv.wsmp.IDW;

public class IDWPoint {

	private double x;
	private double y;
	private double mercatorX;
	private double mercatorY;
	private double z;
	private double d;    //该点距目标点的距离
	private double w;    //权重
	public IDWPoint(){}
	public IDWPoint(double x,double y){
		this.setX(x);
		this.setY(y);
	}
	
	public IDWPoint(double x,double y,double z){
		this.setX(x);
		this.setY(y);
		this.z=z;
	}
	
	public double getX() {
		return x;
	}
	public void setX(double x) {
		this.x = x;
		this.mercatorX=(x* 20037508.34 / 180);
	}
	public double getY() {
		return y;
	}
	public void setY(double y) {
		this.y = y;
	//	 this.mercatorY=y;
		 double tempy = Math.log(Math.tan((90 + y) * Math.PI / 360)) / (Math.PI / 180);
		 this.mercatorY=(tempy * 20037508.34 / 180);
	}
	public double getZ() {
		return z;
	}
	public void setZ(double z) {
		this.z = z;
	}
	public double getD() {
		return d;
	}
	public void setD(double d) {
		this.d = d;
	}
	public double getW() {
		return w;
	}
	public void setW(double w) {
		this.w = w;
	}

	public double getMercatorX() {
		return mercatorX;
	}
	public void setMercatorX(double mercatorX) {
		this.mercatorX = mercatorX;
	}
	public double getMercatorY() {
		return mercatorY;
	}
	public void setMercatorY(double mercatorY) {
		this.mercatorY = mercatorY;
	}
	
	
	@Override
	public String toString() {
		return "IDWPoint [x=" + x + ", y=" + y + ", mercatorX=" + mercatorX + ", mercatorY=" + mercatorY + ", z=" + z
				+ ", d=" + d + ", w=" + w + "]\n";
	}
	
	
}
