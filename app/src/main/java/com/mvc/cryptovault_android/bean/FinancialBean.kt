package com.mvc.cryptovault_android.bean

import java.math.BigDecimal

data class FinancialBean(
        var code: Int,
        var data: DataBean,
        var message: String
) {
    data class DataBean(
            var balance: BigDecimal,
            var income: BigDecimal,
            var lastIncome: BigDecimal
    )
}
