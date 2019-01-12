package com.mvc.cryptovault_android.base;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.WindowManager;
import android.widget.Toast;

import com.blankj.utilcode.util.KeyboardUtils;
import com.blankj.utilcode.util.SPUtils;
import com.mvc.cryptovault_android.activity.LoginActivity;
import com.mvc.cryptovault_android.activity.SelectLoginActivity;
import com.mvc.cryptovault_android.activity.StartActivity;
import com.mvc.cryptovault_android.common.Constant;
import com.mvc.cryptovault_android.utils.LanguageUtils;
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;

import java.util.Locale;

import static com.mvc.cryptovault_android.common.Constant.LANGUAGE.CHINESE;
import static com.mvc.cryptovault_android.common.Constant.SP.TOKEN;

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
        Intent intent = new Intent(activity, SelectLoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

    protected void showToast(String toast) {
        Toast.makeText(this, toast, Toast.LENGTH_SHORT).show();
    }

    protected void showToast(int msgId) {
        Toast.makeText(this, getResources().getText(msgId), Toast.LENGTH_SHORT).show();
    }

    protected String getToken() {
        return SPUtils.getInstance().getString(TOKEN);
    }

    @Override
    protected void onDestroy() {
        KeyboardUtils.hideSoftInput(this);
        super.onDestroy();
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
