package com.xfhy.vmovie.model.series.list;

import com.google.gson.annotations.SerializedName;

/**
 * Created by xfhy on 2017/6/18.
 * 系列电影条目的信息
 */

public class SeriesMovieInfo {

    @SerializedName("seriesid")
    private String seriesId;
    @SerializedName("title")
    private String title;
    /**
     * 图片地址
     */
    @SerializedName("image")
    private String imageUrl;

    /**
     * 每周的更新时间
     */
    @SerializedName("weekly")
    private String weekly;

    /**
     * 条目内容简介
     */
    @SerializedName("content")
    private String content;

    /**
     * 宣传图 图片的url
     */
    @SerializedName("app_image")
    private String appImageUrl;

    /**
     * 是否已经结束   0:未结束   1:结束
     */
    @SerializedName("is_end")
    private String isEnd;
    /**
     * 更新到第几集了
     */
    @SerializedName("update_to")
    private String updateTo;
    /**
     * 订阅人数
     */
    @SerializedName("follower_num")
    private String followerNum;

    public SeriesMovieInfo(String seriesId, String title, String imageUrl, String weekly, String
            content, String appImageUrl, String isEnd, String updateTo, String
                                   followerNum) {
        this.seriesId = seriesId;
        this.title = title;
        this.imageUrl = imageUrl;
        this.weekly = weekly;
        this.content = content;
        this.appImageUrl = appImageUrl;
        this.isEnd = isEnd;
        this.updateTo = updateTo;
        this.followerNum = followerNum;
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

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getWeekly() {
        return weekly;
    }

    public void setWeekly(String weekly) {
        this.weekly = weekly;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getAppImageUrl() {
        return appImageUrl;
    }

    public void setAppImageUrl(String appImageUrl) {
        this.appImageUrl = appImageUrl;
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

    public String getFollowerNum() {
        return followerNum;
    }

    public void setFollowerNum(String followerNum) {
        this.followerNum = followerNum;
    }

    @Override
    public String toString() {
        return "SeriesMovieInfo{" +
                "seriesId='" + seriesId + '\'' +
                ", title='" + title + '\'' +
                ", imageUrl='" + imageUrl + '\'' +
                ", weekly='" + weekly + '\'' +
                ", content='" + content + '\'' +
                ", appImageUrl='" + appImageUrl + '\'' +
                ", isEnd='" + isEnd + '\'' +
                ", updateTo='" + updateTo + '\'' +
                ", followerNum='" + followerNum + '\'' +
                '}';
    }
}
