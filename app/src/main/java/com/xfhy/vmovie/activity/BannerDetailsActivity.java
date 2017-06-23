package com.xfhy.vmovie.activity;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.xfhy.vmovie.R;
import com.xfhy.vmovie.base.BaseActivity;
import com.xfhy.vmovie.utils.LogUtil;
import com.xfhy.vmovie.utils.PopupShareMenu;

/**
 * banner详情页
 *
 * @author xfhy
 *         create at 2017年6月22日15:15:07
 */
public class BannerDetailsActivity extends BaseActivity implements View.OnClickListener {

    private static final String TAG = "BannerDetailsActivity";
    private static final String BANNER_WEB_URL = "url";
    private ImageView mBack;
    private ImageView mShare;
    private TextView mTitle;
    private LinearLayout mRootView;
    private String mUrl;
    private WebView mWebView;
    private WebSettings mWebViewSettings;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_banner_details;
    }

    @Override
    protected void initView() {
        mRootView = (LinearLayout) findViewById(R.id.ll_banner_detail_root_view);
        mWebView = (WebView) findViewById(R.id.wv_banner_detail_content);
        mBack = (ImageView) findViewById(R.id.iv_banner_details_back);
        mShare = (ImageView) findViewById(R.id.iv_banner_detail_share);
        mTitle = (TextView) findViewById(R.id.tv_banner_detail_title);

        mBack.setOnClickListener(this);
        mShare.setOnClickListener(this);
    }

    @Override
    protected void setupView() {
        Intent intent = getIntent();
        if (intent != null) {
            mUrl = intent.getStringExtra(BANNER_WEB_URL);
            mWebView.loadUrl(mUrl);

            mWebViewSettings = mWebView.getSettings();
            mWebViewSettings.setJavaScriptEnabled(true); //开启JS

            //可以查看加载进度,以及js调用监控的客户端
            mWebView.setWebChromeClient(new WebChromeClient() {
                @Override
                public void onReceivedTitle(WebView view, String title) {
                    super.onReceivedTitle(view, title);
                    mTitle.setText(title);
                }
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

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_banner_details_back:
                finish();
                break;
            case R.id.iv_banner_detail_share:
                PopupShareMenu popupShareMenu = new PopupShareMenu(this, mRootView, R.layout
                        .layout_share);
                popupShareMenu.showSharePopupWindow();
                break;
        }
    }

    /**
     * 启动Activity
     * @param url url
     */
    public static void actionStart(Context context, String url){
        Intent intent = new Intent(context, BannerDetailsActivity.class);
        intent.putExtra(BANNER_WEB_URL,url);
        context.startActivity(intent);
    }

}
