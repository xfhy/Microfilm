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
import com.xfhy.vmovie.activity.ArticleDetailsActivity;
import com.xfhy.vmovie.model.behind.article.ArticleBean;
import com.xfhy.vmovie.utils.LogUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xfhy on 2017/6/19.
 * 幕后界面的文章列表adapter
 */

public class BehindArticleAdapter extends RecyclerView.Adapter<BehindArticleAdapter.ViewHolder>
        implements View.OnClickListener {

    private static final String TAG = "BehindArticleAdapter";
    /**
     * 文章列表
     */
    private List<ArticleBean> articleBeanList;
    private Context context;
    private RecyclerView mRecyclerView;
    LayoutInflater inflater;

    public BehindArticleAdapter(Context context, List<ArticleBean> articleBeanList) {
        this.context = context;
        inflater = LayoutInflater.from(context);
        this.articleBeanList = articleBeanList;
    }

    @Override
    public void onClick(View v) {
        //条目点击事件
        int childAdapterPosition = mRecyclerView.getChildAdapterPosition(v);

        LogUtil.e(TAG, "onClick: 点击事件触发 位置: " + childAdapterPosition);

        //开启文章详情页面
        ArticleBean articleBean = articleBeanList.get(childAdapterPosition);
        ArticleDetailsActivity.acionStart(context, articleBean
                .getPostId(), articleBean.getLikeNum(), articleBean.getShareNum());
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        ImageView articleImg;  //文章图片
        TextView articleTitle; //文章标题
        TextView shareNum;     //文章分享数量
        TextView likeNum;      //文章喜欢数量

        public ViewHolder(View itemView) {
            super(itemView);

            articleImg = (ImageView) itemView.findViewById(R.id.iv_behind_article_img);
            articleTitle = (TextView) itemView.findViewById(R.id.tv_article_title);
            shareNum = (TextView) itemView.findViewById(R.id.tv_share_num);
            likeNum = (TextView) itemView.findViewById(R.id.tv_like_num);
        }
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        this.mRecyclerView = recyclerView;
        super.onAttachedToRecyclerView(recyclerView);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_behind_article_layout, parent, false);
        view.setOnClickListener(this);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        if (articleBeanList != null && articleBeanList.size() > position) {
            ArticleBean articleBean = articleBeanList.get(position);

            //用Glide加载图片
            Glide.with(context)
                    .load(articleBean.getImageUrl())
                    .into(holder.articleImg);

            //标题
            holder.articleTitle.setText(articleBean.getTitle());
            //分享数
            holder.shareNum.setText(articleBean.getShareNum());
            //喜欢数
            holder.likeNum.setText(articleBean.getLikeNum());
        }
    }

    @Override
    public int getItemCount() {
        return articleBeanList == null ? 0 : articleBeanList.size();
    }

    /**
     * 刷新数据源  更新Header的数据
     *
     * @param data 最新的数据
     */
    public void updateData(List<ArticleBean> data) {
        if (data == null) {
            return;
        }
        if (articleBeanList == null) {
            articleBeanList = new ArrayList<>();
        }
        articleBeanList.clear();
        articleBeanList.addAll(data);
        LogUtil.e(TAG, "updateData: " + articleBeanList.toString());
        //更新适配器
        this.notifyDataSetChanged();
    }

    /**
     * 刷新数据源  添加数据
     *
     * @param data 最新的数据
     */
    public void addData(List<ArticleBean> data) {
        if (data == null) {
            return;
        }
        if (articleBeanList == null) {
            articleBeanList = new ArrayList<>();
        }
        articleBeanList.addAll(data);
        LogUtil.e(TAG, "addData: " + articleBeanList.toString());
        //更新适配器
        this.notifyDataSetChanged();
    }

}
