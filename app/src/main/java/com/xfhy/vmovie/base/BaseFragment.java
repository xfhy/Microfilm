package com.xfhy.vmovie.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


/**
 * Created by xfhy on 2017/6/15.
 */

public abstract class BaseFragment extends Fragment {

    /**
     * 该fragment所对应的布局
     */
    protected View mLayout;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable
            Bundle savedInstanceState) {
        //需要返回页面布局   所有子类需要返回view
        mLayout = inflater.inflate(getLayoutId(), container, false);
        initArguments();
        initView();
        setupView();
        return mLayout;
    }

    /**
     * 方便获取传递过来的参数
     */
    protected void initArguments() {

    }

    /**
     * 方便查找id
     * @param id
     * @return
     */
    public View findViewById(int id){
        return mLayout.findViewById(id);
    }

    /**
     * 设置布局数据
     */
    protected void setupView() {

    }

    /**
     * 设置布局的id
     *
     * @return 返回子类布局id
     */
    protected abstract int getLayoutId();

    /**
     * 初始化View
     */
    protected abstract void initView();

}
