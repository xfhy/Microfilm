package com.xfhy.vmovie.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * Created by Administrator on 2017/6/9.
 * 引导界面的ViewPager的适配器
 */

public class GuideAdapter extends FragmentPagerAdapter {

    private List<Fragment> dataList;

    public GuideAdapter(FragmentManager fm, List<Fragment> dataList) {
        super(fm);
        this.dataList = dataList;
    }

    /*
    返回与指定位置相关联的Fragment
    前提是拥有所有Fragment的一个集合
     */
    @Override
    public Fragment getItem(int position) {
        if (dataList != null && dataList.size() > position) {
            return dataList.get(position);
        }
        return null;
    }

    //个数
    @Override
    public int getCount() {
        return dataList == null ? 0 : dataList.size();
    }
}
