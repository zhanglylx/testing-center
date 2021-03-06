package com.testing.center.web.dao.book_resource_centre.book_management.business_book.impl;


import com.testing.center.web.common.utils.ZLYJSON.ZLYJSONObject;
import com.testing.center.web.common.utils.http.HttpUtils;
import com.testing.center.web.common.utils.http.NetworkHeaders;
import com.testing.center.web.dao.book_resource_centre.book_management.business_book.BookAddChannelDaoMapper;
import com.testing.center.web.dao.book_resource_centre.book_management.business_book.entity.BookAddChannel;
import com.testing.center.web.dao.entity.ServerBean;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

@Repository("bookAddChannelDaoMapper")
public class BookAddChannelDaoMapperImpl implements BookAddChannelDaoMapper {
    @Value("#{book_resource_centre.bookAddChannel}")
    private String bookAddChannelUrl;

    @Override
    public BookAddChannel addBooks(String channel, String... book) {
        if (StringUtils.isBlank(channel)) {
            throw new IllegalArgumentException("channel为空");
        }
        if (!StringUtils.isNoneBlank(book)) {
            throw new RuntimeException("有空值：" + ArrayUtils.toString(book));
        }
        BookAddChannel bookAddChannel = new BookAddChannel();
        String response;
        Map<String, Object> bodyMap = new HashMap<>();
        bodyMap.put("btSelectAll", "on");
        bodyMap.put("btSelectItem", "on");
        bodyMap.put("bookName", "");
        bodyMap.put("params[endTime]", "");
        bodyMap.put("params[beginTime]", "");
        bodyMap.put("bookListIds", channel);
        ZLYJSONObject jsonObject;
        Map<String, ServerBean> successful = new HashMap<>();
        Map<String, ServerBean> failing = new HashMap<>();
        ServerBean serverBean;
        NetworkHeaders networkHeaders = new NetworkHeaders();
        Map<String, Object> headers = new HashMap<>();
//        headers.put("Cookie", "JSESSIONID=110e6366-dccd-419e-b146-7e89a118545a");
        for (String b : book) {
            bodyMap.put("bookIds", b.trim());
            serverBean = new ServerBean();
            serverBean.set_testingCenterRequestBody(bodyMap);
            serverBean.setRequestMethodPost();
            serverBean.set_testingCenterRequestUri(bookAddChannelUrl);
            try {
                response = HttpUtils.doPost(bookAddChannelUrl, bodyMap, headers, networkHeaders);
                serverBean.set_responseStatusCode(networkHeaders);
                if (networkHeaders.getResponseCode() == 302) {
                    serverBean.set_testingCenterRequestMsg("登录失效啦");
                    failing.put(b, serverBean);
                    break;
                }
                jsonObject = ZLYJSONObject.fromObject(response);
                serverBean.set_testingCenterRequestMsg(jsonObject.getString("msg"));
                if (jsonObject.getInt("code") == 0) {
                    successful.put(b, serverBean);
                } else {
                    failing.put(b, serverBean);
                }

            } catch (Exception e) {
                e.printStackTrace();
                serverBean.set_testingCenterRequestMsg(e);
                failing.put(b, serverBean);
            }

        }
        bookAddChannel.setFailing(failing);
        bookAddChannel.setSuccessful(successful);
        return bookAddChannel;
    }

}
