package com.xfhy.vmovie.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;


/**
 * Created by xfhy on 2017/6/15.
 * Activity的基类
 */

public abstract class BaseActivity extends AppCompatActivity {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());

        initView();
        setupView();
    }

    /**
     * 设置该界面布局的id
     * @return 返回子类布局id
     */
    protected abstract int getLayoutId();

    /**
     * 初始化View
     */
    protected abstract void initView();

    /**
     * 设置布局数据
     */
    protected void setupView() {

    }

}
