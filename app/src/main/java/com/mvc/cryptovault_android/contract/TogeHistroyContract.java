package com.mvc.cryptovault_android.contract;

import com.mvc.cryptovault_android.base.BasePresenter;
import com.mvc.cryptovault_android.base.IBaseActivity;
import com.mvc.cryptovault_android.base.IBaseModel;
import com.mvc.cryptovault_android.bean.TogeHisBean;

import java.util.List;

import io.reactivex.Observable;

public interface TogeHistroyContract {
    abstract class TogeHistroyPresenter extends BasePresenter<ITogeHisMol, ITogeHisView> {
        public abstract void getReservation(String token, int id, int pageSize,String projectName, int type);
    }

    interface ITogeHisMol extends IBaseModel {
        Observable<TogeHisBean> getReservation(String token, int id, int pageSize, String projectName, int type);
    }

    interface ITogeHisView extends IBaseActivity {
        void showSuccess(List<TogeHisBean.DataBean> beanList);
        void showSearchList(List<TogeHisBean.DataBean> beanList);
    }
}
