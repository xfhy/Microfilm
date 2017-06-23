package com.xfhy.vmovie.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.xfhy.vmovie.fragments.BehindListFragment;
import com.xfhy.vmovie.model.behind.title.TitleBean;
import com.xfhy.vmovie.utils.LogUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xfhy on 2017/6/19.
 * 幕后界面的ViewPager的adapter
 */

public class BehindColumnAdapter extends FragmentPagerAdapter {

    private static final String TAG = "BehindColumnAdapter";
    private List<TitleBean> dataList;

    public BehindColumnAdapter(FragmentManager fm, List<TitleBean> dataList) {
        super(fm);
        this.dataList = dataList;
    }

    @Override
    public Fragment getItem(int position) {
        if (dataList != null && dataList.size() > position) {
            //将数据从集合中取出来
            TitleBean titleBean = dataList.get(position);
            //创建Fragment
            BehindListFragment behindListFragment = BehindListFragment.newInstance(titleBean
                    .getCateId());
            //将数据传递到fragment中
            return behindListFragment;
        }
        return null;
    }

    @Override
    public int getCount() {
        return dataList == null ? 0 : dataList.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        if (dataList != null && dataList.size() > position) {
            return dataList.get(position).getCateName();
        }
        return "";
    }

    /**
     * 刷新数据源  更新正常的数据
     *
     * @param data 最新的数据
     */
    public void updateData(List<TitleBean> data) {
        if (data == null) {
            return;
        }
        if (dataList == null) {
            dataList = new ArrayList<>();
        }
        dataList.clear();
        dataList.addAll(data);
        LogUtil.e(TAG, "updateData: " + dataList.toString());
        //更新适配器
        this.notifyDataSetChanged();
    }

}
