package com.mvc.cryptovault_android.model;

import com.mvc.cryptovault_android.api.ApiStroe;
import com.mvc.cryptovault_android.base.BaseModel;
import com.mvc.cryptovault_android.bean.UserInfoBean;
import com.mvc.cryptovault_android.contract.MineContract;
import com.mvc.cryptovault_android.utils.RetrofitUtils;
import com.mvc.cryptovault_android.utils.RxHelper;

import io.reactivex.Observable;
import io.reactivex.functions.Function;
import okhttp3.ResponseBody;

public class MineModel extends BaseModel implements MineContract.IMineModel {
    public static MineModel getInstance() {
        return new MineModel();
    }

    @Override
    public Observable<UserInfoBean> getUserInfo(String token) {
        return RetrofitUtils.client(ApiStroe.class).getUserInfo(token)
                .compose(RxHelper.rxSchedulerHelper())
                .map(new Function<UserInfoBean, UserInfoBean>() {
            @Override
            public UserInfoBean apply(UserInfoBean responseBody) throws Exception {
                return responseBody;
            }
        });
    }
}
