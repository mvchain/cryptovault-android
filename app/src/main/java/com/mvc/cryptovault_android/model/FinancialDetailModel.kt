package com.mvc.cryptovault_android.model

import com.mvc.cryptovault_android.MyApplication
import com.mvc.cryptovault_android.api.ApiStore
import com.mvc.cryptovault_android.base.BaseModel
import com.mvc.cryptovault_android.bean.FinancialBean
import com.mvc.cryptovault_android.bean.FinancialDetailBean
import com.mvc.cryptovault_android.bean.FinancialListBean
import com.mvc.cryptovault_android.bean.HttpTokenBean
import com.mvc.cryptovault_android.contract.FinancialContract
import com.mvc.cryptovault_android.contract.FinancialDetailContract
import com.mvc.cryptovault_android.utils.RetrofitUtils
import com.mvc.cryptovault_android.utils.RxHelper
import io.reactivex.Observable

class FinancialDetailModel : BaseModel(), FinancialDetailContract.FinancialDetailModel {
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