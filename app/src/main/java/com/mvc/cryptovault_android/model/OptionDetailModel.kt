package com.mvc.cryptovault_android.model

import com.mvc.cryptovault_android.MyApplication
import com.mvc.cryptovault_android.api.ApiStore
import com.mvc.cryptovault_android.base.BaseModel
import com.mvc.cryptovault_android.bean.OptionDailyIncomeBean
import com.mvc.cryptovault_android.bean.OptionDetailBean
import com.mvc.cryptovault_android.bean.UpdateBean
import com.mvc.cryptovault_android.contract.IOptionDetailContract
import com.mvc.cryptovault_android.utils.RetrofitUtils
import com.mvc.cryptovault_android.utils.RxHelper
import io.reactivex.Observable

class OptionDetailModel : BaseModel(), IOptionDetailContract.OptionDetailModel {
    override fun getDailyIncome(id: Int,qId:Int,pageSize:Int): Observable<OptionDailyIncomeBean> {
        return RetrofitUtils.client(MyApplication.getBaseUrl(),ApiStore::class.java)
                .getDailyIncome(MyApplication.getTOKEN(), id, qId, pageSize)
                .compose(RxHelper.rxSchedulerHelper())
                .map { dayBean -> dayBean }
    }

    override fun getOptionDetail(id: Int): Observable<OptionDetailBean> {
        return RetrofitUtils.client(MyApplication.getBaseUrl(),ApiStore::class.java)
                .getOptionDetail(MyApplication.getTOKEN(), id)
                .compose(RxHelper.rxSchedulerHelper())
                .map { optionDetailBean -> optionDetailBean }
    }

    override fun extractOptionDetail(id: Int): Observable<UpdateBean> {
        return RetrofitUtils.client(MyApplication.getBaseUrl(),ApiStore::class.java)
                .extractOptionDetail(MyApplication.getTOKEN(), id)
                .compose(RxHelper.rxSchedulerHelper())
                .map { update -> update }

    }

    companion object {
        val instance: OptionDetailModel
            get() = OptionDetailModel()
    }

}