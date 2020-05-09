package com.testing.center.service.book_resource_centre.book_management;

import com.testing.center.common.utils.TestingCenterResult;

public interface BookAddChannelService {
    TestingCenterResult<Object> addBooks(String channel,String... books);
}
