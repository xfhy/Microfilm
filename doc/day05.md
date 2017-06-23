# day05

# 1.准备开始写系列详情页了

# 2. 自定义一个TextView可以展开,可以收缩

> 其实就是设置它的行数

# 3. 在上面的TextView展开收缩的时候动态设置下面的收缩展开按钮

设置它的Drawable然后就可以改变了,设置它的资源图片
    mShowHide.setCompoundDrawablesRelativeWithIntrinsicBounds(null,null,getResources().getDrawable(R.drawable.dropup),
                    null);
                    
# 4. 无用

SeriesDetailsStagingAdapter
StagingListFragment
fragment_staging_list
StagingSetBean  可以不用实现Seraliable..

# 5.在代码中动态生成TabLayout的标签

# 6.将系列布局中的集列表(RecyclerView)的子项布局完成

# 7.系列界面的RecyclerView可从网络加载数据并显示到界面上

# 8.GSON使用笔记（1） -- 序列化时排除字段的几种方式

- 排除transient字段 给字段加上transient修饰符就可以了
- 使用@Expose注解

# 9.使在播放视频的子项图片是灰色背景

    //设置正在播放的视频图片为灰色
    ColorMatrix matrix = new ColorMatrix();
    matrix.setSaturation(0);

    ColorMatrixColorFilter filter = new ColorMatrixColorFilter(matrix);
    //想要恢复颜色的话,设置为null
    holder.videoImg.setColorFilter(filter);