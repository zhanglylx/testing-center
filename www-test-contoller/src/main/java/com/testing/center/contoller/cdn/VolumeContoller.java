package com.testing.center.contoller.cdn;

import java.util.HashMap;

import com.testing.center.cmmon.utils.TestingCenterResult;
import com.testing.center.entity.cdn.volume.Volume;
import com.testing.center.service.cdn.VolumeService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.Map;

@Controller
@RequestMapping("/cdn")
public class VolumeContoller {
    @Resource(name = "volumeService")
    private VolumeService volumeService;

    @RequestMapping("/getCdnVolume")
    @ResponseBody
    public TestingCenterResult<Volume> getCdnVolume(
            @RequestParam("bookId") String bookId,
            @RequestParam("isOnline") Integer isOnline,
            @RequestParam("cnid") Integer cnid) {
        return volumeService.getCdnVolume(bookId, isOnline, cnid);
    }
}
