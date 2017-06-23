# day02

# 1. 在写频道的时候,发现RecyclerView中间始终有一定的间隔,很不爽

RecyclerView没有可以直接设置间距的属性，但可以用ItemDecoration来装饰一个item，所以继承重写ItemDecoration就可以实现间距了

啊,好吧,网上就这一种方式....然而我的却不起任何作用....尴尬

解决方式:老师说官方不建议设置ItemDecoration,然后需要动态设置子项的宽度和高度,根据屏幕的宽度来设置就行

# 2. bug:首页中,轮播图不能跟着下面的列表一起滑动...

思路(不一定可行):将Banner布局写入RecyclerView头布局中
可参考:http://blog.csdn.net/xiaozhiwz/article/details/49275609

解决:已经将banner布局加入RecyclerView的头布局,现在可以跟着一起滑动了

# 3. bug:电影详情页面的任何链接都无法点击..

解决:原来里面的链接需要和后台人员交流一下,获取接口才可以写.

# 4.频道列表页

一个RecyclerView和一个adapter

# 5.频道详情页

一个RecyclerView和一个adapter

# 6.加入导航界面

三个fragment和一个viewPager

# 7.加入系列界面

就是一个RecyclerView

# 8.系列界面下拉刷新

RecyclerView.OnScrollListener监听recyclerView的滑动事件,然后当用户需要下拉刷新的时候,就加载分页数据,
然后再将加载出来的数据加到RecyclerView的adapter的数据的后面,刷新适配器就行

# 9.由于代码过度臃肿,不得不优化了一下代码

- 我将RecyclerView的滑动监听单独提取出了类,实现下拉刷新更容易
- 将OkHttp网络请求单独封装了起来,然后结果通过接口回调,自我感觉还比较良好啦,
代码确实变得稍微清晰了些,然后代码量也少了很多