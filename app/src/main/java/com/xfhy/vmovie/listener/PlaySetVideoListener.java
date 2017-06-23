package com.xfhy.vmovie.listener;

import com.xfhy.vmovie.model.series.details.SetBean;

/**
 * Created by xfhy on 2017/6/21.
 * 系列界面中播放视频  点击视频之后需要回调回Activity
 * 然后在Activity中接收到url地址,然后进行视频的播放
 */

public interface PlaySetVideoListener {

    /**
     * 播放视频
     *
     * @param position RecyclerView点击的位置
     * @param setBean  点击项的详情
     */
    void playVideo(int position, SetBean setBean);

}
