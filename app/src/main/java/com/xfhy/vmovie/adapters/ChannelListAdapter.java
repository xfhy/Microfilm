package com.xfhy.vmovie.adapters;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.xfhy.vmovie.R;
import com.xfhy.vmovie.activity.ChannelDetailsActivity;
import com.xfhy.vmovie.model.channel.ChannelInfo;
import com.xfhy.vmovie.model.channel.ChannelListBean;
import com.xfhy.vmovie.utils.AppUtils;
import com.xfhy.vmovie.utils.LogUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xfhy on 2017/6/17.
 * 频道列表RecyclerView的adapter
 */

public class ChannelListAdapter extends RecyclerView.Adapter<ChannelListAdapter.ViewHolder>
        implements View.OnClickListener {

    private static final String TAG = "ChannelListAdapter";
    private List<ChannelInfo> dataList;
    private Context context;
    private RecyclerView recyclerView;

    public ChannelListAdapter(Context context, List<ChannelInfo> dataList) {
        this.context = context;
        this.dataList = dataList;
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        this.recyclerView = recyclerView;
        super.onAttachedToRecyclerView(recyclerView);
    }

    @Override
    public void onClick(View v) {
        //条目点击事件
        int childAdapterPosition = recyclerView.getChildAdapterPosition(v);
        LogUtil.e(TAG, "onClick: childAdapterPosition:" + childAdapterPosition);
        ChannelInfo channelInfo = dataList.get(childAdapterPosition);
        LogUtil.e(TAG, "onClick: " + channelInfo.toString());

        //打开详情页面
        if (channelInfo != null) {
            ChannelDetailsActivity.acionStart(context, channelInfo.getCateid(), channelInfo
                    .getCateName());
        }
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView channelImgBg;
        TextView channelName;

        public ViewHolder(View itemView) {
            super(itemView);

            channelImgBg = (ImageView) itemView.findViewById(R.id.iv_channel_type_bg);
            channelName = (TextView) itemView.findViewById(R.id.tv_channel_catename);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout
                .item_channel_movie_layout, parent, false);

        //这里需要动态设置子项的宽度和高度  以使其保持一致,然后是正方形
        GridLayoutManager.LayoutParams layoutParams = (GridLayoutManager.LayoutParams) view
                .getLayoutParams();
        DisplayMetrics displayMetrics = AppUtils.getAppWidth();
        layoutParams.width = displayMetrics.widthPixels / 2;
        layoutParams.height = layoutParams.width;
        view.setLayoutParams(layoutParams);

        //设置改子项的点击事件
        view.setOnClickListener(this);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        if (dataList != null && dataList.size() > position) {
            ChannelInfo channelInfo = dataList.get(position);

            //使用glide加载图片
            Glide.with(context)
                    .load(channelInfo.getIconUrl())
                    .into(holder.channelImgBg);
            //设置频道名称
            holder.channelName.setText("#" + channelInfo.getCateName() + "#");
        }
    }

    @Override
    public int getItemCount() {
        return dataList == null ? 0 : dataList.size();
    }

    /**
     * 刷新数据源  更新数据
     *
     * @param data 最新的数据
     */
    public void updateData(List<ChannelInfo> data) {
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
