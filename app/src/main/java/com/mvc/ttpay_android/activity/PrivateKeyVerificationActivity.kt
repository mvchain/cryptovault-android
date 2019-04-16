package com.mvc.ttpay_android.activity

import android.annotation.SuppressLint
import android.view.View
import com.blankj.utilcode.util.SPUtils
import com.gyf.barlibrary.ImmersionBar
import com.mvc.ttpay_android.MyApplication
import com.mvc.ttpay_android.R
import com.mvc.ttpay_android.api.ApiStore
import com.mvc.ttpay_android.base.BaseActivity
import com.mvc.ttpay_android.common.Constant.SP.USER_EMAIL
import com.mvc.ttpay_android.utils.RetrofitUtils
import com.mvc.ttpay_android.utils.RxHelper
import com.mvc.ttpay_android.view.DialogHelper
import kotlinx.android.synthetic.main.activity_layout_privatekey_verifition.*
import okhttp3.MediaType
import okhttp3.RequestBody
import org.json.JSONObject

class PrivateKeyVerificationActivity : BaseActivity() {
    private var dialogHelper: DialogHelper? = null

    override fun getLayoutId(): Int {
        return R.layout.activity_layout_privatekey_verifition
    }

    override fun initData() {

    }

    override fun initView() {
        ImmersionBar.with(this).titleBar(R.id.status_bar).statusBarDarkFont(true).init()
        dialogHelper = DialogHelper.instance
    }

    fun onClick(view: View) {
        when (view.id) {
            R.id.private_back -> {
                finish()
            }
            R.id.submit -> {
                var email = SPUtils.getInstance().getString(USER_EMAIL)
                if (checkNotNullValue()) {
                    dialogHelper?.create(this, R.drawable.pending_icon_1, getString(R.string.in_verification))?.show()
                    verification(email, 1, private_key.text.toString())
                }
            }
        }
    }

    private fun checkNotNullValue(): Boolean {
        if (private_key.text.toString() == "") {
            dialogHelper?.create(this, R.drawable.miss_icon, "私钥不可为空")?.show()
            dialogHelper?.dismissDelayed(null)
            return false
        }
        return true
    }

    @SuppressLint("CheckResult")
    fun verification(email: String, type: Int, value: String) {
        var json = JSONObject()
        json.put("email", email)
        json.put("resetType", type)
        json.put("value", value)
        var body = RequestBody.create(MediaType.parse("text/html"), json.toString())
        RetrofitUtils.client(MyApplication.getBaseUrl(), ApiStore::class.java)
                .updatePassword(body)
                .compose(RxHelper.rxSchedulerHelper())
                .subscribe({ httpData ->
                    if (httpData.code === 200) {
                        dialogHelper?.dismiss()
                        var tokenIntent = intent
                        tokenIntent.putExtra("token", httpData.data)
                        startActivity(ResetPasswordActivity::class.java, tokenIntent)
                    } else {
                        dialogHelper?.resetDialogResource(this, R.drawable.miss_icon, httpData.message)
                        dialogHelper?.dismissDelayed(null)
                    }
                }, { error ->
                    dialogHelper?.resetDialogResource(this, R.drawable.miss_icon, error.message!!)
                    dialogHelper?.dismissDelayed(null)
                })
    }
}