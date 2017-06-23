package com.xfhy.vmovie.model.series.list;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by xfhy on 2017/6/18.
 * 系列电影列表bean
 */

public class SeriesMovieListBean {

    private String msg;
    @SerializedName("data")
    private List<SeriesMovieInfo> seriesMovieInfoList;

    public SeriesMovieListBean(String msg, List<SeriesMovieInfo> seriesMovieInfoList) {
        this.msg = msg;
        this.seriesMovieInfoList = seriesMovieInfoList;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public List<SeriesMovieInfo> getSeriesMovieInfoList() {
        return seriesMovieInfoList;
    }

    public void setSeriesMovieInfoList(List<SeriesMovieInfo> seriesMovieInfoList) {
        this.seriesMovieInfoList = seriesMovieInfoList;
    }

    @Override
    public String toString() {
        return "SeriesMovieListBean{" +
                "msg='" + msg + '\'' +
                ", seriesMovieInfoList=" + seriesMovieInfoList +
                '}';
    }
}
