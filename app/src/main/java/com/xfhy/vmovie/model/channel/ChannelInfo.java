package com.xfhy.vmovie.model.channel;

import com.google.gson.annotations.SerializedName;

/**
 * Created by xfhy on 2017/6/17.
 */

public class ChannelInfo {
    @SerializedName("cateid")
    private String cateid;
    @SerializedName("catename")
    private String cateName;
    @SerializedName("icon")
    private String iconUrl;

    public ChannelInfo(String cateid, String cateName, String iconUrl) {
        this.cateid = cateid;
        this.cateName = cateName;
        this.iconUrl = iconUrl;
    }

    public String getCateid() {
        return cateid;
    }

    public void setCateid(String cateid) {
        this.cateid = cateid;
    }

    public String getCateName() {
        return cateName;
    }

    public void setCateName(String cateName) {
        this.cateName = cateName;
    }

    public String getIconUrl() {
        return iconUrl;
    }

    public void setIconUrl(String iconUrl) {
        this.iconUrl = iconUrl;
    }

    @Override
    public String toString() {
        return "ChannelInfo{" +
                "cateid='" + cateid + '\'' +
                ", cateName='" + cateName + '\'' +
                ", iconUrl='" + iconUrl + '\'' +
                '}';
    }
}
