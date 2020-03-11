package com.testing.center.service.cdn.impl;

import com.testing.center.cdn.VolumeDaoMapper;
import com.testing.center.cmmon.utils.TestingCenterResult;
import com.testing.center.entity.cdn.volume.Volume;
import com.testing.center.service.cdn.VolumeService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service("volumeService")
public class VolumeServiceImpl implements VolumeService {
    @Resource(name = "volumeDaoMapper")
    private VolumeDaoMapper volumeDaoMapper;

    @Override
    public TestingCenterResult<Volume> getCdnVolume(String bookId, Integer isOnline, Integer cnid) {
        TestingCenterResult<Volume> testingCenterResult = new TestingCenterResult<>();
        if (StringUtils.isBlank(bookId)) {
            return testingCenterResult.errorParameterDefaultNull("bookId");
        }
        if (isOnline == null) {
            return testingCenterResult.errorParameterDefaultNull("isOnline");
        }
        if (cnid == null) {
            return testingCenterResult.errorParameterDefaultNull("cnid");
        }
        Volume volume = volumeDaoMapper.getVolume(bookId, isOnline, cnid);
        return testingCenterResult.setSuccess("查询成功", volume);
    }
}
