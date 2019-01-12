package com.mvc.cryptovault_android.bean

data class MnemonicsBean(
        var code: Int,
        var message: String,
        var dataBean: DataBean
) {
    data class DataBean(
            var privateKey: String,
            var mnemonics: List<String>
    )
}
