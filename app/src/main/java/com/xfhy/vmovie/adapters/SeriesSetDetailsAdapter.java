package com.xfhy.vmovie.adapters;

import android.content.Context;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.xfhy.vmovie.R;
import com.xfhy.vmovie.listener.PlaySetVideoListener;
import com.xfhy.vmovie.model.series.details.SetBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xfhy on 2017/6/21.
 * 系列界面的分期RecyclerView的列表adapter
 */

public class SeriesSetDetailsAdapter extends RecyclerView.Adapter<SeriesSetDetailsAdapter
        .ViewHolder> implements View.OnClickListener {

    private static final String TAG = "SeriesSetDetailsAdapter";
    private List<SetBean> setBeanList;
    private Context context;
    private RecyclerView mRecyclerView;
    LayoutInflater inflater;
    /**
     * 回调接口   可以讲数据返回
     */
    private PlaySetVideoListener listener;

    public SeriesSetDetailsAdapter(Context context, List<SetBean> setBeanList) {
        this.context = context;
        this.setBeanList = setBeanList;
        inflater = LayoutInflater.from(context);
    }

    /**
     * 设置点击事件的回调监听
     *
     * @param listener
     */
    public void setOnPlayVideoClickListener(PlaySetVideoListener listener) {
        this.listener = listener;
    }

    @Override
    public void onClick(View v) {
        //获取点击的位置
        int childAdapterPosition = mRecyclerView.getChildAdapterPosition(v);
        Log.e(TAG, "onClick: childAdapterPosition:" + childAdapterPosition);

        if (listener != null) {
            //回调 接口 点击位置和点击项的详情
            listener.playVideo(childAdapterPosition, setBeanList.get(childAdapterPosition));
        }

        //将播放状态进行切换
        for (int i = 0; i < setBeanList.size(); i++) {
            if (childAdapterPosition == i) {
                Log.e(TAG, "onClick: 第" + i + "项改为在播放");
                setBeanList.get(i).setPlay(true);
            } else {
                setBeanList.get(i).setPlay(false);
            }
        }
        //更新适配器数据
        this.notifyDataSetChanged();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView videoImg;
        TextView isPlay;
        TextView videoDuration;
        TextView videoTitle;
        TextView addTime;

        public ViewHolder(View itemView) {
            super(itemView);

            videoImg = (ImageView) itemView.findViewById(R.id.iv_video_img);
            isPlay = (TextView) itemView.findViewById(R.id.tv_is_play);
            videoDuration = (TextView) itemView.findViewById(R.id.tv_duration);
            videoTitle = (TextView) itemView.findViewById(R.id.tv_video_title);
            addTime = (TextView) itemView.findViewById(R.id.tv_add_time);
        }
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        this.mRecyclerView = recyclerView;
        super.onAttachedToRecyclerView(recyclerView);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_series_set_detail, parent, false);
        view.setOnClickListener(this);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        if (setBeanList != null && setBeanList.size() > position) {
            SetBean setBean = setBeanList.get(position);
            //用Glide加载图片
            Glide.with(context)
                    .load(setBean.getThumbnail())
                    .into(holder.videoImg);

            //判断是否在播放
            if (setBean.isPlay()) {
                Log.e(TAG, "onBindViewHolder: 第" + position + "在播放");
                holder.isPlay.setVisibility(View.VISIBLE);

                //设置正在播放的视频图片为灰色
                ColorMatrix matrix = new ColorMatrix();
                matrix.setSaturation(0);

                ColorMatrixColorFilter filter = new ColorMatrixColorFilter(matrix);
                holder.videoImg.setColorFilter(filter);
            } else {
                holder.isPlay.setVisibility(View.INVISIBLE);
                //设置没有在播放的为彩色
                holder.videoImg.setColorFilter(null);
            }

            //设置时间
            String duration = setBean.getDuration();
            int length = 0;
            try {
                length = Integer.parseInt(duration);
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
            holder.videoDuration.setText(String.format("%d:%d", length / 60, length % 60));
            holder.videoTitle.setText(setBean.getTitle());
            holder.addTime.setText(setBean.getAddtime());
        }
    }

    @Override
    public int getItemCount() {
        return setBeanList == null ? 0 : setBeanList.size();
    }

    /**
     * 更新适配器数据
     *
     * @param dataList
     */
    public void updateData(List<SetBean> dataList) {
        if (dataList == null) {
            return;
        }
        if (setBeanList == null) {
            setBeanList = new ArrayList<>();
        }
        setBeanList.clear();
        setBeanList.addAll(dataList);

        //设置默认第一项是在播放着的
        if (setBeanList.size() > 1) {
            setBeanList.get(0).setPlay(true);
        }
        this.notifyDataSetChanged();
    }

}
