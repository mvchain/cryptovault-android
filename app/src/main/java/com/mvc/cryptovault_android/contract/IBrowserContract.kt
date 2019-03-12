package com.mvc.cryptovault_android.contract

import com.mvc.cryptovault_android.base.BasePresenter
import com.mvc.cryptovault_android.base.IBaseActivity
import com.mvc.cryptovault_android.base.IBaseModel

interface IBrowserContract {
    abstract class IBrowserPresenter : BasePresenter<IBrowserModel, IBrowserView>() {

    }
    interface IBrowserModel :IBaseModel{

    }
    interface IBrowserView :IBaseActivity{

    }
}