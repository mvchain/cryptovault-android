package com.mvc.cryptovault_android.contract;

import com.mvc.cryptovault_android.base.BasePresenter;
import com.mvc.cryptovault_android.base.IBaseActivity;
import com.mvc.cryptovault_android.base.IBaseModel;

import java.util.List;

import io.reactivex.Observable;

public interface HistroyChildContract {
    abstract class HistroyChildPrecenter extends BasePresenter<IHistroyChildModel, IHistroyChildView> {
        public abstract void getMsg();
    }

    interface IHistroyChildModel extends IBaseModel {
        /**
         * 请求数据
         *
         * @return
         */
        Observable<List<String>> getMsg();
    }

    interface IHistroyChildView extends IBaseActivity {
        void showSuccess(List<String> msgs);
    }
}
