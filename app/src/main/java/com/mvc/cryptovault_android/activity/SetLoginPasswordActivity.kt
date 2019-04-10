package com.mvc.cryptovault_android.activity

import android.text.InputFilter
import android.text.InputType
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.view.View
import com.blankj.utilcode.util.EncryptUtils
import com.blankj.utilcode.util.SPUtils
import com.gyf.barlibrary.ImmersionBar
import com.mvc.cryptovault_android.R
import com.mvc.cryptovault_android.base.BaseMVPActivity
import com.mvc.cryptovault_android.base.BasePresenter
import com.mvc.cryptovault_android.common.Constant.SP.UPDATE_PASSWORD_TYPE
import com.mvc.cryptovault_android.common.Constant.SP.USER_EMAIL
import com.mvc.cryptovault_android.contract.ISetPasswordContract
import com.mvc.cryptovault_android.listener.EditTextChange
import com.mvc.cryptovault_android.presenter.SetLoginPresenter
import com.mvc.cryptovault_android.view.DialogHelper
import kotlinx.android.synthetic.main.activity_set_password.*
import java.util.regex.Pattern

class SetLoginPasswordActivity : BaseMVPActivity<ISetPasswordContract.SetPasswordPresenter>(), ISetPasswordContract.SetPasswordView {
    private var dialogHelper: DialogHelper? = null


    override fun showError(error: String) {
        dialogHelper!!.resetDialogResource(this, R.drawable.miss_icon, error)
        dialogHelper!!.dismissDelayed(null)
    }

    override fun showSuccess(msg: String) {
        dialogHelper!!.resetDialogResource(this, R.drawable.success_icon, msg)
        dialogHelper!!.dismissDelayed(object : DialogHelper.IDialogDialog {
            override fun callback() {
                startTaskActivity(this@SetLoginPasswordActivity)
            }
        })
    }

    override fun initPresenter(): BasePresenter<*, *> {
        return SetLoginPresenter.newInstance()
    }

    override fun initMVPData() {
    }

    override fun initMVPView() {
        ImmersionBar.with(this).statusBarView(R.id.status_bar).statusBarDarkFont(true).init()
        type = intent.getIntExtra("type", 0)
        dialogHelper = DialogHelper.instance
        when (type) {
            LOGIN_PASSWORD -> {
                toolbar_title.text = "修改登录密码"
                forget_pwd.text = "忘记登录密码？"
                old_pwd.inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
                new_pwd.inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
            }
            PAY_PASSWORD -> {
                toolbar_title.text = "修改支付密码"
                forget_pwd.text = "忘记支付密码？"
                old_pwd.inputType = InputType.TYPE_CLASS_NUMBER or InputType.TYPE_NUMBER_VARIATION_PASSWORD
                new_pwd.inputType = InputType.TYPE_CLASS_NUMBER or InputType.TYPE_NUMBER_VARIATION_PASSWORD
                old_pwd.filters = arrayOf<InputFilter>(InputFilter.LengthFilter(6))
                new_pwd.filters = arrayOf<InputFilter>(InputFilter.LengthFilter(6))
            }
        }
        //设置眼睛可见
        old_pwd.addTextChangedListener(object : EditTextChange() {
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                val length = s.length
                old_pwd_show.visibility = if (length > 0) View.VISIBLE else View.INVISIBLE
            }
        })
        new_pwd.addTextChangedListener(object : EditTextChange() {
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                val length = s.length
                new_pwd_show.visibility = if (length > 0) View.VISIBLE else View.INVISIBLE
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
            R.id.old_pwd_show -> {
                if (old_pwd.transformationMethod == HideReturnsTransformationMethod.getInstance()) {
                    old_pwd.transformationMethod = PasswordTransformationMethod.getInstance()
                    old_pwd_show.setImageResource(R.drawable.edit_hide)
                    old_pwd.setSelection(old_pwd.text.length)
                } else {
                    old_pwd.transformationMethod = HideReturnsTransformationMethod.getInstance()
                    old_pwd_show.setImageResource(R.drawable.edit_show)
                    old_pwd.setSelection(old_pwd.text.length)
                }
            }
            R.id.new_pwd_show -> {
                if (new_pwd.transformationMethod == HideReturnsTransformationMethod.getInstance()) {
                    new_pwd.transformationMethod = PasswordTransformationMethod.getInstance()
                    new_pwd_show.setImageResource(R.drawable.edit_hide)
                    new_pwd.setSelection(new_pwd.text.length)
                } else {
                    new_pwd.transformationMethod = HideReturnsTransformationMethod.getInstance()
                    new_pwd_show.setImageResource(R.drawable.edit_show)
                    new_pwd.setSelection(new_pwd.text.length)
                }
            }
        }
    }

    private fun checkNotNullValue(): Boolean {
        if (type == 0) {
            if (old_pwd.text.toString() == "") {
                dialogHelper!!.create(this, R.drawable.miss_icon, "旧密码不可为空").show()
                dialogHelper!!.dismissDelayed(null)
                return false
            }
            if (new_pwd.text.toString() == "") {
                dialogHelper!!.create(this, R.drawable.miss_icon, "新密码不可为空").show()
                dialogHelper!!.dismissDelayed(null)
                return false
            }
            if (new_pwd.text.toString().length < 8) {
                dialogHelper!!.create(this, R.drawable.miss_icon, "登录密码必须在8位以上").show()
                dialogHelper!!.dismissDelayed(null)
                return false
            }
            if (Pattern.compile("[0-9]*").matcher(new_pwd.text.toString()).matches()) {
                dialogHelper!!.create(this, R.drawable.miss_icon, "密码不可为纯数字").show()
                dialogHelper!!.dismissDelayed(null)
                return false
            }
            return true
        } else {
            if (old_pwd.text.toString() == "") {
                dialogHelper!!.create(this, R.drawable.miss_icon, "旧密码不可为空").show()
                dialogHelper!!.dismissDelayed(null)
                return false
            }
            if (new_pwd.text.toString() == "") {
                dialogHelper!!.create(this, R.drawable.miss_icon, "新密码不可为空").show()
                dialogHelper!!.dismissDelayed(null)
                return false
            }
            if (new_pwd.text.toString().length < 6) {
                dialogHelper!!.create(this, R.drawable.miss_icon, "支付密码必须6位").show()
                dialogHelper!!.dismissDelayed(null)
                return false
            }
            return true
        }
    }

    override fun initView() {
    }
}