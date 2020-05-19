package com.testing.center.web.contoller.book_resource_centre;



import com.testing.center.web.common.utils.TestingCenterResult;
import com.testing.center.web.service.book_resource_centre.book_management.BookAddChannelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotBlank;

@RestController
@RequestMapping("/bookResourceCentre/bookManagement")
@Validated
public class BookManagementContoller {
    @Autowired
    private BookAddChannelService bookAddChannelService;

    @PostMapping(value = "addChannelBooks", produces = "application/json;charset:UTF-8")
    public TestingCenterResult<Object> addChannelBooks(
            @NotBlank String channel, @NotBlank String books) {
        books = books.replace("，", ",");
        String[] b = books.split(",");
        return bookAddChannelService.addBooks(channel, b);
    }
}
