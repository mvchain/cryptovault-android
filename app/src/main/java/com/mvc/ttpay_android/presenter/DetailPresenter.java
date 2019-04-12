package com.mvc.ttpay_android.presenter;

import com.blankj.utilcode.util.LogUtils;
import com.mvc.ttpay_android.base.BasePresenter;
import com.mvc.ttpay_android.contract.IDetailContract;
import com.mvc.ttpay_android.model.DetailModel;

public class DetailPresenter extends IDetailContract.DetailPresenter {
    public static BasePresenter newIntance() {
        return new DetailPresenter();
    }

    @Override
    public void getDetailOnID(int id) {
        rxUtils.register(mIModel.getDetailOnID(id)
                .subscribe(bean -> mIView.showDetail(bean)
                        , throwable -> {
                            LogUtils.e("DetailPresenter", throwable.getMessage());
                        }));
    }

    @Override
    protected IDetailContract.IDetailModel getModel() {
        return DetailModel.getInstance();
    }

    @Override
    public void onStart() {

    }
}
