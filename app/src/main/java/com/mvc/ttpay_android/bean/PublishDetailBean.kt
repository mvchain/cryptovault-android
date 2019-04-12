package com.mvc.ttpay_android.bean

data class PublishDetailBean(
        var code: Int,
        var data: DataBean

) {
    data class DataBean(
            var baseTokenId: Int,
            var baseTokenName: String,
            var createdAt: Long,
            var payed: Double,
            var projectId: Int,
            var projectImage: String,
            var projectLimit: Int,
            var projectName: String,
            var ratio: Double,
            var releaseValue: Double,
            var startedAt: Long,
            var status: Int,
            var stopAt: Long,
            var successPayed: Double,
            var successValue: Double,
            var tokenId: Int,
            var tokenName: String,
            var total: Double,
            var updatedAt: Long,
            var value: Double
    )
}