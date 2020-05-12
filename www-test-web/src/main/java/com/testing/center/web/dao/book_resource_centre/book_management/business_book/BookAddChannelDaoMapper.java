package com.testing.center.web.dao.book_resource_centre.book_management.business_book;

import com.testing.center.web.dao.book_resource_centre.book_management.business_book.entity.BookAddChannel;

public interface BookAddChannelDaoMapper {

    BookAddChannel addBooks(String channel, String... book);
}
