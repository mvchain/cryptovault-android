package com.mvc.cryptovault_android.activity

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.view.View
import com.blankj.utilcode.util.LogUtils
import com.blankj.utilcode.util.ToastUtils
import com.bumptech.glide.Glide
import com.gyf.barlibrary.ImmersionBar
import com.mvc.cryptovault_android.MyApplication
import com.mvc.cryptovault_android.R
import com.mvc.cryptovault_android.api.ApiStore
import com.mvc.cryptovault_android.base.BaseActivity
import com.mvc.cryptovault_android.utils.AppInnerDownLoder
import com.mvc.cryptovault_android.utils.RetrofitUtils
import com.mvc.cryptovault_android.utils.RxHelper
import kotlinx.android.synthetic.main.activity_google_code.*
import kotlinx.android.synthetic.main.activity_mine_mnemonics.*

class GoogleCodeActivity : BaseActivity() {
    override fun getLayoutId(): Int {
        return R.layout.activity_google_code
    }

    override fun initData() {
        RetrofitUtils.client(MyApplication.getBaseUrl(), ApiStore::class.java).getGoogleInfo(MyApplication.getTOKEN())
                .compose(RxHelper.rxSchedulerHelper())
                .subscribe({
                    google->
                    if (google.code == 200) {
                        google_key.text = google.data.secret
                        Glide.with(this@GoogleCodeActivity).load(google.data.otpAuthURL).into(google_qcode)
                    }
                },{
                    error->
                    LogUtils.e(error?.message)
                })
    }

    override fun initView() {
        ImmersionBar.with(this).statusBarView(R.id.status_bar).statusBarDarkFont(true).init()
    }

    fun onClick(v: View) {
        when (v.id) {
            R.id.google_back -> {
                finish()
            }
            R.id.google_key -> {
                var clipManager = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
                var clipText = ClipData.newPlainText("google_key", private_key.text.toString())
                clipManager.primaryClip = clipText
                ToastUtils.showLong("16位密钥已复制至剪贴板")
            }
            R.id.google_app -> {
                AppInnerDownLoder.downLoadApk(this, "", "google")
            }
            R.id.google_next -> {
                startActivity(GoogleVerificationActivity::class.java)
            }
        }
    }
}