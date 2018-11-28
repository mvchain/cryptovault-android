package com.mvc.cryptovault_android.presenter;

import com.mvc.cryptovault_android.base.BasePresenter;
import com.mvc.cryptovault_android.contract.VRTContract;

public class VRTPresenter extends VRTContract.VRTPresenter {

    public static BasePresenter newIntance() {
        return new VRTPresenter();
    }

    @Override
    public void getVrt() {

    }

    @Override
    public void getBalanceTransactions() {

    }

    @Override
    protected VRTContract.IVRTModel getModel() {
        return null;
    }

    @Override
    public void onStart() {

    }
}
