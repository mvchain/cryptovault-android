package com.mvc.cryptovault_android.contract;

import com.mvc.cryptovault_android.base.BasePresenter;
import com.mvc.cryptovault_android.base.IBaseActivity;
import com.mvc.cryptovault_android.base.IBaseModel;
import com.mvc.cryptovault_android.base.VPBalanceBean;
import com.mvc.cryptovault_android.bean.UpdateBean;

import io.reactivex.Observable;

public interface BalanceContract {
    abstract class BalancePresenter extends BasePresenter<BalanceModel, BalanceView> {
        public abstract void getBalance(String token);

        public abstract void sendDebitMsg(String token, String password, String value);
    }

    interface BalanceModel extends IBaseModel {
        Observable<VPBalanceBean> getBalance(String token);

        Observable<UpdateBean> sendDebitMsg(String token, String password, String value);
    }

    interface BalanceView extends IBaseActivity {
        void showSuccess(VPBalanceBean data);

        void callBack(UpdateBean bean);
    }
}
