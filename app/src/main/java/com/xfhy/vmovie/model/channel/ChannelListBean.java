package com.xfhy.vmovie.model.channel;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by xfhy on 2017/6/17.
 * 频道bean
 */

public class ChannelListBean {

    @SerializedName("data")
    private List<ChannelInfo> channelInfoList;

    public List<ChannelInfo> getChannelInfoList() {
        return channelInfoList;
    }

    public void setChannelInfoList(List<ChannelInfo> channelInfoList) {
        this.channelInfoList = channelInfoList;
    }

    @Override
    public String toString() {
        return "ChannelListBean{" +
                "channelInfoList=" + channelInfoList +
                '}';
    }
}
