package com.mvc.ttpay_android.bean

data class BlockTransactionBean(
        /**
         * code : 0
         * data : [{"confirm":0,"createdAt":0,"hash":"string","height":0,"transactionId":0}]
         * message : string
         */
        var code: Int,
        var message: String,
        var data: List<DataBean>
) {
   data class DataBean(
           /**
            * confirm : 0
            * createdAt : 0
            * hash : string
            * height : 0
            * transactionId : 0
            */
           var confirm: Int,
           var createdAt: Long,
           var hash: String,
           var height: Int,
           var transactionId: Int
   )
}
