package com.testing.center.web.dao.entity.cxb.cdn.volume;

import com.testing.center.web.dao.entity.ServerBean;

import java.util.List;
import java.util.Map;

public class CdnVolume extends ServerBean {
    private String error_code;
    private List<Map> list;

    public String getError_code() {
        return error_code;
    }

    public void setError_code(String error_code) {
        this.error_code = error_code;
    }

    public List<Map> getList() {
        return list;
    }

    public void setList(List<Map> list) {
        this.list = list;
    }
}
