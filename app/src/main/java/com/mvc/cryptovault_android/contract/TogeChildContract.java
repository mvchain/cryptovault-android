package com.mvc.cryptovault_android.contract;

import com.mvc.cryptovault_android.base.BasePresenter;
import com.mvc.cryptovault_android.base.IBaseFragment;
import com.mvc.cryptovault_android.base.IBaseModel;

import java.util.List;

import io.reactivex.Observable;

public interface TogeChildContract {
    abstract class TogeChildPresenter extends BasePresenter<ITogeModel,ITogeView> {
        public abstract void getMsg();
    }
    interface ITogeModel extends IBaseModel{
        /**
         * 请求数据
         *
         * @return
         */
        Observable<List<String>> getMsg();
    }
    interface ITogeView extends IBaseFragment{
        void showSuccess(List<String> msgs);
    }
}
