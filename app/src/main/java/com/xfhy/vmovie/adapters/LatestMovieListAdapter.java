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
import com.xfhy.vmovie.activity.BannerDetailsActivity;
import com.xfhy.vmovie.activity.MovieDetailActivity;
import com.xfhy.vmovie.model.banner.ExtraData;
import com.xfhy.vmovie.model.latest.list.CateBean;
import com.xfhy.vmovie.model.latest.list.MovieInfoBean;
import com.xfhy.vmovie.model.banner.BannerItemBean;
import com.xfhy.vmovie.utils.GlideImageLoader;
import com.xfhy.vmovie.utils.LogUtil;
import com.youth.banner.Banner;
import com.youth.banner.listener.OnBannerListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by xfhy on 2017/6/16.
 * 最新电影界面 电影列表的adapter
 */

public class LatestMovieListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>
        implements View.OnClickListener, OnBannerListener {

    private RecyclerView recyclerView;
    private static final String TAG = "MainMovieListAdapter";
    private LayoutInflater inflater;

    /**
     * header的数据   轮播图的数据
     */
    private List<BannerItemBean> bannerDataList;

    /**
     * item的数据
     */
    private List<MovieInfoBean> itemDataList;

    private Context context;
    /**
     * header
     */
    public static final int HEADER = 1;
    /**
     * 正常item
     */
    public static final int ITEM = 2;

    public LatestMovieListAdapter(Context context, List<BannerItemBean> bannerDataList,
                                  List<MovieInfoBean> itemDataList) {
        this.context = context;
        this.inflater = LayoutInflater.from(context);
        this.bannerDataList = bannerDataList;
        this.itemDataList = itemDataList;
    }

    @Override
    public void OnBannerClick(int position) {
        LogUtil.e(TAG, "OnBannerClick: 点击了banner位置:" + position);
        LogUtil.e(TAG, "OnBannerClick: 该位置的数据为:" + bannerDataList.get(position).toString());
        if (bannerDataList.size() < position) {
            return;
        }
        ExtraData extraData = bannerDataList.get(position).getExtraData();
        String appBannerType = extraData.getAppBannerType();
        if (appBannerType.equals("1")) {
            LogUtil.e(TAG, "OnBannerClick: 这是轮播图点击,准备打开webview详情页");
            BannerDetailsActivity.actionStart(context,extraData.getAppBannerParam());
        } else if (appBannerType.equals("2")) {
            LogUtil.e(TAG, "OnBannerClick: 这是轮播图点击,准备打开电影详情页");

            //喜欢数和分享数都没有接口....
            Random random = new Random();
            MovieDetailActivity.acionStart(context, extraData.getAppBannerParam());
        }
    }

    /**
     * 头布局的ViewHolder
     */
    static class HeaderViewHolder extends RecyclerView.ViewHolder {
        Banner banner; //轮播图

        public HeaderViewHolder(View itemView) {
            super(itemView);
            banner = (Banner) itemView.findViewById(R.id.banner_home);
            //设置图片加载器
            banner.setImageLoader(new GlideImageLoader());
        }
    }

    /**
     * 正常布局的ViewHolder
     */
    static class ItemViewHolder extends RecyclerView.ViewHolder {
        ImageView movieImage;  //条目图片
        TextView movieTitle;    //条目名称
        TextView movieInfo;  //条目信息

        public ItemViewHolder(View itemView) {
            super(itemView);

            movieImage = (ImageView) itemView.findViewById(R.id.iv_movie_img);
            movieTitle = (TextView) itemView.findViewById(R.id.tv_movie_title);
            movieInfo = (TextView) itemView.findViewById(R.id.tv_movie_info);
        }
    }

    //item的类型
    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return HEADER;
        } else {
            return ITEM;
        }
    }

    //recyclerView开始使用该adapter时调用此方法
    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        this.recyclerView = recyclerView;
        super.onAttachedToRecyclerView(recyclerView);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //根据类型加载不同的布局
        LayoutInflater mInflater = LayoutInflater.from(context);
        RecyclerView.ViewHolder holder = null;
        View view = null;
        if (HEADER == viewType) {
            view = mInflater.inflate(R.layout.item_latest_movie_header_layout, parent, false);
            Banner banner = (Banner) view.findViewById(R.id.banner_home);
            banner.setOnBannerListener(this);
            holder = new HeaderViewHolder(view);
        } else {
            view = mInflater.inflate(R.layout.item_latest_movie_normal_layout, parent, false);
            holder = new ItemViewHolder(view);
            //设置item的点击事件
            view.setOnClickListener(this);
        }
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        //判断以下是哪个布局的holder
        if (holder instanceof HeaderViewHolder) {
            //这是header的holder
            if (bannerDataList != null && position == 0) {
                //转换成item正常的holder
                HeaderViewHolder headerViewHolder = (HeaderViewHolder) holder;

                List<String> bannerImgUrls = new ArrayList<>();
                for (BannerItemBean bannerItemBean : bannerDataList) {
                    bannerImgUrls.add(bannerItemBean.getImageUrl());
                }
                //设置图片集合
                headerViewHolder.banner.setImages(bannerImgUrls);
                //开始轮播
                headerViewHolder.banner.start();
            }
        } else if (holder instanceof ItemViewHolder) {
            //这是正常的布局的holder
            if (itemDataList != null && itemDataList.size() > position) {
                //转换成item正常的holder
                ItemViewHolder itemViewHolder = (ItemViewHolder) holder;
                //获取对应item位置的数据
                MovieInfoBean movieInfoBean = itemDataList.get(position);

                //使用glide加载图片
                Glide.with(context)
                        .load(movieInfoBean.getImageUrl())
                        .into(itemViewHolder.movieImage);

                itemViewHolder.movieTitle.setText(movieInfoBean.getTitle());
                CateBean cateBean = movieInfoBean.getCateBeanList().get(0);
                String duration = movieInfoBean.getDuration();
                int length = 0;
                try {
                    length = Integer.parseInt(duration);
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                }
                itemViewHolder.movieInfo.setText(String.format("%s / %d'%d''", cateBean
                        .getCateName(), length
                        / 60, length % 60));
            }
        }
    }

    @Override
    public int getItemCount() {
        //多了一个banner
        return itemDataList == null ? 1 : itemDataList.size() + 1;  //header+item
    }

    /**
     * 刷新数据源  更新Header的数据
     *
     * @param data 最新的数据
     */
    public void updateHeaderData(List<BannerItemBean> data) {
        if (data == null) {
            return;
        }
        if (bannerDataList == null) {
            bannerDataList = new ArrayList<>();
        }
        bannerDataList.clear();
        bannerDataList.addAll(data);
        LogUtil.e(TAG, "updateHeaderData: " + bannerDataList.toString());
        //更新适配器
        this.notifyDataSetChanged();
    }

    /**
     * 刷新数据源  更新正常的数据
     *
     * @param data 最新的数据
     */
    public void updateNormalItemData(List<MovieInfoBean> data) {
        if (data == null) {
            return;
        }
        if (itemDataList == null) {
            itemDataList = new ArrayList<>();
        }
        itemDataList.clear();
        itemDataList.addAll(data);
        LogUtil.e(TAG, "updateData: " + itemDataList.toString());
        //更新适配器
        this.notifyDataSetChanged();
    }

    /**
     * 刷新数据源  添加数据
     *
     * @param data 最新的数据
     */
    public void addNormalItemData(List<MovieInfoBean> data) {
        if (data == null) {
            return;
        }
        if (itemDataList == null) {
            itemDataList = new ArrayList<>();
        }
        itemDataList.addAll(data);
        LogUtil.e(TAG, "addData: " + itemDataList.toString());
        //更新适配器
        this.notifyDataSetChanged();
    }

    @Override
    public void onClick(View v) {
        //条目点击事件
        int childAdapterPosition = recyclerView.getChildAdapterPosition(v);

        LogUtil.e(TAG, "onClick: 点击事件触发 位置: " + childAdapterPosition);

        if (childAdapterPosition == 0) {
            //点击了banner
            LogUtil.e(TAG, "onClick: 点击了banner");
        } else {
            //开启电影详情页面
            MovieInfoBean movieInfoBean = itemDataList.get(childAdapterPosition);
            MovieDetailActivity.acionStart(context, movieInfoBean
                    .getPostid());
        }
    }

}
