package com.testing.center.cdn.impl;

import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.testing.center.cdn.VolumeDaoMapper;
import com.testing.center.cmmon.utils.ParameterInspect;
import com.testing.center.cmmon.utils.cxb.URLEnvironment;
import com.testing.center.cmmon.utils.http.HttpUtils;
import com.testing.center.entity.cdn.volume.Volume;
import org.apache.http.client.utils.URIBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Objects;

@Repository("volumeDaoMapper")
public class VolumeDaoMapperImpl implements VolumeDaoMapper {
    @Value("#{cdn_url.service_getCDNVolume}")
    private String url;
    @Autowired
    private ObjectMapper objectMapper;

    @Override
    public Volume getVolume(String bookId, Integer isOnline, Integer cnid) {
        ParameterInspect.stringIsBlank(bookId);
        Objects.requireNonNull(isOnline);
        Objects.requireNonNull(cnid);
        URIBuilder uriBuilder;
        URI uri;
        Map<String, Object> headers = new HashMap<String, Object>();
        headers.put("cnid", cnid);
        try {
            uriBuilder = new URIBuilder(URLEnvironment.contextSwitching(url, isOnline));
            uriBuilder.addParameter("bookId", bookId);
            uri = uriBuilder.build();
            String response = HttpUtils.doGet(uri, headers, null);
            Volume volume = objectMapper.readValue(response, Volume.class);
            volume.set_testingCenterRequestUri(uri);
            volume.setHeaderObjcet(headers);
            volume.setRequestMethodGet();
            return volume;
        } catch (URISyntaxException | JsonProcessingException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
}
