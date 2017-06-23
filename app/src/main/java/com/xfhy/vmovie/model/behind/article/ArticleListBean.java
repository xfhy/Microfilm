package com.xfhy.vmovie.model.behind.article;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by xfhy on 2017/6/19.
 * 文章列表
 */

public class ArticleListBean {

    private String msg;
    @SerializedName("data")
    private List<ArticleBean> articleBeanList;

    public ArticleListBean(String msg, List<ArticleBean> articleBeanList) {
        this.msg = msg;
        this.articleBeanList = articleBeanList;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public List<ArticleBean> getArticleBeanList() {
        return articleBeanList;
    }

    public void setArticleBeanList(List<ArticleBean> articleBeanList) {
        this.articleBeanList = articleBeanList;
    }

    @Override
    public String toString() {
        return "ArticleListBean{" +
                "msg='" + msg + '\'' +
                ", articleBeanList=" + articleBeanList +
                '}';
    }
}
