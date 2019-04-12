package com.mvc.ttpay_android.bean

data class LoginBean(
        /**
         * code : 200
         * data : {"refreshToken":"lIjoiYXBwIiwidZFud2vVciTEBLM5xNzu4sRZaIxdehgnQeFA","token":"eyJ1c2VybmFtZSI6IjE4VCeJ3kAhIev4OHaYxkbHXEIjJmYs","userId":1}
         * message :
         */
        var code: Int,
        var data: DataBean,
        var message: String
) {


    data class DataBean(
            /**
             * refreshToken : eyJhbGcidXNlcklkIjoxLCJzZXJ2aWNlIjoiYXBwIiwidHlwZSI6InJlZnJlc2giLCJleHAiOjE1NDM5MjE4Mzh9.48EXkjrQZFud2vVciTEBLM5xNzu4sRZaIxdehgnQeFA
             * publicKey :
             * token : lcklkIjoxLCJzZXJ2aWNlIjoiYXBwIiwidHlwZSI6InRva2VuIiwiZXhwIjoxNTQzMzIzMDg2fQ.WIN2gtguw3NLm5LVCeJ3kAhIev4OHaYxkbHXEIjJmYs
             * userId : 1
             */
            var email: String,
            var googleCheck: Int,
            var publicKey: String,
            var refreshToken: String,
            var salt: String,
            var token: String,
            var userId: Int = 0
    )
}
