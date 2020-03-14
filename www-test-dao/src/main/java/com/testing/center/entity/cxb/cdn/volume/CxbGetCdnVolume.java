package com.testing.center.entity.cxb.cdn.volume;

import com.testing.center.entity.ServerBean;

public class CxbGetCdnVolume extends ServerBean {
    private Long date;
    private Integer error_code;
    private String error_msg;
    private CxbGetCdnVolumeData data;

    @Override
    public String toString() {
        return "Volume{" +
                "date=" + date +
                ", error_code=" + error_code +
                ", error_msg='" + error_msg + '\'' +
                ", data=" + data +
                '}';
    }

    public String getError_msg() {
        return error_msg;
    }

    public void setError_msg(String error_msg) {
        this.error_msg = error_msg;
    }

    public Long getDate() {
        return date;
    }

    public void setDate(Long date) {
        this.date = date;
    }

    public Integer getError_code() {
        return error_code;
    }

    public void setError_code(Integer error_code) {
        this.error_code = error_code;
    }


    public CxbGetCdnVolumeData getData() {
        return data;
    }

    public void setData(CxbGetCdnVolumeData data) {
        this.data = data;
    }

}
