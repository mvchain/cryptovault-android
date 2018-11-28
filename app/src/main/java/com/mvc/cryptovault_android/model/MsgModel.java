package com.mvc.cryptovault_android.model;

import android.support.annotation.Nullable;

import com.mvc.cryptovault_android.base.BaseModel;
import com.mvc.cryptovault_android.contract.MsgContract;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;

public class MsgModel extends BaseModel implements MsgContract.IMsgModel {
    private List<String> strings = new ArrayList<>();

    @Nullable
    public static MsgModel getInstance() {
        return new MsgModel();
    }

    @Override
    public Observable<List<String>> getMsg() {
        for (int i = 0; i < 10; i++) {
            strings.add(">>>>> I <<<<<");
        }
        return Observable.just(strings).map(strings -> strings);
    }
}
