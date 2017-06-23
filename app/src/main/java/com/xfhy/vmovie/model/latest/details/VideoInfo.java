package com.xfhy.vmovie.model.latest.details;

import com.google.gson.annotations.SerializedName;

/**
 * Created by xfhy on 2017/6/23.
 * 电影详情页  视频信息实体类
 */

public class VideoInfo {

    /**
     * 画质
     */
    @SerializedName("profile_name")
    private String profileName;

    /**
     * 视频播放地址
     */
    @SerializedName("qiniu_url")
    private String videoUrl;

    /**
     * 文件大小
     */
    @SerializedName("filesize")
    private String fileSize;

    /**
     * 视频长度
     */
    @SerializedName("duration")
    private String duration;

    @SerializedName("width")
    private String width;

    @SerializedName("height")
    private String height;

    public VideoInfo(String profileName, String videoUrl, String fileSize, String duration,
                     String width, String height) {
        this.profileName = profileName;
        this.videoUrl = videoUrl;
        this.fileSize = fileSize;
        this.duration = duration;
        this.width = width;
        this.height = height;
    }

    public String getProfileName() {
        return profileName;
    }

    public void setProfileName(String profileName) {
        this.profileName = profileName;
    }

    public String getVideoUrl() {
        return videoUrl;
    }

    public void setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
    }

    public String getFileSize() {
        return fileSize;
    }

    public void setFileSize(String fileSize) {
        this.fileSize = fileSize;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getWidth() {
        return width;
    }

    public void setWidth(String width) {
        this.width = width;
    }

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    @Override
    public String toString() {
        return "VideoInfo{" +
                "profileName='" + profileName + '\'' +
                ", videoUrl='" + videoUrl + '\'' +
                ", fileSize='" + fileSize + '\'' +
                ", duration='" + duration + '\'' +
                ", width='" + width + '\'' +
                ", height='" + height + '\'' +
                '}';
    }
}
