package com.mvc.cryptovault_android.model;

import com.mvc.cryptovault_android.base.BaseModel;
import com.mvc.cryptovault_android.contract.HistroyContract;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;

public class HistoryModel extends BaseModel implements HistroyContract.IHistroyModel {
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
