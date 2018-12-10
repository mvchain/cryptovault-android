package com.mvc.cryptovault_android.contract;

import com.mvc.cryptovault_android.base.BasePresenter;
import com.mvc.cryptovault_android.base.IBaseActivity;
import com.mvc.cryptovault_android.base.IBaseModel;
import com.mvc.cryptovault_android.bean.IDToTransferBean;

import io.reactivex.Observable;

public interface BTCTransferContract {
    abstract class BTCTransferPresenter extends BasePresenter<BTCTransferModel,BTCTransferView>{
        public abstract void getDetail(String token,int id);
    }
    interface BTCTransferModel extends IBaseModel{
        Observable<IDToTransferBean> getDetail(String token,int id);
    }
    interface BTCTransferView extends IBaseActivity{
        void showSuccess(IDToTransferBean.DataBean data);
    }
}
