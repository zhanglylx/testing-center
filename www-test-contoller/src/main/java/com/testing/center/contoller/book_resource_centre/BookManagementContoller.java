package com.testing.center.contoller.book_resource_centre;


import com.testing.center.book_resource_centre.book_management.business_book.entity.BookAddChannel;
import com.testing.center.cmmon.utils.TestingCenterResult;
import com.testing.center.service.book_resource_centre.book_management.BookAddChannelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@RestController
@RequestMapping("/bookResourceCentre/bookManagement")
@Validated
public class BookManagementContoller {
    @Autowired
    private BookAddChannelService bookAddChannelService;

    @PostMapping(value = "addChannelBooks", produces = "application/json;charset:utf-8")
    public TestingCenterResult<Object> addChannelBooks(
            @NotBlank String channel, @NotBlank String books) {
        books = books.replace("，", ",");
        String[] b = books.split(",");
        return bookAddChannelService.addBooks(channel, b);
    }
}
