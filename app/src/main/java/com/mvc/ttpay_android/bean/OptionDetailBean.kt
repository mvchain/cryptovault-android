package com.mvc.ttpay_android.bean

data class OptionDetailBean(
        /**
         * code : 200
         * data : {"baseTokenName":"USDT","financialName":"cctv","income":0,"incomeMax":5,"incomeMin":1,"times":11,"tokenName":"VRT","value":2}
         * message :
         */

        var code: Int,
        var data: DataBean,
        var message: String
) {
    data class DataBean(
            /**
             * baseTokenName : USDT
             * financialName : cctv
             * income : 0
             * incomeMax : 5
             * incomeMin : 1
             * times : 11
             * tokenName : VRT
             * needSign:1
             * value : 2
             */

            var baseTokenName: String,
            var financialName: String,
            var income: Double,
            var incomeMax: Double,
            var incomeMin: Double,
            var times: Int,
            var needSign: Int,
            var tokenName: String,
            var value: Double
    )
}
