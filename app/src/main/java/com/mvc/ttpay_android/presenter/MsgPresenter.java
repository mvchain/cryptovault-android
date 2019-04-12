package com.mvc.ttpay_android.presenter;

import com.blankj.utilcode.util.LogUtils;
import com.mvc.ttpay_android.base.BasePresenter;
import com.mvc.ttpay_android.contract.IMsgContract;
import com.mvc.ttpay_android.model.MsgModel;

public class MsgPresenter extends IMsgContract.MsgPresenter {

    public static BasePresenter newIntance() {
        return new MsgPresenter();
    }

    @Override
    public void getMsg(long timestamp, int type, int pageSize) {
        rxUtils.register(mIModel.getMsg(timestamp, type, pageSize).subscribe(msgBean -> {
            if (msgBean.getData().size() == 0) {
                mIView.showNullMsg();
            } else {
                mIView.showSuccess(msgBean);
            }
        }, throwable -> {
            LogUtils.e("MsgPresenter", throwable.getMessage());
        }));
    }

    @Override
    protected MsgModel getModel() {
        return MsgModel.Companion.getInstance();
    }

    @Override
    public void onStart() {

    }
}