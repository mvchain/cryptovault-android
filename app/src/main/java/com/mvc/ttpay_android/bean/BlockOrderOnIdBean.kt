package com.mvc.ttpay_android.bean

data class BlockOrderOnIdBean(
        /**
         * code : 0
         * data : {"buyTokenId":0,"buyTokenName":"string","buyValue":0,"classify":0,"createdAt":0,"from":"string","sellTokenId":0,"sellTokenName":"string","sellValue":0,"to":"string"}
         * message : string
         */

        var code: Int,
        var data: DataBean,
        var message: String
) {
    data class DataBean(
            /**
             * buyTokenId : 0
             * buyTokenName : string
             * buyValue : 0
             * classify : 0
             * createdAt : 0
             * from : string
             * sellTokenId : 0
             * sellTokenName : string
             * sellValue : 0
             * to : string
             */
            var buyTokenId: Int,
            var buyTokenName: String,
            var buyValue: Double,
            var classify: Int,
            var createdAt: Long,
            var from: String,
            var sellTokenId: Int,
            var sellTokenName: String,
            var sellValue: Double,
            var to: String
    )
}
