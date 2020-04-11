package com.testing.center.service.book_resource_centre.book_management.impl;

import com.testing.center.book_resource_centre.book_management.business_book.BookAddChannelDaoMapper;
import com.testing.center.book_resource_centre.book_management.business_book.entity.BookAddChannel;
import com.testing.center.cmmon.utils.TestingCenterResult;
import com.testing.center.service.book_resource_centre.book_management.BookAddChannelService;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

@Service("bookAddChannelService")
public class BookAddChannelServiceImpl implements BookAddChannelService {
    @Resource(name = "bookAddChannelDaoMapper")
    private BookAddChannelDaoMapper bookAddChannelDaoMapper;

    @Override
    public TestingCenterResult<Object> addBooks(String channel, String... books) {
        TestingCenterResult<Object> testingCenterResult = new TestingCenterResult<>();
        if (StringUtils.isBlank(channel)) {
            testingCenterResult.errorParameterDefaultNull("channel");
            return testingCenterResult;
        }
        if (!StringUtils.isNoneBlank(books)) {
            testingCenterResult.errorParameter("books存在空：" + ArrayUtils.toString(books));
            return testingCenterResult;
        }
        BookAddChannel bookAddChannel = bookAddChannelDaoMapper.addBooks(channel, books);
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("共解析出【 ");
        stringBuilder.append(books.length);
        stringBuilder.append(" 】书籍ID,");
        stringBuilder.append("成功添加【 ");
        stringBuilder.append(bookAddChannel.getSuccessful().size());
        stringBuilder.append(" 】本书籍，失败【 ");
        stringBuilder.append(bookAddChannel.getFailing().size());
        stringBuilder.append(" 】本书籍");
        testingCenterResult.setSuccess(stringBuilder.toString(), bookAddChannel);
        return testingCenterResult;
    }
}
