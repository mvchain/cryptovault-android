package com.mvc.cryptovault_android.utils;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.util.Log;

import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.Utils;
import com.mvc.cryptovault_android.MyApplication;
import com.mvc.cryptovault_android.api.ApiStore;
import com.mvc.cryptovault_android.bean.HttpTokenBean;

import java.util.Arrays;
import java.util.HashSet;

import cn.jpush.android.api.JPushInterface;
import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import retrofit2.HttpException;

import static com.mvc.cryptovault_android.common.Constant.SP.REFRESH_TOKEN;
import static com.mvc.cryptovault_android.common.Constant.SP.TAG_NAME;
import static com.mvc.cryptovault_android.common.Constant.SP.TOKEN;

public class RxHelper {
    /**
     * 统一线程处理
     * <p>
     * 发布事件io线程，接收事件主线程
     */
    public static <T> ObservableTransformer<T, T> rxSchedulerHelper() {//compose处理线程 并且处理token过期问题
        return upstream -> upstream.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
}
