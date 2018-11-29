package com.mvc.cryptovault_android.presenter;

import com.mvc.cryptovault_android.base.BasePresenter;
import com.mvc.cryptovault_android.contract.HistroyChildContract;
import com.mvc.cryptovault_android.model.HistroyChildModel;

import java.util.List;

import io.reactivex.functions.Consumer;

public class HistroyChildPresenter extends HistroyChildContract.HistroyChildPrecenter {
     public static BasePresenter newIntance() {
             return new HistroyChildPresenter();
     }

    @Override
    public void getMsg() {
        rxUtils.register(mIModel.getMsg().subscribe(new Consumer<List<String>>() {
            @Override
            public void accept(List<String> strings) throws Exception {
                mIView.showSuccess(strings);
            }
        }, throwable -> {
        }));
    }

    @Override
    protected HistroyChildModel getModel() {
        return HistroyChildModel.getInstance();
    }

    @Override
    public void onStart() {

    }
}
