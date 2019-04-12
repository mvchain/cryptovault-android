package com.mvc.ttpay_android.contract

import com.mvc.ttpay_android.base.BasePresenter
import com.mvc.ttpay_android.base.IBaseActivity
import com.mvc.ttpay_android.base.IBaseModel

interface IPublicKeyContract {
    abstract class IPublicKeyPresenter : BasePresenter<IPublicKeyModel, IPublicKeyView>() {

    }

    interface IPublicKeyModel : IBaseModel {

    }

    interface IPublicKeyView : IBaseActivity {

    }

}