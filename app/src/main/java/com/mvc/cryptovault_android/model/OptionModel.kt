package com.mvc.cryptovault_android.model

import com.mvc.cryptovault_android.MyApplication
import com.mvc.cryptovault_android.api.ApiStore
import com.mvc.cryptovault_android.base.BaseModel
import com.mvc.cryptovault_android.bean.OptionBean
import com.mvc.cryptovault_android.contract.OptionContract
import com.mvc.cryptovault_android.utils.RetrofitUtils
import com.mvc.cryptovault_android.utils.RxHelper
import io.reactivex.Observable

class OptionModel : BaseModel(), OptionContract.OptionModel {

    companion object {
        val instance: OptionModel
            get() = OptionModel()
    }

    override fun getOptionInfo(financialType: Int, id: Int, pageSize: Int): Observable<OptionBean> {
        return RetrofitUtils.client(ApiStore::class.java).getFinancialPartake(MyApplication.getTOKEN(), financialType, id, pageSize)
                .compose(RxHelper.rxSchedulerHelper())
                .map { option -> option }
    }
}