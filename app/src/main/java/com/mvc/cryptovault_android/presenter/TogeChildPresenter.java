package com.mvc.cryptovault_android.presenter;

import com.mvc.cryptovault_android.base.BasePresenter;
import com.mvc.cryptovault_android.contract.TogeChildContract;
import com.mvc.cryptovault_android.model.TogeChildModel;

public class TogeChildPresenter extends TogeChildContract.TogeChildPresenter {

    public static BasePresenter newIntance() {
        return new TogeChildPresenter();
    }

    @Override
    protected TogeChildModel getModel() {
        return TogeChildModel.getInstance();
    }

    @Override
    public void onStart() {

    }

    @Override
    public void getMsg() {
        rxUtils.register(mIModel.getMsg().subscribe(strings -> mIView.showSuccess(strings), throwable -> {
        }));
    }
}
