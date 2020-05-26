package com.testing.center.web.dao.cxb.other.impl;

import java.net.URI;
import java.net.URISyntaxException;
import java.security.KeyStore.Builder;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.jndi.toolkit.url.Uri;
import com.testing.center.web.common.utils.EncryptionUtils;
import com.testing.center.web.common.utils.ZLYDateUtils;
import com.testing.center.web.common.utils.ZLYJSON.ZLYJSONObject;
import com.testing.center.web.common.utils.ZLYRandomUtils;
import com.testing.center.web.common.utils.cxb.URLEnvironment;
import com.testing.center.web.common.utils.http.HttpUtils;
import com.testing.center.web.common.utils.http.NetworkHeaders;
import com.testing.center.web.dao.cxb.other.BusinessLiftDaoMapper;
import com.testing.center.web.dao.entity.cxb.other.BusinessLift;
import com.testing.center.web.dao.entity.cxb.other.GuangDtClickData;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.util.Objects;

@Repository
public class BusinessLiftDaoMapperImpl implements BusinessLiftDaoMapper {

    @Value("#{BusinessLiftDaoMapperImpl.guangdt}")
    private String url;

    @Autowired
    private ObjectMapper objectMapper;

    @Override
    public BusinessLift execute(String bookId, String imei, String oaid, Integer environment) {
        Objects.requireNonNull(bookId);
        Objects.requireNonNull(imei);
        Objects.requireNonNull(oaid);
        imei = EncryptionUtils.getMD5Str(imei);
        bookId += "-" + ZLYRandomUtils.getRandomMixtureEnglish_Number(1, 20);
        BusinessLift businessLift = new BusinessLift();
        try {
            URIBuilder uriBuilder = new URIBuilder(URLEnvironment.contextSwitchingZwscad(url, environment));
            HttpUtils.addParameterURIBuilder(uriBuilder, "muid", imei);
            HttpUtils.addParameterURIBuilder(uriBuilder, "click_time", ZLYDateUtils.getLongSysDefaultFormat());
            HttpUtils.addParameterURIBuilder(uriBuilder, "click_id", ZLYRandomUtils.getRandomString());
            HttpUtils.addParameterURIBuilder(uriBuilder, "appid", ZLYRandomUtils.getRandomString());
            HttpUtils.addParameterURIBuilder(uriBuilder, "advertiser_id", ZLYRandomUtils.getRandomString());
            HttpUtils.addParameterURIBuilder(uriBuilder, "app_type", "Android");
            HttpUtils.addParameterURIBuilder(uriBuilder, "oaid", oaid);
            HttpUtils.addParameterURIBuilder(uriBuilder, "adgroup_name", bookId);
            NetworkHeaders networkHeaders = new NetworkHeaders();
            URI uri = uriBuilder.build();
            String response = HttpUtils.doGet(uri, null, networkHeaders);
            businessLift.setRequestMethodGet();
            businessLift.set_testingCenterRequestUri(uri);
            businessLift.set_responseStatusCode(networkHeaders);
            if (networkHeaders.getResponseCode() != 200) {
                return businessLift;
            } else {
                GuangDtClickData guangDtClickData = objectMapper.readValue(response, GuangDtClickData.class);
                businessLift.setGuangDtClickData(guangDtClickData);
                if (guangDtClickData.getRet() == 0 && "success".equals(guangDtClickData.getMsg())) {
                    businessLift.set_testingCenterRequestMsg("发送点击请求成功");
                }
            }
            return businessLift;
        } catch (URISyntaxException | JsonProcessingException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
}
