package com.mvc.ttpay_android.bean

data class BannerBean(

        /**
         * code : 0
         * data : [{"content":"string","createdAt":0,"id":0,"title":"string"}]
         * message : string
         */

        var code: Int,
        var message: String,
        var data: ArrayList<DataBean>

) {
    data class DataBean(

            /**
             * content : string
             * createdAt : 0
             * id : 0
             * title : string
             */

            var content: String,
            var createdAt: Long,
            var id: Int,
            var title: String
    )
}
