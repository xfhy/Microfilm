package com.xfhy.vmovie.model.behind.article;

import com.google.gson.annotations.SerializedName;
import com.xfhy.vmovie.model.latest.list.CateBean;

import java.util.List;

/**
 * Created by xfhy on 2017/6/19.
 * 文章实体类
 */

public class ArticleBean {

    @SerializedName("postid")
    private String postId;
    @SerializedName("title")
    private String title;
    @SerializedName("image")
    private String imageUrl;
    @SerializedName("like_num")
    private String likeNum;
    @SerializedName("share_num")
    private String shareNum;

    /**
     * 只有1个元素的集合
     */
    @SerializedName("cates")
    private List<CateBean> cateBeenList;

    public ArticleBean(String postId, String title, String imageUrl, String likeNum, String
            shareNum, List<CateBean> cateBeenList) {
        this.postId = postId;
        this.title = title;
        this.imageUrl = imageUrl;
        this.likeNum = likeNum;
        this.shareNum = shareNum;
        this.cateBeenList = cateBeenList;
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

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
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

    public List<CateBean> getCateBeenList() {
        return cateBeenList;
    }

    public void setCateBeenList(List<CateBean> cateBeenList) {
        this.cateBeenList = cateBeenList;
    }

    @Override
    public String toString() {
        return "ArticleBean{" +
                "postId='" + postId + '\'' +
                ", title='" + title + '\'' +
                ", imageUrl='" + imageUrl + '\'' +
                ", likeNum='" + likeNum + '\'' +
                ", shareNum='" + shareNum + '\'' +
                ", cateBeenList=" + cateBeenList +
                '}';
    }
}
