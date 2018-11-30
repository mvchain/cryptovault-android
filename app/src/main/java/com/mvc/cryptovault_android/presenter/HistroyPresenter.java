package com.mvc.cryptovault_android.presenter;

import com.mvc.cryptovault_android.base.BasePresenter;
import com.mvc.cryptovault_android.contract.HistroyContract;
import com.mvc.cryptovault_android.model.HistroyModel;

import java.util.List;

import io.reactivex.functions.Consumer;

public class HistroyPresenter extends HistroyContract.HistroyPrecenter {
     public static BasePresenter newIntance() {
             return new HistroyPresenter();
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
    protected HistroyModel getModel() {
        return HistroyModel.getInstance();
    }

    @Override
    public void onStart() {

    }
}
