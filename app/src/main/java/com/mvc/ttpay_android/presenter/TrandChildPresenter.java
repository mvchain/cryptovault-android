package com.mvc.ttpay_android.presenter;

import com.blankj.utilcode.util.SPUtils;
import com.mvc.ttpay_android.base.BasePresenter;
import com.mvc.ttpay_android.contract.ITrandChildContract;
import com.mvc.ttpay_android.model.TrandChildModel;
import com.mvc.ttpay_android.utils.JsonHelper;

import static com.mvc.ttpay_android.common.Constant.SP.TRAND_BALANCE_LIST;
import static com.mvc.ttpay_android.common.Constant.SP.TRAND_VRT_LIST;

public class TrandChildPresenter extends ITrandChildContract.TrandChildPresenter {

    public static BasePresenter newIntance() {
        return new TrandChildPresenter();
    }

    @Override
    public void getVrt(int pairType) {
        rxUtils.register(mIModel.getVrt(pairType).subscribe(list -> {
            if (list.getCode() == 200) {
                SPUtils.getInstance().put(TRAND_VRT_LIST, JsonHelper.jsonToString(list));
                mIView.showSuccess(list.getData());
            } else {
                mIView.showNull();
            }
        }, throwable ->
        {
            mIView.showError();
        }));
    }

    @Override
    public void getBalanceTransactions(int pairType) {
        rxUtils.register(mIModel.getBalanceTransactions(pairType).subscribe(list -> {
            if (list.getCode() == 200) {
                SPUtils.getInstance().put(TRAND_BALANCE_LIST, JsonHelper.jsonToString(list));
                mIView.showSuccess(list.getData());
            } else {
                mIView.showNull();
            }
        }, throwable ->
        {
            mIView.showError();
        }));
    }

    @Override
    protected TrandChildModel getModel() {
        return TrandChildModel.getInstance();
    }

    @Override
    public void onStart() {

    }
}