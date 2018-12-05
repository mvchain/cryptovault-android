package com.mvc.cryptovault_android.contract;

import com.mvc.cryptovault_android.base.BasePresenter;
import com.mvc.cryptovault_android.base.IBaseActivity;
import com.mvc.cryptovault_android.base.IBaseModel;
import com.mvc.cryptovault_android.bean.HistroyBean;

import java.util.List;

import io.reactivex.Observable;

public interface HistroyChildContract {
    abstract class HistroyChildPrecenter extends BasePresenter<IHistroyChildModel, IHistroyChildView> {
        public abstract void getAll(String token, int id, int pageSize, int tokenId, int transactionType, int type);
        public abstract void getOut(String token, int id, int pageSize, int tokenId, int transactionType, int type);
        public abstract void getIn(String token, int id, int pageSize, int tokenId, int transactionType, int type);
    }

    interface IHistroyChildModel extends IBaseModel {
        /**
         * 请求全部数据
         *
         * @return
         */
        Observable<HistroyBean> getAll(String token, int id, int pageSize, int tokenId, int transactionType, int type);

        /**
         * 请求转出数据
         *
         * @return
         */
        Observable<HistroyBean> getOut(String token, int id, int pageSize, int tokenId, int transactionType, int type);

        /**
         * 请求收款数据
         *
         * @return
         */
        Observable<HistroyBean> getIn(String token, int id, int pageSize, int tokenId, int transactionType, int type);
    }

    interface IHistroyChildView extends IBaseActivity {
        void showSuccess(List<HistroyBean.DataBean> msgs);
        void showNull();
    }
}
