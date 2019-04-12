package com.mvc.ttpay_android.contract;

import com.mvc.ttpay_android.base.BasePresenter;
import com.mvc.ttpay_android.base.IBaseActivity;
import com.mvc.ttpay_android.base.IBaseModel;
import com.mvc.ttpay_android.bean.HistroyBean;

import java.util.List;

import io.reactivex.Observable;

public interface IHistroyChildContract {
    abstract class HistroyChildPrecenter extends BasePresenter<IHistroyChildModel, IHistroyChildView> {
        public abstract void getAll(int classify, int id, int pageSize, int tokenId, int transactionType, int type);
    }

    interface IHistroyChildModel extends IBaseModel {
        /**
         * 请求全部数据
         *
         * @return
         */
        Observable<HistroyBean> getAll(int classify, int id, int pageSize, int tokenId, int transactionType, int type);
    }

    interface IHistroyChildView extends IBaseActivity {
        void showSuccess(List<HistroyBean.DataBean> msgs);

        void showNull();
    }
}
