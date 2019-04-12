package com.mvc.cryptovault_android.bean

data class FinancialListBean(
        /**
         * code : 0
         * data : [{"baseTokenName":"string","id":0,"incomeMax":0,"incomeMin":0,"minValue":0,"name":"string","stopAt":0,"times":0}]
         * message : string
         */

        var code: Int,
        var message: String,
        var data: List<DataBean>
) {
    data class DataBean(

            /**
             * baseTokenName : string
             * id : 0
             * incomeMax : 0
             * incomeMin : 0
             * minValue : 0
             * name : string
             * needSign : 0
             * sold :double
             * stopAt : 0
             * times : 0
             */
            var baseTokenName: String,
            var id: Int,
            var incomeMax: Double,
            var incomeMin: Double,
            var limitValue: Double,
            var minValue: Double,
            var name: String,
            var needSign: Int,
            var sold: Double,
            var stopAt: Long,
            var times: Long
    )
}
