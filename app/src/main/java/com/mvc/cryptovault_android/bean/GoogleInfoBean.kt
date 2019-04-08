package com.mvc.cryptovault_android.bean

data class GoogleInfoBean(
        var code: Int,
        var data: DataBean,
        var message: String
) {
    data class DataBean(
            /**
             * otpAuthURL : string
             * secret : string
             */
            var otpAuthURL: String,
            var secret: String
    )
}