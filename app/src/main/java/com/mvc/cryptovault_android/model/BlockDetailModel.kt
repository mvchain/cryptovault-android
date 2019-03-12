package com.mvc.cryptovault_android.model

import com.mvc.cryptovault_android.base.BaseModel
import com.mvc.cryptovault_android.contract.IBlockDetailContract

class BlockDetailModel :BaseModel(),IBlockDetailContract.IBlockDetailModel {
    companion object {
        val instance: BlockDetailModel
              get() = BlockDetailModel()
    }
}