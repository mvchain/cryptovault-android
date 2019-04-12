package com.mvc.ttpay_android.model;

import com.mvc.ttpay_android.MyApplication;
import com.mvc.ttpay_android.api.ApiStore;
import com.mvc.ttpay_android.base.BaseModel;
import com.mvc.ttpay_android.bean.DetailBean;
import com.mvc.ttpay_android.contract.IDetailContract;
import com.mvc.ttpay_android.utils.RetrofitUtils;
import com.mvc.ttpay_android.utils.RxHelper;

import io.reactivex.Observable;

public class DetailModel extends BaseModel implements IDetailContract.IDetailModel {
    public static DetailModel getInstance() {
        return new DetailModel();
    }

    @Override
    public Observable<DetailBean> getDetailOnID(int id) {
        return RetrofitUtils.client(MyApplication.getBaseUrl(),ApiStore.class)
                .getDetailOnID(MyApplication.getTOKEN(), id)
                .compose(RxHelper.rxSchedulerHelper())
                .map(detailBean -> detailBean);
    }
}
