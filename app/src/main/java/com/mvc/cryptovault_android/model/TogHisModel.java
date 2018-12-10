package com.mvc.cryptovault_android.model;

import com.mvc.cryptovault_android.base.BaseModel;
import com.mvc.cryptovault_android.contract.TogeHistroyContract;

public class TogHisModel extends BaseModel implements TogeHistroyContract.ITogeHisMol {
    public static TogHisModel getInstance(){
        return new TogHisModel();
    }
}
