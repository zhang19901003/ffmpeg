package com.adasplus.proadas.common.data;

/**
 * Created by zhangyapeng on 18-5-3.
 */

public class WarnParams {

    @Override
    public String toString() {
        return "WarnParams{" +
                "ret='" + ret + '\'' +
                ", ldwEnable='" + ldwEnable + '\'' +
                ", ldwSpeed='" + ldwSpeed + '\'' +
                ", ldwSensitivity='" + ldwSensitivity + '\'' +
                ", fcwEnable='" + fcwEnable + '\'' +
                ", fcwSpeed='" + fcwSpeed + '\'' +
                ", fcwSensitivity='" + fcwSensitivity + '\'' +
                ", pcwEnable='" + pcwEnable + '\'' +
                ", pcwSpeed='" + pcwSpeed + '\'' +
                ", pcwSensitivity='" + pcwSensitivity + '\'' +
                ", dfwEnable='" + dfwEnable + '\'' +
                ", dfwSpeed='" + dfwSpeed + '\'' +
                ", dfwSensitivity='" + dfwSensitivity + '\'' +
                ", smokingEnable='" + smokingEnable + '\'' +
                ", callphoneEnable='" + callphoneEnable + '\'' +
                ", yawnEnable='" + yawnEnable + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        WarnParams that = (WarnParams) o;

        if (ldwEnable != null ? !ldwEnable.equals(that.ldwEnable) : that.ldwEnable != null)
            return false;
        if (ldwSpeed != null ? !ldwSpeed.equals(that.ldwSpeed) : that.ldwSpeed != null)
            return false;
        if (ldwSensitivity != null ? !ldwSensitivity.equals(that.ldwSensitivity) : that.ldwSensitivity != null)
            return false;
        if (fcwEnable != null ? !fcwEnable.equals(that.fcwEnable) : that.fcwEnable != null)
            return false;
        if (fcwSpeed != null ? !fcwSpeed.equals(that.fcwSpeed) : that.fcwSpeed != null)
            return false;
        if (fcwSensitivity != null ? !fcwSensitivity.equals(that.fcwSensitivity) : that.fcwSensitivity != null)
            return false;
        if (pcwEnable != null ? !pcwEnable.equals(that.pcwEnable) : that.pcwEnable != null)
            return false;
        if (pcwSpeed != null ? !pcwSpeed.equals(that.pcwSpeed) : that.pcwSpeed != null)
            return false;
        if (pcwSensitivity != null ? !pcwSensitivity.equals(that.pcwSensitivity) : that.pcwSensitivity != null)
            return false;
        if (dfwEnable != null ? !dfwEnable.equals(that.dfwEnable) : that.dfwEnable != null)
            return false;
        if (dfwSpeed != null ? !dfwSpeed.equals(that.dfwSpeed) : that.dfwSpeed != null)
            return false;
        if (dfwSensitivity != null ? !dfwSensitivity.equals(that.dfwSensitivity) : that.dfwSensitivity != null)
            return false;
        if (smokingEnable != null ? !smokingEnable.equals(that.smokingEnable) : that.smokingEnable != null)
            return false;
        if (callphoneEnable != null ? !callphoneEnable.equals(that.callphoneEnable) : that.callphoneEnable != null)
            return false;
        return yawnEnable != null ? yawnEnable.equals(that.yawnEnable) : that.yawnEnable == null;

    }

    @Override
    public int hashCode() {
        int result = ret != null ? ret.hashCode() : 0;
        result = 31 * result + (ldwEnable != null ? ldwEnable.hashCode() : 0);
        result = 31 * result + (ldwSpeed != null ? ldwSpeed.hashCode() : 0);
        result = 31 * result + (ldwSensitivity != null ? ldwSensitivity.hashCode() : 0);
        result = 31 * result + (fcwEnable != null ? fcwEnable.hashCode() : 0);
        result = 31 * result + (fcwSpeed != null ? fcwSpeed.hashCode() : 0);
        result = 31 * result + (fcwSensitivity != null ? fcwSensitivity.hashCode() : 0);
        result = 31 * result + (pcwEnable != null ? pcwEnable.hashCode() : 0);
        result = 31 * result + (pcwSpeed != null ? pcwSpeed.hashCode() : 0);
        result = 31 * result + (pcwSensitivity != null ? pcwSensitivity.hashCode() : 0);
        result = 31 * result + (dfwEnable != null ? dfwEnable.hashCode() : 0);
        result = 31 * result + (dfwSpeed != null ? dfwSpeed.hashCode() : 0);
        result = 31 * result + (dfwSensitivity != null ? dfwSensitivity.hashCode() : 0);
        result = 31 * result + (smokingEnable != null ? smokingEnable.hashCode() : 0);
        result = 31 * result + (callphoneEnable != null ? callphoneEnable.hashCode() : 0);
        result = 31 * result + (yawnEnable != null ? yawnEnable.hashCode() : 0);
        return result;
    }

    /**
     * ret : 1
     * ldwEnable : true
     * ldwSpeed : 60
     * ldwSensitivity : 1
     * fcwEnable : true
     * fcwSpeed : true
     * fcwSensitivity : true
     * pcwEnable : false
     * pcwSpeed : false
     * pcwSensitivity : false
     * dfwEnable : true
     * dfwSpeed : true
     * dfwSensitivity : true
     * smokingEnable : true
     * callphoneEnable : true
     * yawnEnable : true
     */

    private String ret;
    private String ldwEnable;
    private String ldwSpeed;
    private String ldwSensitivity;
    private String fcwEnable;
    private String fcwSpeed;
    private String fcwSensitivity;
    private String pcwEnable;
    private String pcwSpeed;
    private String pcwSensitivity;
    private String dfwEnable;
    private String dfwSpeed;
    private String dfwSensitivity;
    private String smokingEnable;
    private String callphoneEnable;
    private String yawnEnable;

    public String getRet() {
        return ret;
    }

    public void setRet(String ret) {
        this.ret = ret;
    }

    public String getLdwEnable() {
        return ldwEnable;
    }

    public void setLdwEnable(String ldwEnable) {
        this.ldwEnable = ldwEnable;
    }

    public String getLdwSpeed() {
        return ldwSpeed;
    }

    public void setLdwSpeed(String ldwSpeed) {
        this.ldwSpeed = ldwSpeed;
    }

    public String getLdwSensitivity() {
        return ldwSensitivity;
    }

    public void setLdwSensitivity(String ldwSensitivity) {
        this.ldwSensitivity = ldwSensitivity;
    }

    public String getFcwEnable() {
        return fcwEnable;
    }

    public void setFcwEnable(String fcwEnable) {
        this.fcwEnable = fcwEnable;
    }

    public String getFcwSpeed() {
        return fcwSpeed;
    }

    public void setFcwSpeed(String fcwSpeed) {
        this.fcwSpeed = fcwSpeed;
    }

    public String getFcwSensitivity() {
        return fcwSensitivity;
    }

    public void setFcwSensitivity(String fcwSensitivity) {
        this.fcwSensitivity = fcwSensitivity;
    }

    public String getPcwEnable() {
        return pcwEnable;
    }

    public void setPcwEnable(String pcwEnable) {
        this.pcwEnable = pcwEnable;
    }

    public String getPcwSpeed() {
        return pcwSpeed;
    }

    public void setPcwSpeed(String pcwSpeed) {
        this.pcwSpeed = pcwSpeed;
    }

    public String getPcwSensitivity() {
        return pcwSensitivity;
    }

    public void setPcwSensitivity(String pcwSensitivity) {
        this.pcwSensitivity = pcwSensitivity;
    }

    public String getDfwEnable() {
        return dfwEnable;
    }

    public void setDfwEnable(String dfwEnable) {
        this.dfwEnable = dfwEnable;
    }

    public String getDfwSpeed() {
        return dfwSpeed;
    }

    public void setDfwSpeed(String dfwSpeed) {
        this.dfwSpeed = dfwSpeed;
    }

    public String getDfwSensitivity() {
        return dfwSensitivity;
    }

    public void setDfwSensitivity(String dfwSensitivity) {
        this.dfwSensitivity = dfwSensitivity;
    }

    public String getSmokingEnable() {
        return smokingEnable;
    }

    public void setSmokingEnable(String smokingEnable) {
        this.smokingEnable = smokingEnable;
    }

    public String getCallphoneEnable() {
        return callphoneEnable;
    }

    public void setCallphoneEnable(String callphoneEnable) {
        this.callphoneEnable = callphoneEnable;
    }

    public String getYawnEnable() {
        return yawnEnable;
    }

    public void setYawnEnable(String yawnEnable) {
        this.yawnEnable = yawnEnable;
    }

}
