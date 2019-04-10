package com.mvc.cryptovault_android.activity

import android.annotation.SuppressLint
import android.content.Intent
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.view.View
import com.blankj.utilcode.util.SPUtils
import com.gyf.barlibrary.ImmersionBar
import com.mvc.cryptovault_android.MainActivity
import com.mvc.cryptovault_android.R
import com.mvc.cryptovault_android.base.BaseMVPActivity
import com.mvc.cryptovault_android.base.BasePresenter
import com.mvc.cryptovault_android.bean.LoginBean
import com.mvc.cryptovault_android.common.Constant.SP.*
import com.mvc.cryptovault_android.contract.IGoogleContract
import com.mvc.cryptovault_android.listener.IDialogViewClickListener
import com.mvc.cryptovault_android.presenter.GooglePresenter
import com.mvc.cryptovault_android.view.DialogHelper
import kotlinx.android.synthetic.main.activity_google_verification.*

class GoogleVerificationActivity : BaseMVPActivity<IGoogleContract.GooglePresenter>(), IGoogleContract.GoogleView {
    private var googleStatus = SPUtils.getInstance().getInt(USER_GOOGLE, 0)
    private var googleSecret = ""
    private lateinit var dialogHelper: DialogHelper

    override fun getLayoutId(): Int {
        return R.layout.activity_google_verification
    }

    override fun initData() {

    }

    @SuppressLint("SetTextI18n")
    override fun initView() {
        googleSecret = intent.getStringExtra("googleSecret")
        google_title.text = "${if (googleStatus == 0) "开启" else "关闭"}Google安全验证"
        google_submit.text = "${if (googleStatus == 0) "开启" else "关闭"}Google验证"
    }

    fun onClick(v: View) {
        when (v.id) {
            R.id.pay_pwd_show -> {
                if (google_pwd.transformationMethod == HideReturnsTransformationMethod.getInstance()) {
                    google_pwd.transformationMethod = PasswordTransformationMethod.getInstance()
                    pay_pwd_show.setBackgroundResource(R.drawable.edit_hide)
                } else {
                    google_pwd.transformationMethod = HideReturnsTransformationMethod.getInstance()
                    pay_pwd_show.setBackgroundResource(R.drawable.edit_show)
                }
            }

            R.id.google_back -> {
                finish()
            }

            R.id.google_submit -> {
                dialogHelper.create(this, R.drawable.pending_icon, "验证中...").show()
                mPresenter.changeGoogleVerification(google_code.text.toString(),googleSecret, google_pwd.text.toString(), if (googleStatus == 0) 1 else 0)
            }
        }
    }

    override fun initPresenter(): BasePresenter<*, *> {
        return GooglePresenter.newInstance()
    }

    override fun initMVPData() {

    }

    override fun initMVPView() {
        ImmersionBar.with(this).titleBar(R.id.status_bar).statusBarDarkFont(true).init()
        dialogHelper = DialogHelper.instance
    }

    override fun startActivity() {

    }

    override fun changeSuccess(loginBean: LoginBean) {
        dialogHelper.dismiss()
        SPUtils.getInstance().put(REFRESH_TOKEN, loginBean.data.refreshToken)
        SPUtils.getInstance().put(TOKEN, loginBean.data.token)
        SPUtils.getInstance().put(USER_ID, loginBean.data.userId)
        SPUtils.getInstance().put(USER_EMAIL, loginBean.data.email)
        SPUtils.getInstance().put(USER_PUBLIC_KEY, loginBean.data.publicKey)
        SPUtils.getInstance().put(USER_SALT, loginBean.data.salt)
        SPUtils.getInstance().put(USER_GOOGLE, loginBean.data.googleCheck)
        dialogHelper.create(this, IDialogViewClickListener { viewId ->
            dialogHelper.dismiss()
            val intent = Intent(this@GoogleVerificationActivity, MainActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
        }, "已成功${if (googleStatus == 0) "开启" else "关闭"}Google验证登录").show()
    }

    override fun changeFailed(msg: String) {
        dialogHelper.resetDialogResource(this, R.drawable.miss_icon, msg)
        dialogHelper.dismissDelayed(null)
    }
}