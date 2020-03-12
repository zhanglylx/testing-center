package com.testing.center.service.cdn;

import com.testing.center.cmmon.utils.TestingCenterResult;
import com.testing.center.entity.cdn.volume.CxbGetCdnVolume;

public interface VolumeService {
    TestingCenterResult<CxbGetCdnVolume> getCdnVolume(String bookId, Integer environment, Integer cnid);
}
