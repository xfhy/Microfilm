package com.xfhy.vmovie.fragments;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.Gson;
import com.xfhy.vmovie.R;
import com.xfhy.vmovie.adapters.LatestMovieListAdapter;
import com.xfhy.vmovie.base.BaseFragment;
import com.xfhy.vmovie.db.CacheDao;
import com.xfhy.vmovie.listener.EndLessOnScrollListener;
import com.xfhy.vmovie.listener.HttpCallbackListener;
import com.xfhy.vmovie.model.latest.list.MovieInfoBean;
import com.xfhy.vmovie.model.latest.list.MovieListBean;
import com.xfhy.vmovie.model.banner.BannerItemBean;
import com.xfhy.vmovie.model.banner.BannerListBean;
import com.xfhy.vmovie.utils.HttpUtils;
import com.xfhy.vmovie.utils.LogUtil;

import java.util.ArrayList;
import java.util.List;

import static com.xfhy.vmovie.constants.CacheType.LATEST_MOVIE_BANNER_DATA;
import static com.xfhy.vmovie.constants.CacheType.LATEST_MOVIE_LIST_DATA;
import static com.xfhy.vmovie.constants.NetworkInterface.BANNER_LIST_URL_PAGE;
import static com.xfhy.vmovie.constants.NetworkInterface.LATEST_MOVIE_LIST_URL;
import static com.xfhy.vmovie.constants.NetworkInterface.LATEST_MOVIE_LIST_URL_PAGE;

/**
 * Created by xfhy on 2017/6/16.
 * 最新电影列表
 * 首页fragment中的viewpager中的最新电影列表的fragment
 */

public class LatestMovieFragment extends BaseFragment implements SwipeRefreshLayout
        .OnRefreshListener, HttpCallbackListener {

    private static final String TAG = "LatestMovieFragment";
    private static final int REQUEST_NORMAL_DATA = 1002;
    private static final int REQUEST_BANNER_DATA = 1003;



    private LatestMovieListAdapter mAdapter;
    /**
     * 加载完成
     */
    private static final int LOAD_FINISHED = 1000;
    /**
     * 加载分页信息完成
     */
    private static final int LOAD_PAGE_FINISHED = 1001;
    /**
     * 加载轮播图信息完成
     */
    private static final int LOAD_BANNER_FINISHED = 1002;
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case LOAD_FINISHED:
                    //更新适配器
                    List<MovieInfoBean> movieListBean = (List<MovieInfoBean>) msg.obj;
                    mAdapter.updateNormalItemData(movieListBean);
                    //关闭下拉刷新
                    mRefreshLayout.setRefreshing(false);
                    break;
                case LOAD_PAGE_FINISHED:
                    //更新适配器
                    List<MovieInfoBean> moviePageListBean = (List<MovieInfoBean>) msg.obj;
                    mAdapter.addNormalItemData(moviePageListBean);
                    break;
                case LOAD_BANNER_FINISHED:   //加载轮播图数据完成
                    Log.e(TAG, "handleMessage: 轮播图加载完毕");
                    List<BannerItemBean> bannerItemBeanList = (List<BannerItemBean>) msg.obj;
                    mAdapter.updateHeaderData(bannerItemBeanList);
                    break;
            }
        }
    };
    private CacheDao mDao;
    private RecyclerView mMovieList;
    private SwipeRefreshLayout mRefreshLayout;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_latest_movie;
    }

    @Override
    protected void initView() {
        mMovieList = (RecyclerView) findViewById(R.id.rv_latest_movie);
        mRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.srl_latest_movie_content);

        //添加下拉刷新监听器
        mRefreshLayout.setOnRefreshListener(this);
        //设置进度条的颜色主题
        mRefreshLayout.setColorSchemeResources(R.color.colorAccent);

        //使用RecyclerView之前记得设置LayoutManager
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        mMovieList.setLayoutManager(linearLayoutManager);

        //设置RecyclerView的adapter
        mAdapter = new LatestMovieListAdapter(getActivity(),
                getBannerData(), getNormalData());
        mMovieList.setAdapter(mAdapter);

        //添加列表滑动监听
        mMovieList.addOnScrollListener(new EndLessOnScrollListener(linearLayoutManager,
                LATEST_MOVIE_LIST_URL_PAGE, this));
    }

    private List<BannerItemBean> getBannerData() {
        List<BannerItemBean> bannerItemBeanList = new ArrayList<>();
        return bannerItemBeanList;
    }

    private List<MovieInfoBean> getNormalData() {
        List<MovieInfoBean> movieInfoBeanList = new ArrayList<>();
        return movieInfoBeanList;
    }

    /**
     * 请求网络数据 从服务器获取数据
     */
    public void requestDataFromServer() {
        //请求网络数据  最新电影列表
        HttpUtils.requestGet(LATEST_MOVIE_LIST_URL, REQUEST_NORMAL_DATA, this);
        //请求轮播图网络数据
        HttpUtils.requestGet(BANNER_LIST_URL_PAGE, REQUEST_BANNER_DATA, this);
    }

    /**
     * 从数据库获取缓存数据
     */
    public void requestDataFromDataBase() {
        LogUtil.e(TAG, "requestDataFromDataBase: 从数据库获取缓存数据");
        String latestMovieListData = mDao.getCache(LATEST_MOVIE_LIST_DATA);
        String latestMovieBannerData = mDao.getCache(LATEST_MOVIE_BANNER_DATA);

        Gson gson = new Gson();

        if (!TextUtils.isEmpty(latestMovieListData)) {
            //解析正常数据
            MovieListBean movieListBean = gson.fromJson(latestMovieListData, MovieListBean.class);
            LogUtil.e(TAG, "解析成功: " + movieListBean.toString());
            mAdapter.updateNormalItemData(movieListBean.getMovieInfoBeanList());
        }

        if (!TextUtils.isEmpty(latestMovieBannerData)) {
            //请求banner数据
            BannerListBean bannerListBean = gson.fromJson(latestMovieBannerData, BannerListBean
                    .class);
            LogUtil.e(TAG, "解析成功: " + bannerListBean.toString());
            //更新适配器数据
            mAdapter.updateHeaderData(bannerListBean.getDataList());
        }

    }

    @Override
    protected void setupView() {
        //获取缓存数据库dao
        mDao = CacheDao.getInstance(getContext());

        //判断是否有网络连接
        if (HttpUtils.isNetworkConnected(getContext())) {
            //如果有网络连接
            requestDataFromServer();//请求网络数据 从服务器获取数据
        } else {
            //当前没有网络连接
            Toast.makeText(getContext(), "网络无法连接,请稍后重试", Toast.LENGTH_LONG).show();
            requestDataFromDataBase(); //从数据库获取缓存数据
        }
    }

    public static LatestMovieFragment newInstance() {

        Bundle args = new Bundle();

        LatestMovieFragment fragment = new LatestMovieFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onRefresh() {
        //判断是否有网络连接
        if (HttpUtils.isNetworkConnected(getContext())) {
            //如果有网络连接
            LogUtil.e(TAG, "onRefresh: 下拉刷新....");
            //重新请求网络数据 从服务器获取数据
            requestDataFromServer();
            //显示刷新进度条
            mRefreshLayout.setRefreshing(true);
        } else {
            //当前没有网络连接
            Toast.makeText(getContext(), "网络无法连接,请稍后重试", Toast.LENGTH_LONG).show();
            mRefreshLayout.setRefreshing(false);
        }
    }

    @Override
    public void onFinish(int from, String response) {
        Gson gson = new Gson();
        Message msg = Message.obtain();
        if (from == REQUEST_NORMAL_DATA) {
            //请求正常数据
            MovieListBean movieListBean = gson.fromJson(response, MovieListBean.class);
            LogUtil.e(TAG, "解析成功: " + movieListBean.toString());

            //网络数据访问成功,现在去更新列表
            msg.what = LOAD_FINISHED;
            msg.obj = movieListBean.getMovieInfoBeanList();

            //设置缓存
            mDao.setCache(LATEST_MOVIE_LIST_DATA, response);
        } else if (from == REQUEST_BANNER_DATA) {
            //请求banner数据
            BannerListBean bannerListBean = gson.fromJson(response, BannerListBean.class);
            LogUtil.e(TAG, "解析成功: " + bannerListBean.toString());

            //网络数据访问成功,现在去更新列表
            msg.what = LOAD_BANNER_FINISHED;
            msg.obj = bannerListBean.getDataList();

            //设置缓存
            mDao.setCache(LATEST_MOVIE_BANNER_DATA, response);
        } else if (from == EndLessOnScrollListener.LOAD_MORE) {
            //请求加载更多数据
            MovieListBean movieListBean = gson.fromJson(response, MovieListBean
                    .class);
            LogUtil.e(TAG, "解析成功: " + movieListBean.toString());

            //网络数据访问成功,现在去更新列表
            msg.what = LOAD_PAGE_FINISHED;
            msg.obj = movieListBean.getMovieInfoBeanList();
        }
        mHandler.sendMessage(msg);
    }

    @Override
    public void onError(Exception e) {

    }

}
