package com.mvc.cryptovault_android.activity

import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.view.View
import com.blankj.utilcode.util.LogUtils
import com.blankj.utilcode.util.SPUtils
import com.gyf.barlibrary.ImmersionBar
import com.mvc.cryptovault_android.R
import com.mvc.cryptovault_android.api.ApiStore
import com.mvc.cryptovault_android.base.BaseActivity
import com.mvc.cryptovault_android.bean.MnemonicsBean
import com.mvc.cryptovault_android.common.Constant.SP.*
import com.mvc.cryptovault_android.listener.EditTextChange
import com.mvc.cryptovault_android.utils.RetrofitUtils
import com.mvc.cryptovault_android.utils.RxHelper
import com.mvc.cryptovault_android.view.DialogHelper
import kotlinx.android.synthetic.main.activity_reg_setpassword.*
import okhttp3.MediaType
import okhttp3.RequestBody
import org.json.JSONObject

class RegisterSetPwdActivity : BaseActivity(), View.OnClickListener {
    private var dialogHelper: DialogHelper? = null
    private var userInfo = SPUtils.getInstance().getString(REG_INVITATION)


    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.reg_back -> {
                finish()
            }
            R.id.reg_submit -> {
                if (checkNotNullValue()) {
                    var userJson = JSONObject(userInfo)
                    userJson.put("password", reg_login_pwd.text.toString())
                    userJson.put("transactionPassword", reg_pay_pwd.text.toString())
                    dialogHelper?.create(this, R.drawable.pending_icon_1, "请稍后")?.show()
                    val body = RequestBody.create(MediaType.parse("text/html"), userJson.toString())
                    RetrofitUtils.client(ApiStore::class.java).userRegister(body).compose(RxHelper.rxSchedulerHelper())
                            .subscribe({ mnemon ->
                                if (mnemon.code == 200) {
                                    dialogHelper?.dismissDelayed(null, 0)
                                    var sb = StringBuffer()
                                    var datas = mnemon.data.mnemonics

                                    for (data in datas) {
                                        LogUtils.e("$data,")
                                        sb.append("$data,")
                                    }
                                    SPUtils.getInstance().put(REG_MINEMNEMONICS, sb.toString())
                                    SPUtils.getInstance().put(REG_PRIVATEKEY, mnemon.data.privateKey)
                                    SPUtils.getInstance().put(REG_REGISTER, true)
                                    startActivity(MineMnemonicsActivity::class.java)
                                } else {
                                    dialogHelper?.resetDialogResource(baseContext, R.drawable.miss_icon, mnemon.message)
                                    dialogHelper?.dismissDelayed { null }
                                }
                            }, { t ->
                                LogUtils.e(t.message)
                                dialogHelper?.resetDialogResource(baseContext, R.drawable.miss_icon, t.message)
                                dialogHelper?.dismissDelayed { null }
                            })
                }
            }
            R.id.login_pwd_show -> {
                if (reg_login_pwd.transformationMethod === PasswordTransformationMethod.getInstance()) {
                    reg_login_pwd.transformationMethod = HideReturnsTransformationMethod.getInstance()
                    login_pwd_show.setImageResource(R.drawable.edit_show)
                } else {
                    reg_login_pwd.transformationMethod = PasswordTransformationMethod.getInstance()
                    login_pwd_show.setImageResource(R.drawable.edit_hide)
                }
                reg_login_pwd.setSelection(reg_login_pwd.text.length)
            }
            R.id.pay_pwd_show -> {
                if (reg_pay_pwd.transformationMethod === PasswordTransformationMethod.getInstance()) {
                    reg_pay_pwd.transformationMethod = HideReturnsTransformationMethod.getInstance()
                    pay_pwd_show.setImageResource(R.drawable.edit_show)
                } else {
                    reg_pay_pwd.transformationMethod = PasswordTransformationMethod.getInstance()
                    pay_pwd_show.setImageResource(R.drawable.edit_hide)
                }
                reg_pay_pwd.setSelection(reg_pay_pwd.text.length)
            }
        }
    }

    /**
     * 检查元素是否为空
     */
    private fun checkNotNullValue(): Boolean {
        if (reg_login_pwd.text.toString() == "") {
            dialogHelper?.create(this, R.drawable.miss_icon, "登陆密码不可为空")?.show()
            dialogHelper?.dismissDelayed(null)
            return false
        }
        if (reg_pay_pwd.text.toString() == "") {
            dialogHelper?.create(this, R.drawable.miss_icon, "支付密码不可为空")?.show()
            dialogHelper?.dismissDelayed(null)
            return false
        }
        return true
    }

    override fun getLayoutId(): Int {
        return R.layout.activity_reg_setpassword
    }

    override fun initData() {

    }

    override fun initView() {
        ImmersionBar.with(this).titleBar(R.id.status_bar).statusBarDarkFont(true).init()
        dialogHelper = DialogHelper.getInstance()
        reg_layout.viewTreeObserver.addOnDrawListener {
            if (reg_login_pwd.text.toString() != ""
                    && reg_pay_pwd.text.toString() != "") {
                reg_submit.setBackgroundResource(R.drawable.bg_login_submit)
                reg_submit.isEnabled = true
            } else {
                reg_submit.setBackgroundResource(R.drawable.bg_toge_child_item_tv_blue_nocheck)
                reg_submit.isEnabled = false
            }
        }

        reg_login_pwd.addTextChangedListener(object : EditTextChange() {
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                var lenght = s!!.length
                if (lenght > 0) {
                    login_pwd_show.visibility = View.VISIBLE
                } else {
                    login_pwd_show.visibility = View.INVISIBLE
                }
            }
        })
        reg_pay_pwd.addTextChangedListener(object : EditTextChange() {
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                var lenght = s!!.length
                if (lenght > 0) {
                    pay_pwd_show.visibility = View.VISIBLE
                } else {
                    pay_pwd_show.visibility = View.INVISIBLE
                }
            }
        })
    }
}