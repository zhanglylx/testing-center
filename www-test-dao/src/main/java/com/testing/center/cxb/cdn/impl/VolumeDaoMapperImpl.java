package com.testing.center.cxb.cdn.impl;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.testing.center.cxb.cdn.VolumeDaoMapper;
import com.testing.center.cmmon.utils.EncryptionUtils;
import com.testing.center.cmmon.utils.ParameterInspect;
import com.testing.center.cmmon.utils.cdn.Encryptor;
import com.testing.center.cmmon.utils.cxb.URLEnvironment;
import com.testing.center.cmmon.utils.http.HttpUtils;
import com.testing.center.cmmon.utils.http.NetworkHeaders;
import com.testing.center.entity.cxb.cdn.volume.CdnVolume;
import com.testing.center.entity.cxb.cdn.volume.CxbGetCdnVolume;
import org.apache.http.client.utils.URIBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Objects;

@Repository("volumeDaoMapper")
public class VolumeDaoMapperImpl implements VolumeDaoMapper {
    @Value("#{volume_url.service_getCDNVolume}")
    private String serviceGetCdnVolumeUrl;
    @Value("#{volume_url.cdn_getCDNVolume}")
    private String cdnGetCdnVolumeUrl;
    @Value("#{volume_url.cdn_volume_salt}")
    private String cdnVolumeSalt;
    @Value("#{volume_url.cdn_encoding}")
    private String cdnEncoding;
    @Autowired
    private ObjectMapper objectMapper;

    @Override
    public CxbGetCdnVolume getVolume(String bookId, Integer environment, Integer cnid) {
        ParameterInspect.stringIsBlank(bookId);
        Objects.requireNonNull(environment);
        Objects.requireNonNull(cnid);
        URIBuilder uriBuilder;
        URI uri;
        Map<String, Object> headers = new HashMap<String, Object>();
        headers.put("cnid", cnid);
        try {
            uriBuilder = new URIBuilder(URLEnvironment.contextSwitching(serviceGetCdnVolumeUrl, environment));
            uriBuilder.addParameter("bookId", bookId);
            uri = uriBuilder.build();
            String response = HttpUtils.doGet(uri, headers, null);
            CxbGetCdnVolume volume;
            volume = objectMapper.readValue(response, CxbGetCdnVolume.class);
            volume.set_testingCenterRequestUri(uri);
            volume.set_testingCenterRequestHeaders(headers);
            volume.setRequestMethodGet();
            return volume;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    @Override
    public CdnVolume getCdnVolume(String path, String bookId, Integer version, Integer environment) {
        ParameterInspect.stringIsBlank(path);
        ParameterInspect.stringIsBlank(bookId);
        Objects.requireNonNull(version);
        Objects.requireNonNull(environment);
        try {
            URIBuilder uriBuilder = new URIBuilder(URLEnvironment.contextSwitching(this.cdnGetCdnVolumeUrl, environment) + path);
            NetworkHeaders networkHeader = new NetworkHeaders();
            byte[] bytes = HttpUtils.doGetByte(uriBuilder.build(), null, networkHeader);
            CdnVolume cdnVolume;
            if (networkHeader.getResponseCode() == 200) {
                Encryptor encryptor = new Encryptor(EncryptionUtils.getMd5Byte(bookId, "-", version, "-", this.cdnVolumeSalt), Charset.forName(this.cdnEncoding));
                cdnVolume = objectMapper.readValue(encryptor.decode(bytes), CdnVolume.class);
                cdnVolume.set_testingCenterRequestServerResponseStatusCode(200);
            } else {
                cdnVolume = new CdnVolume();
                cdnVolume.set_testingCenterRequestServerResponseStatusCode(networkHeader.getResponseCode());
            }
            cdnVolume.setRequestMethodGet();
            cdnVolume.set_testingCenterRequestUri(uriBuilder.build());

            return cdnVolume;
        } catch (URISyntaxException | IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
}
