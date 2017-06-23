package com.xfhy.vmovie.constants;

/**
 * Created by xfhy on 2017/6/16.
 * 网络接口
 */

public class NetworkInterface {

    /**
     * 请求数据成功  这是json中的msg中数据
     */
    public static final String REQUEST_FROM_SERVER_OK = "ok";

    /**
     * 最新电影列表
     */
    public static final String LATEST_MOVIE_LIST_URL = "";
    /**
     * 最新电影列表按页加载
     */
    public static final String LATEST_MOVIE_LIST_URL_PAGE = "";
    /**
     * 最新电影列表子项 点击进去的电影详情页
     */
    public static final String LATEST_MOVIE_ITEM_DETAILS_URL = "";

    /**
     * 电影详情   包含电影播放地址
     */
    public static final String MOVIE_DETAILS = "";

    /**
     * 轮播图地址
     */
    public static final String BANNER_LIST_URL_PAGE = "";
    /**
     * 频道列表
     */
    public static final String CHANNEL_LIST_URL = "";
    /**
     * 频道列表详情未分页
     */
    public static final String CHANNEL_LIST_DETAILS_URL = "";
    /**
     * 频道列表详情   分页
     */
    public static final String CHANNEL_LIST_DETAILS_PAGE_URL = "";
    /**
     * 系列列表 未分页
     */
    public static final String SERIES_LIST_URL = "";
    /**
     * 系列列表 分页
     */
    public static final String SERIES_LIST_PAGE_URL = "";
    /**
     * 系列详情页面
     */
    public static final String SERIES_DETAILS_PAGE_URL = "";

    /**
     * 幕后界面  标题的列表
     */
    public static final String BEHIND_TITLE_LIST_URL = "";
    /**
     * 幕后界面 对应栏目的未分页 的 文章列表数据
     * 需要自己替换cateid
     */
    public static final String BEHIND_TITLE_LIST_NOT_PAGED_URL = "";
    /**
     * 幕后界面 对应栏目的已分页 的 文章列表数据
     * 需要自己替换cateid  和在末尾添加p(分页)
     */
    public static final String BEHIND_TITLE_LIST_PAGED_URL = "";

    /**
     * 幕后文章界面的url   里面的%s 是postid
     */
    public static final String BEHIND_ARTICLE_DETAILS_URL = "";

}
