package com.mvc.cryptovault_android.activity

import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.view.View
import com.gyf.barlibrary.ImmersionBar
import com.mvc.cryptovault_android.R
import com.mvc.cryptovault_android.base.BaseActivity
import com.mvc.cryptovault_android.bean.LanguageEvent
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe

class SelectLoginActivity : BaseActivity(), View.OnClickListener {
    override fun getLayoutId(): Int {
        return R.layout.activity_selectlogin
    }

    override fun initData() {

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        EventBus.getDefault().register(this)
    }

    override fun onDestroy() {
        super.onDestroy()
        EventBus.getDefault().unregister(this)
    }

    override fun initView() {
        if (Build.VERSION.SDK_INT >= 21) {
            var decorView = window.decorView
            var option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
            decorView.systemUiVisibility = option
            window.statusBarColor = Color.TRANSPARENT
        } else {
            ImmersionBar.with(this).titleBar(R.id.status_bar).statusBarDarkFont(true).init()
        }
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.login -> {
                startActivity(LoginActivity::class.java)
            }
            R.id.register -> {
                startActivity(RegisterInvitationActivity::class.java)
            }
            R.id.change_language->{
                startActivity(LanguageActivity::class.java)
            }
        }
    }

    @Subscribe
    fun changeLanguage(languageEvent: LanguageEvent) {
        recreate()
    }
}
