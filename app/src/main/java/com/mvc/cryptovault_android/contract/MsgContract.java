package com.mvc.cryptovault_android.contract;

import com.mvc.cryptovault_android.base.BasePresenter;
import com.mvc.cryptovault_android.base.IBaseActivity;
import com.mvc.cryptovault_android.base.IBaseModel;

import java.util.List;

import io.reactivex.Observable;

public interface MsgContract {
    abstract class MsgPresenter extends BasePresenter<IMsgModel, IMsgView> {
        public abstract void getMsg();
    }

    interface IMsgModel extends IBaseModel {
        /**
         * 请求数据
         *
         * @return
         */
        Observable<List<String>> getMsg();
    }

    interface IMsgView extends IBaseActivity {
        void showSuccess(List<String> msgs);
    }
}
