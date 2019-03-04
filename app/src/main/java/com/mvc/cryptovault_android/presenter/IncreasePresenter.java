package com.mvc.cryptovault_android.presenter;

import com.blankj.utilcode.util.LogUtils;
import com.mvc.cryptovault_android.base.BasePresenter;
import com.mvc.cryptovault_android.contract.IncreaseContract;
import com.mvc.cryptovault_android.model.IncreaseModel;

public class IncreasePresenter extends IncreaseContract.IncreasePresenter {
    public static BasePresenter newIntance() {
        return new IncreasePresenter();
    }

    @Override
    protected IncreaseModel getModel() {
        return IncreaseModel.getInstance();
    }

    @Override
    public void onStart() {

    }

    @Override
    public void getCurrencyAll() {
        rxUtils.register(mIModel.getCurrencyAll().subscribe(increaseBeans -> {
            if (increaseBeans.size() > 0){
                mIView.showCurrency(increaseBeans);
            }else{
                mIView.showNull();
            }
        }, throwable -> {
            LogUtils.e("IncreasePresenter", throwable.getMessage());
        }));
    }

    @Override
    public void getCurrencySerach(String serach) {
        rxUtils.register(mIModel.getCurrencySearchList(serach).subscribe(increaseBeans -> {
            if (increaseBeans.size() > 0){
                mIView.showSearchCurrency(increaseBeans);
            }else{
                mIView.showNull();
            }
        }, throwable -> {
            LogUtils.e("IncreasePresenter", throwable.getMessage());
        }));
    }
}
