package com.mvc.ttpay_android.model

import com.mvc.ttpay_android.MyApplication
import com.mvc.ttpay_android.api.ApiStore
import com.mvc.ttpay_android.base.BaseModel
import com.mvc.ttpay_android.bean.OptionBean
import com.mvc.ttpay_android.contract.IOptionContract
import com.mvc.ttpay_android.utils.RetrofitUtils
import com.mvc.ttpay_android.utils.RxHelper
import io.reactivex.Observable

class OptionModel : BaseModel(), IOptionContract.OptionModel {

    companion object {
        val instance: OptionModel
            get() = OptionModel()
    }

    override fun getOptionInfo(financialType: Int, id: Int, pageSize: Int): Observable<OptionBean> {
        return RetrofitUtils.client(MyApplication.getBaseUrl(),ApiStore::class.java).getFinancialPartake(MyApplication.getTOKEN(), financialType, id, pageSize)
                .compose(RxHelper.rxSchedulerHelper())
                .map { option -> option }
    }
}