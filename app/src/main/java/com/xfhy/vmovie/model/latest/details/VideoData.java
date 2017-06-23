package com.xfhy.vmovie.model.latest.details;

import com.google.gson.annotations.SerializedName;

/**
 * Created by xfhy on 2017/6/23.
 */

public class VideoData {

    @SerializedName("postid")
    private String postId;
    @SerializedName("title")
    private String title;
    @SerializedName("wx_small_app_title")
    private String appTitle;
    @SerializedName("intro")
    private String intro;

    /**
     * 评论数
     */
    @SerializedName("count_comment")
    private String countComment;


    /**
     * 里面包含了视频播放地址
     */
    @SerializedName("content")
    private VideoContent content;

    /**
     * 评分
     */
    @SerializedName("rating")
    private String rating;

    /**
     * 出品时间
     */
    @SerializedName("publish_time")
    private String publishTime;

    /**
     * 喜欢个数
     */
    @SerializedName("count_like")
    private String countLike;

    /**
     * 分享数量
     */
    @SerializedName("count_share")
    private String countShare;


    public VideoData(String postId, String title, String appTitle, String intro, String
            countComment, VideoContent content, String rating, String publishTime, String
            countLike, String countShare) {
        this.postId = postId;
        this.title = title;
        this.appTitle = appTitle;
        this.intro = intro;
        this.countComment = countComment;
        this.content = content;
        this.rating = rating;
        this.publishTime = publishTime;
        this.countLike = countLike;
        this.countShare = countShare;
    }

    public String getPostId() {
        return postId;
    }

    public void setPostId(String postId) {
        this.postId = postId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAppTitle() {
        return appTitle;
    }

    public void setAppTitle(String appTitle) {
        this.appTitle = appTitle;
    }

    public String getIntro() {
        return intro;
    }

    public void setIntro(String intro) {
        this.intro = intro;
    }

    public String getCountComment() {
        return countComment;
    }

    public void setCountComment(String countComment) {
        this.countComment = countComment;
    }

    public VideoContent getContent() {
        return content;
    }

    public void setContent(VideoContent content) {
        this.content = content;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getPublishTime() {
        return publishTime;
    }

    public void setPublishTime(String publishTime) {
        this.publishTime = publishTime;
    }

    public String getCountLike() {
        return countLike;
    }

    public void setCountLike(String countLike) {
        this.countLike = countLike;
    }

    public String getCountShare() {
        return countShare;
    }

    public void setCountShare(String countShare) {
        this.countShare = countShare;
    }

    @Override
    public String toString() {
        return "VideoData{" +
                "postId='" + postId + '\'' +
                ", title='" + title + '\'' +
                ", appTitle='" + appTitle + '\'' +
                ", intro='" + intro + '\'' +
                ", countComment='" + countComment + '\'' +
                ", content=" + content +
                ", rating='" + rating + '\'' +
                ", publishTime='" + publishTime + '\'' +
                ", countLike='" + countLike + '\'' +
                ", countShare='" + countShare + '\'' +
                '}';
    }
}
