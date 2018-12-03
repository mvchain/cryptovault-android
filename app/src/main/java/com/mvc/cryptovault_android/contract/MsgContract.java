package com.mvc.cryptovault_android.contract;

import com.mvc.cryptovault_android.base.BasePresenter;
import com.mvc.cryptovault_android.base.IBaseActivity;
import com.mvc.cryptovault_android.base.IBaseModel;
import com.mvc.cryptovault_android.bean.MsgBean;

import io.reactivex.Observable;

public interface MsgContract {
    abstract class MsgPresenter extends BasePresenter<IMsgModel, IMsgView> {
        public abstract void getMsg(String token,long timestamp,int type,int pagesize);
    }

    interface IMsgModel extends IBaseModel {
        /**
         * 请求数据
         *
         * @return
         */
        Observable<MsgBean> getMsg(String token,long timestamp,int type,int pagesize);
    }

    interface IMsgView extends IBaseActivity {
        void showSuccess(MsgBean msgs);
        void showNullMsh();
    }
}
