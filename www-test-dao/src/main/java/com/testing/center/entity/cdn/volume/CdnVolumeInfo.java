package com.testing.center.entity.cdn.volume;

import java.io.Serializable;
import java.util.List;

public class CdnVolumeInfo implements Serializable {
    private String bookId;
    private Integer idx;
    private Integer id;
    private String name;
    private List<CdnVolumeBookChapter> bookChapters;

    public String getBookId() {
        return bookId;
    }

    public void setBookId(String bookId) {
        this.bookId = bookId;
    }

    public Integer getIdx() {
        return idx;
    }

    public void setIdx(Integer idx) {
        this.idx = idx;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<CdnVolumeBookChapter> getBookChapters() {
        return bookChapters;
    }

    public void setBookChapters(List<CdnVolumeBookChapter> bookChapters) {
        this.bookChapters = bookChapters;
    }
}
