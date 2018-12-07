package com.mvc.cryptovault_android.contract;

import com.mvc.cryptovault_android.base.BasePresenter;
import com.mvc.cryptovault_android.base.IBaseActivity;
import com.mvc.cryptovault_android.base.IBaseModel;
import com.mvc.cryptovault_android.bean.ReceiptBean;

import io.reactivex.Observable;

public interface ReceiptQRContract {
    abstract class ReceiptQRPresenter extends BasePresenter<IReceiptQRModel,IReceiptQRView>{
        public abstract void getMineQcode(String token,int tokenId);
    }
    interface IReceiptQRModel extends IBaseModel{
       Observable<ReceiptBean> getMineQcode(String token, int tokenId);
    }
    interface IReceiptQRView extends IBaseActivity{
        void showSuccess(ReceiptBean receiptBean);
        void showError();

    }
}
