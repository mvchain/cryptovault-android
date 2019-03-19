package com.mvc.cryptovault_android.bean

data class BlockBrowserDataBean(
        var blockLastBean: BlockLastBean,
        var blockListBean: BlockListBean,
        var blockTransactionBean: BlockTransactionBean,
        var blockNodeBean: ArrayList<BlockNodeBean>
)