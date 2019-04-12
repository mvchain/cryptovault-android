package com.mvc.ttpay_android.model;

import com.mvc.ttpay_android.base.BaseModel;
import com.mvc.ttpay_android.contract.IHistoryContract;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;

public class HistoryModel extends BaseModel implements IHistoryContract.IHistroyModel {
    private List<String> strings = new ArrayList<>();

    public static HistoryModel getInstance() {
        return new HistoryModel();
    }

    @Override
    public Observable<List<String>> getMsg() {
        for (int i = 0; i < 10; i++) {
            strings.add(">>>>> I <<<<<");
        }
        return Observable.just(strings).map(strings -> strings);
    }
}
