package com.xfhy.vmovie.activity;

import android.content.Context;
import android.support.design.widget.Snackbar;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.xfhy.vmovie.R;
import com.xfhy.vmovie.base.BaseActivity;
import com.xfhy.vmovie.utils.LogUtil;

/**
 * 登录界面
 *
 * @author xfhy
 *         create at  2017年6月22日10:50:33
 */
public class LoginActivity extends BaseActivity implements TextView.OnEditorActionListener,
        TextWatcher, View.OnClickListener, View.OnFocusChangeListener {

    private static final String TAG = "LoginActivity";
    private static final String EMAIL_REGEX = "^[a-zA-Z0-9_-]+@[a-zA-Z0-9_-]+(\\.[a-zA-Z0-9_-]+)+$";
    private EditText mEmail;
    private EditText mPass;
    private TextView mLogin;
    private ImageView mLoginFromQQ;
    private ImageView mLoginFromSina;
    private ImageView mLoginFromWechat;
    private ImageView mBack;
    private ImageView mEmailClear;
    private ImageView mPassClear;
    private FrameLayout mRootView;
    private boolean isInputEmail = true;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    protected void initView() {
        //让软件盘默认不弹出
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

        mRootView = (FrameLayout) findViewById(R.id.fl_login_root_view);
        mEmail = (EditText) findViewById(R.id.et_login_email);
        mPass = (EditText) findViewById(R.id.et_login_password);
        mLogin = (TextView) findViewById(R.id.tv_login);
        mEmailClear = (ImageView) findViewById(R.id.tv_email_input_clear);
        mPassClear = (ImageView) findViewById(R.id.tv_password_input_clear);
        mLoginFromQQ = (ImageView) findViewById(R.id.iv_login_from_qq);
        mLoginFromSina = (ImageView) findViewById(R.id.iv_login_from_sina);
        mLoginFromWechat = (ImageView) findViewById(R.id.iv_login_from_wechat);
        mBack = (ImageView) findViewById(R.id.iv_login_back);

        //设置当enter按下时的监听事件
        mEmail.setOnEditorActionListener(this);
        //监听EditText文本输入的变化
        mEmail.addTextChangedListener(this);
        mEmail.setOnFocusChangeListener(this);
        //设置当enter按下时的监听事件
        mPass.setOnEditorActionListener(this);
        //监听EditText文本输入的变化
        mPass.addTextChangedListener(this);

        mLogin.setOnClickListener(this);
        mEmailClear.setOnClickListener(this);
        mPassClear.setOnClickListener(this);
        mLoginFromQQ.setOnClickListener(this);
        mLoginFromSina.setOnClickListener(this);
        mLoginFromWechat.setOnClickListener(this);
        mBack.setOnClickListener(this);

    }

    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        if (event != null && event.getKeyCode() == KeyEvent.KEYCODE_ENTER) {
            LogUtil.e(TAG, "onEditorAction: 用户按下了回车键");

            if (actionId == R.id.et_login_email) {
                LogUtil.e(TAG, "onEditorAction: 从邮箱按下的enter");
            }
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
        if (isInputEmail) {
            LogUtil.e(TAG, "afterTextChanged: 正在输入邮箱");
            String inputEmail = mEmail.getText().toString();
            LogUtil.e(TAG, "afterTextChanged: 您输入的内容是" + inputEmail);

            if (TextUtils.isEmpty(inputEmail)) {
                //如果输入框为空  则需要隐藏清空文字的按钮
                mEmailClear.setVisibility(View.INVISIBLE);
            } else {
                //需要显示那个清空输入的按钮
                mEmailClear.setVisibility(View.VISIBLE);
            }
        } else {
            String inputPass = mPass.getText().toString();
            LogUtil.e(TAG, "afterTextChanged: 您输入的内容是" + inputPass);

            if (TextUtils.isEmpty(inputPass)) {
                //如果输入框为空  则需要隐藏清空文字的按钮
                mPassClear.setVisibility(View.INVISIBLE);
            } else {
                //需要显示那个清空输入的按钮
                mPassClear.setVisibility(View.VISIBLE);
            }
        }

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_login:
                loginCheck();
                break;
            case R.id.tv_email_input_clear:
                mEmail.setText("");
                mEmailClear.setVisibility(View.INVISIBLE);
                break;
            case R.id.tv_password_input_clear:
                mPass.setText("");
                mPassClear.setVisibility(View.INVISIBLE);
                break;
            case R.id.iv_login_from_qq:
                Snackbar.make(mRootView, "QQ登录成功!", Snackbar.LENGTH_LONG).setAction("取消", this)
                        .show();
                break;
            case R.id.iv_login_from_sina:
                Snackbar.make(mRootView, "新浪微博登录成功!", Snackbar.LENGTH_LONG).setAction("取消", this)
                        .show();
                break;
            case R.id.iv_login_from_wechat:
                Snackbar.make(mRootView, "微信登录成功!", Snackbar.LENGTH_LONG).setAction("取消", this)
                        .show();
                break;
            case R.id.iv_login_back:
                finish();
                break;
            default:
                Toast.makeText(this, "取消有用吗?", Toast.LENGTH_SHORT).show();
                break;
        }
    }

    /**
     * 检查登录是否合法
     */
    private void loginCheck() {
        String email = mEmail.getText().toString().trim();
        String pass = mPass.getText().toString().trim();
        if (TextUtils.isEmpty(email) || TextUtils.isEmpty(pass)) {
            Toast.makeText(this, "输入不能为空", Toast.LENGTH_SHORT).show();
            return;
        }
        if (!email.matches(EMAIL_REGEX)) {
            Toast.makeText(this, "请输入正确的邮箱格式", Toast.LENGTH_SHORT).show();
            return;
        }
        if (pass.length() < 6) {
            Toast.makeText(this, "密码长度小于6位", Toast.LENGTH_SHORT).show();
            return;
        }
        Toast.makeText(this, "格式正确", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        switch (v.getId()) {
            case R.id.et_login_email:
                if (hasFocus) {
                    isInputEmail = true;
                    if (mEmail.getText().toString().length()>0) {
                        mEmailClear.setVisibility(View.VISIBLE);
                    }
                    mPassClear.setVisibility(View.INVISIBLE);
                } else {
                    isInputEmail = false;
                    mEmailClear.setVisibility(View.INVISIBLE);
                }
                break;
            case R.id.et_login_password:
                if (hasFocus) {
                    isInputEmail = false;
                    if (mPass.getText().toString().length()>0) {
                        mPassClear.setVisibility(View.VISIBLE);
                    }
                    mEmailClear.setVisibility(View.INVISIBLE);
                } else {
                    isInputEmail = true;
                    mPassClear.setVisibility(View.INVISIBLE);
                }
                break;
        }
    }
}
