package com.adasplus.proadas.common.data;

/**
 * Created by zhangyapeng on 18-5-3.
 */

public class SdcardInfo {

    /**
     * ret : -1
     * total : null
     * used : null
     * available : null
     */

    private String ret;
    private String total;
    private String used;
    private String available;

    public String getRet() {
        return ret;
    }

    public void setRet(String ret) {
        this.ret = ret;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public String getUsed() {
        return used;
    }

    public void setUsed(String used) {
        this.used = used;
    }

    public String getAvailable() {
        return available;
    }

    public void setAvailable(String available) {
        this.available = available;
    }
}
