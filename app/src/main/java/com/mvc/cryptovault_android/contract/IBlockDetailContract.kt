package com.mvc.cryptovault_android.contract

import com.mvc.cryptovault_android.base.BasePresenter
import com.mvc.cryptovault_android.base.IBaseActivity
import com.mvc.cryptovault_android.base.IBaseModel

interface IBlockDetailContract {
    abstract class IBlockDetailPresenter : BasePresenter<IBlockDetailModel, IBlockDetailView>() {

    }
    interface IBlockDetailModel : IBaseModel {

    }
    interface IBlockDetailView : IBaseActivity {

    }
}