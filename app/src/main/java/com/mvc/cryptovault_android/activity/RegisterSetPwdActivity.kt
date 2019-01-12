package com.mvc.cryptovault_android.activity

import android.view.View
import com.gyf.barlibrary.ImmersionBar
import com.mvc.cryptovault_android.R
import com.mvc.cryptovault_android.base.BaseActivity
import com.mvc.cryptovault_android.view.DialogHelper
import kotlinx.android.synthetic.main.activity_reg_setpassword.*

class RegisterSetPwdActivity : BaseActivity(), View.OnClickListener {
    private var dialogHelper: DialogHelper? = null

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.reg_back -> {
                finish()
            }
            R.id.reg_submit -> {
                if (checkNotNullValue()) {

                }
            }
        }
    }

    /**
     * 检查元素是否为空
     */
    private fun checkNotNullValue(): Boolean {
        if (reg_login_pwd.text.toString() == "") {
            dialogHelper?.create(this, R.drawable.miss_icon, "登陆密码不可为空")?.show()
            dialogHelper?.dismissDelayed(null)
            return false
        }
        if (reg_pay_pwd.text.toString() == "") {
            dialogHelper?.create(this, R.drawable.miss_icon, "支付密码不可为空")?.show()
            dialogHelper?.dismissDelayed(null)
            return false
        }
        return true
    }

    override fun getLayoutId(): Int {
        return R.layout.activity_reg_setpassword
    }

    override fun initData() {

    }

    override fun initView() {
        ImmersionBar.with(this).titleBar(R.id.status_bar).statusBarDarkFont(true).init()
        dialogHelper = DialogHelper.getInstance()
        reg_layout.viewTreeObserver.addOnDrawListener {
            if (reg_login_pwd.text.toString() != ""
                    && reg_pay_pwd.text.toString() != "") {
                reg_submit.setBackgroundResource(R.drawable.bg_login_submit)
                reg_submit.isEnabled = true
            } else {
                reg_submit.setBackgroundResource(R.drawable.bg_toge_child_item_tv_blue_nocheck)
                reg_submit.isEnabled = false
            }
        }
    }
}