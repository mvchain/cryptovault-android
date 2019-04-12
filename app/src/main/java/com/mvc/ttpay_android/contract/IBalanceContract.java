package com.mvc.ttpay_android.contract;

import com.mvc.ttpay_android.base.BasePresenter;
import com.mvc.ttpay_android.base.IBaseActivity;
import com.mvc.ttpay_android.base.IBaseModel;
import com.mvc.ttpay_android.bean.VPBalanceBean;
import com.mvc.ttpay_android.bean.UpdateBean;

import io.reactivex.Observable;

public interface IBalanceContract {
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
