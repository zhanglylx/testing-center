package com.testing.center.web.dao.book_resource_centre.impl;

import com.testing.center.web.common.utils.cxb.URLEnvironment;
import com.testing.center.web.common.utils.http.HttpUtils;
import com.testing.center.web.dao.book_resource_centre.GetBookByBookListIdDaoMapper;
import com.testing.center.web.dao.entity.ServerBean;
import org.apache.http.client.utils.URIBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.net.URISyntaxException;

@Repository
public class GetBookByBookListIdDaoMapperImpl implements GetBookByBookListIdDaoMapper {
    private Logger logger = LoggerFactory.getLogger(GetBookByBookListIdDaoMapperImpl.class);

    @Value("#{book_resource_centre.getBookByBookListId}")
    private String getBookByBookListIdUrl;

    @Override
    public ServerBean getBookByBookListId(String bookId,Integer environment) {
        String url = URLEnvironment.contextSwitching(getBookByBookListIdUrl, environment);
        String response;
        ServerBean serverBean = new ServerBean();
        try {
            URIBuilder uriBuilder = new URIBuilder(url);
            HttpUtils.addParameterURIBuilder(uriBuilder, "bookListId", 7000);
            HttpUtils.addParameterURIBuilder(uriBuilder, "bookId", bookId);
            serverBean.set_testingCenterRequestUri(uriBuilder.build());
            response = HttpUtils.doGet(uriBuilder.build());
            serverBean.set_testingCenterRequestMsg(response);
        } catch (URISyntaxException e) {
            response = "转换URL异常:" + url;
            logger.error(response, e);
            serverBean.set_testingCenterRequestMsg(response);
        }
        return serverBean;
    }
}
