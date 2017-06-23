package com.xfhy.vmovie.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * Created by xfhy on 2017/6/16.
 * 首页fragment中的切换最新和频道的ViewPager的adapter
 */

public class HomeSwitchPagerAdapter extends FragmentPagerAdapter {

    private List<Fragment> dataList;

    public HomeSwitchPagerAdapter(FragmentManager fm, List<Fragment> dataList) {
        super(fm);
        this.dataList = dataList;
    }

    @Override
    public Fragment getItem(int position) {
        if (dataList != null && dataList.size() > position) {
            Fragment fragment = dataList.get(position);
            return fragment;
        }
        return null;
    }

    @Override
    public int getCount() {
        return dataList != null ? dataList.size() : 0;
    }
}
