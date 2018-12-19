package com.mvc.cryptovault_android.presenter;

import com.blankj.utilcode.util.LogUtils;
import com.mvc.cryptovault_android.base.BasePresenter;
import com.mvc.cryptovault_android.bean.UserInfoBean;
import com.mvc.cryptovault_android.contract.MineContract;
import com.mvc.cryptovault_android.model.MineModel;

import io.reactivex.functions.Consumer;

public class MinePresenter extends MineContract.MinePresenter {
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
    public void getUserInfo(String token) {
        rxUtils.register(mIModel.getUserInfo(token).subscribe(new Consumer<UserInfoBean>() {
            @Override
            public void accept(UserInfoBean user) throws Exception {
                if(user.getCode() == 200) {
                    mIView.setUser(user);
                }else{
                    mIView.serverError();
                }
            }
        }, throwable -> {
            mIView.serverError();
            LogUtils.e("MinePresenter", throwable.getMessage());
        }));
    }
}
