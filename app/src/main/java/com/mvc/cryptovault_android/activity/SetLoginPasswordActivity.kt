package com.mvc.cryptovault_android.activity

import android.view.View
import com.blankj.utilcode.util.SPUtils
import com.gyf.barlibrary.ImmersionBar
import com.mvc.cryptovault_android.R
import com.mvc.cryptovault_android.base.BaseMVPActivity
import com.mvc.cryptovault_android.base.BasePresenter
import com.mvc.cryptovault_android.common.Constant.SP.UPDATE_PASSWORD_TYPE
import com.mvc.cryptovault_android.contract.SetPasswordContract
import com.mvc.cryptovault_android.listener.EditTextChange
import com.mvc.cryptovault_android.presenter.SetLoginPresenter
import com.mvc.cryptovault_android.view.DialogHelper
import kotlinx.android.synthetic.main.activity_set_password.*

class SetLoginPasswordActivity : BaseMVPActivity<SetPasswordContract.SetPasswordPresenter>(), SetPasswordContract.SetPasswordView {
    private var dialogHelper: DialogHelper? = null


    override fun showError(error: String) {
        dialogHelper!!.resetDialogResource(this, R.drawable.miss_icon, error)
        dialogHelper!!.dismissDelayed { null }
    }

    override fun showSuccess(msg: String) {
        dialogHelper!!.resetDialogResource(this, R.drawable.success_icon, msg)
        dialogHelper!!.dismissDelayed {
            startTaskActivity(this)
        }
    }

    override fun initPresenter(): BasePresenter<*, *> {
        return SetLoginPresenter.newIntance()
    }

    override fun initMVPData() {
    }

    override fun initMVPView() {
        ImmersionBar.with(this).statusBarView(R.id.status_bar).statusBarDarkFont(true).init()
        type = intent.getIntExtra("type", 0)
        dialogHelper = DialogHelper.getInstance()
        when (type) {
            LOGIN_PASSWORD -> {
                toolbar_title.text = "修改登录密码"
                old_layout.hint = "输入当前登录密码"
                new_layout.hint = "输入新登录密码"
                forget_pwd.text = "忘记登录密码？"
            }
            PAY_PASSWORD -> {
                toolbar_title.text = "修改支付密码"
                old_layout.hint = "输入当前支付密码"
                new_layout.hint = "输入新支付密码"
                forget_pwd.text = "忘记支付密码？"
            }
        }
        //设置眼睛可见
        old_pwd.addTextChangedListener(object : EditTextChange() {
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                val length = s.length
                old_layout.isPasswordVisibilityToggleEnabled = length > 0
            }
        })
        new_pwd.addTextChangedListener(object : EditTextChange() {
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                val length = s.length
                new_layout.isPasswordVisibilityToggleEnabled = length > 0
            }
        })
    }

    override fun startActivity() {
    }

    private var type: Int = 0
    private val LOGIN_PASSWORD: Int = 0
    private val PAY_PASSWORD: Int = 1
    override fun getLayoutId(): Int {
        return R.layout.activity_set_password
    }

    override fun initData() {

    }

    fun onClick(v: View) {
        when (v.id) {
            R.id.back -> {
                finish()
            }
            R.id.submit -> {
                if (checkNotNullValue()) {
                    dialogHelper!!.create(this, R.drawable.pending_icon_1, "修改中").show()
                    if (type == 0) {
                        mPresenter.setLoginPassword(old_pwd.text.toString(), new_pwd.text.toString())
                    } else if (type == 1) {
                        mPresenter.setPayPassword(old_pwd.text.toString(), new_pwd.text.toString())
                    }
                }
            }
            R.id.forget_pwd -> {
                if (type == 0) {
                    SPUtils.getInstance().put(UPDATE_PASSWORD_TYPE, "1")
                    startActivity(ForgetPasswordActivity::class.java)
                } else if (type == 1) {
                    SPUtils.getInstance().put(UPDATE_PASSWORD_TYPE, "2")
                    startActivity(ForgetPasswordActivity::class.java)
                }
            }
        }
    }

    private fun checkNotNullValue(): Boolean {
        if (old_pwd.text.toString() == "") {
            dialogHelper!!.create(this, R.drawable.miss_icon, "旧密码不可为空").show()
            dialogHelper!!.dismissDelayed { null }
            return false
        }
        if (new_pwd.text.toString() == "") {
            dialogHelper!!.create(this, R.drawable.miss_icon, "新密码不可为空").show()
            dialogHelper!!.dismissDelayed { null }
            return false
        }
        return true
    }

    override fun initView() {
    }
}