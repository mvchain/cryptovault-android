package com.mvc.cryptovault_android.contract;

import com.mvc.cryptovault_android.base.BasePresenter;
import com.mvc.cryptovault_android.base.IBaseActivity;
import com.mvc.cryptovault_android.base.IBaseModel;

import java.util.List;

import io.reactivex.Observable;

public interface IHistoryContract {
    abstract class HistroyPrecenter extends BasePresenter<IHistroyModel, IHistroyView> {
        public abstract void getMsg();
    }

    interface IHistroyModel extends IBaseModel {
        /**
         * 请求数据
         *
         * @return
         */
        Observable<List<String>> getMsg();
    }

    interface IHistroyView extends IBaseActivity {
        void showSuccess(List<String> msgs);
    }
}
