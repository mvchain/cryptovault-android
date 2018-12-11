package com.mvc.cryptovault_android.presenter;

import com.blankj.utilcode.util.LogUtils;
import com.mvc.cryptovault_android.base.BasePresenter;
import com.mvc.cryptovault_android.contract.DetailContract;
import com.mvc.cryptovault_android.model.DetailModel;

public class DetailPresenter extends DetailContract.DetailPresenter {
    public static BasePresenter newIntance() {
        return new DetailPresenter();
    }

    @Override
    public void getDetailOnID(String token, int id) {
        rxUtils.register(mIModel.getDetailOnID(token, id).subscribe(bean -> mIView.showDetail(bean), throwable -> {
            LogUtils.e("DetailPresenter", throwable.getMessage());
        }));
    }

    @Override
    protected DetailContract.IDetailModel getModel() {
        return DetailModel.getInstance();
    }

    @Override
    public void onStart() {

    }
}
