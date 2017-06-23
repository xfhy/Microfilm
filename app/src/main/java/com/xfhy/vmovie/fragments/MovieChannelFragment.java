package com.xfhy.vmovie.fragments;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.widget.Toast;

import com.google.gson.Gson;
import com.xfhy.vmovie.R;
import com.xfhy.vmovie.adapters.ChannelListAdapter;
import com.xfhy.vmovie.base.BaseFragment;
import com.xfhy.vmovie.db.CacheDao;
import com.xfhy.vmovie.listener.HttpCallbackListener;
import com.xfhy.vmovie.model.channel.ChannelInfo;
import com.xfhy.vmovie.model.channel.ChannelListBean;
import com.xfhy.vmovie.utils.HttpUtils;
import com.xfhy.vmovie.utils.LogUtil;

import java.util.ArrayList;
import java.util.List;

import static com.xfhy.vmovie.constants.CacheType.CHANNEL_LIST_DATA;
import static com.xfhy.vmovie.constants.NetworkInterface.CHANNEL_LIST_URL;

/**
 * Created by xfhy on 2017/6/16.
 * 电影频道列表
 * 首页fragment中的viewpager中的电影频道的fragment
 */

public class MovieChannelFragment extends BaseFragment implements HttpCallbackListener {

    private static final String TAG = "MovieChannelFragment";
    private static final int LOAD_FINISHED = 1000;
    private static final int REQUEST_CHANNEL_DATA = 1001;
    private ChannelListAdapter mAdapter;
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case LOAD_FINISHED:
                    //更新适配器
                    List<ChannelInfo> channelInfoList = (List<ChannelInfo>) msg.obj;
                    mAdapter.updateData(channelInfoList);
                    break;
            }
        }
    };
    private CacheDao mDao;
    private RecyclerView mChannelList;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_movie_channel;
    }

    @Override
    protected void initView() {
        mChannelList = (RecyclerView) findViewById(R.id.rv_movie_channel);

        //设置LayoutManager
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 2);
        mChannelList.setLayoutManager(gridLayoutManager);
        //设置适配器
        mAdapter = new ChannelListAdapter(getContext(), getData());
        mChannelList.setAdapter(mAdapter);
    }

    private List<ChannelInfo> getData() {
        List<ChannelInfo> channelInfoList = new ArrayList<>();
        return channelInfoList;
    }

    public static MovieChannelFragment newInstance() {

        Bundle args = new Bundle();

        MovieChannelFragment fragment = new MovieChannelFragment();
        fragment.setArguments(args);
        return fragment;
    }

    /**
     * 请求网络数据 从服务器获取数据
     */
    public void requestDataFromServer() {
        //请求网络数据
        HttpUtils.requestGet(CHANNEL_LIST_URL, REQUEST_CHANNEL_DATA, this);
    }

    /**
     * 从数据库获取缓存数据
     */
    public void requestDataFromDataBase() {
        LogUtil.e(TAG, "requestDataFromDataBase: 从数据库获取缓存数据");
        String channelListData = mDao.getCache(CHANNEL_LIST_DATA);

        if (!TextUtils.isEmpty(channelListData)) {
            Gson gson = new Gson();
            ChannelListBean channelListBean = gson.fromJson(channelListData, ChannelListBean.class);
            LogUtil.e(TAG, "解析成功: " + channelListBean.toString());
            //更新适配器
            mAdapter.updateData(channelListBean.getChannelInfoList());
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
            requestDataFromDataBase(); //从数据库获取缓存数据
        }
    }

    @Override
    public void onFinish(int from, String response) {
        if (from == REQUEST_CHANNEL_DATA) {
            Gson gson = new Gson();
            ChannelListBean channelListBean = gson.fromJson(response, ChannelListBean.class);
            LogUtil.e(TAG, "解析成功: " + channelListBean.toString());

            //网络数据访问成功,现在去更新列表
            Message msg = Message.obtain();
            msg.what = LOAD_FINISHED;
            msg.obj = channelListBean.getChannelInfoList();
            mHandler.sendMessage(msg);

            //设置缓存
            mDao.setCache(CHANNEL_LIST_DATA, response);
        }
    }

    @Override
    public void onError(Exception e) {
    }
}
