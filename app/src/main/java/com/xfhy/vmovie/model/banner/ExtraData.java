package com.xfhy.vmovie.model.banner;

import com.google.gson.annotations.SerializedName;

/**
 * Created by xfhy on 2017/6/17.
 */

public class ExtraData {

    /**
     * banner类型   1:WebView  2:电影详情
     */
    @SerializedName("app_banner_type")
    private String appBannerType;
    @SerializedName("app_banner_param")
    private String appBannerParam;

    public ExtraData(String appBannerType, String appBannerParam) {
        this.appBannerType = appBannerType;
        this.appBannerParam = appBannerParam;
    }

    public String getAppBannerType() {
        return appBannerType;
    }

    public void setAppBannerType(String appBannerType) {
        this.appBannerType = appBannerType;
    }

    public String getAppBannerParam() {
        return appBannerParam;
    }

    public void setAppBannerParam(String appBannerParam) {
        this.appBannerParam = appBannerParam;
    }

    @Override
    public String toString() {
        return "ExtraData{" +
                "appBannerType='" + appBannerType + '\'' +
                ", appBannerParam='" + appBannerParam + '\'' +
                '}';
    }
}
