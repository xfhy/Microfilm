# 1.把系列详情页面内容全部填充上去

# 2.让EditText不能自动获取焦点
解决之道：在EditText的父级控件中找一个，设置成

      android:focusable="true"  
       android:focusableInTouchMode="true"
       
# 3. edittext 下划线颜色更改

    styles里apptheme（app主题）
    加 <item name="colorAccent">@color/primary_blue（其他颜色也行）</item>
    
# 4. Android中的EditText默认时不弹出软键盘的方法

      在 父 Activity 中 onCreate 中加上
    
           getWindow().setSoftInputMode(   WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
           
# 5.写banner的点击事件
# 6.新建Activity,banner点击进去的详情

# 7.通过WebView获取网页标题

    //可以查看加载进度,以及js调用监控的客户端
    mWebView.setWebChromeClient(new WebChromeClient() {
        @Override
        public void onReceivedTitle(WebView view, String title) {
            super.onReceivedTitle(view, title);
            mTitle.setText(title);
        }
    });
    
 # 8.完成首页banner的点击,以及可以点击进去查看完整的详情
 # 9.单独将设置界面的布局,抽取出来,定义一个自定义View
 并且设置自定义属性
 
    1. 在`res/value/`下新建`attrs.xml`声明节点declare-styleable
    
    		<?xml version="1.0" encoding="utf-8"?>
    		<resources>
    		    
    		    <declare-styleable name="ToggleView">
    		        <!--fornat属性用来指定属性的类型
    			      dimension表示大小
    			      reference引用属性
    			      color是颜色
    			      -->
    			      <attr name="switch_background" format="reference" />
    			      <attr name="slide_button" format="reference" />
    			      <attr name="switch_state" format="boolean" />
    		    </declare-styleable>
    		    
    		</resources>
    
    	2. R会自动创建变量
    
    		attr 3个变量
    		styleable 一个int数组, 3个变量(保存位置)
    
    	3. 在用到自定义控件的xml配置声明的属性/ 注意添加命名空间
    	
    	    <RelativeLayout xmlns:android="http://schemas.android.com/apk/res/android"
    		    xmlns:tools="http://schemas.android.com/tools"
    			xmlns:attr="http://schemas.android.com/apk/res-auto"
    		    android:layout_width="match_parent"
    		    android:layout_height="match_parent"
    		    tools:context="com.xfhy.toggleview.MainActivity$PlaceholderFragment" >
    		
    		    <com.xfhy.toggleview.view.ToggleView
    		        android:id="@+id/tv_switch"
    		        android:layout_width="wrap_content"
    		        android:layout_height="wrap_content"
    		        attr:switch_background="@drawable/switch_background"
    		        attr:slide_button="@drawable/slide_button"
    		        attr:switch_state="false"	
    		        android:layout_centerInParent="true" />
    		
    		</RelativeLayout>
    
    	4. 然后在自定义控件的构造函数中获取并使用
    
    		// 获取配置的自定义属性   
    		xmlns:attr="http://schemas.android.com/apk/res/com.xfhy.toggleview"
    		String namespace = "http://schemas.android.com/apk/res/com.xfhy.toggleview";
    		int switchBackgroundResource = attrs.getAttributeResourceValue(namespace , "switch_background", -1);
    		int slideButtonResource = attrs.getAttributeResourceValue(namespace, "slide_button", -1);
    		boolean switchState = attrs.getAttributeBooleanValue(namespace, "switch_state", false);
 
# 10.设置界面的布局基本完成,差一个在wifi下观看的布局
# 11.设置界面加入自定义ViewGroup,开关型的item
# 12.通过Glide获取Glide缓存的图片大小,并清除缓存
# 13.获取手机存储可用大小和手机外部存储可用大小
# 14.获取V电影版本号
# 15.设置界面分享
