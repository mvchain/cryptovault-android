package com.mvc.cryptovault_android.contract;

import com.mvc.cryptovault_android.base.BasePresenter;
import com.mvc.cryptovault_android.base.IBaseFragment;
import com.mvc.cryptovault_android.base.IBaseModel;

public interface VRTContract {
    abstract class VRTPresenter extends BasePresenter<IVRTModel,IVRTView> {
        public abstract void getVrt();
        public abstract void getBalanceTransactions();
    }
    interface IVRTModel extends IBaseModel{

    }
    interface IVRTView extends IBaseFragment{

    }
}
