package com.mvc.cryptovault_android.activity

import android.view.View
import com.blankj.utilcode.util.SPUtils
import com.gyf.barlibrary.ImmersionBar
import com.mvc.cryptovault_android.MyApplication
import com.mvc.cryptovault_android.R
import com.mvc.cryptovault_android.api.ApiStore
import com.mvc.cryptovault_android.base.BaseActivity
import com.mvc.cryptovault_android.bean.UserInfoBean
import com.mvc.cryptovault_android.common.Constant.SP.USER_INFO
import com.mvc.cryptovault_android.utils.JsonHelper
import com.mvc.cryptovault_android.utils.RetrofitUtils
import com.mvc.cryptovault_android.utils.RxHelper
import com.mvc.cryptovault_android.view.DialogHelper
import kotlinx.android.synthetic.main.activity_send_email.*
import okhttp3.MediaType
import okhttp3.RequestBody
import org.json.JSONObject

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
                dialogHelper!!.create(this, R.drawable.pending_icon_1, "发送验证码").show()
                RetrofitUtils.client(ApiStore::class.java).sendEmail(MyApplication.getTOKEN())
                        .compose(RxHelper.rxSchedulerHelper())
                        .subscribe({ codeBean ->
                            if (codeBean.code === 200) {
                                dialogHelper!!.resetDialogResource(this, R.drawable.success_icon, "验证码发送成功")
                            } else {
                                dialogHelper!!.resetDialogResource(this, R.drawable.miss_icon, codeBean.message)
                            }
                            dialogHelper?.dismissDelayed { null }
                        }, {
                            dialogHelper!!.resetDialogResource(this, R.drawable.miss_icon, it.message)
                            dialogHelper?.dismissDelayed { null }
                        })
            }
            R.id.submit -> {
                if (checkNotNullValue()) {
                    dialogHelper!!.create(this, R.drawable.pending_icon_1, "验证中").show()
                    var code = email_code.text.toString()
                    var json = JSONObject()
                    json.put("valiCode", code)
                    var body = RequestBody.create(MediaType.parse("text/html"), json.toString())
                    RetrofitUtils.client(ApiStore::class.java).verificationCode(MyApplication.getTOKEN(), body)
                            .compose(RxHelper.rxSchedulerHelper())
                            .subscribe({ codeBean ->
                                if (codeBean.code === 200) {
                                    dialogHelper!!.dismissDelayed({ null }, 0)
                                    var dataIntent = intent
                                    dataIntent.putExtra("token", codeBean.code)
                                    startActivity(SetEmailActivity::class.java, dataIntent)
                                } else {
                                    dialogHelper!!.resetDialogResource(this, R.drawable.miss_icon, codeBean.message)
                                }
                                dialogHelper?.dismissDelayed { null }
                            }, {
                                dialogHelper!!.resetDialogResource(this, R.drawable.miss_icon, it.message)
                                dialogHelper?.dismissDelayed { null }
                            })

                }

            }
        }
    }

    private fun checkNotNullValue(): Boolean {
        if (email_code.text.toString() == "") {
            dialogHelper!!.create(this, R.drawable.miss_icon, "验证码不可为空").show()
            dialogHelper?.dismissDelayed { null }
            return false
        }
        return true
    }

    override fun initView() {
        ImmersionBar.with(this).statusBarView(R.id.status_bar).statusBarDarkFont(true).init()
        dialogHelper = DialogHelper.getInstance()
        val userJson = SPUtils.getInstance().getString(USER_INFO)
        val infoBean = JsonHelper.stringToJson(userJson, UserInfoBean::class.java) as UserInfoBean
        email_content.text = "验证当前邮箱：${infoBean.data.username}"
    }
}