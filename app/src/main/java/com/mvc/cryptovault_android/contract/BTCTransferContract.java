package com.mvc.cryptovault_android.contract;

import com.mvc.cryptovault_android.base.BasePresenter;
import com.mvc.cryptovault_android.base.IBaseActivity;
import com.mvc.cryptovault_android.base.IBaseModel;

public interface BTCTransferContract {
    abstract class BTCTransferPresenter extends BasePresenter<BTCTransferModel,BTCTransferView>{

    }
    interface BTCTransferModel extends IBaseModel{

    }
    interface BTCTransferView extends IBaseActivity{

    }
}
