package com.xfhy.vmovie.adapters;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

/**
 * Created by xfhy on 2017/6/17.
 * RecyclerView没有可以直接设置间距的属性，但可以用ItemDecoration来装饰一个item，
 * 所以继承重写ItemDecoration就可以实现间距了
 */

public class SpaceItemDecoration extends RecyclerView.ItemDecoration {
    private int space;
    private static final String TAG = "SpaceItemDecoration";

    public SpaceItemDecoration(int space) {
        this.space = space;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State
            state) {
        outRect.top = space;
        outRect.left = space;
        outRect.bottom = space;
        outRect.right = space;
        Log.e(TAG, "getItemOffsets: space:"+space);
    }
}
