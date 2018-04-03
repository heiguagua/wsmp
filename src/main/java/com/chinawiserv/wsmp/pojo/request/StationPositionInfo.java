package com.chinawiserv.wsmp.pojo.request;

/**
 * Created by yangh on 4/3/2018.
 */
public class StationPositionInfo {
    /**
     * 经度
     */
    private Double flat;
    /**
     * 纬度
     */
    private Double flon;

    private Long id;
    /**
     * 平均能量和
     */
    private double level;

    public Double getFlat() {
        return flat;
    }

    public void setFlat(Double flat) {
        this.flat = flat;
    }

    public Double getFlon() {
        return flon;
    }

    public void setFlon(Double flon) {
        this.flon = flon;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public double getLevel() {
        return level;
    }

    public void setLevel(double level) {
        this.level = level;
    }
}
