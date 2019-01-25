package com.mvc.cryptovault_android.activity

import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.view.View
import com.blankj.utilcode.util.EncryptUtils
import com.blankj.utilcode.util.LogUtils
import com.blankj.utilcode.util.SPUtils
import com.gyf.barlibrary.ImmersionBar
import com.mvc.cryptovault_android.R
import com.mvc.cryptovault_android.api.ApiStore
import com.mvc.cryptovault_android.base.BaseActivity
import com.mvc.cryptovault_android.common.Constant.SP.*
import com.mvc.cryptovault_android.listener.EditTextChange
import com.mvc.cryptovault_android.utils.RetrofitUtils
import com.mvc.cryptovault_android.utils.RxHelper
import com.mvc.cryptovault_android.view.DialogHelper
import kotlinx.android.synthetic.main.activity_reg_setpassword.*
import kotlinx.android.synthetic.main.activity_register.*
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
                    var email = SPUtils.getInstance().getString(REG_EMAIL)
                    LogUtils.e(reg_login_pwd.text.toString())
                    LogUtils.e(email)
                    userJson.put("password", EncryptUtils.encryptMD5ToString(email + EncryptUtils.encryptMD5ToString(reg_login_pwd.text.toString())))
                    userJson.put("transactionPassword", EncryptUtils.encryptMD5ToString(email + EncryptUtils.encryptMD5ToString(reg_pay_pwd.text.toString())))
                    dialogHelper?.create(this, R.drawable.pending_icon_1, "请稍后")?.show()
                    val body = RequestBody.create(MediaType.parse("text/html"), userJson.toString())
                    RetrofitUtils.client(ApiStore::class.java).userRegister(body).compose(RxHelper.rxSchedulerHelper())
                            .subscribe({ mnemon ->
                                if (mnemon.code == 200) {
                                    dialogHelper?.dismissDelayed(null, 0)
                                    var sb = StringBuffer()
                                    var datas = mnemon.data.mnemonics
                                    for (data in datas) {
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
        }
    }

    /**
     * 检查元素是否为空
     */
    private fun checkNotNullValue(): Boolean {
        if (reg_login_pwd.text.toString() == "") {
            dialogHelper?.create(this, R.drawable.miss_icon, "登录密码不可为空")?.show()
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
        reg_login_pwd.addTextChangedListener(object : EditTextChange() {
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                var lenght = s!!.length
                login_pwd_layout.isPasswordVisibilityToggleEnabled = lenght > 0
            }
        })
        reg_pay_pwd.addTextChangedListener(object : EditTextChange() {
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                var lenght = s!!.length
                pay_pwd_layout.isPasswordVisibilityToggleEnabled = lenght > 0
            }
        })
    }
}