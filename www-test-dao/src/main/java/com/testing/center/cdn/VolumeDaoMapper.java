package com.testing.center.cdn;

import com.testing.center.entity.cdn.volume.CxbGetCdnVolume;

public interface VolumeDaoMapper {
    CxbGetCdnVolume getVolume(String bookId, Integer environment, Integer cnid);
}
