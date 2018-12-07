package com.mvc.cryptovault_android.model;

import com.mvc.cryptovault_android.base.BaseModel;
import com.mvc.cryptovault_android.contract.BTCTransferContract;

public class BTCTransferModel extends BaseModel implements BTCTransferContract.BTCTransferModel {
    public static BTCTransferModel getInstance(){
        return new BTCTransferModel();
    }
}
