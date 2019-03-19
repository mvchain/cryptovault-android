package com.mvc.cryptovault_android.bean

data class BlockLastBean(
        /**
         * code : 0
         * data : {"blockId":0,"confirmTime":0,"serviceTime":0,"total":0,"transactionCount":0,"version":"string"}
         * message : string
         */
        var code: Int,
        var data: DataBean,
        var message: String
) {


    data class DataBean(
            /**
             * blockId : 0
             * confirmTime : 0
             * serviceTime : 0
             * total : 0
             * transactionCount : 0
             * version : string
             */
            var blockId: Int,
            var confirmTime: Long,
            var serviceTime: Long,
            var total: Int,
            var transactionCount: Int,
            var version: String
    )
}
