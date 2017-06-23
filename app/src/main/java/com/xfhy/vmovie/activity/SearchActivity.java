package com.xfhy.vmovie.activity;

import android.content.Context;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.xfhy.vmovie.R;
import com.xfhy.vmovie.base.BaseActivity;
import com.xfhy.vmovie.utils.LogUtil;
import com.xfhy.vmovie.utils.SpUtil;

import static com.xfhy.vmovie.constants.SharedParams.SEARCH_HISTORY;

/**
 * 搜索界面
 *
 * @author xfhy
 *         create at 2017年6月19日21:53:48
 */
public class SearchActivity extends BaseActivity implements View.OnClickListener, TextView
        .OnEditorActionListener, TextWatcher {

    private static final String TAG = "SearchActivity";
    private TextView mCancel;
    private EditText mEdit;
    private Button mClearHistroy;
    private ImageView mClearInput;
    private ArrayAdapter<String> mAdapter;
    private static final int HISTROY_INPUT_COUNT = 5;
    private ListView mHistroyList;
    private String mHistoryText;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_search;
    }

    @Override
    protected void initView() {
        mCancel = (TextView) findViewById(R.id.tv_cancel);
        mEdit = (EditText) findViewById(R.id.et_search_edit);
        mClearInput = (ImageView) findViewById(R.id.tv_search_input_clear);
        mClearHistroy = (Button) findViewById(R.id.btn_clear_histroy);
        mHistroyList = (ListView) findViewById(R.id.lv_histroy_input);

        //取消
        mCancel.setOnClickListener(this);
        mClearHistroy.setOnClickListener(this);
        mClearInput.setOnClickListener(this);

        //设置当enter按下时的监听事件
        mEdit.setOnEditorActionListener(this);
        //监听EditText文本输入的变化
        mEdit.addTextChangedListener(this);

        //初始化搜索记录
        initSearchHistroy();
    }

    /**
     * 初始化搜索记录
     */
    private void initSearchHistroy() {
        // 获取搜索记录SP内容
        mHistoryText = SpUtil.getString(this, SEARCH_HISTORY, "");
        if (TextUtils.isEmpty(mHistoryText)) {
            mClearHistroy.setVisibility(View.INVISIBLE);
            return;
        }

        try {
            // 用逗号分割内容返回数组
            String[] historyArr = mHistoryText.split(",");

            // 保留前5条数据
            if (historyArr.length > HISTROY_INPUT_COUNT) {
                String[] newArrays = new String[HISTROY_INPUT_COUNT];
                // 实现数组之间的复制   复制5个
                System.arraycopy(historyArr, 0, newArrays, 0, HISTROY_INPUT_COUNT);

                //将原来的超过5个的记录除去
                StringBuilder textTemp = new StringBuilder();
                for (int i = 0; i < HISTROY_INPUT_COUNT; i++) {
                    textTemp.append(newArrays[i] + ",");
                }
                mHistoryText = textTemp.toString();
                LogUtil.e(TAG, "initSearchHistroy: 现在mHistoryText的值是" + mHistoryText);

                mAdapter = new ArrayAdapter<>(this,
                        R.layout.item_search_histroy_list, newArrays);
            } else {
                // 新建适配器，适配器数据为搜索历史文件内容
                mAdapter = new ArrayAdapter<>(this,
                        R.layout.item_search_histroy_list, historyArr);
            }

            // 设置适配器
            mHistroyList.setAdapter(mAdapter);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 保存当前搜索的内容
     */
    private void saveSearchText() {
        String input = mEdit.getText().toString();
        if (!TextUtils.isEmpty(input) && !mHistoryText.contains(input)) {
            // 利用StringBuilder.append新增内容，逗号便于读取内容时用逗号拆分开
            StringBuilder textTemp = new StringBuilder(input + ",");
            textTemp.append(mHistoryText);
            mHistoryText = textTemp.toString();
            LogUtil.e(TAG, "保存到SP中的内容是: " + textTemp.toString());
            SpUtil.putString(this, SEARCH_HISTORY, textTemp.toString());
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_cancel:
                finish();
                break;
            case R.id.btn_clear_histroy:  //清空历史
                mHistoryText = "";
                SpUtil.remove(this, SEARCH_HISTORY); //移除节点
                //隐藏列表
                mHistroyList.setVisibility(View.GONE);
                //隐藏按钮
                mClearHistroy.setVisibility(View.GONE);
                break;
            case R.id.tv_search_input_clear:  //清空这次的输入
                mEdit.setText("");
                mClearInput.setVisibility(View.INVISIBLE);  //隐藏清空输入的按钮
                break;
        }
    }

    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        if (event != null && event.getKeyCode() == KeyEvent.KEYCODE_ENTER) {
            LogUtil.e(TAG, "onEditorAction: 用户按下了回车键");

            if (!TextUtils.isEmpty(mEdit.getText().toString())) {
                //TODO 这里需要做的是搜索

                //需要保存记录
                saveSearchText();

                //隐藏软键盘
                InputMethodManager imm = (InputMethodManager) getSystemService(Context
                        .INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(mEdit.getWindowToken(), 0);

                //隐藏ListView,隐藏清空历史记录按钮
                mHistroyList.setVisibility(View.INVISIBLE);
                mClearHistroy.setVisibility(View.INVISIBLE);

            }
            return true;
        }
        return false;
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
    }

    @Override
    public void afterTextChanged(Editable s) {
        String inputText = mEdit.getText().toString();
        LogUtil.e(TAG, "afterTextChanged: 您输入的内容是" + inputText);

        if (TextUtils.isEmpty(inputText)) {
            //如果输入框为空  则需要隐藏清空文字的按钮
            mClearInput.setVisibility(View.INVISIBLE);
        } else {
            //需要显示那个清空输入的按钮
            mClearInput.setVisibility(View.VISIBLE);
        }
    }
}
