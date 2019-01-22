package com.mvc.cryptovault_android.activity

import android.annotation.SuppressLint
import android.support.v4.content.ContextCompat
import android.view.View
import com.gyf.barlibrary.ImmersionBar
import com.mvc.cryptovault_android.MyApplication
import com.mvc.cryptovault_android.R
import com.mvc.cryptovault_android.api.ApiStore
import com.mvc.cryptovault_android.base.BaseActivity
import com.mvc.cryptovault_android.listener.OnTimeEndCallBack
import com.mvc.cryptovault_android.utils.RetrofitUtils
import com.mvc.cryptovault_android.utils.RxHelper
import com.mvc.cryptovault_android.utils.TimeVerification
import com.mvc.cryptovault_android.view.DialogHelper
import kotlinx.android.synthetic.main.activity_set_email.*
import okhttp3.MediaType
import okhttp3.RequestBody
import org.json.JSONObject
import java.net.SocketTimeoutException

class SetEmailActivity : BaseActivity() {
    private var dialogHelper: DialogHelper? = null
    private lateinit var tempToken: String

    override fun getLayoutId(): Int {
        return R.layout.activity_set_email
    }

    override fun initData() {

    }

    fun onClick(v: View) {
        when (v.id) {
            R.id.back -> {
                finish()
            }
            R.id.send_code -> {
                if (checkEmailNotNullValue()) {
                    dialogHelper!!.create(this, R.drawable.pending_icon_1, "发送验证码").show()
                    RetrofitUtils.client(ApiStore::class.java).sendValiCode(email.text.toString())
                            .compose(RxHelper.rxSchedulerHelper())
                            .subscribe({ bean ->
                                if (bean.code === 200) {
                                    dialogHelper!!.resetDialogResource(this, R.drawable.success_icon, "验证码发送成功")
                                    TimeVerification.getInstence().resome()
                                    setViewStatus()
                                } else {
                                    dialogHelper!!.resetDialogResource(this, R.drawable.miss_icon, bean.message)
                                }
                                dialogHelper?.dismissDelayed { null }
                            }, { throwable ->
                                if (throwable is SocketTimeoutException) {
                                    dialogHelper!!.resetDialogResource(this, R.drawable.pending_icon_1, "连接超时")
                                }else{
                                    dialogHelper!!.resetDialogResource(this, R.drawable.pending_icon_1, throwable.message)
                                }
                                dialogHelper?.dismissDelayed { null }
                            })
                }
            }
            R.id.submit -> {
                if (checkCodeNotNullValue()) {
                    dialogHelper!!.create(this, R.drawable.pending_icon_1, "绑定中").show()
                    var code = email_code.text.toString()
                    var email = email.text.toString()
                    var json = JSONObject()
                    json.put("email", email)
                    json.put("token", tempToken)
                    json.put("valiCode", code)
                    var body = RequestBody.create(MediaType.parse("text/html"), json.toString())
                    RetrofitUtils.client(ApiStore::class.java).bindNewsEmail(MyApplication.getTOKEN(), body)
                            .compose(RxHelper.rxSchedulerHelper())
                            .subscribe({ codeBean ->
                                if (codeBean.code === 200) {
                                    dialogHelper!!.resetDialogResource(this, R.drawable.success_icon, "换绑成功")
                                    dialogHelper!!.dismissDelayed {
                                        startTaskActivity(this)
                                    }
                                } else {
                                    dialogHelper!!.resetDialogResource(this, R.drawable.miss_icon, codeBean.message)
                                    dialogHelper?.dismissDelayed { null }
                                }
                            }, {
                                if (it is SocketTimeoutException) {
                                    dialogHelper!!.resetDialogResource(this, R.drawable.pending_icon_1, "连接超时")
                                }else{
                                    dialogHelper!!.resetDialogResource(this, R.drawable.pending_icon_1, it.message)
                                }
                                dialogHelper?.dismissDelayed { null }
                            })
                }
            }
        }
    }

    private fun setViewStatus() {
        TimeVerification.getInstence().setOnTimeEndCallBack(object : OnTimeEndCallBack {
            override fun updata(time: Int) {
                send_code.isEnabled = false
                send_code.setBackgroundResource(R.drawable.shape_load_sendcode_bg)
                send_code.setTextColor(ContextCompat.getColor(baseContext, R.color.edit_bg))
                send_code.text = "${time}s"
            }

            override fun exit() {
                send_code.isEnabled = true
                send_code.setBackgroundResource(R.drawable.shape_sendcode_bg)
                send_code.setTextColor(ContextCompat.getColor(baseContext, R.color.login_content))
                send_code.text = "重新发送"
            }
        }).updataTime()
    }

    private fun checkCodeNotNullValue(): Boolean {
        if (email_code.text.toString() == "") {
            dialogHelper!!.create(this, R.drawable.miss_icon, "验证码不可为空").show()
            dialogHelper?.dismissDelayed { null }
            return false
        }
        return true
    }
    private fun checkEmailNotNullValue(): Boolean {
        if (email.text.toString() == "") {
            dialogHelper!!.create(this, R.drawable.miss_icon, "邮箱不可为空").show()
            dialogHelper?.dismissDelayed { null }
            return false
        }
        return true
    }

    @SuppressLint("CheckResult")
    override fun initView() {
        ImmersionBar.with(this).statusBarView(R.id.status_bar).statusBarDarkFont(true).init()
        dialogHelper = DialogHelper.getInstance()
        tempToken = intent.getStringExtra("token")

    }
}