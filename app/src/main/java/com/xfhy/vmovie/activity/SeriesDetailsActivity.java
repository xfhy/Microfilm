package com.xfhy.vmovie.activity;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.media.MediaPlayer;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.TabLayout;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import com.google.gson.Gson;
import com.xfhy.vmovie.R;
import com.xfhy.vmovie.adapters.SeriesSetDetailsAdapter;
import com.xfhy.vmovie.base.BaseActivity;
import com.xfhy.vmovie.listener.HttpCallbackListener;
import com.xfhy.vmovie.listener.PlaySetVideoListener;
import com.xfhy.vmovie.model.series.details.SetAllInfoBean;
import com.xfhy.vmovie.model.series.details.SetBean;
import com.xfhy.vmovie.model.series.details.SetData;
import com.xfhy.vmovie.model.series.details.StagingSetBean;
import com.xfhy.vmovie.utils.HttpUtils;
import com.xfhy.vmovie.utils.LogUtil;
import com.xfhy.vmovie.utils.PopupShareMenu;
import com.xfhy.vmovie.view.ExpandableTextView;
import com.xfhy.vmovie.view.MyVideoView;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static com.xfhy.vmovie.constants.NetworkInterface.REQUEST_FROM_SERVER_OK;
import static com.xfhy.vmovie.constants.NetworkInterface.SERIES_DETAILS_PAGE_URL;

/**
 * 系列详情页
 *
 * @author xfhy
 *         create at 2017年6月21日9:29:42
 */
public class SeriesDetailsActivity extends BaseActivity implements HttpCallbackListener, View
        .OnClickListener, TabLayout.OnTabSelectedListener, PlaySetVideoListener, MediaPlayer
        .OnPreparedListener, MediaPlayer.OnCompletionListener, CompoundButton
        .OnCheckedChangeListener, SeekBar.OnSeekBarChangeListener {

    private static final String TAG = "SeriesDetailsActivity";
    private static final int LOAD_SERIES_DATA_FINISHED = 1000;
    private static final int REQUEST_SERIES_DETAILS_DATA = 1002;
    private static final String SERIES_ID = "series_id";
    private static final int UPDATE_VIDEO_PROGRESS = 1003;
    private String seriesId;
    /**
     * 显示/隐藏  内容
     */
    private TextView mShowHide;
    private ExpandableTextView mContent;
    private TabLayout mTabLayout;
    private SetAllInfoBean setAllInfoBean;
    /**
     * 分期列表
     */
    private RecyclerView mSetList;
    /**
     * 下面的分期RecyclerView的adapter
     */
    private SeriesSetDetailsAdapter mAdapter;
    /**
     * 播放布局的根布局
     */
    private FrameLayout mVideoRootView;
    /**
     * 我的播放控件
     */
    private MyVideoView mVideoView;
    /**
     * 播放/暂停
     */
    private CheckBox mPlay;
    /**
     * 全屏
     */
    private CheckBox mFullScreen;
    /**
     * 视频播放的当前进度
     */
    private TextView mCurrentTime;
    /**
     * 视频的总长
     */
    private TextView mTotalTime;
    /**
     * 视频的进度条
     */
    private SeekBar mVideoProgress;
    /**
     * 视频播放器的控制布局
     */
    private FrameLayout mCtroView;
    /**
     * 返回按钮
     */
    private ImageView mBack;
    /**
     * 视频右上角的分享
     */
    private ImageView mVideoLanShare;
    /**
     * 该视频的集数和视频标题
     */
    private TextView mNumAndTitle;
    /**
     * 最外层的大标题
     */
    private TextView mTitle;
    /**
     * 周几更新
     */
    private TextView mWeekly;
    /**
     * 更新到哪一集了
     */
    private TextView mUpdateTo;
    /**
     * 类型
     */
    private TextView mTagName;
    /**
     * 分享
     */
    private TextView mShare;
    /**
     * 评论
     */
    private TextView mComment;
    /**
     * 缓存按钮
     */
    private TextView mCache;
    private int mVideoHeight;
    private PopupShareMenu mPopupShareMenu;
    private LinearLayout mRootView;

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case LOAD_SERIES_DATA_FINISHED:
                    //加载本界面数据完成
                    setAllInfoBean = (SetAllInfoBean) msg.obj;

                    updateThisPage();

                    break;
                case UPDATE_VIDEO_PROGRESS:
                    updateCurrentTime();   //更新当前时间
                    break;
            }
        }
    };

    /**
     * 更新当前界面的数据
     */
    private void updateThisPage() {
        // 设置播放地址     支持本地和网络的    如果是网络的,记得加权限
        SetData setData = setAllInfoBean.getSetData();
        SetBean setBean = setData.getSetPostsList().get(0)
                .getSetBeanList().get(0);
        mVideoView.setVideoPath(setBean.getSourceLink());   //这是网络播放

        //从网络加载的分期标题数据动态添加进去
        for (StagingSetBean stagingSetBean : setData
                .getSetPostsList()) {
            mTabLayout.addTab(mTabLayout.newTab().setText(stagingSetBean.getFromTo()));
        }

        //默认是显示第一列的数据
        mAdapter.updateData(setData.getSetPostsList().get(0)
                .getSetBeanList());
        //设置视频标题,默认是最新的那一集
        mNumAndTitle.setText(String.format("第%s集 %s", setBean.getNumber(), setBean.getTitle()));
        //设置大标题
        mTitle.setText(setData.getTitle());
        //设置更新时间
        mWeekly.setText(String.format("更新: %s", setData.getWeekly()));
        mUpdateTo.setText(String.format("集数: 更新至%s集",setData.getUpdateTo()));
        mTagName.setText(String.format("类型: %s",setData.getTagName()));
        mContent.setText(setData.getContent());

        //没有数据  怪我了     分享,评论
        Random random = new Random();
        mShare.setText(random.nextInt(500)+"");
        mComment.setText(random.nextInt(500)+"");
    }


    @Override
    protected int getLayoutId() {
        return R.layout.activity_series_details;
    }

    @Override
    protected void initView() {
        mRootView = (LinearLayout) findViewById(R.id.ll_series_root_view);
        mShowHide = (TextView) findViewById(R.id.tv_show_hide);
        mContent = (ExpandableTextView) findViewById(R.id.et_movie_info);
        mTabLayout = (TabLayout) findViewById(R.id.tl_series_details_title);
        mSetList = (RecyclerView) findViewById(R.id.rv_series_details_staging);

        mVideoRootView = (FrameLayout) findViewById(R.id.fl_video_root_view);
        mVideoView = (MyVideoView) findViewById(R.id.vv_video_movie);
        mPlay = (CheckBox) findViewById(R.id.cb_play_pause);
        mFullScreen = (CheckBox) findViewById(R.id.cb_full_screen);
        mCurrentTime = (TextView) findViewById(R.id.tv_current_time);
        mTotalTime = (TextView) findViewById(R.id.tv_video_total_time);
        mVideoProgress = (SeekBar) findViewById(R.id.sb_video_progress);
        mCtroView = (FrameLayout) findViewById(R.id.fl_ctrl_view);

        mBack = (ImageView) findViewById(R.id.iv_video_lan_back);
        mVideoLanShare = (ImageView) findViewById(R.id.iv_video_lan_share);

        mNumAndTitle = (TextView) findViewById(R.id.tv_number_title);
        mTitle = (TextView) findViewById(R.id.tv_title);
        mWeekly = (TextView) findViewById(R.id.tv_weekly);
        mUpdateTo = (TextView) findViewById(R.id.tv_update_to);
        mTagName = (TextView) findViewById(R.id.tv_tag_name);
        mNumAndTitle = (TextView) findViewById(R.id.tv_number_title);

        mShare = (TextView) findViewById(R.id.tv_series_detail_bottom_share);
        mComment = (TextView) findViewById(R.id.tv_series_detail_bottom_comment);
        mCache = (TextView) findViewById(R.id.tv_series_detail_bottom_cache);


        //设置准备好了才进行播放
        mVideoView.setOnPreparedListener(this);

        //设置视频播放完成的监听
        mVideoView.setOnCompletionListener(this);
        //设置控制器点击事件
        mCtroView.setOnClickListener(this);
        //返回按钮
        mBack.setOnClickListener(this);
        //视频栏的视频分享
        mVideoLanShare.setOnClickListener(this);

        //设置LayoutManager
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        mSetList.setLayoutManager(layoutManager);

        //设置适配器
        mAdapter = new SeriesSetDetailsAdapter(this,
                getData());
        mSetList.setAdapter(mAdapter);

        mAdapter.setOnPlayVideoClickListener(this);

        //设置tabLayout的选择监听
        mTabLayout.setOnTabSelectedListener(this);

        //表示每个标签都保持自身宽度，一旦标签过多，给标题栏提供支持横向滑动的功能
        mTabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);

        //添加TabLayout分割线
        LinearLayout linearLayout = (LinearLayout) mTabLayout.getChildAt(0);
        linearLayout.setShowDividers(LinearLayout.SHOW_DIVIDER_MIDDLE);
        linearLayout.setDividerDrawable(ContextCompat.getDrawable(this,
                R.drawable.layout_divider_vertical));
        linearLayout.setDividerPadding(35);
        linearLayout.setMinimumHeight(20);

        //展开 收缩内容 监听
        mShowHide.setOnClickListener(this);
        //设置控制器点击事件
        mCtroView.setOnClickListener(this);
        //返回按钮
        mBack.setOnClickListener(this);
        //视频栏的视频分享
        mVideoLanShare.setOnClickListener(this);
        //分享
        mShare.setOnClickListener(this);
        //评论
        mComment.setOnClickListener(this);
        //缓存
        mCache.setOnClickListener(this);

        mPlay.setOnCheckedChangeListener(this);
        mFullScreen.setOnCheckedChangeListener(this);
        mVideoProgress.setOnSeekBarChangeListener(this);

        //隐藏视频控制器
        switchVideoCtroShow();

        //初始化分享布局PopupWindow
        mPopupShareMenu = new PopupShareMenu(this, mRootView, R.layout.layout_share);
    }

    private List<SetBean> getData() {
        List<SetBean> setBeanList = new ArrayList<>();
        return setBeanList;
    }


    /**
     * 请求网络数据 从服务器获取数据
     */
    public void requestDataFromServer() {
        //请求网络数据
        LogUtil.e(TAG, "requestDataFromServer: " + String.format(SERIES_DETAILS_PAGE_URL,
                seriesId));
        HttpUtils.requestGet(String.format(SERIES_DETAILS_PAGE_URL, seriesId),
                REQUEST_SERIES_DETAILS_DATA, this);
    }

    @Override
    protected void setupView() {
        Intent intent = getIntent();
        if (intent != null) {
            seriesId = intent.getStringExtra(SERIES_ID);
        }

        requestDataFromServer();//请求网络数据 从服务器获取数据
    }

    /**
     * 启动系列详情页Activity(启动活动的最佳写法,省去了传递参数key)
     *
     * @param context  调用这个Activity的context
     * @param seriesId 需要传递过来的seriesId的值
     */
    public static void acionStart(Context context, String seriesId) {
        Intent intent = new Intent(context, SeriesDetailsActivity.class);
        intent.putExtra(SERIES_ID, seriesId);
        context.startActivity(intent);
    }

    @Override
    public void onFinish(int from, String response) {
        if (from == REQUEST_SERIES_DETAILS_DATA) {
            Gson gson = new Gson();
            SetAllInfoBean setAllInfoBean = gson.fromJson(response, SetAllInfoBean.class);
            LogUtil.e(TAG, "onFinish: 系列详情页解析成功:" + setAllInfoBean.toString());
            if (setAllInfoBean.getMsg().equals(REQUEST_FROM_SERVER_OK)) {

                //请求数据成功   发送消息给主线程  更新适配器
                Message msg = Message.obtain();
                msg.what = LOAD_SERIES_DATA_FINISHED;
                msg.obj = setAllInfoBean;
                mHandler.sendMessage(msg);
            }
        }
    }

    @Override
    public void onError(Exception e) {
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_show_hide:
                switchContentShow();  //切换内容的显示全部和收缩
                break;
            case R.id.fl_ctrl_view:
                switchVideoCtroShow();
                break;
            case R.id.iv_video_lan_back:
                finish();
                break;
            case R.id.iv_video_lan_share:
                LogUtil.e(TAG, "onClick: 分享");
                //这里应该弹出PopupWindow
                mPopupShareMenu.showSharePopupWindow();
                break;
            case R.id.tv_series_detail_bottom_share:  //分享
                LogUtil.e(TAG, "onClick: 分享");
                mPopupShareMenu.showSharePopupWindow();
                break;
            case R.id.tv_series_detail_bottom_comment: //评论
                LogUtil.e(TAG, "onClick: 评论");
                break;
            case R.id.tv_series_detail_bottom_cache:   //缓存
                LogUtil.e(TAG, "onClick: 缓存");
                break;
        }
    }

    /**
     * 切换全屏,取消全屏
     * 全屏的时候会横屏,这时候Activity的生命周期,注意控制一下
     *
     * @param isChecked 全屏按钮(CheckBox)是否被选中
     */
    private void switchFullScreen(boolean isChecked) {
        if (isChecked) {
            //切换到全屏模式
            //添加一个全屏的标记
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
            //请求横屏
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

            //设置视频播放控件的布局的高度是match_parent
            LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) mVideoRootView
                    .getLayoutParams();
            //将默认的高度缓存下来
            mVideoHeight = layoutParams.height;
            layoutParams.height = FrameLayout.LayoutParams.MATCH_PARENT;
            mVideoRootView.setLayoutParams(layoutParams);
        } else {
            //切换到默认模式
            //清除全屏标记
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
            //请求纵屏
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

            //设置视频播放控件的布局的高度是200
            LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) mVideoRootView
                    .getLayoutParams();
            layoutParams.height = mVideoHeight;  //这里的单位是px
            mVideoRootView.setLayoutParams(layoutParams);
        }
    }

    /**
     * 切换播放暂停视频
     *
     * @param isChecked
     */
    private void switchPlayPauseVideo(boolean isChecked) {
        if (isChecked) {
            mVideoView.pause();

            //暂停的时候,不需要更新进度    这里为了节约资源的耗费
            mHandler.removeMessages(UPDATE_VIDEO_PROGRESS);
        } else {
            mVideoView.start();

            //继续播放的时候,继续1秒更新一次 时间
            mHandler.sendEmptyMessageDelayed(UPDATE_VIDEO_PROGRESS, 1000);
        }
    }

    /**
     * 切换内容的显示全部和收缩
     */
    private void switchContentShow() {
        boolean expandableStatus = mContent.getExpandableStatus();
        expandableStatus = !expandableStatus;

        //切换打开收缩按钮的状态
        if (expandableStatus) {
            mShowHide.setText("收起简介");
            //设置它的资源图片
            mShowHide.setCompoundDrawablesRelativeWithIntrinsicBounds(null, null, getResources()
                            .getDrawable(R.drawable.dropup),
                    null);
        } else {
            mShowHide.setText("查看全部");
            mShowHide.setCompoundDrawablesRelativeWithIntrinsicBounds(null, null, getResources()
                            .getDrawable(R.drawable.dropdown),
                    null);
        }

        //设置TextView
        mContent.setExpandable(expandableStatus);
    }


    //当TabLayout的标签选择状态改变时 调用此方法
    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        int position = tab.getPosition();
        LogUtil.e(TAG, "onTabSelected: " + position);
        CharSequence title = tab.getText();
        LogUtil.e(TAG, "onTabSelected: " + title);

        for (StagingSetBean stagingSetBean : setAllInfoBean.getSetData().getSetPostsList()) {
            if (stagingSetBean.getFromTo().equals(title)) {
                //用户选择的那一项的内容
                LogUtil.e(TAG, "onTabSelected: " + stagingSetBean.toString());
                //更新适配器
                mAdapter.updateData(stagingSetBean.getSetBeanList());
            }
        }
    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {

    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {

    }

    @Override
    public void playVideo(int position, SetBean setBean) {
        mVideoView.setVideoPath(setBean.getSourceLink());
        mNumAndTitle.setText(String.format("第%s集 %s", setBean.getNumber(), setBean.getTitle()));
    }

    @Override
    public void onPrepared(MediaPlayer mp) {
        Log.e(TAG, "onPrepared: ");
        //准备好了的监听    下面开始播放
        mVideoView.start();
        //发送消息通知Handler   当前时间进行更新    也就是播放进度进行更新   只发送了1次,这里
        mHandler.sendEmptyMessageDelayed(UPDATE_VIDEO_PROGRESS, 1000);

        //获取视频的总时长
        int duration = mp.getDuration();
        mVideoProgress.setMax(duration);

        //设置视频总时长
        mTotalTime.setText(DateFormat.format("mm:ss", duration));
    }

    /**
     * 更新当前播放视频 时间
     */
    private void updateCurrentTime() {
        //获取当前播放的视频的进度  再转换格式为00:00  分秒
        int currentPosition = mVideoView.getCurrentPosition();
        mCurrentTime.setText(DateFormat.format("mm:ss", currentPosition));

        //设置进度条当前的视频进度
        mVideoProgress.setProgress(currentPosition);

        //1秒之后又得更新时间进度
        mHandler.sendEmptyMessageDelayed(UPDATE_VIDEO_PROGRESS, 1000);
    }

    @Override
    public void onCompletion(MediaPlayer mp) {
        mPlay.setChecked(true);

        //播放结束  不再更新进度
        mHandler.removeMessages(UPDATE_VIDEO_PROGRESS);
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        switch (buttonView.getId()) {
            case R.id.cb_play_pause:
                switchPlayPauseVideo(isChecked);  //切换播放暂停视频
                break;
            case R.id.cb_full_screen:    //切换全屏,取消全屏
                switchFullScreen(isChecked);
                break;
        }
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        if (fromUser) {
            //如果是用户改变的,那么将视频进度移动到指定位置进行播放
            mVideoView.seekTo(progress);
        }
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {
    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
    }

    /**
     * 控制视频控制器的显示和隐藏
     */
    private void switchVideoCtroShow() {
        if (mCurrentTime.getVisibility() == View.VISIBLE) {
            mCurrentTime.setVisibility(View.INVISIBLE);
            mVideoProgress.setVisibility(View.INVISIBLE);
            mTotalTime.setVisibility(View.INVISIBLE);
            mPlay.setVisibility(View.INVISIBLE);
            mFullScreen.setVisibility(View.INVISIBLE);
        } else {
            mCurrentTime.setVisibility(View.VISIBLE);
            mVideoProgress.setVisibility(View.VISIBLE);
            mTotalTime.setVisibility(View.VISIBLE);
            mPlay.setVisibility(View.VISIBLE);
            mFullScreen.setVisibility(View.VISIBLE);
        }
    }
}
