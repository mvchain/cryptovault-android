package com.mvc.ttpay_android.model

import com.mvc.ttpay_android.MyApplication
import com.mvc.ttpay_android.api.ApiStore
import com.mvc.ttpay_android.base.BaseModel
import com.mvc.ttpay_android.bean.FinancialDetailBean
import com.mvc.ttpay_android.contract.IFinancialDetailContract
import com.mvc.ttpay_android.utils.RetrofitUtils
import com.mvc.ttpay_android.utils.RxHelper
import io.reactivex.Observable

class FinancialDetailModel : BaseModel(), IFinancialDetailContract.FinancialDetailModel {
    override fun getFinancialDetail(id: Int): Observable<FinancialDetailBean> {
        return RetrofitUtils.client(MyApplication.getBaseUrl(),ApiStore::class.java)
                .getFinancialDetail(MyApplication.getTOKEN(), id)
                .compose(RxHelper.rxSchedulerHelper())
                .map { detail -> detail }
    }


        companion object {
            val instance: FinancialDetailModel
                get() = FinancialDetailModel()
        }
}