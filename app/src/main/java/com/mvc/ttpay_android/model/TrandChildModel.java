package com.mvc.ttpay_android.model;

import android.support.annotation.Nullable;

import com.mvc.ttpay_android.MyApplication;
import com.mvc.ttpay_android.api.ApiStore;
import com.mvc.ttpay_android.base.BaseModel;
import com.mvc.ttpay_android.bean.TrandChildBean;
import com.mvc.ttpay_android.contract.ITrandChildContract;
import com.mvc.ttpay_android.utils.RetrofitUtils;
import com.mvc.ttpay_android.utils.RxHelper;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;

public class TrandChildModel extends BaseModel implements ITrandChildContract.ITrandChildModel {
    private List<TrandChildBean> strings = new ArrayList<>();

    @Nullable
    public static TrandChildModel getInstance() {
        return new TrandChildModel();
    }

    @Override
    public Observable<TrandChildBean> getVrt(int pairType) {
        return RetrofitUtils.client(MyApplication.getBaseUrl(),ApiStore.class).getVrtAndBalance(MyApplication.getTOKEN(), pairType).compose(RxHelper.rxSchedulerHelper()).map(trandChildBean -> trandChildBean);
    }

    @Override
    public Observable<TrandChildBean> getBalanceTransactions(int pairType) {
        return RetrofitUtils.client(MyApplication.getBaseUrl(),ApiStore.class).getVrtAndBalance(MyApplication.getTOKEN(), pairType).compose(RxHelper.rxSchedulerHelper()).map(trandChildBean -> trandChildBean);

    }
}
