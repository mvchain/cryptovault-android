package com.mvc.cryptovault_android.contract;

import com.mvc.cryptovault_android.base.BasePresenter;
import com.mvc.cryptovault_android.base.IBaseActivity;
import com.mvc.cryptovault_android.base.IBaseModel;

public interface HistroyContract {
    abstract class HistroyPrecenter extends BasePresenter<IHistroyModel, IHistroyView> {

    }

    interface IHistroyModel extends IBaseModel {

    }

    interface IHistroyView extends IBaseActivity {

    }
}
