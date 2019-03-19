package com.mvc.cryptovault_android.model

import com.mvc.cryptovault_android.base.BaseModel
import com.mvc.cryptovault_android.contract.IPublicKeyContract

class PublicKeyModel :BaseModel(),IPublicKeyContract.IPublicKeyModel {
    companion object {
        val instance: PublicKeyModel
              get() = PublicKeyModel()
    }

}