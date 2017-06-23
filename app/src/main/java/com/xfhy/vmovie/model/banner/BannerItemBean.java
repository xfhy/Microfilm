package com.xfhy.vmovie.model.banner;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by xfhy on 2017/6/17.
 */

public class BannerItemBean {

    @SerializedName("bannerid")
    private String bannerId;
    @SerializedName("image")
    private String imageUrl;
    @SerializedName("extra_data")
    private ExtraData extraData;

    public BannerItemBean(String bannerId, String imageUrl, ExtraData extraData) {
        this.bannerId = bannerId;
        this.imageUrl = imageUrl;
        this.extraData = extraData;
    }

    public String getBannerId() {
        return bannerId;
    }

    public void setBannerId(String bannerId) {
        this.bannerId = bannerId;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public ExtraData getExtraData() {
        return extraData;
    }

    public void setExtraData(ExtraData extraData) {
        this.extraData = extraData;
    }

    @Override
    public String toString() {
        return "BannerItemBean{" +
                "bannerId='" + bannerId + '\'' +
                ", imageUrl='" + imageUrl + '\'' +
                ", extraData=" + extraData +
                '}';
    }
}
