package com.mvc.cryptovault_android.presenter;

import com.blankj.utilcode.util.LogUtils;
import com.mvc.cryptovault_android.base.BasePresenter;
import com.mvc.cryptovault_android.bean.VPBalanceBean;
import com.mvc.cryptovault_android.bean.UpdateBean;
import com.mvc.cryptovault_android.contract.BalanceContract;
import com.mvc.cryptovault_android.model.VPBalanceModel;

import io.reactivex.functions.Consumer;

public class VPBalancePresenter extends BalanceContract.BalancePresenter {
    public static BasePresenter newIntance() {
        return new VPBalancePresenter();
    }

    @Override
    public void getBalance(String token) {
        rxUtils.register(mIModel.getBalance(token).subscribe(new Consumer<VPBalanceBean>() {
            @Override
            public void accept(VPBalanceBean vpBalanceBean) {
                mIView.showSuccess(vpBalanceBean);
            }
        }, throwable -> {
            LogUtils.e("VPBalancePresenter", throwable.getMessage());
        }));
    }

    @Override
    public void sendDebitMsg(String token, String password, String value) {
        rxUtils.register(mIModel.sendDebitMsg(token, password, value).subscribe(new Consumer<UpdateBean>() {
            @Override
            public void accept(UpdateBean updateBean) {
                mIView.callBack(updateBean);
            }
        }, throwable -> {
            LogUtils.e("VPBalancePresenter", throwable.getMessage());
        }));
    }

    @Override
    protected BalanceContract.BalanceModel getModel() {
        return VPBalanceModel.getInstance();
    }

    @Override
    public void onStart() {

    }
}
