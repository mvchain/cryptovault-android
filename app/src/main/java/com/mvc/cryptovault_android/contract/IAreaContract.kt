package com.mvc.cryptovault_android.contract

import com.mvc.cryptovault_android.base.BasePresenter
import com.mvc.cryptovault_android.base.IBaseActivity
import com.mvc.cryptovault_android.base.IBaseModel
import com.mvc.cryptovault_android.bean.BlockOrderOnIdBean
import io.reactivex.Observable

interface IAreaContract {
    abstract class AreaPresenter : BasePresenter<AreaModel, AreaView>() {
        abstract fun getAreaToId(id: Int)
    }

    interface AreaModel : IBaseModel {

    }

    interface AreaView : IBaseActivity {
    }
}