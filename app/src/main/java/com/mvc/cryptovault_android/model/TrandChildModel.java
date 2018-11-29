package com.mvc.cryptovault_android.model;

import android.support.annotation.Nullable;

import com.mvc.cryptovault_android.base.BaseModel;
import com.mvc.cryptovault_android.contract.TrandChildContract;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;

public class TrandChildModel extends BaseModel implements TrandChildContract.ITrandChildModel {
    private List<String> strings = new ArrayList<>();

    @Nullable
    public static TrandChildModel getInstance() {
        return new TrandChildModel();
    }

    @Override
    public Observable<List<String>> getVrt() {
        for (int i = 0; i < 10; i++) {
            strings.add(">>>>> I <<<<<");
        }
        return Observable.just(strings).map(strings -> strings);
    }

    @Override
    public void getBalanceTransactions() {

    }
}
