package com.mvc.cryptovault_android.model;

import android.support.annotation.Nullable;

import com.mvc.cryptovault_android.base.BaseModel;
import com.mvc.cryptovault_android.contract.WallteContract;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;

public class WalletModel extends BaseModel implements WallteContract.IWallteModel {
    private List<String> strings = new ArrayList<>();
    @Nullable
    public static WalletModel getInstance() {
        return new WalletModel();
    }

    @Override
    public Observable<List<String>> getData() {
        for (int i = 0; i < 10; i++) {
            strings.add(">>>>> I <<<<<");
        }
        return Observable.just(strings).map(strings -> strings);
    }
}
