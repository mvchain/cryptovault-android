package com.mvc.ttpay_android.contract;

import com.mvc.ttpay_android.base.BasePresenter;
import com.mvc.ttpay_android.base.IBaseActivity;
import com.mvc.ttpay_android.base.IBaseModel;
import com.mvc.ttpay_android.bean.ReceiptBean;

import io.reactivex.Observable;

public interface IReceiptQRContract {
    abstract class ReceiptQRPresenter extends BasePresenter<IReceiptQRModel,IReceiptQRView>{
        public abstract void getMineQcode(int tokenId);
    }
    interface IReceiptQRModel extends IBaseModel{
       Observable<ReceiptBean> getMineQcode(int tokenId);
    }
    interface IReceiptQRView extends IBaseActivity{
        void showSuccess(ReceiptBean receiptBean);
        void showError();

    }
}
