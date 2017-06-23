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
import com.xfhy.vmovie.adapters.BehindArticleAdapter;
import com.xfhy.vmovie.base.BaseFragment;
import com.xfhy.vmovie.db.CacheDao;
import com.xfhy.vmovie.listener.EndLessOnScrollListener;
import com.xfhy.vmovie.listener.HttpCallbackListener;
import com.xfhy.vmovie.model.behind.article.ArticleBean;
import com.xfhy.vmovie.model.behind.article.ArticleListBean;
import com.xfhy.vmovie.utils.HttpUtils;
import com.xfhy.vmovie.utils.LogUtil;

import java.util.ArrayList;
import java.util.List;

import static com.xfhy.vmovie.constants.NetworkInterface.BEHIND_TITLE_LIST_NOT_PAGED_URL;
import static com.xfhy.vmovie.constants.NetworkInterface.BEHIND_TITLE_LIST_PAGED_URL;

/**
 * Created by xfhy on 2017/6/19.
 * 幕后文章栏目的下面的ViewPager里面的  title对应的 fragment
 */

public class BehindListFragment extends BaseFragment implements HttpCallbackListener,
        SwipeRefreshLayout.OnRefreshListener {
    private static final String TAG = "BehindListFragment";
    private static final String CATE_ID = "cateid";
    private static final int REQUEST_ARTICLE_LIST_DATA = 1000;
    private static final int LOAD_ARTICLE_DATA_FINISHED = 1001;
    private static final int LOAD_ARTICLE_PAGE_FINISHED = 1002;
    private RecyclerView mArticleList;
    private BehindArticleAdapter mAdapter;
    private String cateId;
    private CacheDao mDao;
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case LOAD_ARTICLE_DATA_FINISHED:
                    //加载网络数据完成   更新适配器
                    List<ArticleBean> articleBeanList = (List<ArticleBean>) msg.obj;
                    mAdapter.updateData(articleBeanList);
                    //关闭下拉刷新
                    mRefreshLayout.setRefreshing(false);
                    break;
                case LOAD_ARTICLE_PAGE_FINISHED:
                    //更新适配器
                    List<ArticleBean> articlePageListBean = (List<ArticleBean>) msg.obj;
                    mAdapter.addData(articlePageListBean);
                    break;
            }
        }
    };
    private SwipeRefreshLayout mRefreshLayout;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_behind_list;
    }

    @Override
    protected void initArguments() {
        Bundle arguments = getArguments();
        if (arguments != null) {
            cateId = arguments.getString(CATE_ID);
            Log.e(TAG, "initArguments: 当前fragment的cateid:" + cateId);
        }
    }

    @Override
    protected void initView() {
        mArticleList = (RecyclerView) findViewById(R.id.rv_behind_article_list);
        mRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.srl_article_list);

        //设置LayoutManager
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        mArticleList.setLayoutManager(linearLayoutManager);

        //设置RecyclerView适配器
        mAdapter = new BehindArticleAdapter(getContext(),
                getData());
        mArticleList.setAdapter(mAdapter);

        //添加滑动监听  实现下拉刷新
        mArticleList.addOnScrollListener(new EndLessOnScrollListener(linearLayoutManager, String
                .format(BEHIND_TITLE_LIST_PAGED_URL, cateId), this));

        //添加下拉刷新监听器
        mRefreshLayout.setOnRefreshListener(this);
        //设置进度条的颜色主题
        mRefreshLayout.setColorSchemeResources(R.color.colorAccent);
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

    /**
     * 请求网络数据 从服务器获取数据
     */
    public void requestDataFromServer() {
        //请求网络数据
        HttpUtils.requestGet(String.format(BEHIND_TITLE_LIST_NOT_PAGED_URL, cateId),
                REQUEST_ARTICLE_LIST_DATA, this);
    }

    /**
     * 从数据库获取缓存数据
     */
    public void requestDataFromDataBase() {
        LogUtil.e(TAG, "requestDataFromDataBase: 从数据库获取缓存数据");
        //根据cateId从数据库获取对应的数据
        String articleList = mDao.getCache(Integer.parseInt(cateId));

        //用GSON解析
        Gson gson = new Gson();
        if (!TextUtils.isEmpty(articleList)) {
            //解析正常数据
            ArticleListBean articleListBean = gson.fromJson(articleList, ArticleListBean.class);
            LogUtil.e(TAG, "幕后文章" + cateId + "从数据库解析成功: " + articleListBean.toString());
            //更新文章列表适配器
            mAdapter.updateData(articleListBean.getArticleBeanList());
        }

    }

    private List<ArticleBean> getData() {
        List<ArticleBean> articleBeanList = new ArrayList<>();
        return articleBeanList;
    }

    public static BehindListFragment newInstance(String cateId) {
        LogUtil.e(TAG, "newInstance: 幕后viewPager中的fragment收到cateid:" + cateId);

        Bundle bundle = new Bundle();
        bundle.putString(CATE_ID, cateId);
        BehindListFragment fragment = new BehindListFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onFinish(int from, String response) {
        //用GSON解析
        Gson gson = new Gson();
        //解析正常数据
        ArticleListBean articleListBean = gson.fromJson(response, ArticleListBean.class);
        LogUtil.e(TAG, "幕后文章" + cateId + "从网络解析成功: " + articleListBean.toString());
        //网络数据加载成功
        Message msg = Message.obtain();
        if (from == REQUEST_ARTICLE_LIST_DATA) {
            msg.what = LOAD_ARTICLE_DATA_FINISHED;

            //设置缓存   只加载前10条数据吧
            mDao.setCache(Integer.parseInt(cateId), response);
        } else if(from==EndLessOnScrollListener.LOAD_MORE){
            //这是加载更多
            msg.what = LOAD_ARTICLE_PAGE_FINISHED;
        }
        msg.obj = articleListBean.getArticleBeanList();
        mHandler.sendMessage(msg);
    }

    @Override
    public void onError(Exception e) {
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
}
