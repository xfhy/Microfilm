package com.xfhy.vmovie.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.xfhy.vmovie.R;
import com.xfhy.vmovie.activity.SeriesDetailsActivity;
import com.xfhy.vmovie.model.series.list.SeriesMovieInfo;
import com.xfhy.vmovie.utils.LogUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xfhy on 2017/6/18.
 * 系列列表的RecyclerView的adapter
 */

public class SeriesMovieAdapter extends RecyclerView.Adapter<SeriesMovieAdapter.ViewHolder>
        implements View.OnClickListener {

    private static final String TAG = "SeriesMovieAdapter";
    private List<SeriesMovieInfo> dataList;
    private Context context;
    LayoutInflater inflater;
    private RecyclerView mRecyclerView;

    public SeriesMovieAdapter(Context context, List<SeriesMovieInfo> dataList) {
        this.context = context;
        this.dataList = dataList;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public void onClick(View v) {
        //获取点击的位置
        int childAdapterPosition = mRecyclerView.getChildAdapterPosition(v);

        if (childAdapterPosition < dataList.size()) {
            SeriesMovieInfo seriesMovieInfo = dataList.get(childAdapterPosition);
            LogUtil.e(TAG, "onClick: 点击位置详情:"+seriesMovieInfo.toString());
            //启动系列详情页
            SeriesDetailsActivity.acionStart(context, seriesMovieInfo.getSeriesId());
        }

    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView publicityMap;  //宣传图
        TextView movieTitle;  //标题
        TextView movieInfo; //信息
        TextView movieContent; //内容简介

        public ViewHolder(View itemView) {
            super(itemView);
            publicityMap = (ImageView) itemView.findViewById(R.id.iv_series_item_img);
            movieTitle = (TextView) itemView.findViewById(R.id.tv_series_item_title);
            movieInfo = (TextView) itemView.findViewById(R.id.tv_series_item_info);
            movieContent = (TextView) itemView.findViewById(R.id.tv_series_item_content);
        }
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        this.mRecyclerView = recyclerView;
        super.onAttachedToRecyclerView(recyclerView);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_series_layout, parent, false);
        view.setOnClickListener(this);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        if (dataList != null && dataList.size() > position) {
            SeriesMovieInfo seriesMovieInfo = dataList.get(position);

            //用Glide加载图片
            Glide.with(context)
                    .load(seriesMovieInfo.getAppImageUrl())
                    .into(holder.publicityMap);
            //设置标题
            holder.movieTitle.setText(seriesMovieInfo.getTitle());

            if (seriesMovieInfo.getIsEnd().equals("0")) {
                //未结束
                //更新信息
                String updateToInfo = String.format("已更新至%s集    %s人已订阅", seriesMovieInfo
                        .getUpdateTo(), seriesMovieInfo.getFollowerNum());
                holder.movieInfo.setText(updateToInfo);
            } else if (seriesMovieInfo.getIsEnd().equals("1")) {
                //已结束
                holder.movieInfo.setText(String.format("已完结    %s人已订阅", seriesMovieInfo
                        .getFollowerNum()));
            }

            //设置条目简介
            holder.movieContent.setText(seriesMovieInfo.getContent());
        }
    }

    @Override
    public int getItemCount() {
        return dataList == null ? 0 : dataList.size();
    }

    /**
     * 刷新数据源  更新正常的数据
     *
     * @param data 最新的数据
     */
    public void updateData(List<SeriesMovieInfo> data) {
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

    /**
     * 刷新数据源  添加数据
     *
     * @param data 最新的数据
     */
    public void addData(List<SeriesMovieInfo> data) {
        if (data == null) {
            return;
        }
        if (dataList == null) {
            dataList = new ArrayList<>();
        }
        dataList.addAll(data);
        LogUtil.e(TAG, "updateData: " + dataList.toString());
        //更新适配器
        this.notifyDataSetChanged();
    }

}
