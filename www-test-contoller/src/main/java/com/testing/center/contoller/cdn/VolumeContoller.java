package com.testing.center.contoller.cdn;

import com.testing.center.cmmon.utils.TestingCenterResult;
import com.testing.center.entity.cdn.volume.CxbGetCdnVolume;
import com.testing.center.service.cdn.VolumeService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

@Controller
@RequestMapping("/cdn")
public class VolumeContoller {
    @Resource(name = "volumeService")
    private VolumeService volumeService;

    @RequestMapping("/getCdnVolume")
    @ResponseBody
    public TestingCenterResult<CxbGetCdnVolume> getCdnVolume(
            @RequestParam("bookId") String bookId,
            @RequestParam("environment") Integer environment,
            @RequestParam("cnid") Integer cnid) {
        return volumeService.getCdnVolume(bookId, environment, cnid);
    }
}
