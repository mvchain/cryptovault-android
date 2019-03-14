package com.mvc.cryptovault_android.model

import com.mvc.cryptovault_android.base.BaseModel
import com.mvc.cryptovault_android.contract.BlockAssetsContract

class BlockAssetsModel :BaseModel(),BlockAssetsContract.BlockAssetsModel {
    companion object {
        val instance: BlockAssetsModel
              get() = BlockAssetsModel()
    }
}