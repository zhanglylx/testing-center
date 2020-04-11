package com.testing.center.book_resource_centre.book_management.business_book.entity;


import com.testing.center.entity.ServerBean;
import lombok.Data;

import java.io.Serializable;
import java.util.Map;

@Data
public class BookAddChannel implements Serializable {
    private Map<String, ServerBean> successful;
    private Map<String, ServerBean> failing;

}
