package com.mvc.cryptovault_android.contract;

import com.mvc.cryptovault_android.base.BasePresenter;
import com.mvc.cryptovault_android.base.IBaseFragment;
import com.mvc.cryptovault_android.base.IBaseModel;
import com.mvc.cryptovault_android.bean.TrandChildBean;

import java.util.List;

import io.reactivex.Observable;

public interface TrandChildContract {
    abstract class TrandChildPresenter extends BasePresenter<ITrandChildModel, ITrandChildView> {
        public abstract void getVrt(String token,int pairType);

        public abstract void getBalanceTransactions(String token,int pairType);
    }

    interface ITrandChildModel extends IBaseModel {
        Observable<TrandChildBean> getVrt(String token,int pairType);

        Observable<TrandChildBean> getBalanceTransactions(String token,int pairType);
    }

    interface ITrandChildView extends IBaseFragment {
        void showSuccess(List<TrandChildBean.DataBean> msgs);

        void showNull();
    }
}
