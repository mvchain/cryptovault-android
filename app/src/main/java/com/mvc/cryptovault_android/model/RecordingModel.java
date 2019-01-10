package com.mvc.cryptovault_android.model;

import com.mvc.cryptovault_android.MyApplication;
import com.mvc.cryptovault_android.api.ApiStore;
import com.mvc.cryptovault_android.base.BaseModel;
import com.mvc.cryptovault_android.bean.RecorBean;
import com.mvc.cryptovault_android.contract.RecordingContract;
import com.mvc.cryptovault_android.utils.RetrofitUtils;
import com.mvc.cryptovault_android.utils.RxHelper;

import io.reactivex.Observable;

public class RecordingModel extends BaseModel implements RecordingContract.IRecordingModel {
    public static RecordingModel getInstance() {
        return new RecordingModel();
    }

    @Override
    public Observable<RecorBean> getRecorList(int id, int pageSize, int pairId, int transactionType, int type) {
        return RetrofitUtils.client(ApiStore.class)
                .getRecording(MyApplication.getTOKEN(), id, pageSize, pairId, transactionType, type)
                .compose(RxHelper.rxSchedulerHelper())
                .map(recorBean -> recorBean);
    }
}
