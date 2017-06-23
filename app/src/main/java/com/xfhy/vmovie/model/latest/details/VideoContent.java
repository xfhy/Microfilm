package com.xfhy.vmovie.model.latest.details;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by xfhy on 2017/6/23.
 */

public class VideoContent {

    @SerializedName("video")
    private List<VideoTopInfo> videoTopInfoList;

    public VideoContent(List<VideoTopInfo> videoTopInfoList) {
        this.videoTopInfoList = videoTopInfoList;
    }

    public List<VideoTopInfo> getVideoTopInfoList() {
        return videoTopInfoList;
    }

    public void setVideoTopInfoList(List<VideoTopInfo> videoTopInfoList) {
        this.videoTopInfoList = videoTopInfoList;
    }

    @Override
    public String toString() {
        return "VideoContent{" +
                "videoTopInfoList=" + videoTopInfoList +
                '}';
    }
}
