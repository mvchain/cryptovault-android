package com.mvc.cryptovault_android.presenter;

import com.blankj.utilcode.util.LogUtils;
import com.mvc.cryptovault_android.base.BasePresenter;
import com.mvc.cryptovault_android.contract.RecordingContract;
import com.mvc.cryptovault_android.model.RecordingModel;

public class RecordingPresenter extends RecordingContract.RecordingPresenter {
    public static BasePresenter newIntance() {
        return new RecordingPresenter();
    }

    @Override
    protected RecordingContract.IRecordingModel getModel() {
        return RecordingModel.getInstance();
    }

    @Override
    public void onStart() {

    }

    @Override
    public void getRecorList(int id, int pageSize, int pairId, int transactionType, int type) {
        rxUtils.register(mIModel.getRecorList(id, pageSize, pairId, transactionType, type).subscribe(recorBean -> {
            if (recorBean.getCode() == 200 && recorBean.getData().size() > 0) {
                mIView.showSuccess(recorBean.getData());
            } else {
                mIView.showNull();
            }
        }, throwable -> {
            mIView.serverError();
            LogUtils.e("RecordingPresenter", throwable.getMessage());
        }));
    }
}
