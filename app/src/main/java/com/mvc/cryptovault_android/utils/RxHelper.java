package com.mvc.cryptovault_android.utils;

import android.annotation.SuppressLint;
import android.content.Intent;

import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.Utils;
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
                .observeOn(AndroidSchedulers.mainThread())
                .retryWhen((Function<Observable<Throwable>, ObservableSource<?>>) throwableObservable -> {
                    return throwableObservable.flatMap((Function<Throwable, ObservableSource<?>>) throwable -> {
                        if (throwable instanceof HttpException) {
                            if (((HttpException) throwable).code() == 403) {
                                return RetrofitUtils.client(ApiStore.class).refreshToken(SPUtils.getInstance().getString(REFRESH_TOKEN))
                                        .subscribeOn(Schedulers.io())
                                        .observeOn(AndroidSchedulers.mainThread())
                                        .doOnNext(httpTokenBean -> {
                                            SPUtils.getInstance().put(TOKEN, httpTokenBean.getData());
                                            //使用新token重新注册极光
                                            RetrofitUtils.client(ApiStore.class).getPushTag(httpTokenBean.getData()).compose(RxHelper.rxSchedulerHelper())
                                                    .subscribe(tagBean -> {
                                                        if (tagBean.getCode() == 200) {
                                                            SPUtils.getInstance().put(TAG_NAME, tagBean.getData());
                                                        }
                                                        String[] tags = tagBean.getData().split(",");
                                                        for (int i = 0; i < tags.length; i++) {
                                                            if (i == 0) {
                                                                JPushInterface.setTags(Utils.getApp().getApplicationContext(), Integer.parseInt(tags[i]), new HashSet<>(Arrays.asList(tags[i])));
                                                            } else {
                                                                JPushInterface.addTags(Utils.getApp().getApplicationContext(), Integer.parseInt(tags[i]), new HashSet<>(Arrays.asList(tags[i])));
                                                            }
                                                        }
                                                    }, throwable1 -> {
                                                        LogUtils.e("RxHelper", throwable1.getMessage());
                                                    });
                                        })
                                        .doOnError(throwable12 -> {
                                            Intent intent = new Intent();
                                            intent.setAction("android.login");
                                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                            Utils.getApp().startActivity(intent);
                                        });
                            } else {
                                return Observable.error(throwable);
                            }
                        } else {
                            return Observable.error(throwable);
                        }
                    });
                });
    }
}
