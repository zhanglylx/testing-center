package com.testing.center.web.dao.entity.cxb.cdn.volume;

import java.io.Serializable;
import java.util.Objects;

public class BookChapters implements Serializable {
    private String bookid;
    private Integer chapterindex;
    private Integer chapterNum;
    private Integer charge;
    private Integer contentstatus;
    private Integer cpautoid;
    private Long createDate;
    private Integer id;
    private Integer idx;
    private Integer isVip;
    private String name;
    private Integer s3exist;
    private Integer status;
    private Long updateDate;
    private Integer version;
    private Integer volumeId;
    private Integer wordCount;

    @Override
    public String toString() {
        return "bookChapters{" +
                "bookid='" + bookid + '\'' +
                ", chapterindex=" + chapterindex +
                ", chapterNum=" + chapterNum +
                ", charge=" + charge +
                ", contentstatus=" + contentstatus +
                ", cpautoid=" + cpautoid +
                ", createDate=" + createDate +
                ", id=" + id +
                ", idx=" + idx +
                ", isVip=" + isVip +
                ", name='" + name + '\'' +
                ", s3exist=" + s3exist +
                ", status=" + status +
                ", updateDate=" + updateDate +
                ", version=" + version +
                ", volumeId=" + volumeId +
                ", wordCount=" + wordCount +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BookChapters that = (BookChapters) o;
        return Objects.equals(bookid, that.bookid) &&
                Objects.equals(chapterindex, that.chapterindex) &&
                Objects.equals(chapterNum, that.chapterNum) &&
                Objects.equals(charge, that.charge) &&
                Objects.equals(contentstatus, that.contentstatus) &&
                Objects.equals(cpautoid, that.cpautoid) &&
                Objects.equals(createDate, that.createDate) &&
                Objects.equals(id, that.id) &&
                Objects.equals(idx, that.idx) &&
                Objects.equals(isVip, that.isVip) &&
                Objects.equals(name, that.name) &&
                Objects.equals(s3exist, that.s3exist) &&
                Objects.equals(status, that.status) &&
                Objects.equals(updateDate, that.updateDate) &&
                Objects.equals(version, that.version) &&
                Objects.equals(volumeId, that.volumeId) &&
                Objects.equals(wordCount, that.wordCount);
    }

    @Override
    public int hashCode() {
        return Objects.hash(bookid, chapterindex, chapterNum, charge, contentstatus, cpautoid, createDate, id, idx, isVip, name, s3exist, status, updateDate, version, volumeId, wordCount);
    }

    public String getBookid() {
        return bookid;
    }

    public void setBookid(String bookid) {
        this.bookid = bookid;
    }

    public Integer getChapterindex() {
        return chapterindex;
    }

    public void setChapterindex(Integer chapterindex) {
        this.chapterindex = chapterindex;
    }

    public Integer getChapterNum() {
        return chapterNum;
    }

    public void setChapterNum(Integer chapterNum) {
        this.chapterNum = chapterNum;
    }

    public Integer getCharge() {
        return charge;
    }

    public void setCharge(Integer charge) {
        this.charge = charge;
    }

    public Integer getContentstatus() {
        return contentstatus;
    }

    public void setContentstatus(Integer contentstatus) {
        this.contentstatus = contentstatus;
    }

    public Integer getCpautoid() {
        return cpautoid;
    }

    public void setCpautoid(Integer cpautoid) {
        this.cpautoid = cpautoid;
    }

    public Long getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Long createDate) {
        this.createDate = createDate;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getIdx() {
        return idx;
    }

    public void setIdx(Integer idx) {
        this.idx = idx;
    }

    public Integer getIsVip() {
        return isVip;
    }

    public void setIsVip(Integer isVip) {
        this.isVip = isVip;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getS3exist() {
        return s3exist;
    }

    public void setS3exist(Integer s3exist) {
        this.s3exist = s3exist;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Long getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Long updateDate) {
        this.updateDate = updateDate;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public Integer getVolumeId() {
        return volumeId;
    }

    public void setVolumeId(Integer volumeId) {
        this.volumeId = volumeId;
    }

    public Integer getWordCount() {
        return wordCount;
    }

    public void setWordCount(Integer wordCount) {
        this.wordCount = wordCount;
    }
}
