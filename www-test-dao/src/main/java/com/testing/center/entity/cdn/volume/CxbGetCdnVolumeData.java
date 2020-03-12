package com.testing.center.entity.cdn.volume;

import java.io.Serializable;

public class CxbGetCdnVolumeData implements Serializable {
    private String bookId;
    private String cdnUrl;
    private Integer s3exist;
    private Integer version;

    public String getBookId() {
        return bookId;
    }

    public void setBookId(String bookId) {
        this.bookId = bookId;
    }

    public String getCdnUrl() {
        return cdnUrl;
    }

    public void setCdnUrl(String cdnUrl) {
        this.cdnUrl = cdnUrl;
    }

    public Integer getS3exist() {
        return s3exist;
    }

    public void setS3exist(Integer s3exist) {
        this.s3exist = s3exist;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }
}
