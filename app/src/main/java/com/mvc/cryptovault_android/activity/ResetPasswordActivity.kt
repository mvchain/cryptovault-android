package com.mvc.cryptovault_android.activity

import android.text.InputType
import android.view.View
import com.blankj.utilcode.util.EncryptUtils
import com.blankj.utilcode.util.LogUtils
import com.blankj.utilcode.util.SPUtils
import com.gyf.barlibrary.ImmersionBar
import com.mvc.cryptovault_android.MyApplication
import com.mvc.cryptovault_android.R
import com.mvc.cryptovault_android.api.ApiStore
import com.mvc.cryptovault_android.base.BaseActivity
import com.mvc.cryptovault_android.common.Constant
import com.mvc.cryptovault_android.common.Constant.SP.USER_EMAIL
import com.mvc.cryptovault_android.event.PayPwdRefreshEvent
import com.mvc.cryptovault_android.listener.EditTextChange

import com.mvc.cryptovault_android.utils.RetrofitUtils
import com.mvc.cryptovault_android.utils.RxHelper
import com.mvc.cryptovault_android.view.DialogHelper
import kotlinx.android.synthetic.main.activity_reset_password.*
import okhttp3.MediaType
import okhttp3.RequestBody
import org.greenrobot.eventbus.EventBus
import org.json.JSONObject
import java.net.SocketTimeoutException

class ResetPasswordActivity : BaseActivity(), View.OnClickListener {
    private var dialogHelper: DialogHelper? = null
    private lateinit var account: String
    private var type: Int = 0
    private lateinit var value: String
    private var passwordType = SPUtils.getInstance().getString(Constant.SP.UPDATE_PASSWORD_TYPE)
    private val TYPE_LOGIN_PASSWORD = "1"
    private val TYPE_PAY_PASSWORD = "2"


    override fun initView() {
        ImmersionBar.with(this).titleBar(R.id.status_bar).statusBarDarkFont(true).init()
        dialogHelper = DialogHelper.getInstance()
        account = intent.getStringExtra("token")
        type = intent.getIntExtra("type", -1)
        when (passwordType) {
            TYPE_LOGIN_PASSWORD -> {
                account_pay_hint.visibility = View.GONE
                reset_title.text = "设置登录密码"

            }
            TYPE_PAY_PASSWORD -> {
                account_hint.visibility = View.GONE
                reset_title.text = "设置支付密码"
            }
        }
    }

    override fun getLayoutId(): Int {
        return R.layout.activity_reset_password
    }

    override fun initData() {
        //设置眼睛可见
        login_pwd.addTextChangedListener(object : EditTextChange() {
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                val length = s.length
                account_hint.isPasswordVisibilityToggleEnabled = length > 0
            }
        })
        //设置眼睛可见
        pay_pwd.addTextChangedListener(object : EditTextChange() {
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                val length = s.length
                account_pay_hint.isPasswordVisibilityToggleEnabled = length > 0
            }
        })
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.back -> {
                finish()
            }
            R.id.submit -> {
                when (passwordType) {
                    TYPE_LOGIN_PASSWORD -> {
                        value = login_pwd.text.toString()
                        if (value == "") {
                            dialogHelper?.create(this, R.drawable.miss_icon, "登录密码不可为空")?.show()
                            dialogHelper?.dismissDelayed(null)
                            return
                        }
                    }
                    TYPE_PAY_PASSWORD -> {
                        value = pay_pwd.text.toString()
                        if (value == "") {
                            dialogHelper?.create(this, R.drawable.miss_icon, "支付密码不可为空")?.show()
                            dialogHelper?.dismissDelayed(null)
                            return
                        }
                    }
                }
                dialogHelper?.create(this, R.drawable.pending_icon_1, "重置中")?.show()
                var json = JSONObject()
                json.put("token", account)
                val email = SPUtils.getInstance().getString(USER_EMAIL)
                LogUtils.e("$email email")
                json.put("password", EncryptUtils.encryptMD5ToString(email + EncryptUtils.encryptMD5ToString(value)))
                json.put("type", passwordType)
                var body = RequestBody.create(MediaType.parse("text/html"), json.toString())
                RetrofitUtils.client(MyApplication.getBaseUrl(),ApiStore::class.java).userForget(body)
                        .compose(RxHelper.rxSchedulerHelper())
                        .subscribe({ updateBean ->
                            if (updateBean.code === 200) {
                                dialogHelper?.resetDialogResource(this, R.drawable.success_icon, "密码修改成功")
                                dialogHelper?.dismissDelayed {
                                    if (passwordType == TYPE_LOGIN_PASSWORD) {
                                        startTaskActivity(this)
                                    } else {
                                        EventBus.getDefault().post(PayPwdRefreshEvent())
                                        finish()
                                    }
                                }
                            } else {
                                dialogHelper?.resetDialogResource(this, R.drawable.pending_icon_1, updateBean.message)
                                dialogHelper?.dismissDelayed { null }
                            }
                        }, { error ->
                            if (error is SocketTimeoutException) {
                                dialogHelper!!.resetDialogResource(this, R.drawable.pending_icon_1, "连接超时")
                            } else {
                                dialogHelper!!.resetDialogResource(this, R.drawable.pending_icon_1, error.message)
                            }
                            dialogHelper?.dismissDelayed { null }
                        })
            }
        }
    }
}
