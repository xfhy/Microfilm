package com.xfhy.vmovie.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.xfhy.vmovie.R;
import com.xfhy.vmovie.activity.MainActivity;
import com.xfhy.vmovie.activity.SearchActivity;
import com.xfhy.vmovie.adapters.HomeSwitchPagerAdapter;
import com.xfhy.vmovie.base.BaseFragment;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by xfhy on 2017/6/15.
 * 主界面 Fragment
 */

public class HomePageFragment extends BaseFragment implements View.OnClickListener, ViewPager
        .OnPageChangeListener {
    private static final String TAG = "HomePageFragment";
    private ImageView mOpenSlideMenu;
    private ImageView mSearch;
    private TextView mLatestTitle;
    private TextView mChannelTitle;
    private ViewPager mViewPager;
    private LinearLayout mRootView;
    private View mViewIndicator;
    private HomeSwitchPagerAdapter mAdapter;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_main_home;
    }

    @Override
    protected void initView() {
        mOpenSlideMenu = (ImageView) findViewById(R.id.iv_open_slide_menu);
        mSearch = (ImageView) findViewById(R.id.iv_search);
        mLatestTitle = (TextView) findViewById(R.id.tv_latest);
        mChannelTitle = (TextView) findViewById(R.id.tv_channel);
        mViewPager = (ViewPager) findViewById(R.id.vp_home_main_content);
        mRootView = (LinearLayout) findViewById(R.id.fragment_main_home_root_view);
        mViewIndicator = findViewById(R.id.view_indicator);

        //菜单监听
        mOpenSlideMenu.setOnClickListener(this);
        mSearch.setOnClickListener(this);

        //设置适配器
        mAdapter = new HomeSwitchPagerAdapter(getActivity()
                .getSupportFragmentManager(), getData());
        mViewPager.setAdapter(mAdapter);

        //设置ViewPager的滑动监听
        mViewPager.addOnPageChangeListener(this);

        //设置标题的点击事件
        mLatestTitle.setOnClickListener(this);
        mChannelTitle.setOnClickListener(this);
    }

    private List<Fragment> getData() {
        List<Fragment> dataList = new ArrayList<>();
        //将最新电影列表fragment和频道fragment添加进ViewPager里
        LatestMovieFragment latestMovieFragment = LatestMovieFragment.newInstance();
        MovieChannelFragment movieChannelFragment = MovieChannelFragment.newInstance();
        dataList.add(latestMovieFragment);
        dataList.add(movieChannelFragment);
        return dataList;
    }

    /**
     * 请调用此方法实例化fragment
     *
     * @return
     */
    public static HomePageFragment newInstance() {

        Bundle args = new Bundle();

        HomePageFragment fragment = new HomePageFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onClick(View v) {
        MainActivity activity = (MainActivity) getActivity();
        switch (v.getId()) {
            case R.id.iv_open_slide_menu:
                activity.openSlideMenu();  //显示菜单
                break;
            case R.id.tv_latest:
                //将指定位置的Item滚动到屏幕中央
                mViewPager.setCurrentItem(0);
                break;
            case R.id.tv_channel:
                mViewPager.setCurrentItem(1);
                break;
            case R.id.iv_search:
                //打开搜索Activity
                Intent intent = new Intent(getActivity(), SearchActivity.class);
                getActivity().startActivity(intent);
                break;
        }
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        FrameLayout.LayoutParams latestLayoutParams = (FrameLayout.LayoutParams) mLatestTitle
                .getLayoutParams();
        FrameLayout.LayoutParams hotLayoutParams = (FrameLayout.LayoutParams) mChannelTitle
                .getLayoutParams();

        //通过属性动画将mIndicator移动过去
        int width = hotLayoutParams.leftMargin - latestLayoutParams.leftMargin;
        mViewIndicator.setTranslationX(width * positionOffset + position * width);
    }

    @Override
    public void onPageSelected(int position) {
        //选择了界面之后,需要切换标题的颜色
        switch (position) {
            case 0:
                mLatestTitle.setTextColor(getResources().getColor(R.color.color_white));
                mChannelTitle.setTextColor(getResources().getColor(R.color.color_gray_9));
                break;
            case 1:
                mLatestTitle.setTextColor(getResources().getColor(R.color.color_gray_9));
                mChannelTitle.setTextColor(getResources().getColor(R.color.color_white));
                break;
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {
    }
}
