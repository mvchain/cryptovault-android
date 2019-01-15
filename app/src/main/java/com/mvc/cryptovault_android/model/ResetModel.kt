package com.mvc.cryptovault_android.model

import com.mvc.cryptovault_android.base.BaseModel
import com.mvc.cryptovault_android.bean.HttpTokenBean
import com.mvc.cryptovault_android.bean.UpsetMnemonicsBean
import com.mvc.cryptovault_android.contract.ResetPasswordContract
import io.reactivex.Observable

class ResetModel : BaseModel(),ResetPasswordContract.ResetModel{
    override fun verificationEmail(email: String): Observable<HttpTokenBean> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun verificationPrivateKey(privateKey: String): Observable<HttpTokenBean> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun verificationMnenonics(mnemonics: String): Observable<UpsetMnemonicsBean> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    companion object {
        val instance: ResetModel
            get() = ResetModel()
    }

}
