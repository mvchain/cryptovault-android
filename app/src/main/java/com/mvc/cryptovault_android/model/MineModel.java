package com.mvc.cryptovault_android.model;

import com.mvc.cryptovault_android.MyApplication;
import com.mvc.cryptovault_android.api.ApiStore;
import com.mvc.cryptovault_android.base.BaseModel;
import com.mvc.cryptovault_android.bean.UserInfoBean;
import com.mvc.cryptovault_android.contract.MineContract;
import com.mvc.cryptovault_android.utils.RetrofitUtils;
import com.mvc.cryptovault_android.utils.RxHelper;

import io.reactivex.Observable;
import io.reactivex.functions.Function;

public class MineModel extends BaseModel implements MineContract.IMineModel {
    public static MineModel getInstance() {
        return new MineModel();
    }

    @Override
    public Observable<UserInfoBean> getUserInfo() {
        return RetrofitUtils.client(MyApplication.getBaseUrl(),ApiStore.class).getUserInfo(MyApplication.getTOKEN())
                .compose(RxHelper.rxSchedulerHelper())
                .map(responseBody -> responseBody);
    }
}
