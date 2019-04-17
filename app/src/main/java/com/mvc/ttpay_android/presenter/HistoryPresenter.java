package com.mvc.ttpay_android.presenter;

import com.mvc.ttpay_android.base.BasePresenter;
import com.mvc.ttpay_android.contract.IHistoryContract;
import com.mvc.ttpay_android.model.HistoryModel;

import java.util.List;

import io.reactivex.functions.Consumer;

public class HistoryPresenter extends IHistoryContract.HistroyPrecenter {
     public static BasePresenter newIntance() {
             return new HistoryPresenter();
     }

    @Override
    public void getMsg() {
        rxUtils.register(mIModel.getMsg().subscribe(strings -> mIView.showSuccess(strings), throwable -> {}));
    }

    @Override
    protected HistoryModel getModel() {
        return HistoryModel.getInstance();
    }

    @Override
    public void onStart() {

    }
}
