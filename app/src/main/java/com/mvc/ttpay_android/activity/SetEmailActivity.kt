package com.mvc.ttpay_android.activity

import android.annotation.SuppressLint
import android.support.v4.content.ContextCompat
import android.view.View
import com.gyf.barlibrary.ImmersionBar
import com.mvc.ttpay_android.MyApplication
import com.mvc.ttpay_android.R
import com.mvc.ttpay_android.api.ApiStore
import com.mvc.ttpay_android.base.BaseActivity
import com.mvc.ttpay_android.listener.OnTimeEndCallBack
import com.mvc.ttpay_android.utils.RetrofitUtils
import com.mvc.ttpay_android.utils.RxHelper
import com.mvc.ttpay_android.utils.TimeVerification
import com.mvc.ttpay_android.view.DialogHelper
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
                    dialogHelper!!.create(this, R.drawable.pending_icon_1,  getString(R.string.send_code)).show()
                    RetrofitUtils.client(MyApplication.getBaseUrl(),ApiStore::class.java).sendValiCode(email.text.toString())
                            .compose(RxHelper.rxSchedulerHelper())
                            .subscribe({ bean ->
                                if (bean.code === 200) {
                                    dialogHelper!!.resetDialogResource(this, R.drawable.success_icon, "getString(R.string.send_successfully)")
                                    TimeVerification.getInstence().resume()
                                    setViewStatus()
                                } else {
                                    dialogHelper!!.resetDialogResource(this, R.drawable.miss_icon, bean.message)
                                }
                                dialogHelper?.dismissDelayed(null)
                            }, { throwable ->
                                if (throwable is SocketTimeoutException) {
                                    dialogHelper!!.resetDialogResource(this, R.drawable.miss_icon, getString(R.string.connection_timed_out))
                                }else{
                                    dialogHelper!!.resetDialogResource(this, R.drawable.miss_icon, throwable.message!!)
                                }
                                dialogHelper?.dismissDelayed(null)
                            })
                }
            }
            R.id.submit -> {
                if (checkCodeNotNullValue()) {
                    dialogHelper!!.create(this, R.drawable.pending_icon_1, getString(R.string.bind_load)).show()
                    var code = email_code.text.toString()
                    var email = email.text.toString()
                    var json = JSONObject()
                    json.put("email", email)
                    json.put("token", tempToken)
                    json.put("valiCode", code)
                    var body = RequestBody.create(MediaType.parse("text/html"), json.toString())
                    RetrofitUtils.client(MyApplication.getBaseUrl(),ApiStore::class.java).bindNewsEmail(MyApplication.getTOKEN(), body)
                            .compose(RxHelper.rxSchedulerHelper())
                            .subscribe({ codeBean ->
                                if (codeBean.code == 200) {
                                    dialogHelper!!.resetDialogResource(this, R.drawable.success_icon, getString(R.string.successfully_tied))
                                    dialogHelper!!.dismissDelayed  (object :DialogHelper.IDialogDialog{
                                        override fun callback() {
                                            startTaskActivity(this@SetEmailActivity)
                                        }
                                    })
                                } else {
                                    dialogHelper!!.resetDialogResource(this, R.drawable.miss_icon, codeBean.message)
                                    dialogHelper?.dismissDelayed(null)
                                }
                            }, {
                                if (it is SocketTimeoutException) {
                                    dialogHelper!!.resetDialogResource(this, R.drawable.miss_icon, getString(R.string.connection_timed_out))
                                }else{
                                    dialogHelper!!.resetDialogResource(this, R.drawable.miss_icon, it.message!!)
                                }
                                dialogHelper?.dismissDelayed(null)
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
                send_code.setTextColor(ContextCompat.getColor(baseContext, R.color.send_code_tv_bg))
                send_code.text = getString(R.string.reset_send)
            }
        }).updataTime()
    }

    private fun checkCodeNotNullValue(): Boolean {
        if (email_code.text.toString() == "") {
            dialogHelper!!.create(this, R.drawable.miss_icon, getString(R.string.verification_code_cannot_be_empty)).show()
            dialogHelper?.dismissDelayed(null)
            return false
        }
        return true
    }
    private fun checkEmailNotNullValue(): Boolean {
        if (email.text.toString() == "") {
            dialogHelper!!.create(this, R.drawable.miss_icon, getString(R.string.mailbox_cannot_be_empty)).show()
            dialogHelper?.dismissDelayed(null)
            return false
        }
        return true
    }

    @SuppressLint("CheckResult")
    override fun initView() {
        ImmersionBar.with(this).statusBarView(R.id.status_bar).statusBarDarkFont(true).init()
        dialogHelper = DialogHelper.instance
        tempToken = intent.getStringExtra("token")

    }
}