package com.testing.center.web.dao.cxb.cache.impl;

import com.testing.center.web.common.utils.ZLYJSON.ZLYJSONObject;
import com.testing.center.web.common.utils.http.HttpUtils;
import com.testing.center.web.dao.cxb.cache.ClearCacheDaoMapper;
import org.apache.http.client.utils.URIBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.net.URI;
import java.net.URISyntaxException;
@Repository("clearCacheDaoMapper")
public class ClearCacheDaoMapperImpl implements ClearCacheDaoMapper {
    @Value("#{cxb_cache.clearAllQa}")
    private String cacheUrl;

    @Override
    public void clearCache() {
        try {
            URI uriBuilder = new URI(cacheUrl);
            String response = HttpUtils.doGet(uriBuilder);
            ZLYJSONObject zlyjsonObject = ZLYJSONObject.fromObject(response);
            if (zlyjsonObject.getInt("status") != 200) {
                throw new RuntimeException("server reponse:" + zlyjsonObject.getInt("status"));
            }
            if (!"clean redis success".equals(zlyjsonObject.getString("message"))) {
                throw new RuntimeException("server message:" + zlyjsonObject.getString("message"));
            }
        } catch (URISyntaxException e) {
            e.printStackTrace();
            throw new IllegalArgumentException("URL错误:" + cacheUrl);
        }
    }
}
