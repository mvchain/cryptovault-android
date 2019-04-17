package com.mvc.ttpay_android.activity

import android.support.v4.content.ContextCompat
import android.view.View
import com.blankj.utilcode.util.SPUtils
import com.gyf.barlibrary.ImmersionBar
import com.mvc.ttpay_android.MyApplication
import com.mvc.ttpay_android.R
import com.mvc.ttpay_android.api.ApiStore
import com.mvc.ttpay_android.base.BaseActivity
import com.mvc.ttpay_android.bean.UserInfoBean
import com.mvc.ttpay_android.common.Constant.SP.USER_INFO
import com.mvc.ttpay_android.listener.OnTimeEndCallBack
import com.mvc.ttpay_android.utils.JsonHelper
import com.mvc.ttpay_android.utils.RetrofitUtils
import com.mvc.ttpay_android.utils.RxHelper
import com.mvc.ttpay_android.utils.TimeVerification
import com.mvc.ttpay_android.view.DialogHelper
import kotlinx.android.synthetic.main.activity_send_email.*
import okhttp3.MediaType
import okhttp3.RequestBody
import org.json.JSONObject
import java.net.SocketTimeoutException

class SendEmailActivity : BaseActivity() {
    private var dialogHelper: DialogHelper? = null

    override fun getLayoutId(): Int {
        return R.layout.activity_send_email
    }

    override fun initData() {
    }

    fun onClick(v: View) {
        when (v.id) {
            R.id.back -> {
                finish()
            }
            R.id.send_code -> {
                dialogHelper!!.create(this, R.drawable.pending_icon_1,  getString(R.string.send_code)).show()
                RetrofitUtils.client(MyApplication.getBaseUrl(), ApiStore::class.java).sendEmail(MyApplication.getTOKEN())
                        .compose(RxHelper.rxSchedulerHelper())
                        .subscribe({ codeBean ->
                            if (codeBean.code === 200) {
                                dialogHelper!!.resetDialogResource(this, R.drawable.success_icon, "getString(R.string.send_successfully)")
                                setViewStatus()
                            } else {
                                dialogHelper!!.resetDialogResource(this, R.drawable.miss_icon, codeBean.message)
                            }
                            dialogHelper?.dismissDelayed(null)
                        }, {
                            dialogHelper!!.resetDialogResource(this, R.drawable.miss_icon, it.message!!)
                            dialogHelper?.dismissDelayed(null)
                        })
            }
            R.id.submit -> {
                if (checkNotNullValue()) {
                    dialogHelper!!.create(this, R.drawable.pending_icon_1, getString(R.string.in_verification)).show()
                    var code = email_code.text.toString()
                    var json = JSONObject()
                    json.put("valiCode", code)
                    var body = RequestBody.create(MediaType.parse("text/html"), json.toString())
                    RetrofitUtils.client(MyApplication.getBaseUrl(), ApiStore::class.java).verificationCode(MyApplication.getTOKEN(), body)
                            .compose(RxHelper.rxSchedulerHelper())
                            .subscribe({ codeBean ->
                                if (codeBean.code === 200) {
                                    dialogHelper!!.dismissDelayed(null, 0)
                                    var dataIntent = intent
                                    dataIntent.putExtra("token", codeBean.data)
                                    startActivity(SetEmailActivity::class.java, dataIntent)
                                } else {
                                    dialogHelper!!.resetDialogResource(this, R.drawable.miss_icon, codeBean.message)
                                }
                                dialogHelper?.dismissDelayed(null)
                            }, {
                                if (it is SocketTimeoutException) {
                                    dialogHelper!!.resetDialogResource(this, R.drawable.miss_icon, getString(R.string.connection_timed_out))
                                } else {
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


    private fun checkNotNullValue(): Boolean {
        if (email_code.text.toString() == "") {
            dialogHelper!!.create(this, R.drawable.miss_icon, "验证码不可为空").show()
            dialogHelper?.dismissDelayed(null)
            return false
        }
        return true
    }

    override fun initView() {
        ImmersionBar.with(this).statusBarView(R.id.status_bar).statusBarDarkFont(true).init()
        dialogHelper = DialogHelper.instance
        val userJson = SPUtils.getInstance().getString(USER_INFO)
        val infoBean = JsonHelper.stringToJson(userJson, UserInfoBean::class.java) as UserInfoBean
        email_content.text = "验证当前邮箱：${infoBean.data.username}"
    }
}