package com.mvc.ttpay_android.model;

import com.mvc.ttpay_android.MyApplication;
import com.mvc.ttpay_android.api.ApiStore;
import com.mvc.ttpay_android.base.BaseModel;
import com.mvc.ttpay_android.bean.TogeBean;
import com.mvc.ttpay_android.contract.ITogeChildContract;
import com.mvc.ttpay_android.utils.RetrofitUtils;
import com.mvc.ttpay_android.utils.RxHelper;

import io.reactivex.Observable;

public class TogeChildModel extends BaseModel implements ITogeChildContract.ITogeModel {

    public static TogeChildModel getInstance() {
        return new TogeChildModel();
    }

    @Override
    public Observable<TogeBean> getComingList(int pageSize, int projectId, int projectType, int type) {
        return RetrofitUtils.client(MyApplication.getBaseUrl(),ApiStore.class).getCrowdfunding(MyApplication.getTOKEN(), pageSize, projectId, projectType, type).compose(RxHelper.rxSchedulerHelper()).map(togeBean -> togeBean);
    }
}
