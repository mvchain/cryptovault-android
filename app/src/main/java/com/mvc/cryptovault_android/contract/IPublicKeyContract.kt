package com.mvc.cryptovault_android.contract

import com.mvc.cryptovault_android.base.BasePresenter
import com.mvc.cryptovault_android.base.IBaseActivity
import com.mvc.cryptovault_android.base.IBaseModel

interface IPublicKeyContract {
    abstract class IPublicKeyPresenter : BasePresenter<IPublicKeyModel, IPublicKeyView>() {

    }

    interface IPublicKeyModel : IBaseModel {

    }

    interface IPublicKeyView : IBaseActivity {

    }

}