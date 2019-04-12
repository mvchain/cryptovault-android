package com.mvc.ttpay_android.model

import com.mvc.ttpay_android.MyApplication
import com.mvc.ttpay_android.api.ApiStore
import com.mvc.ttpay_android.base.BaseModel
import com.mvc.ttpay_android.bean.FinancialBean
import com.mvc.ttpay_android.bean.FinancialListBean
import com.mvc.ttpay_android.contract.IFinancialContract
import com.mvc.ttpay_android.utils.RetrofitUtils
import com.mvc.ttpay_android.utils.RxHelper
import io.reactivex.Observable

class FinancialModel : BaseModel(), IFinancialContract.FinancialModel {
    override fun getFinancialList(id: Int, pageSize: Int): Observable<FinancialListBean> {
        return RetrofitUtils.client(MyApplication.getBaseUrl(),ApiStore::class.java)
                .getFinancialList(MyApplication.getTOKEN()
                        , id, pageSize)
                .compose(RxHelper.rxSchedulerHelper())
                .map { financial -> financial }
    }

    override fun getFinancialBalance(): Observable<FinancialBean> {
        return RetrofitUtils.client(MyApplication.getBaseUrl(),ApiStore::class.java).getFinancialBalance(MyApplication.getTOKEN())
                .compose(RxHelper.rxSchedulerHelper())
                .map { financial -> financial }
    }

    companion object {
        val instance: FinancialModel
            get() = FinancialModel()
    }
}