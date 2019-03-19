package com.mvc.cryptovault_android.contract;

import com.mvc.cryptovault_android.base.BasePresenter;
import com.mvc.cryptovault_android.base.IBaseActivity;
import com.mvc.cryptovault_android.base.IBaseModel;
import com.mvc.cryptovault_android.bean.VPBalanceBean;
import com.mvc.cryptovault_android.bean.UpdateBean;

import io.reactivex.Observable;

public interface BalanceContract {
    abstract class BalancePresenter extends BasePresenter<BalanceModel, BalanceView> {
        public abstract void getBalance();

        public abstract void sendDebitMsg(String password, String value);
    }

    interface BalanceModel extends IBaseModel {
        Observable<VPBalanceBean> getBalance();

        Observable<UpdateBean> sendDebitMsg(String password, String value);
    }

    interface BalanceView extends IBaseActivity {
        void showSuccess(VPBalanceBean data);

        void callBack(UpdateBean bean);
    }
}
