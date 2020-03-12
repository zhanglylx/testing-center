package com.testing.center.contoller.cdn;

import com.testing.center.cmmon.utils.TestingCenterResult;
import com.testing.center.entity.cdn.volume.CdnVolume;
import com.testing.center.entity.cdn.volume.CxbGetCdnVolume;
import com.testing.center.service.cdn.VolumeService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

@Controller
@RequestMapping("/volume")
public class VolumeContoller {
    @Resource(name = "volumeService")
    private VolumeService volumeService;

    @RequestMapping("/getCxbVolume")
    @ResponseBody
    public TestingCenterResult<CxbGetCdnVolume> getCxbVolume(
            @RequestParam("bookId") String bookId,
            @RequestParam("environment") Integer environment,
            @RequestParam("cnid") Integer cnid) {
        return volumeService.getCxbVolume(bookId, environment, cnid);
    }

    @RequestMapping("/getCdnVolume")
    @ResponseBody
    public TestingCenterResult<CdnVolume> getCdnVolume(
            @RequestParam("cdnUrl") String cdnUrl,
            @RequestParam("bookId") String bookId,
            @RequestParam("version") Integer version) {
        return volumeService.getCdnVolume(cdnUrl, bookId, version);
    }
}
