package com.testing.center.entity.cdn.volume;

import com.testing.center.cmmon.utils.ZLYJSON.ZLYJSONObject;
import com.testing.center.entity.ServerBean;
import net.sf.json.JSONObject;

import java.util.List;

public class CdnVolume extends ServerBean {
    private String error_code;
    private List<CdnVolumeInfo> list;

    public String getError_code() {
        return error_code;
    }

    public void setError_code(String error_code) {
        this.error_code = error_code;
    }

    public List<CdnVolumeInfo> getList() {
        return list;
    }

    public void setList(List<CdnVolumeInfo> list) {
        this.list = list;
    }
}
