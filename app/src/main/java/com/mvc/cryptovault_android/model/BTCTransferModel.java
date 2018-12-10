package com.mvc.cryptovault_android.model;

import com.mvc.cryptovault_android.api.ApiStore;
import com.mvc.cryptovault_android.base.BaseModel;
import com.mvc.cryptovault_android.bean.IDToTransferBean;
import com.mvc.cryptovault_android.contract.BTCTransferContract;
import com.mvc.cryptovault_android.utils.RetrofitUtils;
import com.mvc.cryptovault_android.utils.RxHelper;

import io.reactivex.Observable;

public class BTCTransferModel extends BaseModel implements BTCTransferContract.BTCTransferModel {
    public static BTCTransferModel getInstance(){
        return new BTCTransferModel();
    }

    @Override
    public Observable<IDToTransferBean> getDetail(String token, int id) {
        return RetrofitUtils.client(ApiStore.class).getTranstion(token, id).compose(RxHelper.rxSchedulerHelper()).map(idbean->idbean);
    }
}
