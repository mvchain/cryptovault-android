package com.mvc.cryptovault_android.presenter;

import com.blankj.utilcode.util.LogUtils;
import com.mvc.cryptovault_android.base.BasePresenter;
import com.mvc.cryptovault_android.contract.MsgContract;
import com.mvc.cryptovault_android.model.MsgModel;

public class MsgPresenter extends MsgContract.MsgPresenter {

    public static BasePresenter newIntance() {
        return new MsgPresenter();
    }

    @Override
    public void getMsg(String token, long timestamp, int type, int pageSize) {
        rxUtils.register(mIModel.getMsg(token, timestamp, type, pageSize).subscribe(msgBean -> {
            if (msgBean.getData().size() == 0) {
                mIView.showNullMsh();
            } else {
                mIView.showSuccess(msgBean);
            }
        }, throwable -> {
            LogUtils.e("MsgPresenter", throwable.getMessage());
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
