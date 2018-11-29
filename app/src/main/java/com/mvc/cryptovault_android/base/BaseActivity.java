package com.mvc.cryptovault_android.base;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.Toast;

import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;

public abstract class BaseActivity extends RxAppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());
    }

    protected abstract int getLayoutId();

    /**
     * 普通的页面跳转
     * @param clazz
     */
    protected void startActivity(Class clazz){
        startActivity(new Intent(this,clazz));
    }

    /**
     *带参数的页面跳转
     * @param clazz
     */
    protected void startActivity(Class clazz,Intent intent){
        intent.setClass(this,clazz);
        startActivity(intent);
    }
    /**
     *带参数的页面跳转
     * @param clazz
     */
    protected void startActivity(Class clazz,Bundle bundle){
        Intent intent = new Intent(this, clazz);
        intent.putExtra("bundle",bundle);
        startActivity(intent);
    }

    protected void showToast(String toast){
        Toast.makeText(this, toast, Toast.LENGTH_SHORT).show();
    }
    protected void showToast(int msgId){
        Toast.makeText(this, getResources().getText(msgId), Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
