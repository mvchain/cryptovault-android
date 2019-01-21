package com.mvc.cryptovault_android.model

import com.mvc.cryptovault_android.MyApplication
import com.mvc.cryptovault_android.api.ApiStore
import com.mvc.cryptovault_android.base.BaseModel
import com.mvc.cryptovault_android.bean.FinancialBean
import com.mvc.cryptovault_android.bean.FinancialListBean
import com.mvc.cryptovault_android.contract.FinancialContract
import com.mvc.cryptovault_android.utils.RetrofitUtils
import com.mvc.cryptovault_android.utils.RxHelper
import io.reactivex.Observable

class FinancialModel : BaseModel(), FinancialContract.FinancialModel {
    override fun getFinancialList(id: Int, pageSize: Int): Observable<FinancialListBean> {
        return RetrofitUtils.client(ApiStore::class.java)
                .getFinancialList(MyApplication.getTOKEN()
                        , id, pageSize)
                .compose(RxHelper.rxSchedulerHelper())
                .map { financial -> financial }
    }

    override fun getFinancialBalance(): Observable<FinancialBean> {
        return RetrofitUtils.client(ApiStore::class.java).getFinancialBalance(MyApplication.getTOKEN())
                .compose(RxHelper.rxSchedulerHelper())
                .map { financial -> financial }
    }

    companion object {
        val instance: FinancialModel
            get() = FinancialModel()
    }
}