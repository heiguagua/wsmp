package com.chinawiserv.wsmp.kriging.model;

public class ParamModel {
    private double Nugt;//���ֵ
    private double Sill;//��ֵ̨
    private double Lscl;//���
    private double Power;//ָ��
    private double hole;//��ѨЧӦ���
	public double getNugt() {
		return Nugt;
	}
	public void setNugt(double nugt) {
		Nugt = nugt;
	}
	public double getSill() {
		return Sill;
	}
	public void setSill(double sill) {
		Sill = sill;
	}
	public double getLscl() {
		return Lscl;
	}
	public void setLscl(double lscl) {
		Lscl = lscl;
	}
	public double getPower() {
		return Power;
	}
	public void setPower(double power) {
		Power = power;
	}
	public double getHole() {
		return hole;
	}
	public void setHole(double hole) {
		this.hole = hole;
	}
    
    
}
