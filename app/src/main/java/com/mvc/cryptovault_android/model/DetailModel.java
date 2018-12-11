package com.mvc.cryptovault_android.model;

import com.mvc.cryptovault_android.base.BaseModel;
import com.mvc.cryptovault_android.bean.DetailBean;
import com.mvc.cryptovault_android.contract.DetailContract;

import io.reactivex.Observable;

public class DetailModel extends BaseModel implements DetailContract.IDetailModel {
    public static DetailModel getInstance(){
        return new DetailModel();
    }
    @Override
    public Observable<DetailBean> getDetailOnID(String token, int id) {
        return null;
    }
}
