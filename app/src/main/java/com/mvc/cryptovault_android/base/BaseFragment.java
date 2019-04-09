package com.mvc.cryptovault_android.base;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.mvc.cryptovault_android.activity.SelectLoginActivity;
import com.mvc.cryptovault_android.common.Constant;

import cn.jpush.android.api.JPushInterface;

import static com.mvc.cryptovault_android.common.Constant.SP.*;

public abstract class BaseFragment extends Fragment {

    protected View rootView;
    protected Context context;
    protected BaseActivity activity;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (rootView == null) {
            rootView = inflater.inflate(getLayoutId(), container, false);
            initView();
            initData();
        }
        return rootView;
    }

    protected void startTaskActivity(Activity activity) {
        SPUtils.getInstance().remove(REFRESH_TOKEN);
        SPUtils.getInstance().remove(UPDATE_PASSWORD_TYPE);
        SPUtils.getInstance().remove(TOKEN);
        JPushInterface.deleteAlias(activity.getApplicationContext(), SPUtils.getInstance().getInt(USER_ID));
        SPUtils.getInstance().remove(USER_ID);
        SPUtils.getInstance().remove(USER_PUBLIC_KEY);
        SPUtils.getInstance().remove(USER_SALT);
        SPUtils.getInstance().remove(USER_GOOGLE);
        Intent intent = new Intent(activity, SelectLoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }


    @Override
    public void onAttach(Context context) {
        this.activity = (BaseActivity) context;
        this.context = context;
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        this.context = null;
    }

    protected abstract void initData();

    protected abstract void initView();

    protected abstract int getLayoutId();

    protected void showDialog(String msg) {

    }

    protected void showToast(int resId) {
        ToastUtils.showLong(resId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    protected String getToken() {
        return SPUtils.getInstance().getString(TOKEN);
    }

    protected String getDefalutRate() {
        return SPUtils.getInstance().getString(Constant.SP.SET_RATE);
    }
}

