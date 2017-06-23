package com.xfhy.vmovie.model.latest.details;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by xfhy on 2017/6/23.
 */

public class VideoTopInfo {

    @SerializedName("progressive")
    private List<VideoInfo> videoInfoList;

    public VideoTopInfo(List<VideoInfo> videoInfoList) {
        this.videoInfoList = videoInfoList;
    }

    public List<VideoInfo> getVideoInfoList() {
        return videoInfoList;
    }

    public void setVideoInfoList(List<VideoInfo> videoInfoList) {
        this.videoInfoList = videoInfoList;
    }

    @Override
    public String toString() {
        return "VideoTopInfo{" +
                "videoInfoList=" + videoInfoList +
                '}';
    }
}
