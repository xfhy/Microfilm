package com.xfhy.vmovie.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.xfhy.vmovie.R;
import com.xfhy.vmovie.activity.MainActivity;
import com.xfhy.vmovie.activity.SearchActivity;
import com.xfhy.vmovie.adapters.SeriesMovieAdapter;
import com.xfhy.vmovie.base.BaseFragment;
import com.xfhy.vmovie.db.CacheDao;
import com.xfhy.vmovie.listener.EndLessOnScrollListener;
import com.xfhy.vmovie.listener.HttpCallbackListener;
import com.xfhy.vmovie.model.series.list.SeriesMovieInfo;
import com.xfhy.vmovie.model.series.list.SeriesMovieListBean;
import com.xfhy.vmovie.utils.HttpUtils;
import com.xfhy.vmovie.utils.LogUtil;

import java.util.ArrayList;
import java.util.List;

import static com.xfhy.vmovie.constants.CacheType.SERIES_LIST_DATA;
import static com.xfhy.vmovie.constants.NetworkInterface.SERIES_LIST_PAGE_URL;
import static com.xfhy.vmovie.constants.NetworkInterface.SERIES_LIST_URL;

/**
 * Created by xfhy on 2017/6/15.
 * 系列 Fragment
 */

public class SeriesFragment extends BaseFragment implements View.OnClickListener,
        SwipeRefreshLayout.OnRefreshListener, HttpCallbackListener {
    private static final String TAG = "SeriesFragment";
    private static final int LOAD_FINISHED = 1000;
    private static final int REQUEST_SERIES_DATA = 1001;
    private SeriesMovieAdapter mAdapter;
    /**
     * 加载分页信息完成
     */
    private static final int LOAD_PAGE_FINISHED = 1001;
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case LOAD_FINISHED:
                    //更新适配器
                    List<SeriesMovieInfo> seriesMovieInfoList = (List<SeriesMovieInfo>) msg.obj;
                    mAdapter.updateData(seriesMovieInfoList);
                    //关闭下拉刷新
                    mRefreshLayout.setRefreshing(false);
                    break;
                case LOAD_PAGE_FINISHED:
                    //更新适配器
                    List<SeriesMovieInfo> seriesMovieInfoPageList = (List<SeriesMovieInfo>) msg.obj;
                    mAdapter.addData(seriesMovieInfoPageList);
                    break;
            }
        }
    };
    private CacheDao mDao;
    private ImageView mOpenMenu;
    private TextView mTitle;
    private SwipeRefreshLayout mRefreshLayout;
    private RecyclerView mMovieList;
    private ImageView mSearch;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_main_series;
    }

    @Override
    protected void initView() {
        mOpenMenu = (ImageView) findViewById(R.id.iv_open_slide_menu);
        mTitle = (TextView) findViewById(R.id.tv_title);
        mRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.srl_series_list_refresh);
        mMovieList = (RecyclerView) findViewById(R.id.rv_series_list);
        mSearch = (ImageView) findViewById(R.id.iv_search);

        mTitle.setText("系列");

        //设置LayoutManager
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        mMovieList.setLayoutManager(linearLayoutManager);

        //设置适配器
        mAdapter = new SeriesMovieAdapter(getContext(), getData());
        mMovieList.setAdapter(mAdapter);

        //设置菜单
        mOpenMenu.setOnClickListener(this);
        mSearch.setOnClickListener(this);

        //添加下拉刷新监听器
        mRefreshLayout.setOnRefreshListener(this);
        //设置进度条的颜色主题
        mRefreshLayout.setColorSchemeResources(R.color.colorAccent);

        //添加列表滑动监听
        mMovieList.addOnScrollListener(new EndLessOnScrollListener(linearLayoutManager,
                SERIES_LIST_PAGE_URL, this));
    }

    /**
     * 请求网络数据 从服务器获取数据
     */
    public void requestDataFromServer() {
        //请求网络数据
        HttpUtils.requestGet(SERIES_LIST_URL, REQUEST_SERIES_DATA, this);
    }

    /**
     * 从数据库获取缓存数据
     */
    public void requestDataFromDataBase() {
        LogUtil.e(TAG, "requestDataFromDataBase: 从数据库获取缓存数据");
        String seriesListData = mDao.getCache(SERIES_LIST_DATA);

        if (!TextUtils.isEmpty(seriesListData)) {
            Gson gson = new Gson();
            SeriesMovieListBean seriesMovieListBean = gson.fromJson(seriesListData,
                    SeriesMovieListBean.class);
            LogUtil.e(TAG, "解析成功: " + seriesMovieListBean.toString());
            mAdapter.updateData(seriesMovieListBean.getSeriesMovieInfoList());
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

    private List<SeriesMovieInfo> getData() {
        List<SeriesMovieInfo> seriesMovieInfoList = new ArrayList<>();
        return seriesMovieInfoList;
    }

    /**
     * 请调用此方法实例化fragment
     *
     * @return
     */
    public static SeriesFragment newInstance() {

        Bundle args = new Bundle();

        SeriesFragment fragment = new SeriesFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onClick(View v) {
        MainActivity activity = (MainActivity) getActivity();
        switch (v.getId()) {
            case R.id.iv_open_slide_menu:
                activity.openSlideMenu();  //显示菜单
                break;
            case R.id.iv_search:
                Intent intent = new Intent(getContext(), SearchActivity.class);
                getActivity().startActivity(intent);
                break;
        }
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
        SeriesMovieListBean seriesMovieListBean = gson.fromJson(response,
                SeriesMovieListBean.class);
        LogUtil.e(TAG, "解析成功: " + seriesMovieListBean.toString());

        Message msg = Message.obtain();
        if (from == REQUEST_SERIES_DATA) {
            msg.what = LOAD_FINISHED;

            //设置缓存
            mDao.setCache(SERIES_LIST_DATA, response);
        } else if (from == EndLessOnScrollListener.LOAD_MORE) {
            msg.what = LOAD_PAGE_FINISHED;
        }
        msg.obj = seriesMovieListBean.getSeriesMovieInfoList();
        //网络数据访问成功,现在去更新列表
        mHandler.sendMessage(msg);
    }

    @Override
    public void onError(Exception e) {

    }

}
