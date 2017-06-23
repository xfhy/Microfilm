package com.xfhy.vmovie.activity;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.support.annotation.IdRes;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.KeyEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.xfhy.vmovie.R;
import com.xfhy.vmovie.base.BaseActivity;
import com.xfhy.vmovie.fragments.BehindFragment;
import com.xfhy.vmovie.fragments.HomePageFragment;
import com.xfhy.vmovie.fragments.SeriesFragment;

import java.util.Timer;
import java.util.TimerTask;

/**
 * 主界面
 *
 * @author xfhy
 *         create at 2017年6月15日11:25:33
 */
public class MainActivity extends BaseActivity implements RadioGroup.OnCheckedChangeListener,
        View.OnClickListener, Animator.AnimatorListener {

    private static final String TAG = "MainActivity";
    /**
     * 首页 fragment
     */
    private HomePageFragment mHomePage = null;
    /**
     * 系列 fragment
     */
    private SeriesFragment mSeriesPage = null;
    /**
     * 幕后 fragment
     */
    private BehindFragment mBehindPage = null;
    /**
     * 控制器(首页,系列,幕后)
     */
    private RadioGroup mController;
    /**
     * 菜单的根布局
     */
    private FrameLayout mSlideMenu;
    /**
     * 关闭菜单按钮
     */
    private ImageView mCloseMenu;
    /**
     * 首页选项
     */
    private RadioButton mHomeController;
    /**
     * 系列选项
     */
    private RadioButton mSeriesController;
    /**
     * 幕后选项
     */
    private RadioButton mBehindController;
    /**
     * 菜单的布局的最下面的半透明背景
     */
    private View mMenuBg;
    /**
     * 标志位  是否退出
     */
    private boolean isExit;
    private ImageView mLogin;
    private ImageView mSetting;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView() {
        //1, 获取Fragment管理者对象
        FragmentManager fragmentManager = getSupportFragmentManager();
        //2, 开启事务
        FragmentTransaction transaction = fragmentManager.beginTransaction();

        //3, 执行动作
        /*
         下面的逻辑可以做一下简单优化:
         ①一来就全部加载实例化了所有的fragment,这无疑是浪费流量啊,
         而且有些东西用户并不想看,但是那些内容已经被加载出来了.

         我的想法:当点击RadioGroup中的某一个选项后再进行相关模块的加载

         ②一般来说,fragment一般不是new出来的,不了解的话,可看看http://blog.csdn.net/jo__yang/article/details/49281995
          */
        mHomePage = HomePageFragment.newInstance();
        mSeriesPage = SeriesFragment.newInstance();
        mBehindPage = BehindFragment.newInstance();
        //将页面全部添加
        transaction.add(R.id.fl_main_container, mHomePage);
        transaction.add(R.id.fl_main_container, mSeriesPage);
        transaction.add(R.id.fl_main_container, mBehindPage);

        //将页面全部隐藏
        transaction.hide(mHomePage).hide(mSeriesPage).hide(mBehindPage);

        //4, 提交事务
        transaction.commit();

        //初始化控件
        mCloseMenu = (ImageView) findViewById(R.id.iv_main_close_menu);
        mController = (RadioGroup) findViewById(R.id.rg_main_controller);
        mSlideMenu = (FrameLayout) findViewById(R.id.slide_menu);
        mHomeController = (RadioButton) findViewById(R.id.rb_slide_menu_home);
        mSeriesController = (RadioButton) findViewById(R.id.rb_slide_menu_series);
        mBehindController = (RadioButton) findViewById(R.id.rb_slide_menu_behind);
        mMenuBg = findViewById(R.id.view_slide_menu_bg);
        mLogin = (ImageView) findViewById(R.id.iv_avatar);
        mSetting = (ImageView) findViewById(R.id.iv_slide_menu_setting);


        //设置RadioGroup选项状态改变事件监听
        mController.setOnCheckedChangeListener(this);
        mCloseMenu.setOnClickListener(this);
        mHomeController.setChecked(true);  //默认选中首页
        mHomeController.setOnClickListener(this);
        mSeriesController.setOnClickListener(this);
        mBehindController.setOnClickListener(this);
        mMenuBg.setOnClickListener(this);
        mLogin.setOnClickListener(this);
        mSetting.setOnClickListener(this);
    }

    @Override
    public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
        //1, 获取Fragment管理者对象
        FragmentManager fragmentManager = getSupportFragmentManager();
        //2, 开启事务
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        switch (checkedId) {
            case R.id.rb_slide_menu_home:
                transaction.show(mHomePage).hide(mSeriesPage).hide(mBehindPage);
                break;
            case R.id.rb_slide_menu_series:
                transaction.show(mSeriesPage).hide(mHomePage).hide(mBehindPage);
                break;
            case R.id.rb_slide_menu_behind:
                transaction.show(mBehindPage).hide(mHomePage).hide(mSeriesPage);
                break;
        }
        //4, 提交事务
        transaction.commit();

        //选中改变,直接隐藏菜单
        closeSlideMenu();
    }

    /**
     * 显示菜单
     */
    public void openSlideMenu() {
        //让菜单显示出来
        mSlideMenu.setVisibility(View.VISIBLE);

        //添加动画    关闭按钮的放大动画    控制按钮的顺序显示动画
        ObjectAnimator closeMenuScaleX = ObjectAnimator.ofFloat(mCloseMenu, "scaleX", 0, 0.8f, 1,
                1.2f,
                1.0f);
        ObjectAnimator closeMenuScaleY = ObjectAnimator.ofFloat(mCloseMenu, "scaleY", 0, 0.8f, 1,
                1.2f,
                1.0f);
        //一起播放属性动画
        AnimatorSet closeMenuAnimSet = new AnimatorSet();
        closeMenuAnimSet.play(closeMenuScaleX).with(closeMenuScaleY);
        closeMenuAnimSet.setDuration(300);
        closeMenuAnimSet.start();

        //让菜单界面的三个RadioButton顺序入场
        Animation homeAnim = AnimationUtils.loadAnimation(this, R.anim.slide_menu_home_in);
        Animation seriesAnim = AnimationUtils.loadAnimation(this, R.anim.slide_menu_series_in);
        Animation behindAnim = AnimationUtils.loadAnimation(this, R.anim.slide_menu_behind_in);
        mHomeController.startAnimation(homeAnim);
        mSeriesController.startAnimation(seriesAnim);
        mBehindController.startAnimation(behindAnim);
    }

    /**
     * 隐藏菜单
     */
    public void closeSlideMenu() {
        mSlideMenu.setVisibility(View.INVISIBLE);
    }

    /**
     * 隐藏菜单
     */
    public void closeMenuWithAnim() {
        //添加动画   关闭菜单按钮的缩小动画
        ObjectAnimator closeMenuScaleX = ObjectAnimator.ofFloat(mCloseMenu, "scaleX", 1, 1.2f, 1,
                0.8f, 0);
        ObjectAnimator closeMenuScaleY = ObjectAnimator.ofFloat(mCloseMenu, "scaleY", 1, 1.2f, 1,
                0.8f, 0);
        AnimatorSet closeMenuAnimSet = new AnimatorSet();
        closeMenuAnimSet.play(closeMenuScaleX).with(closeMenuScaleY);
        closeMenuAnimSet.setDuration(300);
        closeMenuAnimSet.start();

        //设置动画监听   当播放完动画时才隐藏菜单
        closeMenuAnimSet.addListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_main_close_menu:   //隐藏菜单
                closeMenuWithAnim();
                break;
            case R.id.rb_slide_menu_home:   //点击首页,幕后,系列都能关闭菜单
            case R.id.rb_slide_menu_series:
            case R.id.rb_slide_menu_behind:
                closeMenuWithAnim();
                break;
            case R.id.view_slide_menu_bg:
                //这是菜单的背景,把点击事件消费掉,可避免菜单显示时下面的fragment还可以点击
                break;
            case R.id.iv_avatar:   //登录
                Intent intent = new Intent(this,LoginActivity.class);
                startActivity(intent);
                break;
            case R.id.iv_slide_menu_setting:
                Intent intentSetting = new Intent(this, SettingActivity.class);
                startActivity(intentSetting);
                break;
        }
    }

    @Override
    public void onAnimationStart(Animator animation) {
    }

    @Override
    public void onAnimationEnd(Animator animation) {
        closeSlideMenu();
    }

    @Override
    public void onAnimationCancel(Animator animation) {
    }

    @Override
    public void onAnimationRepeat(Animator animation) {
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        //按下键是返回键
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (!isExit) {
                Toast.makeText(this, "再按一次退出", Toast.LENGTH_SHORT).show();
                isExit = true;

                Timer timer = new Timer();
                //定时器  如果2000毫秒之后没按,则重新设置为false  表示用户不想退出
                TimerTask timerTask = new TimerTask() {
                    @Override
                    public void run() {
                        //还原状态
                        isExit = false;
                    }
                };
                //2000毫秒之后再执行
                timer.schedule(timerTask, 2000);

                return true;
            }
        }

        return super.onKeyDown(keyCode, event);
    }

}
