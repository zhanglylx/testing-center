package com.testing.center.cxb.ad.impl;

import java.io.*;
import java.nio.charset.StandardCharsets;

import java.util.*;

import java.util.Map;

import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.testing.center.common.utils.cxb.URLEnvironment;
import com.testing.center.common.utils.http.HttpUtils;
import com.testing.center.common.utils.http.NetworkHeaders;
import com.testing.center.cxb.ad.GetAdDaoMapper;
import com.testing.center.entity.cxb.ad.GetAd;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.client.utils.URIBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.net.URISyntaxException;

@Repository("getAdDaoMapper")
public class GetAdDaoMapperImpl implements GetAdDaoMapper {
    @Value("#{ad_url.getAdQa}")
    private String urlQa;
    @Value("#{ad_url.getAdOnline}")
    private String urlOnline;
    @Autowired
    private ObjectMapper objectMapper;

    @Override
    public GetAd getAdLuaFunnel(String net, Integer userId, String version, String advId, String appname, Integer cnid, String packname, Integer environment) {
        String url = URLEnvironment.contextSwitching(urlQa, urlOnline, environment);
        try {
            Integer currId = new Integer("-1");
            String r;
            Integer id = null;
            int index = 0;
            GetAd getAd = new GetAd();
            getAd.set_testingCenterRequestUri(url);
            getAd.setRequestMethodGet();
            List<Map> list = new ArrayList<>();
            NetworkHeaders networkHeaders = new NetworkHeaders();
            StringBuilder adnames = new StringBuilder();
            while (true) {
                URIBuilder uriBuilder = new URIBuilder(url);
                uriBuilder.addParameter("packname", packname);
                uriBuilder.addParameter("cnid", cnid.toString());
                uriBuilder.addParameter("appname", appname);
                uriBuilder.addParameter("version", version);
                uriBuilder.addParameter("advId", advId);
                uriBuilder.addParameter("userId", userId.toString());
                uriBuilder.addParameter("uid", userId.toString());
                uriBuilder.addParameter("net", net.toString());
                uriBuilder.addParameter("currId", currId.toString());
                r = HttpUtils.doGet(uriBuilder.build(), null, networkHeaders);

                if (networkHeaders.getResponseCode() != 200) {
                    getAd.set_testingCenterRequestServerResponseStatusCode(networkHeaders.getResponseCode());
                    return getAd;
                } else {
                    getAd.set_testingCenterRequestServerResponseStatusCode(200);
                }
                Map map = objectMapper.readValue(r, Map.class);
                map.put("_testingCenterRequestUri", uriBuilder.build());
                if (map.size() == 1) {
                    getAd.set_testingCenterRequestMsg("共计 " + index + " 层漏斗");
                    list.add(map);
                    getAd.setList(list);
                    return getAd;
                }
                map.put("_testingCenterRequestMsg", "第【 " + (index + 1) + " 】层");
                currId = (Integer) map.get("id");

                if (index == 0) {
                    id = (Integer) map.get("id");
                } else {
                    if (((Integer) map.get("id")).equals(id)) {
                        getAd.setList(list);
                        getAd.set_testingCenterRequestMsg("共计【 " + (index) + " 】层漏斗，" + adnames.toString());
                        return getAd;
                    }
                }
                adnames.append("【");
                adnames.append(map.get("adname").toString());
                adnames.append("】");
                list.add(map);
                if (index >= 100) {
                    throw new RuntimeException("超出了最大循环限制数量：100");
                }
                index++;

            }
        } catch (URISyntaxException | JsonProcessingException e) {
            e.printStackTrace();
            throw new IllegalArgumentException(e);
        }
    }

    public static void main(String[] args) throws IOException {
        String url = "http://localhost:8080/test_www_page_war/ad/adLuaFunnel";
        Map<String, Object> map = new HashMap<>();
        map.put("uid", -1);
        map.put("environment", 0);
        map.put("net", "wifi");
        String response;
        PrintWriter printWriter = new PrintWriter(
                new OutputStreamWriter(
                        new FileOutputStream(new File("ad.txt")), StandardCharsets.UTF_8), true);
//        BufferedReader bufferedReader = StreamUtil.getCharacterBufferedReader(new File("ad.txt"));
        String buffread;
        int index = 0;
//        测试
        String[] channelids = ("10001," +
                "10013," +
                "10453," +
                "10456," +
                "10456,1062," +
                "10457," +
                "10458," +
                "10459," +
                "1061," +
                "1062," +
                "1062,10456," +
                "1062,1063," +
                "1062,23451," +
                "14002," +
                "14510," +
                "14567," +
                "14568," +
                "14569," +
                "22," +
                "2345," +
                "2345,23451," +
                "2346," +
                "2347").split(",");
        for (int appname = 0; appname < 3; appname++) {
            map.putAll(getApp(appname));
            for (int version = 560; version < 580; version++) {

                    map.put("version", StringUtils.join(String.valueOf(version).toCharArray(), '.'));
                    for (String cnid : channelids) {
                        if (map.get("appname").equals("cxb") && Integer.parseInt(cnid) > 10000 && Integer.parseInt(cnid) != 1062)
                            continue;
                        if (!map.get("appname").equals("cxb") && Integer.parseInt(cnid) < 10000 && Integer.parseInt(cnid) != 1062)
                            continue;
                        map.put("cnid", cnid);
                        for (int GG = 1; GG < 90; GG++) {
                            map.put("adGG", "GG-" + GG);
                            response = HttpUtils.doPost(url, map);
                            printWriter.println(response);
                        }
                    }
                }
            }
//      测试end
//        綫上
//        for (int i = 1000; i < 15000; i++) {
//            map.put("cnid", i);
//            if (i < 1800 && i > 1600) {
//                map.put("version", "5.7.2");
//                map.putAll(getApp(2));
//            } else if (i < 10100 && i > 10000) {
//                map.put("version", "5.7.3");
//                map.putAll(getApp(1));
//            } else if (i > 14000 && i < 14100) {
//                map.put("version", "5.7.0");
//                map.putAll(getApp(0));
//            } else {
//                continue;
//            }
//            response = HttpUtils.doPost(url, map);
//            buffread = bufferedReader.readLine();
//            if (!buffread.equals(response)) {
//                System.out.println(++index + " 检查失败");
//                System.out.println("预期结果：" + buffread);
//                System.out.println("实际结果：" + response);
//                return;
//            }
//            printWriter.println(response);
//        }
        System.out.println("检查通过");
    }


    public static Map<String, String> getApp(int j) {
        Map<String, String> map = new HashMap<>();
        switch (j) {
            case 0:
                map.put("appname", "aks");
                map.put("packname", "com.mfyueduqi.book");
                return map;
            case 1:
                map.put("appname", "mfzs");
                map.put("packname", "com.mianfeizs.book");
                return map;
            case 2:
                map.put("appname", "cxb");
                map.put("packname", "com.mianfeia.book");
                return map;
            default:
                throw new IllegalArgumentException(j + "");
        }
    }

}
