package com.mvc.cryptovault_android.bean

data class BlockBalanceBean(
        /**
         * code : 0
         * data : [{"blockId":0,"createdAt":0,"transactions":0}]
         * message : string
         */

        var code: Int,
        var message: String,
        var data: List<DataBean>
) {
    data class DataBean(
            /**
             * tokenId : 0
             * tokenName : 0
             * value : 0
             */
            var tokenId: Int,
            var tokenName: String,
            var value: Double
    )
}