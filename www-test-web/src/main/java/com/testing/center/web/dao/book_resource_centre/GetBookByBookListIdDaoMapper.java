package com.testing.center.web.dao.book_resource_centre;

import com.testing.center.web.dao.entity.ServerBean;

public interface GetBookByBookListIdDaoMapper {
    ServerBean getBookByBookListId(String bookId,Integer environment);
}
