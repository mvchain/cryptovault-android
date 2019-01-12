package com.mvc.cryptovault_android.activity

import android.view.View
import com.blankj.utilcode.util.LogUtils
import com.blankj.utilcode.util.SPUtils
import com.mvc.cryptovault_android.R
import com.mvc.cryptovault_android.base.BaseActivity
import com.mvc.cryptovault_android.common.Constant.SP.REG_MINEMNEMONICS
import com.mvc.cryptovault_android.common.Constant.SP.REG_PRIVATEKEY

class MineMnemonicsActivity : BaseActivity(), View.OnClickListener {
    var menJson = SPUtils.getInstance().getString(REG_MINEMNEMONICS)
    var privatekey = SPUtils.getInstance().getString(REG_PRIVATEKEY)

    override fun getLayoutId(): Int {
        return R.layout.activity_mine_mnemonics
    }

    override fun initData() {

    }

    override fun initView() {
        LogUtils.e("Mnemonics是$menJson")
    }

    override fun onClick(v: View?) {
        when (v?.id) {

        }
    }
}