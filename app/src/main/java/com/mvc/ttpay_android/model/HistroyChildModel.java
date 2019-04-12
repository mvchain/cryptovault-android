package com.mvc.ttpay_android.model;

import com.mvc.ttpay_android.MyApplication;
import com.mvc.ttpay_android.api.ApiStore;
import com.mvc.ttpay_android.base.BaseModel;
import com.mvc.ttpay_android.bean.HistroyBean;
import com.mvc.ttpay_android.contract.IHistroyChildContract;
import com.mvc.ttpay_android.utils.RetrofitUtils;
import com.mvc.ttpay_android.utils.RxHelper;

import io.reactivex.Observable;

public class HistroyChildModel extends BaseModel implements IHistroyChildContract.IHistroyChildModel {

    public static HistroyChildModel getInstance() {
        return new HistroyChildModel();
    }


    @Override
    public Observable<HistroyBean> getAll(int classify,int id,  int pageSize, int tokenId, int transactionType, int type) {
        return RetrofitUtils.client(MyApplication.getBaseUrl(),ApiStore.class)
                .getHistroyRecording(MyApplication.getTOKEN()
                        , classify
                        , id
                        , pageSize
                        , tokenId
                        , transactionType
                        , type)
                .compose(RxHelper.rxSchedulerHelper())
                .map(histroyBean -> histroyBean);
    }
}
