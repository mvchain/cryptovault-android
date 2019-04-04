package com.mvc.cryptovault_android.model

import com.mvc.cryptovault_android.base.BaseModel
import com.mvc.cryptovault_android.contract.IAreaContract

class AreaModel:BaseModel(),IAreaContract.AreaModel {
    companion object {
        val instance: AreaModel
              get() = AreaModel()
    }
}