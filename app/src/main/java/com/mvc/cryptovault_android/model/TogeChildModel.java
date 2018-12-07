package com.mvc.cryptovault_android.model;

import com.mvc.cryptovault_android.api.ApiStore;
import com.mvc.cryptovault_android.base.BaseModel;
import com.mvc.cryptovault_android.bean.TogeBean;
import com.mvc.cryptovault_android.contract.TogeChildContract;
import com.mvc.cryptovault_android.utils.RetrofitUtils;
import com.mvc.cryptovault_android.utils.RxHelper;

import io.reactivex.Observable;

public class TogeChildModel extends BaseModel implements TogeChildContract.ITogeModel {

    public static TogeChildModel getInstance() {
        return new TogeChildModel();
    }

    @Override
    public Observable<TogeBean> getComingSoon(String token, int pageSize, int projectId, int projectType, int type) {
        return RetrofitUtils.client(ApiStore.class).getCrowdfunding(token, pageSize, projectId, projectType, type).compose(RxHelper.rxSchedulerHelper()).map(togeBean -> togeBean);
    }

    @Override
    public Observable<TogeBean> getProcess(String token, int pageSize, int projectId, int projectType, int type) {
        return RetrofitUtils.client(ApiStore.class).getCrowdfunding(token, pageSize, projectId, projectType, type).compose(RxHelper.rxSchedulerHelper()).map(togeBean -> togeBean);
    }

    @Override
    public Observable<TogeBean> getToEnd(String token, int pageSize, int projectId, int projectType, int type) {
        return RetrofitUtils.client(ApiStore.class).getCrowdfunding(token, pageSize, projectId, projectType, type).compose(RxHelper.rxSchedulerHelper()).map(togeBean -> togeBean);
    }
}
