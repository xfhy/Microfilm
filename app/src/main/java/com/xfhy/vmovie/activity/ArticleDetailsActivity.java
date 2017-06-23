package com.xfhy.vmovie.activity;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.xfhy.vmovie.R;
import com.xfhy.vmovie.base.BaseActivity;
import com.xfhy.vmovie.constants.NetworkInterface;
import com.xfhy.vmovie.utils.LogUtil;
import com.xfhy.vmovie.utils.PopupShareMenu;
import com.xfhy.vmovie.view.MyVideoView;

import java.util.Random;

/**
 * 幕后文章详情页面
 *
 * @author xfhy
 *         create at 2017年6月20日21:51:25
 */
public class ArticleDetailsActivity extends BaseActivity implements View.OnClickListener {

    private static final String TAG = "ArticleDetailsActivity";
    /**
     * 启动该Activity需要的参数postId
     */
    public static final String POST_ID = "postid";
    /**
     * 启动该Activity需要的参数like_num
     */
    private static final String MOVIE_LIKE_NUM = "like_num";
    /**
     * 启动该Activity需要的参数share_num
     */
    private static final String MOVIE_SHARE_NUM = "share_num";
    private WebView mWebView;
    private WebSettings mWebViewSettings;
    private MyVideoView mVideoView;
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
    private PopupShareMenu mPopupShareMenu;
    private LinearLayout mRootView;

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
        return R.layout.activity_article_details;
    }

    @Override
    protected void initView() {
        mRootView = (LinearLayout) findViewById(R.id.ll_article_details_root_view);
        mWebView = (WebView) findViewById(R.id.wv_article_detail);
        mBack = (ImageView) findViewById(R.id.iv_video_lan_back);
        mVideoLanShare = (ImageView) findViewById(R.id.iv_video_lan_share);
        mLike = (TextView) findViewById(R.id.tv_details_like_num);
        mShare = (TextView) findViewById(R.id.tv_article_detail_bottom_share);
        mComment = (TextView) findViewById(R.id.tv_article_detail_bottom_comment);


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

        //初始化分享布局PopupWindow
        mPopupShareMenu = new PopupShareMenu(this, mRootView, R.layout.layout_share);
    }

    @Override
    protected void setupView() {
//获取其他界面传递过来的值
        Intent intent = getIntent();
        if (intent != null) {
            String postId = intent.getStringExtra(POST_ID);
            String likeNum = intent.getStringExtra(MOVIE_LIKE_NUM);
            String shareNum = intent.getStringExtra(MOVIE_SHARE_NUM);

            mLike.setText(likeNum);
            mShare.setText(shareNum);

            //由于评论没有服务端的接口  所以这里用了一个随机数
            Random random = new Random();
            mComment.setText(random.nextInt(100) + "");

            mWebView.loadUrl(String.format(NetworkInterface.BEHIND_ARTICLE_DETAILS_URL, postId));

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
     * 启动文章详情页Activity(启动活动的最佳写法,省去了传递参数key)
     *
     * @param context  调用这个Activity的context
     * @param postId   需要传递过来的postid的值
     * @param likeNum  喜欢的数量
     * @param shareNum 分享的数量
     */
    public static void acionStart(Context context, String postId, String likeNum, String shareNum) {
        Intent intent = new Intent(context, ArticleDetailsActivity.class);
        intent.putExtra(POST_ID, postId);
        intent.putExtra(MOVIE_LIKE_NUM, likeNum);
        intent.putExtra(MOVIE_SHARE_NUM, shareNum);
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
    public void onClick(View v) {
        switch (v.getId()) {
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
            case R.id.tv_article_detail_bottom_share:  //分享
                LogUtil.e(TAG, "onClick: 分享");
                mPopupShareMenu.showSharePopupWindow();
                break;
            case R.id.tv_article_detail_bottom_comment: //评论
                LogUtil.e(TAG, "onClick: 评论");
                break;
        }
    }
}
