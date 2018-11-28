package com.mvc.cryptovault_android.presenter;

import com.mvc.cryptovault_android.base.BasePresenter;
import com.mvc.cryptovault_android.contract.MsgContract;
import com.mvc.cryptovault_android.model.MsgModel;

import java.util.List;

import io.reactivex.functions.Consumer;

public class MsgPresenter extends MsgContract.MsgPresenter {

    public static BasePresenter newIntance() {
        return new MsgPresenter();
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
    protected MsgModel getModel() {
        return MsgModel.getInstance();
    }

    @Override
    public void onStart() {

    }
}
