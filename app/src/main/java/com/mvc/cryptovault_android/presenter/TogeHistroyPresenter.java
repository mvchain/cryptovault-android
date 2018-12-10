package com.mvc.cryptovault_android.presenter;

import com.mvc.cryptovault_android.base.BasePresenter;
import com.mvc.cryptovault_android.contract.TogeHistroyContract;
import com.mvc.cryptovault_android.model.TogHisModel;

public class TogeHistroyPresenter extends TogeHistroyContract.TogeHistroyPresenter {
     public static BasePresenter newIntance() {
             return new TogeHistroyPresenter();
     }
    @Override
    protected TogeHistroyContract.ITogeHisMol getModel() {
        return TogHisModel.getInstance();
    }

    @Override
    public void onStart() {

    }
}
