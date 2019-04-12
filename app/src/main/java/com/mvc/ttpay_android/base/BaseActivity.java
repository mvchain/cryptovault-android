package com.mvc.ttpay_android.base;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.WindowManager;

import com.blankj.utilcode.util.KeyboardUtils;
import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.gyf.barlibrary.ImmersionBar;
import com.mvc.ttpay_android.activity.SelectLoginActivity;
import com.mvc.ttpay_android.common.Constant;
import com.mvc.ttpay_android.utils.LanguageUtils;
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;

import java.util.Locale;

import cn.jpush.android.api.JPushInterface;

import static com.mvc.ttpay_android.common.Constant.SP.*;

public abstract class BaseActivity extends RxAppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());
        initView();
        initData();
    }

    protected abstract int getLayoutId();

    protected abstract void initData();

    protected abstract void initView();

    /**
     * 普通的页面跳转
     *
     * @param clazz
     */
    protected void startActivity(Class clazz) {
        startActivity(new Intent(this, clazz));
    }

    /**
     * 带参数的页面跳转
     *
     * @param clazz
     */
    protected void startActivity(Class clazz, Intent intent) {
        intent.setClass(this, clazz);
        startActivity(intent);
    }

    /**
     * 带参数的页面跳转
     *
     * @param clazz
     */
    protected void startActivity(Class clazz, Bundle bundle) {
        Intent intent = new Intent(this, clazz);
        intent.putExtra("bundle", bundle);
        startActivity(intent);
    }

    protected void startTaskActivity(Activity activity) {
        SPUtils.getInstance().remove(REFRESH_TOKEN);
        SPUtils.getInstance().remove(UPDATE_PASSWORD_TYPE);
        SPUtils.getInstance().remove(TOKEN);
        JPushInterface.deleteAlias(getApplicationContext(), SPUtils.getInstance().getInt(USER_ID));
        SPUtils.getInstance().remove(USER_ID);
        SPUtils.getInstance().remove(USER_PUBLIC_KEY);
        SPUtils.getInstance().remove(USER_SALT);
        SPUtils.getInstance().remove(USER_GOOGLE);
        Intent intent = new Intent(activity, SelectLoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

    protected void showToast(String toast) {
        ToastUtils.showShort(toast);
    }

    protected void showToast(int msgId) {
        ToastUtils.showLong(msgId);
    }

    protected String getToken() {
        return SPUtils.getInstance().getString(TOKEN);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        KeyboardUtils.hideSoftInput(this);
        ImmersionBar.with(this).destroy();
    }

    protected void setAlpha(float alpha) {
        WindowManager.LayoutParams attributes = getWindow().getAttributes();
        attributes.alpha = alpha;
        getWindow().setAttributes(attributes);
    }

    protected String getDefalutRate() {
        return SPUtils.getInstance().getString(Constant.SP.SET_RATE);
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(LanguageUtils.Companion.wrapLocale(newBase, new Locale(LanguageUtils.Companion.getUserSetLocal())));
    }

}
