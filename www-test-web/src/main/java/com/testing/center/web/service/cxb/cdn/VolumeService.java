package com.testing.center.web.service.cxb.cdn;


import com.testing.center.web.common.utils.TestingCenterResult;
import com.testing.center.web.dao.entity.cxb.cdn.volume.CdnVolume;
import com.testing.center.web.dao.entity.cxb.cdn.volume.CxbGetCdnVolume;

public interface VolumeService {
    TestingCenterResult<CxbGetCdnVolume> getCxbVolume(String bookId, Integer environment, Integer cnid);

    TestingCenterResult<CdnVolume> getCdnVolume(String path, String bookId, Integer version, Integer environment);
}
