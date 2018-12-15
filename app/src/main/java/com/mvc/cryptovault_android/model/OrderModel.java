package com.mvc.cryptovault_android.model;

import com.mvc.cryptovault_android.api.ApiStore;
import com.mvc.cryptovault_android.base.BaseModel;
import com.mvc.cryptovault_android.bean.TrandOrderBean;
import com.mvc.cryptovault_android.contract.ITrandOrderContract;
import com.mvc.cryptovault_android.utils.RetrofitUtils;
import com.mvc.cryptovault_android.utils.RxHelper;

import io.reactivex.Observable;

public class OrderModel extends BaseModel implements ITrandOrderContract.ITrandOrderModel {
    public static OrderModel getInstance(){
        return new OrderModel();
    }

    @Override
    public Observable<TrandOrderBean> getTrandOrder(String token, int id, int pageSize, String pairId, int status, String transactionType, int type) {
        return RetrofitUtils.client(ApiStore.class).getTpartake(token, id, pageSize, pairId, status, transactionType, type)
                .compose(RxHelper.rxSchedulerHelper())
                .map(trandOrderBean -> trandOrderBean);
    }
}
