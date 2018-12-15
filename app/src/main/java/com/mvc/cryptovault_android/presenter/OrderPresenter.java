package com.mvc.cryptovault_android.presenter;

import com.blankj.utilcode.util.LogUtils;
import com.mvc.cryptovault_android.base.BasePresenter;
import com.mvc.cryptovault_android.contract.ITrandOrderContract;
import com.mvc.cryptovault_android.model.OrderModel;

public class OrderPresenter extends ITrandOrderContract.TrandOrderPresenter {
    public static BasePresenter newIntance() {
        return new OrderPresenter();
    }

    @Override
    public void getTrandOrder(String token, int id, int pageSize, String pairId, int status, String transactionType, int type) {
        rxUtils.register(mIModel.getTrandOrder(token, id, pageSize, pairId, status, transactionType, type).subscribe(trandOrderBean -> {
            if (trandOrderBean.getCode() == 200) {
                mIView.showSuccess(trandOrderBean.getData());
            }else{
                mIView.showNull();
            }
        }, throwable -> {
            LogUtils.e("OrderPresenter", throwable.getMessage());
        }));
    }

    @Override
    protected ITrandOrderContract.ITrandOrderModel getModel() {
        return OrderModel.getInstance();
    }

    @Override
    public void onStart() {

    }
}
