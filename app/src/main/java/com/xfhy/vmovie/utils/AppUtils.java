package com.xfhy.vmovie.utils;

import android.util.DisplayMetrics;
import android.view.Display;
import android.view.WindowManager;

import com.xfhy.vmovie.global.VMovieApplication;

import static android.content.Context.WINDOW_SERVICE;

/**
 * Created by xfhy on 2017/6/18.
 */

public class AppUtils {

    /**
     * 获取手机屏幕的高度和宽度
     * @return DisplayMetrics对象,displayMetrics.widthPixels是宽度,显示的绝对宽度（以像素为单位）
     */
    public static DisplayMetrics getAppWidth() {
        WindowManager windowManager = (WindowManager) VMovieApplication.getContext()
                .getSystemService(WINDOW_SERVICE);
        Display defaultDisplay = windowManager.getDefaultDisplay();
        DisplayMetrics displayMetrics = new DisplayMetrics();
        defaultDisplay.getMetrics(displayMetrics);
        return displayMetrics;
    }

}
