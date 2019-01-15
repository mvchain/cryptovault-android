package com.mvc.cryptovault_android.activity

import android.view.View
import com.blankj.utilcode.util.SPUtils
import com.gyf.barlibrary.ImmersionBar
import com.mvc.cryptovault_android.R

import com.mvc.cryptovault_android.base.BaseMVPActivity
import com.mvc.cryptovault_android.base.BasePresenter
import com.mvc.cryptovault_android.common.Constant.SP.TOKEN
import com.mvc.cryptovault_android.contract.ResetPasswordContract
import com.mvc.cryptovault_android.presenter.ResetPresenter
import com.mvc.cryptovault_android.view.DialogHelper
import kotlinx.android.synthetic.main.activity_reset_password.*

class ResetPasswordActivity : BaseMVPActivity<ResetPasswordContract.ResetPasswordPresenter>(), View.OnClickListener, ResetPasswordContract.ResetView {
    private var dialogHelper: DialogHelper? = null
    private lateinit var account: String
    private var type: Int = 0
    private lateinit var value: String
    override fun initPresenter(): BasePresenter<*, *> {
        return ResetPresenter.newIntance()
    }

    override fun initMVPData() {
    }

    override fun initView() {
    }

    override fun initMVPView() {
        ImmersionBar.with(this).titleBar(R.id.status_bar).statusBarDarkFont(true).init()
        dialogHelper = DialogHelper.getInstance()
        account = intent.getStringExtra("account")
        type = intent.getIntExtra("type", -1)
        value = intent.getStringExtra("value")
    }

    override fun getLayoutId(): Int {
        return R.layout.activity_reset_password
    }

    override fun initData() {
    }

    override fun startActivity() {

    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.back -> {
                finish()
            }
            R.id.submit -> {
                if (login_pwd.text.toString() == "") {
                    dialogHelper?.create(this, R.drawable.miss_icon, "登陆密码不可为空")?.show()
                    dialogHelper?.dismissDelayed(null)
                    return
                }
                dialogHelper?.create(this, R.drawable.pending_icon_1, "重置中")?.show()
                mPresenter.verification(account, type, value)
            }
        }
    }

    override fun showError(error: String) {
        dialogHelper?.resetDialogResource(this, R.drawable.miss_icon, error)
        dialogHelper?.dismissDelayed { null }
    }

    override fun getRequestBody(token: String) {
        dialogHelper?.resetDialogResource(this, R.drawable.success_icon, "重置成功")
        dialogHelper?.dismissDelayed {
            SPUtils.getInstance().put(TOKEN, token)
        }
    }
}
