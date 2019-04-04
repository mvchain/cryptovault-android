package com.mvc.cryptovault_android.model;

import android.support.annotation.Nullable;

import com.mvc.cryptovault_android.MyApplication;
import com.mvc.cryptovault_android.api.ApiStore;
import com.mvc.cryptovault_android.base.BaseModel;
import com.mvc.cryptovault_android.bean.MsgBean;
import com.mvc.cryptovault_android.contract.IMsgContract;
import com.mvc.cryptovault_android.utils.RetrofitUtils;
import com.mvc.cryptovault_android.utils.RxHelper;

import io.reactivex.Observable;

public class MsgModel extends BaseModel implements IMsgContract.IMsgModel {

    @Nullable
    public static MsgModel getInstance() {
        return new MsgModel();
    }

    @Override
    public Observable<MsgBean> getMsg(long timestamp, int type, int pageSize) {
        return RetrofitUtils.client(MyApplication.getBaseUrl(),ApiStore.class).getMsg(MyApplication.getTOKEN(), timestamp, type, pageSize).compose(RxHelper.rxSchedulerHelper()).map(msgBean -> msgBean);
    }
}
