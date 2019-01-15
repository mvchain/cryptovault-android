package com.mvc.cryptovault_android.activity

import android.view.View
import com.gyf.barlibrary.ImmersionBar
import com.mvc.cryptovault_android.R
import com.mvc.cryptovault_android.api.ApiStore
import com.mvc.cryptovault_android.base.BaseActivity
import com.mvc.cryptovault_android.listener.OnTimeEndCallBack
import com.mvc.cryptovault_android.utils.RetrofitUtils
import com.mvc.cryptovault_android.utils.RxHelper
import com.mvc.cryptovault_android.utils.TimeVerification
import com.mvc.cryptovault_android.view.DialogHelper
import kotlinx.android.synthetic.main.activity_verification_password.*

/**
 * 验证信息的activity
 * 验证私钥，助记词，邮箱
 * 0邮箱 1私钥 2助记词
 */
class VerificationInfoActivity : BaseActivity(), View.OnClickListener {
    private val TYPE_EMAIL = 0
    private val TYPE_PRIVATEKEY = 1
    private val TYPE_MNEMONICS = 2
    private var resetType: Int = 0
    private lateinit var hintMsg: String
    private var dialogHelper: DialogHelper? = null
    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.verification_back -> {
                finish()
            }
            R.id.send_code -> {
                if (resetType == 2 && code.text.toString() == "") {
                    dialogHelper?.create(this, R.drawable.miss_icon, "验证码不可为空")?.show()
                    dialogHelper?.dismissDelayed(null)
                    return
                }
                dialogHelper?.create(this, R.drawable.pending_icon_1, "发送验证码")?.show()
                RetrofitUtils.client(ApiStore::class.java).sendValiCode(code.text.toString())
                        .compose(RxHelper.rxSchedulerHelper())
                        .subscribe({ httpTokenBean ->
                            if (httpTokenBean.code === 200) {
                                dialogHelper?.resetDialogResource(this, R.drawable.success_icon, "验证码发送成功")
                                dialogHelper?.dismissDelayed { null }
                                startTimeCountdown()
                            } else {
                                dialogHelper?.resetDialogResource(this, R.drawable.miss_icon, httpTokenBean.message)
                                dialogHelper?.dismissDelayed { null }
                            }
                        }, {
                            dialogHelper?.resetDialogResource(this, R.drawable.miss_icon, it.message)
                            dialogHelper?.dismissDelayed { null }
                        })
            }
            R.id.submit -> {
                if (checkNotNullValue(resetType)) {
                    //助记词需要先去请求助记词之后才允许跳转设置密码
                    if (resetType == TYPE_MNEMONICS) {
                        //传递邮箱过去 获取助记词
                        var dataIntent = intent
                        dataIntent.putExtra("account",account.text.toString())
                        startActivity(ResetPasswordVerificationMnemonicsActivity::class.java,dataIntent)
                    } else {
                        var dataIntent = intent
                        dataIntent.putExtra("account",account.text.toString())
                        dataIntent.putExtra("type",resetType)
                        startActivity(ResetPasswordActivity::class.java,dataIntent)
                    }
                }
            }
        }
    }

    private fun startTimeCountdown() {
        TimeVerification.getInstence().setOnTimeEndCallBack(object : OnTimeEndCallBack {
            override fun updata(time: Int) {
                send_code.isEnabled = false
                send_code.setBackgroundResource(R.drawable.shape_load_sendcode_bg)
                send_code.text = "${time}s"
            }

            override fun exit() {
                send_code.isEnabled = true
                send_code.setBackgroundResource(R.drawable.shape_sendcode_bg)
                send_code.text = "重新获取验证码"
            }
        }).updataTime()
    }

    private fun checkNotNullValue(resetType: Int): Boolean {
        if (account.text.toString() == "") {
            dialogHelper?.create(this, R.drawable.miss_icon, "${hintMsg}不可为空")?.show()
            dialogHelper?.dismissDelayed(null)
            return false
        }
        if (resetType == TYPE_EMAIL && code.text.toString() == "") {
            dialogHelper?.create(this, R.drawable.miss_icon, "验证码不可为空")?.show()
            dialogHelper?.dismissDelayed(null)
            return false
        }
        return true
    }

    override fun getLayoutId(): Int {
        return R.layout.activity_verification_password
    }

    override fun initData() {
    }

    override fun initView() {
        ImmersionBar.with(this).titleBar(R.id.status_bar).statusBarDarkFont(true).init()
        dialogHelper = DialogHelper.getInstance()
        var getIntent = intent
        resetType = getIntent.getIntExtra("type", -1)
        when (resetType) {
            TYPE_EMAIL -> {
                verification_title.text = "邮箱验证"
                account_hint.hint = "邮箱"
                hintMsg = "邮箱"
                email_layout.visibility = View.VISIBLE
            }
            TYPE_PRIVATEKEY -> {
                verification_title.text = "私钥验证"
                account_hint.hint = "输入私钥"
                hintMsg = "私钥"
                email_layout.visibility = View.GONE
            }
            TYPE_MNEMONICS -> {
                verification_title.text = "输入邮箱账户"
                account_hint.hint = "邮箱"
                hintMsg = "邮箱账户"
                email_layout.visibility = View.GONE
            }
        }
    }
}