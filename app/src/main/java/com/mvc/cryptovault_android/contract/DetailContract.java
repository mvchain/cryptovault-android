package com.mvc.cryptovault_android.contract;

import com.mvc.cryptovault_android.base.BasePresenter;
import com.mvc.cryptovault_android.base.IBaseActivity;
import com.mvc.cryptovault_android.base.IBaseModel;

public interface DetailContract {
    abstract class DetailPresenter extends BasePresenter<IDetailModel,IDetailView> {}

    interface IDetailModel extends IBaseModel {

    }
    interface IDetailView extends IBaseActivity {

    }
}
