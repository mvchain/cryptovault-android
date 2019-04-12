package com.mvc.ttpay_android.presenter;

import com.blankj.utilcode.util.LogUtils;
import com.mvc.ttpay_android.base.BasePresenter;
import com.mvc.ttpay_android.bean.VPBalanceBean;
import com.mvc.ttpay_android.bean.UpdateBean;
import com.mvc.ttpay_android.contract.IBalanceContract;
import com.mvc.ttpay_android.model.VPBalanceModel;

import io.reactivex.functions.Consumer;

public class VPBalancePresenter extends IBalanceContract.BalancePresenter {
    public static BasePresenter newIntance() {
        return new VPBalancePresenter();
    }

    @Override
    public void getBalance() {
        rxUtils.register(mIModel.getBalance().subscribe(new Consumer<VPBalanceBean>() {
            @Override
            public void accept(VPBalanceBean vpBalanceBean) {
                mIView.showSuccess(vpBalanceBean);
            }
        }, throwable -> {
            LogUtils.e("VPBalancePresenter", throwable.getMessage());
        }));
    }

    @Override
    public void sendDebitMsg(String password, String value) {
        rxUtils.register(mIModel.sendDebitMsg(password, value).subscribe(new Consumer<UpdateBean>() {
            @Override
            public void accept(UpdateBean updateBean) {
                mIView.callBack(updateBean);
            }
        }, throwable -> {
            LogUtils.e("VPBalancePresenter", throwable.getMessage());
        }));
    }

    @Override
    protected IBalanceContract.BalanceModel getModel() {
        return VPBalanceModel.getInstance();
    }

    @Override
    public void onStart() {

    }
}
