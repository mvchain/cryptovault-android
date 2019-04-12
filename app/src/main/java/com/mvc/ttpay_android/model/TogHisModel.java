package com.mvc.ttpay_android.model;

import com.mvc.ttpay_android.MyApplication;
import com.mvc.ttpay_android.api.ApiStore;
import com.mvc.ttpay_android.base.BaseModel;
import com.mvc.ttpay_android.bean.TogeHisBean;
import com.mvc.ttpay_android.contract.ITogeHistoryContract;
import com.mvc.ttpay_android.utils.RetrofitUtils;
import com.mvc.ttpay_android.utils.RxHelper;

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
