package com.mvc.ttpay_android.model;

import com.mvc.ttpay_android.MyApplication;
import com.mvc.ttpay_android.api.ApiStore;
import com.mvc.ttpay_android.base.BaseModel;
import com.mvc.ttpay_android.bean.TrandOrderBean;
import com.mvc.ttpay_android.contract.ITrandOrderContract;
import com.mvc.ttpay_android.utils.RetrofitUtils;
import com.mvc.ttpay_android.utils.RxHelper;

import io.reactivex.Observable;

public class OrderModel extends BaseModel implements ITrandOrderContract.ITrandOrderModel {
    public static OrderModel getInstance() {
        return new OrderModel();
    }

    @Override
    public Observable<TrandOrderBean> getTrandOrder(int id, int pageSize, String pairId, int status, String transactionType, int type) {
        return RetrofitUtils.client(MyApplication.getBaseUrl(),ApiStore.class).getTpartake(MyApplication.getTOKEN(), id, pageSize, pairId, status, transactionType, type)
                .compose(RxHelper.rxSchedulerHelper())
                .map(trandOrderBean -> trandOrderBean);
    }
}
