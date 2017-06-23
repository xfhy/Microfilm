# 记录工作日志
>* 写了哪些页面
>* 用了哪些技术
>* 使用技能的遇到什么问题bug
>* bug是如何解决的

# 1. bug1
> 昨天老师带着我们搭好的框架,今天我忽然发现一个小问题,就是当菜单显示出来的时候,
主界面的菜单按钮还是可以点击.

解决:哇,搞了很久,最好老师一句话  把上层的控件设置一个点击事件,把点击事件消费掉就行了

# 2. 使用第三方库

- Glide 加载图片
- OkHttp 网络请求
- butterknife 快速findViewById

# 3. 问题:在主线程中使用OkHttp访问网络数据
> 得到NetworkOnMainThreadException问题

原来OkHttp访问网络没有单独搞一个线程,于是我就自己弄了个子线程,去访问网络

# 4. 使用GSON一直报错
> com.google.gson.JsonSyntaxException: Java.lang.IllegalStateException: closed
 
解决:OkHttp请求,response.body().string()只能调用一次

# 5. RecyclerView一直不显示数据

解决: 啊,原来是忘记了设置LayoutManager()

# 6. RecyclerView的滚动监听,实现上拉加载更多

写一个类继承自RecyclerView.OnScrollListener,然后当判断需要加载的时候调用onLoadMore()--这是自定义的方法

# 7. 轮播图

使用了第三方控件banner

# 8. (未解决)在写频道的时候,发现RecyclerView中间始终有一定的间隔,很不爽

RecyclerView没有可以直接设置间距的属性，但可以用ItemDecoration来装饰一个item，所以继承重写ItemDecoration就可以实现间距了

啊,好吧,网上就这一种方式....然而我的却不起任何作用....尴尬

# 9. (未解决)bug:首页中,轮播图不能跟着下面的列表一起滑动...

思路(不一定可行):将Banner布局写入RecyclerView头布局中
可参考:http://blog.csdn.net/xiaozhiwz/article/details/49275609

# 10. bug:电影详情页一直白茫茫一片

解决:原来是setupView()方法一直没被调用

# 11. (未解决)bug:电影详情页面的任何链接都无法点击..
