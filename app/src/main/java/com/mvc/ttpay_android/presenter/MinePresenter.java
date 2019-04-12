package com.mvc.ttpay_android.presenter;

import com.blankj.utilcode.util.LogUtils;
import com.mvc.ttpay_android.base.BasePresenter;
import com.mvc.ttpay_android.contract.IMineContract;
import com.mvc.ttpay_android.model.MineModel;

public class MinePresenter extends IMineContract.MinePresenter {
    public static BasePresenter newIntance() {
        return new MinePresenter();
    }

    @Override
    protected MineModel getModel() {
        return MineModel.getInstance();
    }

    @Override
    public void onStart() {

    }

    @Override
    public void getUserInfo() {
        rxUtils.register(mIModel.getUserInfo().subscribe(user -> {
            if(user.getCode() == 200) {
                mIView.setUser(user);
            }else{
                mIView.serverError();
            }
        }, throwable -> {
            mIView.serverError();
            LogUtils.e("MinePresenter", throwable.getMessage());
        }));
    }
}
