package com.xfhy.vmovie.model.series.details;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by xfhy on 2017/6/21.
 * 系列详情页  json中的data
 * 里面有所有信息
 */

public class SetData {

    @SerializedName("seriesid")
    private String seriesId;

    @SerializedName("title")
    private String title;

    @SerializedName("image")
    private String image;

    @SerializedName("content")
    private String content;

    @SerializedName("weekly")
    private String weekly;

    @SerializedName("count_follow")
    private String countFollow;

    @SerializedName("isfollow")
    private String isFollow;

    @SerializedName("share_link")
    private String shareLink;

    @SerializedName("is_end")
    private String isEnd;

    @SerializedName("update_to")
    private String updateTo;

    @SerializedName("tag_name")
    private String tagName;

    @SerializedName("post_num_per_seg")
    private String postNumPerSeg;

    @SerializedName("posts")
    private List<StagingSetBean> setPostsList;

    public SetData(String seriesId, String title, String image, String content, String weekly,
                   String countFollow, String isFollow, String shareLink, String isEnd, String
                           updateTo, String tagName, String postNumPerSeg, List<StagingSetBean>
                           setPostsList) {
        this.seriesId = seriesId;
        this.title = title;
        this.image = image;
        this.content = content;
        this.weekly = weekly;
        this.countFollow = countFollow;
        this.isFollow = isFollow;
        this.shareLink = shareLink;
        this.isEnd = isEnd;
        this.updateTo = updateTo;
        this.tagName = tagName;
        this.postNumPerSeg = postNumPerSeg;
        this.setPostsList = setPostsList;
    }

    public String getSeriesId() {
        return seriesId;
    }

    public void setSeriesId(String seriesId) {
        this.seriesId = seriesId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getWeekly() {
        return weekly;
    }

    public void setWeekly(String weekly) {
        this.weekly = weekly;
    }

    public String getCountFollow() {
        return countFollow;
    }

    public void setCountFollow(String countFollow) {
        this.countFollow = countFollow;
    }

    public String getIsFollow() {
        return isFollow;
    }

    public void setIsFollow(String isFollow) {
        this.isFollow = isFollow;
    }

    public String getShareLink() {
        return shareLink;
    }

    public void setShareLink(String shareLink) {
        this.shareLink = shareLink;
    }

    public String getIsEnd() {
        return isEnd;
    }

    public void setIsEnd(String isEnd) {
        this.isEnd = isEnd;
    }

    public String getUpdateTo() {
        return updateTo;
    }

    public void setUpdateTo(String updateTo) {
        this.updateTo = updateTo;
    }

    public String getTagName() {
        return tagName;
    }

    public void setTagName(String tagName) {
        this.tagName = tagName;
    }

    public String getPostNumPerSeg() {
        return postNumPerSeg;
    }

    public void setPostNumPerSeg(String postNumPerSeg) {
        this.postNumPerSeg = postNumPerSeg;
    }

    public List<StagingSetBean> getSetPostsList() {
        return setPostsList;
    }

    public void setSetPostsList(List<StagingSetBean> setPostsList) {
        this.setPostsList = setPostsList;
    }

    @Override
    public String toString() {
        return "SetData{" +
                "seriesId='" + seriesId + '\'' +
                ", title='" + title + '\'' +
                ", image='" + image + '\'' +
                ", content='" + content + '\'' +
                ", weekly='" + weekly + '\'' +
                ", countFollow='" + countFollow + '\'' +
                ", isFollow='" + isFollow + '\'' +
                ", shareLink='" + shareLink + '\'' +
                ", isEnd='" + isEnd + '\'' +
                ", updateTo='" + updateTo + '\'' +
                ", tagName='" + tagName + '\'' +
                ", postNumPerSeg='" + postNumPerSeg + '\'' +
                ", setPostsList=" + setPostsList +
                '}';
    }
}
