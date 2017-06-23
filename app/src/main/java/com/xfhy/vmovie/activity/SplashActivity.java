package com.xfhy.vmovie.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.xfhy.vmovie.R;
import com.xfhy.vmovie.base.BaseActivity;
import com.xfhy.vmovie.constants.SharedParams;
import com.xfhy.vmovie.utils.SpUtil;

/**
 * 闪屏页
 *
 * @author xfhy
 *         create at 2017年6月15日10:59:29
 */
public class SplashActivity extends BaseActivity implements Animation.AnimationListener {

    private ImageView mBgImage;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_splash;
    }

    @Override
    protected void initView() {
        mBgImage = (ImageView) findViewById(R.id.iv_splash_bg);
        Animation animation = AnimationUtils.loadAnimation(this, R.anim.splash_scale);
        mBgImage.startAnimation(animation);

        //设置动画监听  方便在动画结束时进入主界面
        animation.setAnimationListener(this);
    }

    @Override
    public void onAnimationStart(Animation animation) {

    }

    @Override
    public void onAnimationEnd(Animation animation) {
        //动画结束时做页面跳转
        //决定需要跳转到导航页还是主界面   需要使用数据持久化技术
        boolean isFirstCome = SpUtil.getBoolan(this, SharedParams.FIRST_COME, true);
        Intent intent = new Intent();
        if (isFirstCome) {
            //第一次用,进入导航界面
            intent.setClass(this, GuideActivity.class);
            //设置isFirstCome  SP中的值为false
            SpUtil.putBoolean(this, SharedParams.FIRST_COME, false);
        } else {
            //进入主界面
            intent.setClass(this, MainActivity.class);
        }

        startActivity(intent);

        //必须结束这个页面,不能回退到欢迎页
        finish();
    }

    @Override
    public void onAnimationRepeat(Animation animation) {

    }
}
