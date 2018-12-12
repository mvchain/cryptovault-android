package com.mvc.cryptovault_android.model;

import com.mvc.cryptovault_android.api.ApiStore;
import com.mvc.cryptovault_android.base.BaseModel;
import com.mvc.cryptovault_android.bean.TogeHisBean;
import com.mvc.cryptovault_android.contract.TogeHistroyContract;
import com.mvc.cryptovault_android.utils.RetrofitUtils;
import com.mvc.cryptovault_android.utils.RxHelper;

import io.reactivex.Observable;

public class TogHisModel extends BaseModel implements TogeHistroyContract.ITogeHisMol {
    public static TogHisModel getInstance() {
        return new TogHisModel();
    }

    @Override
    public Observable<TogeHisBean> getReservation(String token, int id, int pageSize, int type) {
        return RetrofitUtils.client(ApiStore.class).getReservation(token, id, pageSize, type).compose(RxHelper.rxSchedulerHelper()).map(togeHisBean -> togeHisBean);
    }

}
