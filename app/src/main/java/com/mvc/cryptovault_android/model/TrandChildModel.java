package com.mvc.cryptovault_android.model;

import android.support.annotation.Nullable;

import com.mvc.cryptovault_android.MyApplication;
import com.mvc.cryptovault_android.api.ApiStore;
import com.mvc.cryptovault_android.base.BaseModel;
import com.mvc.cryptovault_android.bean.TrandChildBean;
import com.mvc.cryptovault_android.contract.TrandChildContract;
import com.mvc.cryptovault_android.utils.RetrofitUtils;
import com.mvc.cryptovault_android.utils.RxHelper;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;

public class TrandChildModel extends BaseModel implements TrandChildContract.ITrandChildModel {
    private List<TrandChildBean> strings = new ArrayList<>();

    @Nullable
    public static TrandChildModel getInstance() {
        return new TrandChildModel();
    }

    @Override
    public Observable<TrandChildBean> getVrt(int pairType) {

        return RetrofitUtils.client(ApiStore.class).getVrtAndBalance(MyApplication.getTOKEN(), pairType).compose(RxHelper.rxSchedulerHelper()).map(trandChildBean -> trandChildBean);
    }

    @Override
    public Observable<TrandChildBean> getBalanceTransactions(int pairType) {
        return RetrofitUtils.client(ApiStore.class).getVrtAndBalance(MyApplication.getTOKEN(), pairType).compose(RxHelper.rxSchedulerHelper()).map(trandChildBean -> trandChildBean);

    }
}
