package com.mvc.ttpay_android.model;

import com.mvc.ttpay_android.MyApplication;
import com.mvc.ttpay_android.api.ApiStore;
import com.mvc.ttpay_android.base.BaseModel;
import com.mvc.ttpay_android.bean.UserInfoBean;
import com.mvc.ttpay_android.contract.IMineContract;
import com.mvc.ttpay_android.utils.RetrofitUtils;
import com.mvc.ttpay_android.utils.RxHelper;

import io.reactivex.Observable;

public class MineModel extends BaseModel implements IMineContract.IMineModel {
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
