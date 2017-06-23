package com.xfhy.vmovie.model.latest.list;

import com.google.gson.annotations.SerializedName;

/**
 * Created by xfhy on 2017/6/13.
 * 电影分类  实体类
 */

public class CateBean {

    @SerializedName("cateid")
    private String cateId;
    @SerializedName("catename")
    private String cateName;

    public CateBean(String cateName, String cateId) {
        this.cateName = cateName;
        this.cateId = cateId;
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
        return "CateBean{" +
                "cateName='" + cateName + '\'' +
                ", cateId='" + cateId + '\'' +
                '}';
    }
}
