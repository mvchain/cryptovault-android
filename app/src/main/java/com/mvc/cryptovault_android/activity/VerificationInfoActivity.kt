package com.mvc.cryptovault_android.activity

import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.view.View
import com.blankj.utilcode.util.SPUtils
import com.gyf.barlibrary.ImmersionBar
import com.mvc.cryptovault_android.R
import com.mvc.cryptovault_android.api.ApiStore
import com.mvc.cryptovault_android.base.BaseMVPActivity
import com.mvc.cryptovault_android.base.BasePresenter
import com.mvc.cryptovault_android.common.Constant.SP.USER_EMAIL
import com.mvc.cryptovault_android.contract.VerificationInfoContract
import com.mvc.cryptovault_android.event.PayPwdRefreshEvent
import com.mvc.cryptovault_android.listener.OnTimeEndCallBack
import com.mvc.cryptovault_android.presenter.VerificationInfoPresenter
import com.mvc.cryptovault_android.utils.RetrofitUtils
import com.mvc.cryptovault_android.utils.RxHelper
import com.mvc.cryptovault_android.utils.TimeVerification
import com.mvc.cryptovault_android.view.DialogHelper
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_verification_password.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import java.util.ArrayList

/**
 * 验证信息的activity
 * 验证私钥，助记词，邮箱
 * 0邮箱 1私钥 2助记词
 */
class VerificationInfoActivity : BaseMVPActivity<VerificationInfoContract.VerificationInfoPresenter>(), View.OnClickListener, VerificationInfoContract.VerificationInfoView {


    private val TYPE_EMAIL = 0
    private val TYPE_PRIVATEKEY = 1
    private val TYPE_MNEMONICS = 2
    private var resetType: Int = 0
    private lateinit var hintMsg: String
    private lateinit var list: ArrayList<String>
    private var dialogHelper: DialogHelper? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        EventBus.getDefault().register(this)
    }

    override fun onDestroy() {
        EventBus.getDefault().unregister(this)
        super.onDestroy()
    }

    override fun initPresenter(): BasePresenter<*, *> {
        return VerificationInfoPresenter.newIntance()
    }

    override fun initMVPData() {

    }

    override fun initMVPView() {

    }

    override fun startActivity() {

    }

    override fun showError(error: String) {
        dialogHelper?.resetDialogResource(this, R.drawable.miss_icon, error)
        dialogHelper?.dismissDelayed { null }
    }

    override fun showSuccess(token: String) {
        dialogHelper?.dismissDelayed({ null }, 0)
        var tokenIntent = intent
        tokenIntent.putExtra("token", token)
        startActivity(ResetPasswordActivity::class.java, tokenIntent)
    }

    @Subscribe
    public fun updatePaySuccess(pay: PayPwdRefreshEvent) {
        finish()
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.verification_back -> {
                finish()
            }
            R.id.send_code -> {
                if (account.text.toString() == "") {
                    dialogHelper?.create(this, R.drawable.miss_icon, "邮箱不可为空")?.show()
                    dialogHelper?.dismissDelayed(null)
                    return
                }
                dialogHelper?.create(this, R.drawable.pending_icon_1, "发送验证码")?.show()
                RetrofitUtils.client(ApiStore::class.java).sendValiCode(account.text.toString())
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
                    dialogHelper?.create(this, R.drawable.pending_icon_1, "验证中")?.show()
                    when (resetType) {
                        TYPE_MNEMONICS -> {
                            //获取助记词
                            list.clear()
                            RetrofitUtils.client(ApiStore::class.java).getUserMnemonic(account.text.toString())
                                    .compose(RxHelper.rxSchedulerHelper())
                                    .subscribeOn(Schedulers.io())
                                    .observeOn(AndroidSchedulers.mainThread())
                                    .subscribe({ upset ->
                                        //传递邮箱过去 获取助记词
                                        if (upset.code === 200) {
                                            list.addAll(upset.data)
                                            dialogHelper?.dismissDelayed({ null }, 0)
                                            var tokenIntent = intent
                                            SPUtils.getInstance().put(USER_EMAIL, account.text.toString())
                                            tokenIntent.putExtra("email", account.text.toString())
                                            tokenIntent.putStringArrayListExtra("menmonicss", list)
                                            startActivity(ResetPasswordVerificationMnemonicsActivity::class.java, tokenIntent)
                                        } else {
                                            dialogHelper?.resetDialogResource(this, R.drawable.miss_icon, upset.message)
                                            dialogHelper?.dismissDelayed { null }
                                        }
                                    }, { throwavle ->
                                        dialogHelper?.resetDialogResource(this, R.drawable.miss_icon, throwavle.message)
                                        dialogHelper?.dismissDelayed { null }
                                    })
                        }
                        TYPE_PRIVATEKEY -> {
                            dialogHelper?.dismiss()
                            SPUtils.getInstance().put(USER_EMAIL, account.text.toString())
                            startActivity(PrivateKeyVerificationActivity::class.java)
                        }
                        else -> {
                            SPUtils.getInstance().put(USER_EMAIL, account.text.toString())
                            mPresenter.verification(account.text.toString(), TYPE_EMAIL, code.text.toString())
                        }
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
        list = ArrayList()
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
            TYPE_PRIVATEKEY, TYPE_MNEMONICS -> {
                verification_title.text = "输入邮箱账户"
                account_hint.hint = "邮箱"
                hintMsg = "邮箱账户"
                email_layout.visibility = View.GONE
            }
        }
    }
}