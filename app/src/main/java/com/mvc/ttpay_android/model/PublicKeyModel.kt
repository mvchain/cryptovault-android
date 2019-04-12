package com.mvc.ttpay_android.model

import com.mvc.ttpay_android.base.BaseModel
import com.mvc.ttpay_android.contract.IPublicKeyContract

class PublicKeyModel :BaseModel(),IPublicKeyContract.IPublicKeyModel {
    companion object {
        val instance: PublicKeyModel
              get() = PublicKeyModel()
    }

}