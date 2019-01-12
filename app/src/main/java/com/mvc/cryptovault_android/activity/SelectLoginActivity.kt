package com.mvc.cryptovault_android.activity

import android.view.View
import com.gyf.barlibrary.ImmersionBar
import com.mvc.cryptovault_android.R
import com.mvc.cryptovault_android.base.BaseActivity

class SelectLoginActivity : BaseActivity(), View.OnClickListener {


    override fun getLayoutId(): Int {
        return R.layout.activity_selectlogin
    }

    override fun initData() {

    }

    override fun initView() {
        ImmersionBar.with(this).titleBar(R.id.status_bar).statusBarDarkFont(true).init()
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.login -> {
                startActivity(LoginActivity::class.java)
            }
            R.id.register -> {
                startActivity(RegisterInvitationActivity::class.java)
            }
        }
    }
}
