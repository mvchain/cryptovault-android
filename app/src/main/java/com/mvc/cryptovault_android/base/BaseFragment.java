package com.mvc.cryptovault_android.base;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.blankj.utilcode.util.SPUtils;
import com.mvc.cryptovault_android.common.Constant;

import static com.mvc.cryptovault_android.common.Constant.SP.TOKEN;

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
        Toast.makeText(context, getResources().getString(resId), Toast.LENGTH_SHORT).show();
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

