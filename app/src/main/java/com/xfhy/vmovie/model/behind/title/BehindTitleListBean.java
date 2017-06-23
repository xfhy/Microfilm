package com.xfhy.vmovie.model.behind.title;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by xfhy on 2017/6/19.
 * 幕后文章title的数组
 */

public class BehindTitleListBean {
    private String msg;
    @SerializedName("data")
    private List<TitleBean> titleBeanList;

    public BehindTitleListBean(String msg, List<TitleBean> titleBeanList) {
        this.msg = msg;
        this.titleBeanList = titleBeanList;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public List<TitleBean> getTitleBeanList() {
        return titleBeanList;
    }

    public void setTitleBeanList(List<TitleBean> titleBeanList) {
        this.titleBeanList = titleBeanList;
    }

    @Override
    public String toString() {
        return "BehindTitleListBean{" +
                "msg='" + msg + '\'' +
                ", titleBeanList=" + titleBeanList +
                '}';
    }
}
