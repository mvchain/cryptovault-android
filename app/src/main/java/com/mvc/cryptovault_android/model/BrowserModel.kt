package com.mvc.cryptovault_android.model

import com.mvc.cryptovault_android.base.BaseModel
import com.mvc.cryptovault_android.contract.IBrowserContract

class BrowserModel :BaseModel(),IBrowserContract.IBrowserModel {
        companion object {
            val instance: BrowserModel
                  get() = BrowserModel()
        }
}