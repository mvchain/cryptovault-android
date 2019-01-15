package com.mvc.cryptovault_android.model

import com.mvc.cryptovault_android.base.BaseModel
import com.mvc.cryptovault_android.bean.HttpTokenBean
import com.mvc.cryptovault_android.contract.ResetPasswordContract
import io.reactivex.Observable

class ResetModel : BaseModel(),ResetPasswordContract.ResetModel{
    override fun verification(email: String, type: Int, value: String): Observable<HttpTokenBean> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    companion object {
        val instance: ResetModel
            get() = ResetModel()
    }

}
