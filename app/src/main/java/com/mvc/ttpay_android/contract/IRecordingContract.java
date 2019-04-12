package com.mvc.ttpay_android.contract;

import com.mvc.ttpay_android.base.BasePresenter;
import com.mvc.ttpay_android.base.IBaseFragment;
import com.mvc.ttpay_android.base.IBaseModel;

public interface IRecordingContract {
    abstract class RecordingPresenter extends BasePresenter<IRecordingModel, IRecordingView> {
//        public abstract void getRecorList(int id, int pageSize, int pairId, int transactionType, int type);
    }

    interface IRecordingModel extends IBaseModel {
//        Observable<RecorBean> getRecorList(int id, int pageSize, int pairId, int transactionType, int type);
    }

    interface IRecordingView extends IBaseFragment {
//        void showSuccess(List<RecorBean.DataBean> bean);
//
//        void showNull();
//        void serverError();
    }
}
