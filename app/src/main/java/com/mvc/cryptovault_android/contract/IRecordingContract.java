package com.mvc.cryptovault_android.contract;

import com.mvc.cryptovault_android.base.BasePresenter;
import com.mvc.cryptovault_android.base.IBaseFragment;
import com.mvc.cryptovault_android.base.IBaseModel;
import com.mvc.cryptovault_android.bean.RecorBean;

import java.util.List;

import io.reactivex.Observable;

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
