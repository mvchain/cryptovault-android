package com.mvc.cryptovault_android.contract;

import com.mvc.cryptovault_android.base.BasePresenter;
import com.mvc.cryptovault_android.base.IBaseFragment;
import com.mvc.cryptovault_android.base.IBaseModel;
import com.mvc.cryptovault_android.bean.TogeBean;

import java.util.List;

import io.reactivex.Observable;

public interface TogeChildContract {
    abstract class TogeChildPresenter extends BasePresenter<ITogeModel, ITogeView> {
        public abstract void getComingList(int pageSize, int projectId, int projectType, int type);
    }

    interface ITogeModel extends IBaseModel {
        /**
         * 请求数据
         *
         * @return
         */
        Observable<TogeBean> getComingList(int pageSize, int projectId, int projectType, int type);
    }

    interface ITogeView extends IBaseFragment {
        void showSuccess(List<TogeBean.DataBean> msgs);

        void showNull();
    }
}
