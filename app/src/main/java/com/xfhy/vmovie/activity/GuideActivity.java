package com.xfhy.vmovie.activity;

import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.xfhy.vmovie.R;
import com.xfhy.vmovie.adapters.GuideAdapter;
import com.xfhy.vmovie.base.BaseActivity;
import com.xfhy.vmovie.fragments.GuideOneFragment;
import com.xfhy.vmovie.fragments.GuideThreeFragment;
import com.xfhy.vmovie.fragments.GuideTwoFragment;
import com.xfhy.vmovie.utils.LogUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * 导航页面
 *
 * @author xfhy
 *         create at 2017年6月15日11:24:11
 */
public class GuideActivity extends BaseActivity implements ViewPager.OnPageChangeListener {

    private static final String TAG = "MainActivity";
    private ViewPager mViewPager;
    private GuideAdapter mAdapter;
    /**
     * 引导页
     */
    private List<Fragment> mDataList;
    /**
     * 引导页下面的小点儿
     */
    private RadioGroup mIndicator;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_guide;
    }

    @Override
    protected void initView() {
        mViewPager = (ViewPager) findViewById(R.id.vp_glide);
        mIndicator = (RadioGroup) findViewById(R.id.rg_page);

        //给ViewPager设置适配器
        mAdapter = new GuideAdapter(getSupportFragmentManager(), getData());
        mViewPager.setAdapter(mAdapter);

        //监听器
        mViewPager.addOnPageChangeListener(this);

        //设置第一个默认为选中状态
        RadioButton childOne = (RadioButton) mIndicator.getChildAt(0);
        childOne.setChecked(true);
    }

    /**
     * 获取数据源  Fragment 的List集合
     *
     * @return
     */
    private List<Fragment> getData() {
        mDataList = new ArrayList<>();
        mDataList.add(GuideOneFragment.newInstance());
        mDataList.add(GuideTwoFragment.newInstance());
        mDataList.add(GuideThreeFragment.newInstance());
        return mDataList;
    }

    //positionOffset [0,1)
    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        //页面正在滑动
        LogUtil.d(TAG, "onPageScrolled: position:" + position + "    positionOffset:" +
                positionOffset);
    }

    @Override
    public void onPageSelected(int position) {
        //页面选中
        LogUtil.d(TAG, "onPageSelected: position:" + position);
        RadioButton childAt = (RadioButton) mIndicator.getChildAt(position);
        childAt.setChecked(true);
    }

    @Override
    public void onPageScrollStateChanged(int state) {
        //当滚动状态发生改变的时候
        LogUtil.d(TAG, "onPageScrollStateChanged: ");
    }
}
