package com.mvc.ttpay_android.contract;

import com.mvc.ttpay_android.base.BasePresenter;
import com.mvc.ttpay_android.base.IBaseActivity;
import com.mvc.ttpay_android.base.IBaseModel;
import com.mvc.ttpay_android.bean.IncreaseBean;

import java.util.List;

import io.reactivex.Observable;

public interface IncreaseContract {
    abstract class IncreasePresenter extends BasePresenter<IIncreaseModel,IIncreaseView>{
        public abstract void getCurrencyAll();
        public abstract void getCurrencySerach(String serach);
    }
    interface IIncreaseModel extends IBaseModel{
        Observable<List<IncreaseBean>> getCurrencyAll();
        Observable<List<IncreaseBean>> getCurrencySearchList(String search);
    }
    interface IIncreaseView extends IBaseActivity{
        void showCurrency(List<IncreaseBean> beanList);
        void showSearchCurrency(List<IncreaseBean> beanList);
        void showNull();
    }
}
