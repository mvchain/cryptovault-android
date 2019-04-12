package com.mvc.ttpay_android.contract;

import com.mvc.ttpay_android.base.BasePresenter;
import com.mvc.ttpay_android.base.IBaseActivity;
import com.mvc.ttpay_android.base.IBaseModel;
import com.mvc.ttpay_android.bean.TogeHisBean;

import java.util.List;

import io.reactivex.Observable;

public interface ITogeHistoryContract {
    abstract class TogeHistroyPresenter extends BasePresenter<ITogeHisMol, ITogeHisView> {
        public abstract void getReservation(int id, int pageSize,String projectName, int type);
    }

    interface ITogeHisMol extends IBaseModel {
        Observable<TogeHisBean> getReservation(int id, int pageSize, String projectName, int type);
    }

    interface ITogeHisView extends IBaseActivity {
        void showSuccess(List<TogeHisBean.DataBean> beanList);
        void showSearchList(List<TogeHisBean.DataBean> beanList);
    }
}
