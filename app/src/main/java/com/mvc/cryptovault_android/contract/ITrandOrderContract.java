package com.mvc.cryptovault_android.contract;

import com.mvc.cryptovault_android.base.BasePresenter;
import com.mvc.cryptovault_android.base.IBaseFragment;
import com.mvc.cryptovault_android.base.IBaseModel;
import com.mvc.cryptovault_android.bean.TrandOrderBean;

import java.util.List;

import io.reactivex.Observable;

public interface ITrandOrderContract {
    abstract class TrandOrderPresenter extends BasePresenter<ITrandOrderModel, ITrandOrderView> {
        public abstract void getTrandOrder(String token, int id, int pageSize, String pairId, int status, String transactionType, int type);
    }

    interface ITrandOrderModel extends IBaseModel {
        Observable<TrandOrderBean> getTrandOrder(String token, int id, int pageSize, String pairId, int status, String transactionType, int type);
    }

    interface ITrandOrderView extends IBaseFragment {
        void showSuccess(List<TrandOrderBean.DataBean> dataBeans);

        void showNull();
    }
}
