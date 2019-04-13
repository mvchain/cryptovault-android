package com.mvc.ttpay_android.activity

import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.view.View
import com.blankj.utilcode.util.EncryptUtils
import com.blankj.utilcode.util.LogUtils
import com.blankj.utilcode.util.SPUtils
import com.gyf.barlibrary.ImmersionBar
import com.mvc.ttpay_android.MyApplication
import com.mvc.ttpay_android.R
import com.mvc.ttpay_android.api.ApiStore
import com.mvc.ttpay_android.base.BaseActivity
import com.mvc.ttpay_android.common.Constant.SP.*
import com.mvc.ttpay_android.listener.EditTextChange
import com.mvc.ttpay_android.utils.RetrofitUtils
import com.mvc.ttpay_android.utils.RxHelper
import com.mvc.ttpay_android.view.DialogHelper
import kotlinx.android.synthetic.main.activity_reg_setpassword.*
import okhttp3.MediaType
import okhttp3.RequestBody
import org.json.JSONObject
import java.util.*
import java.util.regex.Pattern

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
                    var salt = UUID.randomUUID().toString().replace("-", "").toUpperCase()
                    userJson.put("salt", salt)
                    userJson.put("password", EncryptUtils.encryptMD5ToString(salt + EncryptUtils.encryptMD5ToString(reg_login_pwd.text.toString())))
                    userJson.put("transactionPassword", EncryptUtils.encryptMD5ToString(salt + EncryptUtils.encryptMD5ToString(reg_pay_pwd.text.toString())))
                    dialogHelper?.create(this, R.drawable.pending_icon_1, "请稍后")?.show()
                    val body = RequestBody.create(MediaType.parse("text/html"), userJson.toString())
                    RetrofitUtils.client(MyApplication.getBaseUrl(), ApiStore::class.java).userRegister(body).compose(RxHelper.rxSchedulerHelper())
                            .subscribe({ loginBean ->
                                if (loginBean.code == 200) {
                                    dialogHelper?.resetDialogResource(baseContext, R.drawable.success_icon, "注册成功")
                                    dialogHelper?.dismissDelayed(null, 0)
                                    SPUtils.getInstance().put(REFRESH_TOKEN, loginBean.data.refreshToken)
                                    SPUtils.getInstance().put(TOKEN, loginBean.data.token)
                                    SPUtils.getInstance().put(USER_ID, loginBean.data.userId)
                                    SPUtils.getInstance().put(USER_EMAIL, loginBean.data.email)
                                    SPUtils.getInstance().put(USER_PUBLIC_KEY, loginBean.data.publicKey)
                                    SPUtils.getInstance().put(USER_SALT, loginBean.data.salt)
                                    SPUtils.getInstance().put(USER_GOOGLE,loginBean.data.googleCheck)
                                    startActivity(TurnGoogleActivity::class.java)
                                } else {
                                    dialogHelper?.resetDialogResource(baseContext, R.drawable.miss_icon, loginBean.message)
                                    dialogHelper?.dismissDelayed(null)
                                }
                            }, { t ->
                                LogUtils.e(t.message)
                                dialogHelper?.resetDialogResource(baseContext, R.drawable.miss_icon, t.message!!)
                                dialogHelper?.dismissDelayed(null)
                            })
                }
            }
            R.id.pwd_show -> {
                if (reg_login_pwd.transformationMethod == HideReturnsTransformationMethod.getInstance()) {
                    reg_login_pwd.transformationMethod = PasswordTransformationMethod.getInstance()
                    pwd_show.setImageResource(R.drawable.edit_hide)
                    reg_login_pwd.setSelection(reg_login_pwd.text.length)

                } else {
                    reg_login_pwd.transformationMethod = HideReturnsTransformationMethod.getInstance()
                    pwd_show.setImageResource(R.drawable.edit_show)
                    reg_login_pwd.setSelection(reg_login_pwd.text.length)
                }
            }

            R.id.pay_pwd_show -> {
                if (reg_pay_pwd.transformationMethod == HideReturnsTransformationMethod.getInstance()) {
                    reg_pay_pwd.transformationMethod = PasswordTransformationMethod.getInstance()
                    pay_pwd_show.setImageResource(R.drawable.edit_hide)
                    reg_pay_pwd.setSelection(reg_pay_pwd.text.length)
                } else {
                    reg_pay_pwd.transformationMethod = HideReturnsTransformationMethod.getInstance()
                    pay_pwd_show.setImageResource(R.drawable.edit_show)
                    reg_pay_pwd.setSelection(reg_pay_pwd.text.length)
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
        if (reg_login_pwd.text.toString().length < 8) {
            dialogHelper?.create(this, R.drawable.miss_icon, "登录密码必须在8位以上")?.show()
            dialogHelper?.dismissDelayed(null)
            return false
        }
        if (Pattern.compile("[0-9]*").matcher(reg_login_pwd.text.toString()).matches()) {
            dialogHelper?.create(this, R.drawable.miss_icon, "登录密码不可为纯数字")?.show()
            dialogHelper?.dismissDelayed(null)
            return false
        }
        if (reg_pay_pwd.text.toString() == "") {
            dialogHelper?.create(this, R.drawable.miss_icon, "支付密码不可为空")?.show()
            dialogHelper?.dismissDelayed(null)
            return false
        }
        if (reg_pay_pwd.text.toString().length < 6) {
            dialogHelper?.create(this, R.drawable.miss_icon, "支付密码必须为6位")?.show()
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
        dialogHelper = DialogHelper.instance
        reg_login_pwd.addTextChangedListener(object : EditTextChange() {
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                var lenght = s!!.length
                pwd_show.visibility = if (lenght > 0) View.VISIBLE else View.INVISIBLE
            }
        })
        reg_pay_pwd.addTextChangedListener(object : EditTextChange() {
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                var lenght = s!!.length
                pay_pwd_show.visibility = if (lenght > 0) View.VISIBLE else View.INVISIBLE
            }
        })
    }
}