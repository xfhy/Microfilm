package com.xfhy.vmovie.view;

import android.content.Context;
import android.support.annotation.AttrRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

import com.kyleduo.switchbutton.SwitchButton;
import com.xfhy.vmovie.R;
import com.xfhy.vmovie.utils.LogUtil;

/**
 * Created by xfhy on 2017/6/22.
 * 这是设置界面的开关项的item布局
 */

public class SettingSwitchItem extends FrameLayout implements CompoundButton.OnCheckedChangeListener {

    private static final String TAG = "SettingSwitchItem";
    /**
     * 命名空间
     */
    public static final String NAMESPACE = "http://schemas.android.com/apk/res-auto";
    /**
     * item标题
     */
    public static final String ATTR_ITEM_TITLE = "setting_item_title";
    /**
     * 是否开启开关
     */
    private static final String ATTR_ITEM_SWITCH = "switch_open";

    /**
     * 标题
     */
    private TextView mItemTitle;
    /**
     * 开关按钮
     */
    private SwitchButton mSwitch;
    /**
     * 当前的开关状态
     */
    private boolean switchStatus;


    /**
     * 在代码中创建布局时会调用此构造方法
     *
     * @param context
     */
    public SettingSwitchItem(@NonNull Context context) {
        //不管调哪个构造方法都会去调用最下面那个构造方法
        this(context, null);
    }

    /**
     * 在xml布局文件中使用该自定义View时会使用到该构造方法
     *
     * @param context
     * @param attrs
     */
    public SettingSwitchItem(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    /**
     * 在xml布局文件中使用该自定义View时,并使用主题会使用到该构造方法
     *
     * @param context
     * @param attrs
     * @param defStyleAttr
     */
    public SettingSwitchItem(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int
            defStyleAttr) {
        super(context, attrs, defStyleAttr);

        //xml->view  将设置界面的一个条目转换成View对象

        //加载子项布局
        View.inflate(context, R.layout.layout_setting_switch_item, this);


        String title = attrs.getAttributeValue(NAMESPACE, ATTR_ITEM_TITLE);
        boolean isSwitch = attrs.getAttributeBooleanValue(NAMESPACE,ATTR_ITEM_SWITCH,false);

        //找到子项中相应的控件
        mItemTitle = (TextView) findViewById(R.id.tv_setting_item_title);
        mSwitch = (SwitchButton) findViewById(R.id.sb_ios_switch);

        LogUtil.e(TAG, "SettingNormalItem: title:"+title);
        mItemTitle.setText(title);
        mSwitch.setChecked(isSwitch);

        mSwitch.setOnCheckedChangeListener(this);
    }

    /**
     * 设置标题
     *
     * @param title 标题
     */
    public void setTitle(String title) {
        mItemTitle.setText(title);
    }


    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if(isChecked){
            switchStatus = true;
        } else {
            switchStatus = false;
        }
    }

    /**
     * 获取当前的开关状态
     * @return
     */
    public boolean getSwitchStatus() {
        return switchStatus;
    }

    /**
     * 设置当前的开关状态
     * @param switchStatus
     */
    public void setSwitchStatus(boolean switchStatus) {
        this.switchStatus = switchStatus;
    }

    /**
     * 切换当前的开关状态
     */
    public void toggle(){
        mSwitch.toggle();
    }

}
