package com.mvc.cryptovault_android.activity

import android.content.Intent
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
        var updateIntent = Intent()
        when (v.id) {
            R.id.back -> {
                finish()
            }
            R.id.update_email -> {
                updateIntent.setClass(this, SendEmailActivity::class.java)
                startActivity(updateIntent)
            }
            R.id.update_password -> {
                updateIntent.setClass(this, SetLoginPasswordActivity::class.java)
                updateIntent.putExtra("type", 0)
                startActivity(updateIntent)
            }
            R.id.update_pay_password -> {
                updateIntent.setClass(this, SetLoginPasswordActivity::class.java)
                updateIntent.putExtra("type", 1)
                startActivity(updateIntent)
            }
        }
    }
}