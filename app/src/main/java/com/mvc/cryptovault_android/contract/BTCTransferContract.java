package com.mvc.cryptovault_android.contract;

import com.mvc.cryptovault_android.base.BasePresenter;
import com.mvc.cryptovault_android.base.IBaseActivity;
import com.mvc.cryptovault_android.base.IBaseModel;
import com.mvc.cryptovault_android.bean.IDToTransferBean;
import com.mvc.cryptovault_android.bean.UpdateBean;

import io.reactivex.Observable;

public interface BTCTransferContract {
    abstract class BTCTransferPresenter extends BasePresenter<BTCTransferModel,BTCTransferView>{
        public abstract void getDetail(int id);
        public abstract void sendTransferMsg(String address,String password,int tokenId,String value);
    }
    interface BTCTransferModel extends IBaseModel{
        Observable<IDToTransferBean> getDetail(int id);
        Observable<UpdateBean> sendTransferMsg(String address,String password,int tokenId,String value);
    }
    interface BTCTransferView extends IBaseActivity{
        void showSuccess(IDToTransferBean.DataBean data);
        void transferCallBack(UpdateBean bean);
    }
}
