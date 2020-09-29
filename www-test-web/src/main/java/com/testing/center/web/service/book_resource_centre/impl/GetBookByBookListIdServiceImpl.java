package com.testing.center.web.service.book_resource_centre.impl;

import com.testing.center.web.common.utils.TestingCenterResult;
import com.testing.center.web.common.utils.ZLYJSON.ZLYJSONObject;
import com.testing.center.web.dao.book_resource_centre.GetBookByBookListIdDaoMapper;
import com.testing.center.web.dao.entity.ServerBean;
import com.testing.center.web.service.book_resource_centre.GetBookByBookListIdService;
import net.sf.json.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GetBookByBookListIdServiceImpl implements GetBookByBookListIdService {

    @Autowired
    private GetBookByBookListIdDaoMapper getBookByBookListIdDaoMapper;

    @Override
    public TestingCenterResult<String> getBookByBookListId(String bookId,Integer environment) {
        TestingCenterResult<String> testingCenterResult = new TestingCenterResult<>();
        ServerBean response = getBookByBookListIdDaoMapper.getBookByBookListId(bookId,environment);
        if (StringUtils.isBlank(response.get_testingCenterRequestMsg())) {
            return testingCenterResult.errorCommon("查询的书籍在图书资源中心不存在");
        }
        JSONObject jsonObject = new JSONObject();
        jsonObject.accumulate("_testingCenterRequestUri", response.get_testingCenterRequestUri().toString());
        jsonObject.accumulateAll(JSONObject.fromObject(response.get_testingCenterRequestMsg()));
        if (!bookId.equals(jsonObject.getString("bookId"))) {
            testingCenterResult.errorCommon("响应的结果bookId匹配不成功");
            testingCenterResult.setData(jsonObject.toString());
        } else {
            testingCenterResult.setSuccess(jsonObject.toString());
        }
        return testingCenterResult;
    }
}
