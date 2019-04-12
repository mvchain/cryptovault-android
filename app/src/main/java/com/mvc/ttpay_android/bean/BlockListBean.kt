package com.mvc.ttpay_android.bean

data class BlockListBean(
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
             * blockId : 0
             * createdAt : 0
             * transactions : 0
             */
            var blockId: Int,
            var createdAt: Long,
            var transactions: Int
    )
}
