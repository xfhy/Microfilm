package com.xfhy.vmovie.activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Environment;
import android.os.StatFs;
import android.support.v7.app.AlertDialog;
import android.text.format.Formatter;
import android.util.Log;
import android.view.View;
import android.widget.ScrollView;
import android.widget.Toast;

import com.xfhy.vmovie.R;
import com.xfhy.vmovie.base.BaseActivity;
import com.xfhy.vmovie.utils.GlideCacheUtil;
import com.xfhy.vmovie.utils.PopupShareMenu;
import com.xfhy.vmovie.view.SettingNormalItem;
import com.xfhy.vmovie.view.SettingSwitchItem;

import java.io.File;

/**
 * 设置界面
 *
 * @author xfhy
 *         create at 2017年6月22日16:35:03
 */
public class SettingActivity extends BaseActivity implements View.OnClickListener {

    private static final String TAG = "SettingActivity";
    private SettingNormalItem mFeedback;
    private SettingNormalItem mClearCache;
    private SettingNormalItem mPlayConfig;
    private SettingSwitchItem mNoWifiDown;
    private SettingNormalItem mOfflineCache;
    private SettingNormalItem mScore;
    private SettingNormalItem mUpdate;
    private SettingNormalItem mShare;
    private SettingNormalItem mSubmission;
    private SettingNormalItem mAttention;
    private SettingNormalItem mJoinQQGroup;
    private SettingNormalItem mAbout;
    private SettingNormalItem mStatement;
    private ScrollView mRootView;
    private PopupShareMenu popupShareMenu;
    private GlideCacheUtil glideCacheUtil;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_setting;
    }

    @Override
    protected void initView() {
        mRootView = (ScrollView) findViewById(R.id.sv_setting_root_view);
        mFeedback = (SettingNormalItem) findViewById(R.id.sni_customer_feedback);
        mClearCache = (SettingNormalItem) findViewById(R.id.sni_clear_cache);
        mPlayConfig = (SettingNormalItem) findViewById(R.id.sni_play_config);
        mNoWifiDown = (SettingSwitchItem) findViewById(R.id.ssi_nowifi_download);
        mOfflineCache = (SettingNormalItem) findViewById(R.id.sni_cffline_cache);
        mScore = (SettingNormalItem) findViewById(R.id.sni_score);
        mUpdate = (SettingNormalItem) findViewById(R.id.sni_update_app);
        mShare = (SettingNormalItem) findViewById(R.id.sni_share);
        mSubmission = (SettingNormalItem) findViewById(R.id.sni_submission);
        mAttention = (SettingNormalItem) findViewById(R.id.sni_attention);
        mJoinQQGroup = (SettingNormalItem) findViewById(R.id.sni_join_qq_group);
        mAbout = (SettingNormalItem) findViewById(R.id.sni_about);
        mStatement = (SettingNormalItem) findViewById(R.id.sni_statement);

        mFeedback.setOnClickListener(this);
        mClearCache.setOnClickListener(this);
        mPlayConfig.setOnClickListener(this);
        mNoWifiDown.setOnClickListener(this);
        mOfflineCache.setOnClickListener(this);
        mScore.setOnClickListener(this);
        mUpdate.setOnClickListener(this);
        mShare.setOnClickListener(this);
        mSubmission.setOnClickListener(this);
        mAttention.setOnClickListener(this);
        mJoinQQGroup.setOnClickListener(this);
        mAbout.setOnClickListener(this);
        mStatement.setOnClickListener(this);

        initShare();
        initImageCache();
        initAppVersion();
        initOfflineCache();

    }

    /**
     * 初始化离线缓存
     */
    private void initOfflineCache() {
        String memFree = getMemFree();
        Log.e(TAG, "initOfflineCache: " + memFree);
    }


    /**
     * 初始化分享
     */
    private void initShare() {
        popupShareMenu = new PopupShareMenu(this, mRootView, R.layout
                .layout_share);
    }

    /**
     * 初始化版本
     */
    private void initAppVersion() {
        int versionCode = getVersionCode();
        mUpdate.setInfo(versionCode + ".0.7");
    }


    /**
     * 初始化图片缓存
     */
    private void initImageCache() {
        glideCacheUtil = GlideCacheUtil.getInstance();
        mClearCache.setInfo(glideCacheUtil.getCacheSize(this)+"");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.sni_customer_feedback:
                break;
            case R.id.sni_clear_cache:
                showClearCacheSuccess();
                break;
            case R.id.sni_play_config:
                break;
            case R.id.ssi_nowifi_download:
                break;
            case R.id.sni_cffline_cache:
                showCachePathConfig();
                break;
            case R.id.sni_score:
                break;
            case R.id.sni_update_app:
                break;
            case R.id.sni_share:
                popupShareMenu.showSharePopupWindow();
                break;
            case R.id.sni_submission:
                break;
            case R.id.sni_attention:
                break;
            case R.id.sni_join_qq_group:
                break;
            case R.id.sni_about:
                break;
            case R.id.sni_statement:
                break;
        }
    }

    /**
     * 弹出对话框告诉用户清除缓存成功
     */
    private void showClearCacheSuccess() {
        glideCacheUtil.clearImageAllCache(this);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle("提示");
        builder.setMessage("清除成功!");
        builder.setNegativeButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });

        AlertDialog alertDialog = builder.create();
        alertDialog.show();

        mClearCache.setInfo(glideCacheUtil.getCacheSize(this)+"");
    }

    /**
     * 判断sd卡是否可用
     */
    public static boolean isExternalStorageAvailable() {
        return Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED);
    }

    /**
     * 获取手机内部可用存储空间
     *
     * @return 以M, G为单位的容量
     */
    @SuppressLint("NewApi")
    private String getMemFree() {
        StatFs fs = new StatFs(Environment.getDataDirectory().getPath());
        return Formatter.formatFileSize(this, (fs.getAvailableBytes()));
    }

    /**
     * 获取外部存储可用空间大小
     *
     * @return
     */
    @SuppressLint("NewApi")
    public String readSDCard() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            File sdcardDir = Environment.getExternalStorageDirectory();
            StatFs sf = new StatFs(sdcardDir.getPath());
            //long availableBlocksLong = sf.getAvailableBlocksLong();
            long availableBytes = sf.getAvailableBytes();
            return Formatter.formatFileSize(this, availableBytes);
        }
        return "无";
    }


    /**
     * 缓存路径配置
     */
    private void showCachePathConfig() {
        //TODO 这里时间不够了,其实应该存入SP中  下次回显出来
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        final String[] single_list = {"手机存储:" + getMemFree(), "手机外部存储:" +
                readSDCard()};
        builder.setSingleChoiceItems(single_list, 0, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                String str = single_list[which];
                Toast.makeText(SettingActivity.this, str + "被点击了", Toast.LENGTH_SHORT).show();
                dialog.dismiss();
                if (which == 0) {
                    mOfflineCache.setInfo("手机存储");
                } else if (which == 1) {
                    mOfflineCache.setInfo("手机外部存储");
                }
            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    /**
     * 获取本地版本号
     *
     * @return 非0则获取成功
     */
    private int getVersionCode() {
        PackageManager packageManager = getPackageManager();
        try {
            PackageInfo packageInfo = packageManager.getPackageInfo(getPackageName(), 0);
            return packageInfo.versionCode;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

}
