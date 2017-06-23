package com.xfhy.vmovie.model.latest.list;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by xfhy on 2017/6/13.
 * json字符串最外层的数据
 * 电影列表
 */

public class MovieListBean {

    private String msg;
    @SerializedName("data")
    private List<MovieInfoBean> movieInfoBeanList;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public List<MovieInfoBean> getMovieInfoBeanList() {
        return movieInfoBeanList;
    }

    public void setMovieInfoBeanList(List<MovieInfoBean> movieInfoBeanList) {
        this.movieInfoBeanList = movieInfoBeanList;
    }

    @Override
    public String toString() {
        return "MovieListBean{" +
                "msg='" + msg + '\'' +
                ", movieInfoBeanList=" + movieInfoBeanList +
                '}';
    }
}
