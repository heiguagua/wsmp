package com.chinawiserv.wsmp.pojo;

/**
 * Created by jh on 2017/8/11.
 */
public class CountResult {

    private String netTs = "0";

    private String orgSystemCode = "0";

    private String num = "0";

    private String status;

    public String getNetTs() {
        return netTs;
    }

    public void setNetTs(String netTs) {
        this.netTs = netTs;
    }

    public String getOrgSystemCode() {
        return orgSystemCode;
    }

    public void setOrgSystemCode(String orgSystemCode) {
        this.orgSystemCode = orgSystemCode;
    }

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
