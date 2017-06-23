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
import com.xfhy.vmovie.activity.MovieDetailActivity;
import com.xfhy.vmovie.model.latest.list.CateBean;
import com.xfhy.vmovie.model.latest.list.MovieInfoBean;
import com.xfhy.vmovie.utils.LogUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xfhy on 2017/6/16.
 * 频道点击进去的详情  的RecyclerView的adapter
 */

public class ChannelDetailsListAdapter extends RecyclerView.Adapter<ChannelDetailsListAdapter
        .ViewHolder>
        implements View.OnClickListener {

    private RecyclerView recyclerView;
    private static final String TAG = "ChannelDetailsListAdapter";
    /**
     * 打气筒
     */
    private LayoutInflater inflater;
    private List<MovieInfoBean> dataList;

    private Context context;

    public ChannelDetailsListAdapter(Context context, List<MovieInfoBean> dataList) {
        this.context = context;
        this.dataList = dataList;
        this.inflater = LayoutInflater.from(context);
    }

    /**
     * 正常布局的ViewHolder
     */
    static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView movieImage;  //条目图片
        TextView movieTitle;    //条目名称
        TextView movieInfo;  //条目信息

        public ViewHolder(View itemView) {
            super(itemView);

            movieImage = (ImageView) itemView.findViewById(R.id.iv_movie_img);
            movieTitle = (TextView) itemView.findViewById(R.id.tv_movie_title);
            movieInfo = (TextView) itemView.findViewById(R.id.tv_movie_info);
        }
    }

    //recyclerView开始使用该adapter时调用此方法
    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        this.recyclerView = recyclerView;
        super.onAttachedToRecyclerView(recyclerView);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //根据类型加载不同的布局
        LayoutInflater mInflater = LayoutInflater.from(context);
        View view = mInflater.inflate(R.layout.item_latest_movie_normal_layout, parent, false);
        //设置item的点击事件
        view.setOnClickListener(this);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        if (dataList != null && dataList.size() > position) {
            //获取对应item位置的数据
            MovieInfoBean movieInfoBean = dataList.get(position);

            //使用glide加载图片
            Glide.with(context)
                    .load(movieInfoBean.getImageUrl())
                    .into(holder.movieImage);

            holder.movieTitle.setText(movieInfoBean.getTitle());
            CateBean cateBean = movieInfoBean.getCateBeanList().get(0);
            String duration = movieInfoBean.getDuration();
            int length = 0;
            try {
                length = Integer.parseInt(duration);
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }

            //设置时间
            holder.movieInfo.setText(String.format("%s / %d'%d''", cateBean
                    .getCateName(), length
                    / 60, length % 60));
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
    public void updateData(List<MovieInfoBean> data) {
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
    public void addData(List<MovieInfoBean> data) {
        if (data == null) {
            return;
        }
        if (dataList == null) {
            dataList = new ArrayList<>();
        }
        dataList.addAll(data);
        LogUtil.e(TAG, "addData: " + dataList.toString());
        //更新适配器
        this.notifyDataSetChanged();
    }

    @Override
    public void onClick(View v) {
        //条目点击事件
        int childAdapterPosition = recyclerView.getChildAdapterPosition(v);

        LogUtil.e(TAG, "onClick: 点击事件触发 位置: " + childAdapterPosition);
        //开启电影详情页面
        MovieInfoBean movieInfoBean = dataList.get(childAdapterPosition);
        MovieDetailActivity.acionStart(context, movieInfoBean
                .getPostid());
    }

}
