package com.mvc.cryptovault_android.contract;

import com.mvc.cryptovault_android.base.BasePresenter;
import com.mvc.cryptovault_android.base.IBaseFragment;
import com.mvc.cryptovault_android.base.IBaseModel;

import java.util.List;

import io.reactivex.Observable;

public interface TrandChildContract {
    abstract class TrandChildPresenter extends BasePresenter<ITrandChildModel, ITrandChildView> {
        public abstract void getVrt();

        public abstract void getBalanceTransactions();
    }

    interface ITrandChildModel extends IBaseModel {
        Observable<List<String>> getVrt();

        void getBalanceTransactions();
    }

    interface ITrandChildView extends IBaseFragment {
        void showSuccess(List<String> msgs);
    }
}
