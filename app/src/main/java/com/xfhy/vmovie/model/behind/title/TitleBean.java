package com.xfhy.vmovie.model.behind.title;

import com.google.gson.annotations.SerializedName;

/**
 * Created by xfhy on 2017/6/19.
 * 幕后的一个栏目的实体类
 */

public class TitleBean {

    @SerializedName("cateid")
    private String cateId;
    @SerializedName("catename")
    private String cateName;

    public TitleBean(String cateId, String cateName) {
        this.cateId = cateId;
        this.cateName = cateName;
    }

    public String getCateId() {
        return cateId;
    }

    public void setCateId(String cateId) {
        this.cateId = cateId;
    }

    public String getCateName() {
        return cateName;
    }

    public void setCateName(String cateName) {
        this.cateName = cateName;
    }

    @Override
    public String toString() {
        return "TitleBean{" +
                "cateId='" + cateId + '\'' +
                ", cateName='" + cateName + '\'' +
                '}';
    }
}
