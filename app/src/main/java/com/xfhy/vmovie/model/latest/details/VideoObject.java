package com.xfhy.vmovie.model.latest.details;

import com.google.gson.annotations.SerializedName;

/**
 * Created by xfhy on 2017/6/23.
 */

public class VideoObject {

    @SerializedName("msg")
    private String msg;
    @SerializedName("data")
    private VideoData data;

    public VideoObject(String msg, VideoData data) {
        this.msg = msg;
        this.data = data;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public VideoData getData() {
        return data;
    }

    public void setData(VideoData data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "VideoObject{" +
                "msg='" + msg + '\'' +
                ", data=" + data +
                '}';
    }
}
