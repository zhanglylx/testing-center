package com.testing.center.entity.cxb.ad;

import com.testing.center.entity.ServerBean;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

public class GetAd extends ServerBean implements Serializable {
    List<Map> list;

    public List<Map> getList() {
        return list;
    }

    public void setList(List<Map> list) {
        this.list = list;
    }
}
