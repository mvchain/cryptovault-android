package com.mvc.cryptovault_android.model;

import com.mvc.cryptovault_android.MyApplication;
import com.mvc.cryptovault_android.api.ApiStore;
import com.mvc.cryptovault_android.base.BaseModel;
import com.mvc.cryptovault_android.bean.ReceiptBean;
import com.mvc.cryptovault_android.contract.ReceiptQRContract;
import com.mvc.cryptovault_android.utils.RetrofitUtils;
import com.mvc.cryptovault_android.utils.RxHelper;

import io.reactivex.Observable;

public class MineReceiptModel extends BaseModel implements ReceiptQRContract.IReceiptQRModel {
    public static MineReceiptModel getInstance() {
        return new MineReceiptModel();
    }

    @Override
    public Observable<ReceiptBean> getMineQcode(int tokenId) {
        return RetrofitUtils.client(ApiStore.class).getRecriptQCode(MyApplication.getTOKEN(), tokenId).compose(RxHelper.rxSchedulerHelper()).map(receiptBean -> receiptBean);
    }
}
