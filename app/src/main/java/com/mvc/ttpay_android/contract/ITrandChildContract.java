package com.mvc.ttpay_android.contract;

import com.mvc.ttpay_android.base.BasePresenter;
import com.mvc.ttpay_android.base.IBaseFragment;
import com.mvc.ttpay_android.base.IBaseModel;
import com.mvc.ttpay_android.bean.TrandChildBean;

import java.util.List;

import io.reactivex.Observable;

public interface ITrandChildContract {
    abstract class TrandChildPresenter extends BasePresenter<ITrandChildModel, ITrandChildView> {
        public abstract void getVrt(int pairType);

        public abstract void getBalanceTransactions(int pairType);
    }

    interface ITrandChildModel extends IBaseModel {
        Observable<TrandChildBean> getVrt(int pairType);

        Observable<TrandChildBean> getBalanceTransactions(int pairType);
    }

    interface ITrandChildView extends IBaseFragment {
        void showSuccess(List<TrandChildBean.DataBean> msgs);

        void showError();

        void showNull();
    }
}