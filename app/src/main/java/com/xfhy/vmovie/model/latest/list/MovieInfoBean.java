package com.xfhy.vmovie.model.latest.list;


import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by xfhy on 2017/6/13.
 * 主界面的列表的条目   电影bean  实体类
 */

public class MovieInfoBean {

    private String title;
    private String postid;
    @SerializedName("duration")
    private String duration;  //时长
    @SerializedName("like_num")
    private String likeNum;  //喜欢数量
    @SerializedName("share_num")
    private String shareNum;  //分享数量


    @SerializedName("cates")
    private List<CateBean> cateBeanList;

    @SerializedName("image")
    private String imageUrl;

    public MovieInfoBean(String title, String postid, String duration, String likeNum, String
            shareNum, List<CateBean> cateBeanList, String imageUrl) {
        this.title = title;
        this.postid = postid;
        this.duration = duration;
        this.likeNum = likeNum;
        this.shareNum = shareNum;
        this.cateBeanList = cateBeanList;
        this.imageUrl = imageUrl;
    }

    public String getLikeNum() {
        return likeNum;
    }

    public void setLikeNum(String likeNum) {
        this.likeNum = likeNum;
    }

    public String getShareNum() {
        return shareNum;
    }

    public void setShareNum(String shareNum) {
        this.shareNum = shareNum;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPostid() {
        return postid;
    }

    public void setPostid(String postid) {
        this.postid = postid;
    }

    /**
     * 计算时长  类似于 1'22''
     *
     * @return
     */
    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public List<CateBean> getCateBeanList() {
        return cateBeanList;
    }

    public void setCateBeanList(List<CateBean> cateBeanList) {
        this.cateBeanList = cateBeanList;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    @Override
    public String toString() {
        return "MovieInfoBean{" +
                "title='" + title + '\'' +
                ", duration='" + duration + '\'' +
                ", cateBeanList=" + cateBeanList +
                ", imageUrl='" + imageUrl + '\'' +
                '}';
    }
}
