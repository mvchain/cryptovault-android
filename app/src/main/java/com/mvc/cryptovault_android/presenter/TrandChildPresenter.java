package com.mvc.cryptovault_android.presenter;

import android.util.Log;

import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.SPUtils;
import com.mvc.cryptovault_android.base.BasePresenter;
import com.mvc.cryptovault_android.contract.TrandChildContract;
import com.mvc.cryptovault_android.model.TrandChildModel;
import com.mvc.cryptovault_android.utils.JsonHelper;

import static com.mvc.cryptovault_android.common.Constant.SP.TRAND_BALANCE_LIST;
import static com.mvc.cryptovault_android.common.Constant.SP.TRAND_VRT_LIST;

public class TrandChildPresenter extends TrandChildContract.TrandChildPresenter {

    public static BasePresenter newIntance() {
        return new TrandChildPresenter();
    }

    @Override
    public void getVrt(String token, int pairType) {
        rxUtils.register(mIModel.getVrt(token, pairType).subscribe(list -> {
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
    public void getBalanceTransactions(String token, int pairType) {
        rxUtils.register(mIModel.getBalanceTransactions(token, pairType).subscribe(list -> {
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
