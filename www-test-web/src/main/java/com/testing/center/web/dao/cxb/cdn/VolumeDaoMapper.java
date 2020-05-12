package com.testing.center.web.dao.cxb.cdn;

import com.testing.center.web.dao.entity.cxb.cdn.volume.CdnVolume;
import com.testing.center.web.dao.entity.cxb.cdn.volume.CxbGetCdnVolume;

public interface VolumeDaoMapper {
    CxbGetCdnVolume getVolume(String bookId, Integer environment, Integer cnid);
    CdnVolume getCdnVolume(String path, String bookId, Integer version, Integer environment);
}
