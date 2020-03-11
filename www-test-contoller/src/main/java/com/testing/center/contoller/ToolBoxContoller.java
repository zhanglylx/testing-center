package com.testing.center.contoller;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.testing.center.cmmon.utils.TestingCenterResult;
import com.testing.center.entity.ToolBox;
import com.testing.center.service.ToolBoxService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.codec.cbor.Jackson2CborDecoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.List;

@Controller
@RequestMapping("/toolBox")
public class ToolBoxContoller {
    @Resource(name = "toolBoxService")
    private ToolBoxService toolBoxService;

    @RequestMapping("/findAll")
    @ResponseBody
    public TestingCenterResult<List<ToolBox>> findAllToolBox() {
        return toolBoxService.findAll();
    }


}
