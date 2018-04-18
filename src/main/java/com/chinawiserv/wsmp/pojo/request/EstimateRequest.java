package com.chinawiserv.wsmp.pojo.request;

import java.util.List;

/**
 * Created by yangh on  4/3/2018.
 */
public class EstimateRequest {

    private String type;

    private String time;

    private List<StationPositionInfo> stations;

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public List<StationPositionInfo> getStations() {
        return stations;
    }

    public void setStations(List<StationPositionInfo> stations) {
        this.stations = stations;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
