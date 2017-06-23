package com.xfhy.vmovie.activity;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import com.google.gson.Gson;
import com.xfhy.vmovie.R;
import com.xfhy.vmovie.base.BaseActivity;
import com.xfhy.vmovie.constants.NetworkInterface;
import com.xfhy.vmovie.listener.HttpCallbackListener;
import com.xfhy.vmovie.model.latest.details.VideoContent;
import com.xfhy.vmovie.model.latest.details.VideoData;
import com.xfhy.vmovie.model.latest.details.VideoInfo;
import com.xfhy.vmovie.model.latest.details.VideoObject;
import com.xfhy.vmovie.model.latest.details.VideoTopInfo;
import com.xfhy.vmovie.utils.HttpUtils;
import com.xfhy.vmovie.utils.LogUtil;
import com.xfhy.vmovie.utils.PopupShareMenu;
import com.xfhy.vmovie.view.MyVideoView;


import static com.xfhy.vmovie.constants.NetworkInterface.MOVIE_DETAILS;
import static com.xfhy.vmovie.constants.NetworkInterface.REQUEST_FROM_SERVER_OK;


/**
 * 电影详情页
 *
 * @author xfhy
 *         create at 2017年6月17日20:42:53
 */
public class MovieDetailActivity extends BaseActivity implements CompoundButton
        .OnCheckedChangeListener, SeekBar.OnSeekBarChangeListener, Handler.Callback, MediaPlayer
        .OnPreparedListener, MediaPlayer.OnCompletionListener, View.OnClickListener, HttpCallbackListener {

    private static final String TAG = "MovieDetailActivity";
    /**
     * 启动该Activity需要的参数postId
     */
    public static final String POST_ID = "postid";
    private static final int REQUEST_MOVIE_DETAILS_DATA = 1000;
    private static final int LOAD_MOVIE_DATA_FINISHED = 1001;
    private WebView mWebView;
    private WebSettings mWebViewSettings;
    private MyVideoView mVideoView;
    /**
     * 控制播放/暂停
     */
    private CheckBox mPlay;
    /**
     * 全屏/退出全屏
     */
    private CheckBox mFullScreen;
    /**
     * 当前视频进度
     */
    private TextView mCurrentTime;
    /**
     * 视频的全长
     */
    private TextView mTotalTime;
    /**
     * 视频进度条
     */
    private SeekBar mVideoProgress;
    private Handler mHandler = new Handler(this);
    /**
     * 更新进度
     */
    private static final int UPDATE_VIDEO_PROGRESS = 1000;
    /**
     * 视频控件的rootView
     */
    private FrameLayout mVideoRootView;
    /**
     * 视频高度
     */
    private int mVideoHeight;
    /**
     * 视频播放的控制器布局
     */
    private FrameLayout mCtroView;
    /**
     * 返回按钮
     */
    private ImageView mBack;
    /**
     * 视频播放右上角的分享按钮
     */
    private ImageView mVideoLanShare;
    /**
     * 喜欢该视频的用户数量
     */
    private TextView mLike;
    /**
     * 该视频被分享的次数
     */
    private TextView mShare;
    /**
     * 该视频的所有评论
     */
    private TextView mComment;
    /**
     * 缓存按钮
     */
    private TextView mCache;
    /**
     * 这个Activity的根布局
     */
    private LinearLayout mRootView;
    private PopupShareMenu mPopupShareMenu;

    @Override
    protected void onResume() {
        if (mWebView != null) {
            //激活WebView为活跃状态，能正常执行网页的响应
            mWebView.onResume();
        }
        super.onResume();

    }

    @Override
    protected void onPause() {
        super.onPause();
        //当页面被失去焦点被切换到后台不可见状态，需要执行onPause
        //通过onPause动作通知内核暂停所有的动作，比如DOM的解析、plugin的执行、JavaScript执行。
        if (mWebView != null) {
            mWebView.onPause();
        }
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_movie_detail;
    }

    @Override
    protected void initView() {
        mRootView = (LinearLayout) findViewById(R.id.ll_movie_detail_root_view);
        mWebView = (WebView) findViewById(R.id.wv_movie_detail);

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
        mLike = (TextView) findViewById(R.id.tv_details_like_num);
        mShare = (TextView) findViewById(R.id.tv_movie_detail_bottom_share);
        mComment = (TextView) findViewById(R.id.tv_movie_detail_bottom_comment);
        mCache = (TextView) findViewById(R.id.tv_movie_detail_bottom_cache);


        //设置控制器点击事件
        mCtroView.setOnClickListener(this);
        //返回按钮
        mBack.setOnClickListener(this);
        //视频栏的视频分享
        mVideoLanShare.setOnClickListener(this);
        //喜欢
        mLike.setOnClickListener(this);
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

    @Override
    protected void setupView() {

        //获取其他界面传递过来的值
        Intent intent = getIntent();
        if (intent != null) {
            String postId = intent.getStringExtra(POST_ID);

            //请求网络数据
            requestDataFromServer(postId);

            mWebView.loadUrl(String.format(NetworkInterface.LATEST_MOVIE_ITEM_DETAILS_URL, postId));

            mWebViewSettings = mWebView.getSettings();
            mWebViewSettings.setJavaScriptEnabled(true); //开启JS

            //可以查看加载进度,以及js调用监控的客户端
            mWebView.setWebChromeClient(new WebChromeClient() {
            });

            //为WebView设置自己的浏览客户端
            mWebView.setWebViewClient(new WebViewClient() {
                //覆写shouldOverrideUrlLoading方法,使得打开网页时不调用系统浏览器,而是在本View中显示
                //This method was deprecated in API level 24.
                //Use shouldOverrideUrlLoading(WebView, WebResourceRequest) instead.
                @Override
                public boolean shouldOverrideUrlLoading(WebView view, String url) {
                    view.loadUrl(url);
                    LogUtil.e(TAG, "shouldOverrideUrlLoading: ");
                    return true;
                }
            });
        }
    }


    /**
     * 请求网络数据 从服务器获取数据
     */
    public void requestDataFromServer(String postId) {
        //请求网络数据
        HttpUtils.requestGet(String.format(MOVIE_DETAILS, postId),
                REQUEST_MOVIE_DETAILS_DATA, this);
    }
    /**
     * 启动电影详情页Activity(启动活动的最佳写法,省去了传递参数key)
     *
     * @param context  调用这个Activity的context
     * @param postId   需要传递过来的postid的值
     */
    public static void acionStart(Context context, String postId) {
        Intent intent = new Intent(context, MovieDetailActivity.class);
        intent.putExtra(POST_ID, postId);
        context.startActivity(intent);
    }

    @Override
    protected void onDestroy() {
        destroyWebView();
        super.onDestroy();
    }

    /**
     * 销毁WebView
     */
    private void destroyWebView() {
        /*
        注意事项：如何避免WebView内存泄露？
        在 Activity 销毁（ WebView ）的时候，先让 WebView 加载null内容，然后移除 WebView，再销毁 WebView，最后置空。
         */
        if (mWebView != null) {
            mWebView.loadDataWithBaseURL(null, "", "text/html", "utf-8", null);
            mWebView.clearHistory();

            ((ViewGroup) mWebView.getParent()).removeView(mWebView);
            mWebView.destroy();
            mWebView = null;
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        Log.e(TAG, "onSaveInstanceState: 我在保存临时数据");
        outState.putInt("current", mVideoView.getCurrentPosition());
        super.onSaveInstanceState(outState);
    }

    /**
     * 播放完成时调用此方法
     *
     * @param mp
     */
    @Override
    public void onCompletion(MediaPlayer mp) {
        mPlay.setChecked(true);

        //播放结束  不再更新进度
        mHandler.removeMessages(UPDATE_VIDEO_PROGRESS);
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
    public boolean handleMessage(Message msg) {
        switch (msg.what) {
            case UPDATE_VIDEO_PROGRESS:
                updateCurrentTime();   //更新当前时间
                break;
            case LOAD_MOVIE_DATA_FINISHED:
                Log.e(TAG, "handleMessage: 加载完成");
                VideoObject videoObject = (VideoObject) msg.obj;
                analyServerData(videoObject);  //处理服务器返回的信息
                break;
        }
        return true;
    }

    /**
     * 处理服务器返回的信息
     * @param videoObject
     */
    private void analyServerData(VideoObject videoObject) {
        //1, 设置播放地址     支持本地和网络的    如果是网络的,记得加权限
        VideoData data = videoObject.getData();
        VideoContent content = data.getContent();
        VideoTopInfo videoTopInfo = content.getVideoTopInfoList().get(0);
        VideoInfo videoInfo = videoTopInfo.getVideoInfoList().get(1);
        String videoUrl = videoInfo.getVideoUrl();


        Log.e(TAG, "analyServerData: 播放的视频地址"+videoUrl);
        Log.e(TAG, "analyServerData: data.getCountLike()"+data.getCountLike());
        Log.e(TAG, "analyServerData: data.getCountShare()"+data.getCountShare());
        Log.e(TAG, "analyServerData: data.getCountComment()"+data.getCountComment());

        //开始播放视频
        mVideoView.setVideoPath(videoUrl);   //这是网络播放
        //设置准备好了才进行播放
        mVideoView.setOnPreparedListener(this);
        //设置视频播放完成的监听
        mVideoView.setOnCompletionListener(this);

        //数量
        mLike.setText(data.getCountLike());
        mShare.setText(data.getCountShare());
        mComment.setText(data.getCountComment());
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

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
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
            case R.id.tv_details_like_num:  //喜欢
                LogUtil.e(TAG, "onClick: 喜欢");
                break;
            case R.id.tv_movie_detail_bottom_share:  //分享
                LogUtil.e(TAG, "onClick: 分享");
                mPopupShareMenu.showSharePopupWindow();
                break;
            case R.id.tv_movie_detail_bottom_comment: //评论
                LogUtil.e(TAG, "onClick: 评论");
                break;
            case R.id.tv_movie_detail_bottom_cache:   //缓存
                LogUtil.e(TAG, "onClick: 缓存");
                break;
        }
    }


    @Override
    public void onFinish(int from, String response) {
        if (from == REQUEST_MOVIE_DETAILS_DATA) {
            Gson gson = new Gson();
            VideoObject videoObject = gson.fromJson(response, VideoObject.class);
            LogUtil.e(TAG, "onFinish: 电影详情页解析成功:" + videoObject.toString());
            if (videoObject.getMsg().equalsIgnoreCase(REQUEST_FROM_SERVER_OK)) {

                //请求数据成功   发送消息给主线程  更新适配器
                Message msg = Message.obtain();
                msg.what = LOAD_MOVIE_DATA_FINISHED;
                msg.obj = videoObject;
                mHandler.sendMessage(msg);
            }
        }
    }

    @Override
    public void onError(Exception e) {

    }
}
