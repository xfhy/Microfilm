package com.xfhy.vmovie.fragments;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.TabLayout;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.xfhy.vmovie.R;
import com.xfhy.vmovie.activity.MainActivity;
import com.xfhy.vmovie.activity.SearchActivity;
import com.xfhy.vmovie.adapters.BehindColumnAdapter;
import com.xfhy.vmovie.base.BaseFragment;
import com.xfhy.vmovie.db.CacheDao;
import com.xfhy.vmovie.listener.HttpCallbackListener;
import com.xfhy.vmovie.model.behind.title.BehindTitleListBean;
import com.xfhy.vmovie.model.behind.title.TitleBean;
import com.xfhy.vmovie.utils.HttpUtils;
import com.xfhy.vmovie.utils.LogUtil;

import java.util.ArrayList;
import java.util.List;

import static com.xfhy.vmovie.constants.CacheType.BEHIND_TITLE_LIST_DATA;
import static com.xfhy.vmovie.constants.NetworkInterface.BEHIND_TITLE_LIST_URL;


/**
 * Created by xfhy on 2017/6/15.
 * 幕后 Fragment
 */

public class BehindFragment extends BaseFragment implements View.OnClickListener,
        HttpCallbackListener, ViewPager.OnPageChangeListener {

    private static final String TAG = "BehindFragment";
    /**
     * 请求标题数据
     */
    private static final int REQUEST_TITLE_DATA = 1000;
    /**
     * 加载标题数据完成
     */
    private static final int LOAD_TITLE_DATA_FINISHED = 1001;
    /**
     * 打开菜单
     */
    private ImageView mOpenMenu;
    /**
     * 标题
     */
    private TextView mTitle;
    /**
     * 操作缓存的dao
     */
    private CacheDao mDao;
    private ViewPager mViewPager;
    private TabLayout mTabLayout;
    private BehindColumnAdapter mAdapter;
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case LOAD_TITLE_DATA_FINISHED:
                    //加载标题数据完成
                    List<TitleBean> titleBeanList = (List<TitleBean>) msg.obj;
                    mAdapter.updateData(titleBeanList);
                    break;
            }
        }
    };
    private ImageView mSearch;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_main_behind;
    }

    @Override
    protected void initView() {
        mTabLayout = (TabLayout) findViewById(R.id.tl_behind_title);
        mViewPager = (ViewPager) findViewById(R.id.vp_behind_content);
        mOpenMenu = (ImageView) findViewById(R.id.iv_open_slide_menu);
        mTitle = (TextView) findViewById(R.id.tv_title);
        mSearch = (ImageView) findViewById(R.id.iv_search);

        mTitle.setText("幕后文章");

        //设置ViewPager适配器
        mAdapter = new BehindColumnAdapter(getActivity().getSupportFragmentManager()
                , getData());
        mViewPager.setAdapter(mAdapter);

        //绑定ViewPager
        mTabLayout.setupWithViewPager(mViewPager);

        //表示每个标签都保持自身宽度，一旦标签过多，给标题栏提供支持横向滑动的功能
        mTabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);

        //添加ViewPager的滑动监听事件
        mViewPager.addOnPageChangeListener(this);
        //设置菜单按钮点击事件
        mOpenMenu.setOnClickListener(this);
        mSearch.setOnClickListener(this);


        //添加TabLayout分割线
        LinearLayout linearLayout = (LinearLayout) mTabLayout.getChildAt(0);
        linearLayout.setShowDividers(LinearLayout.SHOW_DIVIDER_MIDDLE);
        linearLayout.setDividerDrawable(ContextCompat.getDrawable(getActivity(),
                R.drawable.layout_divider_vertical));
        linearLayout.setDividerPadding(35);
        linearLayout.setMinimumHeight(20);
    }

    private List<TitleBean> getData() {
        List<TitleBean> titleBeanList = new ArrayList<>();
        return titleBeanList;
    }

    /**
     * 请求网络数据 从服务器获取数据
     */
    public void requestDataFromServer() {
        //请求网络数据   获取幕后界面标题的数据
        HttpUtils.requestGet(BEHIND_TITLE_LIST_URL, REQUEST_TITLE_DATA, this);
    }

    /**
     * 从数据库获取缓存数据
     */
    public void requestDataFromDataBase() {
        LogUtil.e(TAG, "requestDataFromDataBase: 幕后界面从数据库获取缓存数据");
        String behindTitleList = mDao.getCache(BEHIND_TITLE_LIST_DATA);
        Gson gson = new Gson();

        if (!TextUtils.isEmpty(behindTitleList)) {
            BehindTitleListBean behindTitleListBean = gson.fromJson(behindTitleList,
                    BehindTitleListBean
                            .class);
            LogUtil.e(TAG, "requestDataFromDataBase: 幕后界面获取数据库数据成功" + behindTitleListBean
                    .toString());
            //更新TabLayout上的标题文字
            mAdapter.updateData(behindTitleListBean.getTitleBeanList());
        }

    }

    @Override
    protected void setupView() {
        //获取缓存数据库dao
        mDao = CacheDao.getInstance(getContext());

        //判断是否有网络连接
        if (HttpUtils.isNetworkConnected(getContext())) {
            //如果有网络连接
            requestDataFromServer();//请求网络数据 从服务器获取数据
        } else {
            //当前没有网络连接
            Toast.makeText(getContext(), "网络无法连接,请稍后重试", Toast.LENGTH_LONG).show();
            requestDataFromDataBase(); //从数据库获取缓存数据
        }
    }

    /**
     * 请调用此方法实例化fragment
     *
     * @return
     */
    public static BehindFragment newInstance() {

        Bundle args = new Bundle();

        BehindFragment fragment = new BehindFragment();
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
            case R.id.iv_search:
                Intent intent = new Intent(getContext(), SearchActivity.class);
                getActivity().startActivity(intent);
                break;
        }
    }

    @Override
    public void onFinish(int from, String response) {
        if (from == REQUEST_TITLE_DATA) {
            LogUtil.e(TAG, "onFinish: 请求标题数据成功");
            Gson gson = new Gson();
            BehindTitleListBean behindTitleListBean = gson.fromJson(response, BehindTitleListBean
                    .class);
            LogUtil.e(TAG, "onFinish: 解析幕后标题列表成功" + behindTitleListBean.toString());

            Message msg = Message.obtain();
            msg.what = LOAD_TITLE_DATA_FINISHED;
            msg.obj = behindTitleListBean.getTitleBeanList();
            mHandler.sendMessage(msg);

            //设置缓存
            mDao.setCache(BEHIND_TITLE_LIST_DATA, response);
        }
    }

    @Override
    public void onError(Exception e) {
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
    }

    @Override
    public void onPageSelected(int position) {
        LogUtil.e(TAG, "onPageSelected: position:" + position);
    }

    @Override
    public void onPageScrollStateChanged(int state) {
    }
}
