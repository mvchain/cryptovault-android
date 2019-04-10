package com.mvc.cryptovault_android.activity

import android.content.Intent
import android.view.View
import com.blankj.utilcode.util.LogUtils
import com.blankj.utilcode.util.SPUtils
import com.gyf.barlibrary.ImmersionBar
import com.mvc.cryptovault_android.R
import com.mvc.cryptovault_android.base.BaseActivity
import com.mvc.cryptovault_android.common.Constant.SP.USER_GOOGLE
import kotlinx.android.synthetic.main.activity_select_resetpassword.*

class SelectResetPasswordActivity : BaseActivity() {
    private var googleStatus = SPUtils.getInstance().getInt(USER_GOOGLE)

    override fun getLayoutId(): Int {
        return R.layout.activity_select_resetpassword
    }

    override fun initData() {
    }

    override fun initView() {
        ImmersionBar.with(this).statusBarView(R.id.status_bar).statusBarDarkFont(true).init()
        switch_google_verification.setLeftString(if (googleStatus == 1) getString(R.string.switch_google_verification_off) else getString(R.string.switch_google_verification_on))
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
            R.id.switch_google_verification -> {
                if(googleStatus == 0){
                    updateIntent.setClass(this, GoogleCodeActivity::class.java)
                    startActivity(updateIntent)
                }else{
                    updateIntent.setClass(this, GoogleVerificationActivity::class.java)
                    updateIntent.putExtra("googleSecret", "")
                    startActivity(updateIntent)
                }
            }
        }
    }
}