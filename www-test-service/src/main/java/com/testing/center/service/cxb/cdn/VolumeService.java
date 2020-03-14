package com.testing.center.service.cxb.cdn;

import com.testing.center.cmmon.utils.TestingCenterResult;
import com.testing.center.entity.cxb.cdn.volume.CdnVolume;
import com.testing.center.entity.cxb.cdn.volume.CxbGetCdnVolume;

public interface VolumeService {
    TestingCenterResult<CxbGetCdnVolume> getCxbVolume(String bookId, Integer environment, Integer cnid);

    TestingCenterResult<CdnVolume> getCdnVolume(String path, String bookId, Integer version, Integer environment);
}
