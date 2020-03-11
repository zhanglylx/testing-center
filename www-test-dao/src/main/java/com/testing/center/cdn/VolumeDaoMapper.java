package com.testing.center.cdn;

import com.testing.center.entity.cdn.volume.Volume;

public interface VolumeDaoMapper {
    Volume getVolume(String bookId, Integer isOnline, Integer cnid);
}
