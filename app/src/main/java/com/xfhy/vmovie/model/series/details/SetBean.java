package com.xfhy.vmovie.model.series.details;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by xfhy on 2017/6/21.
 * 系列详情页里面的一集
 */

public class SetBean {

    @SerializedName("series_postid")
    private String seriesPostid;

    /**
     * 第多少集
     */
    @SerializedName("number")
    private String number;

    /**
     * 标题
     */
    @SerializedName("title")
    private String title;

    /**
     * 添加时间
     */
    @SerializedName("addtime")
    private String addtime;

    /**
     * 时长
     */
    @SerializedName("duration")
    private String duration;

    /**
     * 这一集的图片
     */
    @SerializedName("thumbnail")
    private String thumbnail;

    /**
     * 这一集的播放地址
     */
    @SerializedName("source_link")
    private String sourceLink;

    /**
     * 是否正在被播放    加上@Expose注解就不会被GSON序列化
     */
    @Expose
    private boolean isPlay;

    public SetBean(String seriesPostid, String number, String title, String addtime, String
            duration, String thumbnail, String sourceLink) {
        this.seriesPostid = seriesPostid;
        this.number = number;
        this.title = title;
        this.addtime = addtime;
        this.duration = duration;
        this.thumbnail = thumbnail;
        this.sourceLink = sourceLink;
    }

    public String getSeriesPostid() {
        return seriesPostid;
    }

    public void setSeriesPostid(String seriesPostid) {
        this.seriesPostid = seriesPostid;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAddtime() {
        return addtime;
    }

    public void setAddtime(String addtime) {
        this.addtime = addtime;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public String getSourceLink() {
        return sourceLink;
    }

    public void setSourceLink(String sourceLink) {
        this.sourceLink = sourceLink;
    }

    public boolean isPlay() {
        return isPlay;
    }

    public void setPlay(boolean play) {
        isPlay = play;
    }

    @Override
    public String toString() {
        return "SetBean{" +
                "seriesPostid='" + seriesPostid + '\'' +
                ", number='" + number + '\'' +
                ", title='" + title + '\'' +
                ", addtime='" + addtime + '\'' +
                ", duration='" + duration + '\'' +
                ", thumbnail='" + thumbnail + '\'' +
                ", sourceLink='" + sourceLink + '\'' +
                ", isPlay=" + isPlay +
                '}';
    }
}
