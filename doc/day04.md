# day04

# 1. 让搜索界面的EditText默认把软件盘的回车改为搜索

    android:imeOptions="actionSearch"
    android:inputType="text"

# 2. 搜索界面的EditText监听回车事件

    //设置当enter按下时的监听事件
     mEdit.setOnEditorActionListener(this);
 
# 3. 搜索界面的EditText  动态显示和隐藏软键盘

     ///////////////////////////////////////// 隐藏或显示软键盘 /////////////////////////////////  
                 public static void hideSoftKeyboard(EditText editText, Context context) {  
                     if (editText != null && context != null) {  
                         InputMethodManager imm = (InputMethodManager) context  
                                 .getSystemService(Context.INPUT_METHOD_SERVICE);  
                         imm.hideSoftInputFromWindow(editText.getWindowToken(), 0);  
                     }  
                 }  
                 public static void showSoftKeyboard(EditText editText, Context context) {  
                     if (editText != null && context != null) {  
                         InputMethodManager imm = (InputMethodManager) context  
                                 .getSystemService(Activity.INPUT_METHOD_SERVICE);  
                         imm.showSoftInput(editText, 0);  
                     }  
                 }  
                 
 # 4. 监听EditText文本输入的变化
 
    mEdit.addTextChangedListener(this);
    
 # 5. 移除SP中的节点
 
    mSharedPreferences.edit().remove(key).apply();

# 6. 将搜索记录保存到SP中,下一次再进行回显到ListView上

- 由于是简单的一行数据,所以使用简单的ListView
- 搜索记录只保存5个最近的搜索记录

# 7. 幕后界面添加分割线

        //添加TabLayout分割线
        LinearLayout linearLayout = (LinearLayout) mTabLayout.getChildAt(0);
        linearLayout.setShowDividers(LinearLayout.SHOW_DIVIDER_MIDDLE);
        linearLayout.setDividerDrawable(ContextCompat.getDrawable(getActivity(),
                R.drawable.layout_divider_vertical));
        linearLayout.setDividerPadding(35);
        linearLayout.setMinimumHeight(20);

# 8. 电影详情页视频布局,底部布局

并实现电影播放控制,底部控制

# 9. 电影详情页分享布局完成

# 10. 电影详情页分享  需要将分享布局动态弹出来

这里需要用到PopupWindow来弹出布局

# 11. PopupWindow弹出动画

- 需要一个出现动画
- 需要一个消失动画
- 设置动画`popupWindow.setAnimationStyle(R.style.popwin_anim_style);`

# 12. 将分享弹出PopupWindow封装到工具类中,方便下一次弹出

- 实现了分享布局的点击事件

# 13.把幕后文章界面的布局做出来了

# 14.完成幕后文章界面所有逻辑处理

