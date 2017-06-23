package com.xfhy.vmovie.model.series.details;

import com.google.gson.annotations.SerializedName;

/**
 * Created by xfhy on 2017/6/21.
 * 详情页所有信息
 */

public class SetAllInfoBean {

    @SerializedName("msg")
    private String msg;
    @SerializedName("data")
    private SetData setData;

    public SetAllInfoBean(String msg, SetData setData) {
        this.msg = msg;
        this.setData = setData;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public SetData getSetData() {
        return setData;
    }

    public void setSetData(SetData setData) {
        this.setData = setData;
    }

    @Override
    public String toString() {
        return "SetAllInfoBean{" +
                "msg='" + msg + '\'' +
                ", setData=" + setData +
                '}';
    }
}
