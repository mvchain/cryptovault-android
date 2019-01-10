package com.mvc.cryptovault_android.model;

import com.mvc.cryptovault_android.MyApplication;
import com.mvc.cryptovault_android.api.ApiStore;
import com.mvc.cryptovault_android.base.BaseModel;
import com.mvc.cryptovault_android.bean.DetailBean;
import com.mvc.cryptovault_android.contract.DetailContract;
import com.mvc.cryptovault_android.utils.RetrofitUtils;
import com.mvc.cryptovault_android.utils.RxHelper;

import io.reactivex.Observable;

public class DetailModel extends BaseModel implements DetailContract.IDetailModel {
    public static DetailModel getInstance() {
        return new DetailModel();
    }

    @Override
    public Observable<DetailBean> getDetailOnID(int id) {
        return RetrofitUtils.client(ApiStore.class)
                .getDetailOnID(MyApplication.getTOKEN(), id)
                .compose(RxHelper.rxSchedulerHelper())
                .map(detailBean -> detailBean);
    }
}
