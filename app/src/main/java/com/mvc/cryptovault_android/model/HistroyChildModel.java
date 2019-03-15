package com.mvc.cryptovault_android.model;

import com.mvc.cryptovault_android.MyApplication;
import com.mvc.cryptovault_android.api.ApiStore;
import com.mvc.cryptovault_android.base.BaseModel;
import com.mvc.cryptovault_android.bean.HistroyBean;
import com.mvc.cryptovault_android.contract.HistroyChildContract;
import com.mvc.cryptovault_android.utils.RetrofitUtils;
import com.mvc.cryptovault_android.utils.RxHelper;

import io.reactivex.Observable;

public class HistroyChildModel extends BaseModel implements HistroyChildContract.IHistroyChildModel {

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
