package com.xfhy.vmovie.view;

import android.content.Context;
import android.support.annotation.AttrRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.xfhy.vmovie.R;
import com.xfhy.vmovie.utils.LogUtil;

/**
 * Created by xfhy on 2017/6/22.
 * 这是设置界面的普通项的item布局
 */

public class SettingNormalItem extends FrameLayout {

    private static final String TAG = "SettingNormalItem";
    /**
     * 命名空间
     */
    public static final String NAMESPACE = "http://schemas.android.com/apk/res-auto";
    /**
     * item标题
     */
    public static final String ATTR_ITEM_TITLE = "setting_item_title";
    /**
     * item描述信息
     */
    private static final String ATTR_ITEM_INFO = "setting_item_info";
    /**
     * 是否显示描述信息
     */
    private static final String ATTR_ITEM_SHOW_INFO = "setting_info_show";
    /**
     * item的是否显示右边的图片
     */
    private static final String ATTR_ITEM_SHOW_IMG = "setting_img_show";
    /**
     * 右边的图片
     */
    private final ImageView mItemImg;

    /**
     * 标题
     */
    private TextView mItemTitle;
    /**
     * item项的描述信息
     */
    private TextView mItemInfo;

    /**
     * 在代码中创建布局时会调用此构造方法
     *
     * @param context
     */
    public SettingNormalItem(@NonNull Context context) {
        //不管调哪个构造方法都会去调用最下面那个构造方法
        this(context, null);
    }

    /**
     * 在xml布局文件中使用该自定义View时会使用到该构造方法
     *
     * @param context
     * @param attrs
     */
    public SettingNormalItem(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    /**
     * 在xml布局文件中使用该自定义View时,并使用主题会使用到该构造方法
     *
     * @param context
     * @param attrs
     * @param defStyleAttr
     */
    public SettingNormalItem(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int
            defStyleAttr) {
        super(context, attrs, defStyleAttr);

        //xml->view  将设置界面的一个条目转换成View对象

        //加载子项布局
        View.inflate(context, R.layout.layout_setting_normal_item, this);


        String title = attrs.getAttributeValue(NAMESPACE, ATTR_ITEM_TITLE);
        String info = attrs.getAttributeValue(NAMESPACE, ATTR_ITEM_INFO);
        boolean isShowInfo = attrs.getAttributeBooleanValue(NAMESPACE, ATTR_ITEM_SHOW_INFO, true);
        boolean isShowImg = attrs.getAttributeBooleanValue(NAMESPACE, ATTR_ITEM_SHOW_IMG, true);

        //找到子项中相应的控件
        mItemTitle = (TextView) findViewById(R.id.tv_setting_item_title);
        mItemInfo = (TextView) findViewById(R.id.tv_setting_item_info);
        mItemImg = (ImageView) findViewById(R.id.iv_setting_img);

        LogUtil.e(TAG, "SettingNormalItem: title:"+title);
        LogUtil.e(TAG, "SettingNormalItem: info:"+info);
        mItemTitle.setText(title);
        mItemInfo.setText(info);

        setSetShowInfo(isShowInfo);
        setSetShowImg(isShowImg);
    }

    /**
     * 设置标题
     *
     * @param title 标题
     */
    public void setTitle(String title) {
        mItemTitle.setText(title);
    }

    /**
     * 设置描述信息
     *
     * @param des 描述信息
     */
    public void setInfo(String des) {
        mItemInfo.setText(des);
    }

    /**
     * 设置描述信息是否显示
     *
     * @param isShowInfo
     */
    public void setSetShowInfo(boolean isShowInfo) {
        if (isShowInfo) {
            mItemInfo.setVisibility(View.VISIBLE);
        } else {
            mItemInfo.setVisibility(View.GONE);
        }
    }

    /**
     * 设置右边的图片是否显示
     *
     * @param isShowImg
     */
    public void setSetShowImg(boolean isShowImg) {
        if (isShowImg) {
            mItemImg.setVisibility(View.VISIBLE);
        } else {
            mItemImg.setVisibility(View.GONE);
        }
    }

}
