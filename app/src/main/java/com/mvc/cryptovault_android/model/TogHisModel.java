package com.mvc.cryptovault_android.model;

import com.mvc.cryptovault_android.MyApplication;
import com.mvc.cryptovault_android.api.ApiStore;
import com.mvc.cryptovault_android.base.BaseModel;
import com.mvc.cryptovault_android.bean.TogeHisBean;
import com.mvc.cryptovault_android.contract.ITogeHistoryContract;
import com.mvc.cryptovault_android.utils.RetrofitUtils;
import com.mvc.cryptovault_android.utils.RxHelper;

import io.reactivex.Observable;

public class TogHisModel extends BaseModel implements ITogeHistoryContract.ITogeHisMol {
    public static TogHisModel getInstance() {
        return new TogHisModel();
    }

    @Override
    public Observable<TogeHisBean> getReservation(int id, int pageSize,String projectName, int type) {
        return RetrofitUtils.client(MyApplication.getBaseUrl(),ApiStore.class).getReservation(MyApplication.getTOKEN(), id, pageSize,projectName, type).compose(RxHelper.rxSchedulerHelper()).map(togeHisBean -> togeHisBean);
    }

}
