package com.testing.center.service.cdn;

import com.testing.center.cmmon.utils.TestingCenterResult;
import com.testing.center.entity.cdn.volume.CdnVolume;
import com.testing.center.entity.cdn.volume.CxbGetCdnVolume;

public interface VolumeService {
    TestingCenterResult<CxbGetCdnVolume> getCxbVolume(String bookId, Integer environment, Integer cnid);

    TestingCenterResult<CdnVolume> getCdnVolume(String path, String bookId, Integer version, Integer environment);
}
