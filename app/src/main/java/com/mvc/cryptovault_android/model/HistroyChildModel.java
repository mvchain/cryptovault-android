package com.mvc.cryptovault_android.model;

import com.mvc.cryptovault_android.base.BaseModel;
import com.mvc.cryptovault_android.contract.HistroyChildContract;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;

public class HistroyChildModel extends BaseModel implements HistroyChildContract.IHistroyChildModel {
    private List<String> strings = new ArrayList<>();

    public static HistroyChildModel getInstance() {
        return new HistroyChildModel();
    }

    @Override
    public Observable<List<String>> getMsg() {
        for (int i = 0; i < 10; i++) {
            strings.add(">>>>> I <<<<<");
        }
        return Observable.just(strings).map(strings -> strings);
    }
}
