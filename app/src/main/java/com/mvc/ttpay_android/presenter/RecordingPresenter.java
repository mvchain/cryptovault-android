package com.mvc.ttpay_android.presenter;

import com.mvc.ttpay_android.base.BasePresenter;
import com.mvc.ttpay_android.contract.IRecordingContract;
import com.mvc.ttpay_android.model.RecordingModel;

public class RecordingPresenter extends IRecordingContract.RecordingPresenter {
    public static BasePresenter newIntance() {
        return new RecordingPresenter();
    }

    @Override
    protected IRecordingContract.IRecordingModel getModel() {
        return RecordingModel.Companion.getInstance();
    }

    @Override
    public void onStart() {

    }
//
//    @Override
//    public void getRecorList(int id, int pageSize, int pairId, int transactionType, int type) {
//        rxUtils.register(mIModel.getRecorList(id, pageSize, pairId, transactionType, type).subscribe(recorBean -> {
//            if (recorBean.getCode() == 200 && recorBean.getData().size() > 0) {
//                mIView.showSuccess(recorBean.getData());
//            } else {
//                mIView.showNull();
//            }
//        }, throwable -> {
//            mIView.serverError();
//            LogUtils.e("RecordingPresenter", throwable.getMessage());
//        }));
//    }
}
