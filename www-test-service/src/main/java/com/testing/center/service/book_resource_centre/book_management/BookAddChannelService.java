package com.testing.center.service.book_resource_centre.book_management;

import com.testing.center.book_resource_centre.book_management.business_book.BookAddChannelDaoMapper;
import com.testing.center.cmmon.utils.TestingCenterResult;

public interface BookAddChannelService {
    TestingCenterResult<Object> addBooks(String channel,String... books);
}
