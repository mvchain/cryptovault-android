package com.mvc.ttpay_android.model;

import com.mvc.ttpay_android.MyApplication;
import com.mvc.ttpay_android.api.ApiStore;
import com.mvc.ttpay_android.base.BaseModel;
import com.mvc.ttpay_android.bean.ReceiptBean;
import com.mvc.ttpay_android.contract.IReceiptQRContract;
import com.mvc.ttpay_android.utils.RetrofitUtils;
import com.mvc.ttpay_android.utils.RxHelper;

import io.reactivex.Observable;

public class MineReceiptModel extends BaseModel implements IReceiptQRContract.IReceiptQRModel {
    public static MineReceiptModel getInstance() {
        return new MineReceiptModel();
    }

    @Override
    public Observable<ReceiptBean> getMineQcode(int tokenId) {
        return RetrofitUtils.client(MyApplication.getBaseUrl(),ApiStore.class).getRecriptQCode(MyApplication.getTOKEN(), tokenId).compose(RxHelper.rxSchedulerHelper()).map(receiptBean -> receiptBean);
    }
}
