package com.xfhy.vmovie.model.banner;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by xfhy on 2017/6/17.
 */

public class BannerListBean {

    @SerializedName("msg")
    private String msg;
    @SerializedName("data")
    private List<BannerItemBean> dataList;

    public BannerListBean(String msg, List<BannerItemBean> dataList) {
        this.msg = msg;
        this.dataList = dataList;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public List<BannerItemBean> getDataList() {
        return dataList;
    }

    public void setDataList(List<BannerItemBean> dataList) {
        this.dataList = dataList;
    }

    @Override
    public String toString() {
        return "BannerListBean{" +
                "msg='" + msg + '\'' +
                ", dataList=" + dataList +
                '}';
    }
}
