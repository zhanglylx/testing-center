package com.testing.center.web.service.book_resource_centre;

import com.testing.center.web.common.utils.TestingCenterResult;
import com.testing.center.web.dao.entity.ServerBean;
import net.sf.json.JSONObject;

public interface GetBookByBookListIdService {
    TestingCenterResult<String> getBookByBookListId(String bookId,Integer environment);
}
