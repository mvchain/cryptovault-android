package com.mvc.cryptovault_android.activity

import android.view.View
import com.gyf.barlibrary.ImmersionBar
import com.mvc.cryptovault_android.R
import com.mvc.cryptovault_android.base.BaseActivity

class SelectResetPasswordActivity : BaseActivity() {
    override fun getLayoutId(): Int {
        return R.layout.activity_select_resetpassword
    }

    override fun initData() {
    }

    override fun initView() {
        ImmersionBar.with(this).statusBarView(R.id.status_bar).statusBarDarkFont(true).init()
    }

    fun onClick(v: View) {
        when (v.id) {
            R.id.back -> {
                finish()
            }
            R.id.update_email -> {

            }
            R.id.update_password -> {
//                startActivity(ForgetPasswordActivity::class.java)
            }
            R.id.update_pay_password -> {

            }
        }
    }
}