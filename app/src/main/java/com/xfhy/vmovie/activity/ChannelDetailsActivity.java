package com.xfhy.vmovie.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.xfhy.vmovie.R;
import com.xfhy.vmovie.adapters.ChannelDetailsListAdapter;
import com.xfhy.vmovie.base.BaseActivity;
import com.xfhy.vmovie.listener.EndLessOnScrollListener;
import com.xfhy.vmovie.listener.HttpCallbackListener;
import com.xfhy.vmovie.model.latest.list.MovieInfoBean;
import com.xfhy.vmovie.model.latest.list.MovieListBean;
import com.xfhy.vmovie.utils.HttpUtils;
import com.xfhy.vmovie.utils.LogUtil;

import java.util.ArrayList;
import java.util.List;

import static com.xfhy.vmovie.constants.NetworkInterface.CHANNEL_LIST_DETAILS_PAGE_URL;
import static com.xfhy.vmovie.constants.NetworkInterface.CHANNEL_LIST_DETAILS_URL;

/**
 * 频道点进去的详情
 *
 * @author xfhy
 *         create at 2017年6月18日13:25:26
 */
public class ChannelDetailsActivity extends BaseActivity implements View.OnClickListener,
        SwipeRefreshLayout.OnRefreshListener, HttpCallbackListener {

    private static final String TAG = "ChannelDetailsActivity";
    /**
     * 频道详情的id
     */
    private static final String CATE_ID = "cateid";
    /**
     * 标题
     */
    private static final String TITLE = "title";
    /**
     * 请求频道详情页数据
     */
    private static final int REQUEST_CHANNEL_DETAILS = 1000;

    /**
     * 频道详情列表
     */
    private RecyclerView mRecyclerView;
    /**
     * 加载网络数据完成
     */
    private static final int LOAD_FINISHED = 1000;
    /**
     * 加载分页网络数据完成
     */
    private static final int LOAD_PAGE_FINISHED = 1001;
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case LOAD_FINISHED:
                    //更新适配器
                    List<MovieInfoBean> movieListBean = (List<MovieInfoBean>) msg.obj;
                    mAdapter.updateData(movieListBean);
                    //关闭下拉刷新
                    mRefreshLayout.setRefreshing(false);
                    break;
                case LOAD_PAGE_FINISHED:
                    //更新适配器
                    List<MovieInfoBean> moviePageListBean = (List<MovieInfoBean>) msg.obj;
                    mAdapter.addData(moviePageListBean);
                    break;
            }
        }
    };
    /**
     * RecyclerView的适配器
     */
    private ChannelDetailsListAdapter mAdapter;
    /**
     * 详情页的标题
     */
    private TextView mTitle;
    /**
     * 返回按钮
     */
    private ImageView mBack;
    /**
     * 刷新
     */
    private SwipeRefreshLayout mRefreshLayout;
    /**
     * 当前详情页的cateId
     */
    private String cateId;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_channel_details;
    }

    @Override
    protected void initView() {
        LogUtil.e(TAG, "initView: 我是频道详情页面");

        mRecyclerView = (RecyclerView) findViewById(R.id.rv_channel_details);
        mBack = (ImageView) findViewById(R.id.iv_back);
        mTitle = (TextView) findViewById(R.id.tv_title);
        mRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.srl_movie_content);

        //设置进度条的颜色主题
        mRefreshLayout.setColorSchemeResources(R.color.colorAccent);

        //设置LayoutManager
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(linearLayoutManager);

        //设置适配器
        mAdapter = new ChannelDetailsListAdapter(this,
                getData());
        mRecyclerView.setAdapter(mAdapter);

        mBack.setOnClickListener(this);
        mRefreshLayout.setOnRefreshListener(this);

        //添加RecyclerView列表滑动监听
        mRecyclerView.addOnScrollListener(new EndLessOnScrollListener(linearLayoutManager,
                CHANNEL_LIST_DETAILS_PAGE_URL, this));
    }

    private List<MovieInfoBean> getData() {
        List<MovieInfoBean> movieInfoBeanList = new ArrayList<>();
        return movieInfoBeanList;
    }

    @Override
    protected void setupView() {
        //获取页面传递过来的值
        Intent intent = getIntent();
        if (intent != null) {
            cateId = intent.getStringExtra(CATE_ID);
            mTitle.setText(intent.getStringExtra(TITLE));
        }

        //请求网络数据  获取频道详情列表数据
        HttpUtils.requestGet(String.format
                (CHANNEL_LIST_DETAILS_URL, cateId), REQUEST_CHANNEL_DETAILS, this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back: //返回
                finish();
                break;
        }
    }

    @Override
    public void onRefresh() {
        LogUtil.e(TAG, "onRefresh: 下拉刷新....");
        mRefreshLayout.setRefreshing(true);  //显示刷新进度条
        //重新请求网络数据
        setupView();
    }

    /**
     * 启动电影详情页Activity(启动活动的最佳写法,省去了传递参数key)
     *
     * @param context 调用这个Activity的context
     * @param cateId  需要传递过来的postid的值
     * @param title   标题
     */
    public static void acionStart(Context context, String cateId, String title) {
        Intent intent = new Intent(context, ChannelDetailsActivity.class);
        intent.putExtra(CATE_ID, cateId);
        intent.putExtra(TITLE, title);
        context.startActivity(intent);
    }

    @Override
    public void onFinish(int from, String response) {
        Gson gson = new Gson();
        Message msg = Message.obtain();
        MovieListBean movieListBean = gson.fromJson(response, MovieListBean
                .class);
        LogUtil.e(TAG, "解析成功: " + movieListBean.toString());
        if (from == EndLessOnScrollListener.LOAD_MORE) {  //加载更多
            //网络数据访问成功,现在去更新列表
            msg.what = LOAD_PAGE_FINISHED;
        } else if (from == REQUEST_CHANNEL_DETAILS) {  //最初的网络请求

            //网络数据访问成功,现在去更新列表
            msg.what = LOAD_FINISHED;
        }
        msg.obj = movieListBean.getMovieInfoBeanList();
        mHandler.sendMessage(msg);
    }

    @Override
    public void onError(Exception e) {
    }

}
