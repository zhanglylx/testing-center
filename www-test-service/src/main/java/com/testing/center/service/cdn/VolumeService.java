package com.testing.center.service.cdn;

import com.testing.center.cmmon.utils.TestingCenterResult;
import com.testing.center.entity.cdn.volume.Volume;

public interface VolumeService {
    TestingCenterResult<Volume> getCdnVolume(String bookId, Integer isOnline, Integer cnid);
}
