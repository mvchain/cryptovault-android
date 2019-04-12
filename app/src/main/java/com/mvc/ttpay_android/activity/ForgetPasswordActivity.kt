package com.mvc.ttpay_android.activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import com.gyf.barlibrary.ImmersionBar
import com.mvc.ttpay_android.R
import com.mvc.ttpay_android.base.BaseActivity
import com.mvc.ttpay_android.event.PayPwdRefreshEvent
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe

class ForgetPasswordActivity : BaseActivity(), View.OnClickListener {
    override fun getLayoutId(): Int {
        return R.layout.activity_forgetpassword
    }

    override fun initData() {

    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        EventBus.getDefault().register(this)
    }

    override fun onDestroy() {
        EventBus.getDefault().unregister(this)
        super.onDestroy()
    }
    override fun initView() {
        ImmersionBar.with(this).titleBar(R.id.status_bar).statusBarDarkFont(true).init()
    }

    override fun onClick(v: View?) {
        var pIntent = Intent(this, VerificationInfoActivity::class.java)
        when (v?.id) {
            R.id.back -> {
                finish()
            }
            R.id.forget_email -> {
                pIntent.putExtra("type", 0)
                startActivity(pIntent)
            }
            R.id.forget_private_key -> {
                pIntent.putExtra("type", 1)
                startActivity(pIntent)
            }
            R.id.forget_mnemonics -> {
                pIntent.putExtra("type", 2)
                startActivity(pIntent)
            }
        }
    }
    @Subscribe
    public fun updatePaySuccess(pay : PayPwdRefreshEvent){
        finish()
    }
}