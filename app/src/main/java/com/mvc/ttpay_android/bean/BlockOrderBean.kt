package com.mvc.ttpay_android.bean

data class BlockOrderBean(
        var code: Int,
        var data: ArrayList<DataBean>,
        var message: String
) {
    data class DataBean(
            var classify: Int,
            var createdAt: Long,
            var id: Int
    )
}