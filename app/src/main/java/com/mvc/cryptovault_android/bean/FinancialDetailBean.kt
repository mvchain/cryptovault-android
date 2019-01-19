package com.mvc.cryptovault_android.bean

/**
 * code : 200
 * data : {"balance":10000,"baseTokenId":4,"baseTokenName":"USDT","content":"dfs","id":8,"incomeMax":5,"incomeMin":1,"limitValue":100,"minValue":1,"name":"cctv","purchased":0,"ratio":1,"rule":"dfsdf","startAt":1547710446692,"stopAt":1548777600000,"times":11,"tokenId":1,"tokenName":"VRT","userLimit":10}
 * message :
 */
data class FinancialDetailBean(
        var code: Int,
        var data: DataBean,
        var message: String
) {
    /**
     * balance : 10000
     * baseTokenId : 4
     * baseTokenName : USDT
     * content : dfs
     * id : 8
     * incomeMax : 5
     * incomeMin : 1
     * limitValue : 100
     * minValue : 1
     * name : cctv
     * purchased : 0
     * ratio : 1
     * rule : dfsdf
     * startAt : 1547710446692
     * stopAt : 1548777600000
     * times : 11
     * tokenId : 1
     * tokenName : VRT
     * userLimit : 10
     */
    data class DataBean(
            var balance: Int,
            var baseTokenId: Int,
            var baseTokenName: String,
            var content: String,
            var id: Int,
            var incomeMax: Int,
            var incomeMin: Int,
            var limitValue: Int,
            var minValue: Int,
            var name: String,
            var purchased: Int,
            var ratio: Int,
            var rule: String,
            var startAt: Long,
            var stopAt: Long,
            var times: Int,
            var tokenId: Int,
            var tokenName: String,
            var userLimit: Int
    )
}
