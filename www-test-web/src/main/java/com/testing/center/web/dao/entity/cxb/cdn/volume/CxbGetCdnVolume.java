package com.testing.center.web.dao.entity.cxb.cdn.volume;

import com.testing.center.web.dao.entity.ServerBean;

public class CxbGetCdnVolume extends ServerBean {
    private Integer error_code;
    private String error_msg;
    private CxbGetCdnVolumeData data;

    @Override
    public String toString() {
        return "Volume{" +
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
