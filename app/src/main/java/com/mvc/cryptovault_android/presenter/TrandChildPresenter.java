package com.mvc.cryptovault_android.presenter;

import com.mvc.cryptovault_android.base.BasePresenter;
import com.mvc.cryptovault_android.contract.TrandChildContract;
import com.mvc.cryptovault_android.model.TrandChildModel;

public class TrandChildPresenter extends TrandChildContract.TrandChildPresenter {

    public static BasePresenter newIntance() {
        return new TrandChildPresenter();
    }

    @Override
    public void getVrt(String token,int pairType) {
        rxUtils.register(mIModel.getVrt(token, pairType).subscribe(list -> {
            if (list.getCode() == 200) {
                mIView.showSuccess(list.getData());
            }else{
                mIView.showNull();
            }
        }, throwable ->
        {
        }));
    }

    @Override
    public void getBalanceTransactions(String token,int pairType) {
        rxUtils.register(mIModel.getVrt(token, pairType).subscribe(list -> {
            if (list.getCode() == 200) {
                mIView.showSuccess(list.getData());
            }else{
                mIView.showNull();
            }
        }, throwable ->
        {
        }));
    }

    @Override
    public void getAll(String token) {
        rxUtils.register(mIModel.getAll(token).subscribe(list -> {
            if (list.getCode() == 200) {
                mIView.saveAll(list);
            }else{
                mIView.showNull();
            }
        }, throwable ->
        {
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
