package com.testing.center.web.service.book_resource_centre.book_management;


import com.testing.center.web.common.utils.TestingCenterResult;

public interface BookAddChannelService {
    TestingCenterResult<Object> addBooks(String channel, String... books);
}
