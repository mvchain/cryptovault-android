package com.mvc.cryptovault_android.contract;

import com.mvc.cryptovault_android.base.BasePresenter;
import com.mvc.cryptovault_android.base.IBaseActivity;
import com.mvc.cryptovault_android.base.IBaseModel;

public interface TogeHistroyContract {
    abstract class TogeHistroyPresenter extends BasePresenter<ITogeHisMol,ITogeHisView>{

    }
    interface ITogeHisMol extends IBaseModel {

    }
    interface ITogeHisView extends IBaseActivity {

    }
}
