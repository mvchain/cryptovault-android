package com.mvc.cryptovault_android.activity

import android.content.Intent
import android.view.View
import com.gyf.barlibrary.ImmersionBar
import com.mvc.cryptovault_android.R
import com.mvc.cryptovault_android.base.BaseActivity

class ForgetPasswordActivity : BaseActivity(), View.OnClickListener {
    override fun getLayoutId(): Int {
        return R.layout.activity_forgetpassword
    }

    override fun initData() {

    }

    override fun initView() {
        ImmersionBar.with(this).titleBar(R.id.status_bar).statusBarDarkFont(true).init()
    }

    override fun onClick(v: View?) {
        var pIntent = Intent(this,VerificationInfoActivity::class.java)
        when (v?.id) {
            R.id.back -> {
                finish()
            }
            R.id.forget_email -> {
                pIntent.putExtra("type",0)
                startActivity(pIntent)
            }
            R.id.forget_private_key -> {
                pIntent.putExtra("type",1)
                startActivity(pIntent)
            }
            R.id.forget_mnemonics -> {
                pIntent.putExtra("type",2)
                startActivity(pIntent)
            }
        }
    }
}