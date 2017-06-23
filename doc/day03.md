# day03 

# 1. 监测Activity的生命周期事件

在applicatio中加入  this.registerActivityLifecycleCallbacks(this);

# 2.缓存数据库设计
    
    CacheDataOpenHelper
    
    _id  integer
    type integer   类型(最新页的轮播图,最新电影列表,频道列表,频道详情)
    response text  服务端返回的json数据,全部缓存,需要用的时候再解析
    date     text  缓存的日期时间
   
# 3.bug:数据库语句报错

    db.execSQL("create table " + CACHE_TABLE_NAME + "(" +
                    CACHE_TABLE_ID + " integer primary key autoincrement," +
                    CACHE_TABLE_TYPE + " integer not null," +
                    CACHE_TABLE_RESPONSE + " text," +
                    CACHE_TABLE_DATE + " text" +
                    ");");

解决:autoincrement关键字必须写在primary key后面才行

# 4.缓存一些界面

最新电影列表数据,轮播图数据,频道列表数据,系列界面列表数据


# 5.准备写幕后界面,从网络获取幕后界面的标题列表

获取出来了,而且还可以缓存了
第二次进时如果没网则直接加载缓存数据

# 6.感觉幕后界面就写一个fragment就行了

然后不断地复用,只需要传递不同的值就行了


# 7.设置TabLayout的下划线颜色

app:tabIndicatorColor="@color/color_black"  下划线的颜色

# 8. 幕后界面框架已搭建完成

幕后界面可现实所有TabLayout标题,这些标题都是从网络加载回来的,然后ViewPager也可以正常使用了,可以左右滑动

# 9. 幕后界面文章RecyclerView的子项的布局

# 10.幕后界面基本完成

- 可以从网络加载TabLayout标题,断网时可从数据库缓存读取标题
- 可以从网络获取文章内容,断网时可以从数据库读取缓存的文章列表内容

# 11.幕后界面加入下拉刷新

# 12.EditText包围

自定义一个drawable,自己画一个background,是shape

# 13. Android 设置EditText光标Cursor颜色及粗细

在android的输入框里，如果要修改光标的颜色及粗细步骤如下两步即可搞定：
1.在资源文件drawable下新建一个光标控制color_cursor.xml

    <?xml version="1.0" encoding="utf-8"?>
    <shape xmlns:android="http://schemas.android.com/apk/res/android" android:shape="rectangle">
        <size android:width="1dp" />
        <solid android:color="#008000"  />
    </shape>

2.设置EditText：android:textCursorDrawable="@drawable/color_cursor"

# 14. 半圆形按钮

    <shape xmlns:android="http://schemas.android.com/apk/res/android"
        android:shape="rectangle">

        <!--半圆形按钮-->

        <corners android:radius="60dip"/>
        <stroke android:width="0dp" android:color="@color/color_alpha_black_3"/>
        <solid android:color="@color/color_alpha_black_3"/>

    </shape>
