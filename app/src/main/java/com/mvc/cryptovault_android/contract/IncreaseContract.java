package com.mvc.cryptovault_android.contract;

import com.mvc.cryptovault_android.base.BasePresenter;
import com.mvc.cryptovault_android.base.IBaseActivity;
import com.mvc.cryptovault_android.base.IBaseModel;

public interface IncreaseContract {
    abstract class IncreasePresenter extends BasePresenter<IIncreaseModel,IIncreaseView>{
        public abstract void getCurrency();
    }
    interface IIncreaseModel extends IBaseModel{
        void getCurrency(String token);
    }
    interface IIncreaseView extends IBaseActivity{
        void showCurrency();
    }
}
