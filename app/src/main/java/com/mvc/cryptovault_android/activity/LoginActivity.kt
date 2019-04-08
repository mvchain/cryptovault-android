package com.mvc.cryptovault_android.activity

import android.annotation.SuppressLint
import android.content.Intent
import android.support.v4.content.ContextCompat
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.view.View

import com.blankj.utilcode.util.EncryptUtils
import com.blankj.utilcode.util.LogUtils
import com.blankj.utilcode.util.SPUtils
import com.geetest.sdk.GT3ConfigBean
import com.geetest.sdk.GT3ErrorBean
import com.geetest.sdk.GT3GeetestUtils
import com.geetest.sdk.GT3Listener
import com.gyf.barlibrary.ImmersionBar
import com.mvc.cryptovault_android.MainActivity
import com.mvc.cryptovault_android.MyApplication
import com.mvc.cryptovault_android.R
import com.mvc.cryptovault_android.api.ApiStore
import com.mvc.cryptovault_android.base.BaseMVPActivity
import com.mvc.cryptovault_android.base.BasePresenter
import com.mvc.cryptovault_android.bean.LoginBean
import com.mvc.cryptovault_android.bean.LoginValidBean
import com.mvc.cryptovault_android.bean.ValidResultBean
import com.mvc.cryptovault_android.common.Constant
import com.mvc.cryptovault_android.contract.ILoginContract
import com.mvc.cryptovault_android.listener.EditTextChange
import com.mvc.cryptovault_android.listener.OnTimeEndCallBack
import com.mvc.cryptovault_android.presenter.LoginPresenter
import com.mvc.cryptovault_android.utils.JsonHelper
import com.mvc.cryptovault_android.utils.RetrofitUtils
import com.mvc.cryptovault_android.utils.RxHelper
import com.mvc.cryptovault_android.utils.TimeVerification
import com.mvc.cryptovault_android.view.DialogHelper

import org.json.JSONException
import org.json.JSONObject

import java.util.ArrayList
import java.util.Arrays
import java.util.HashSet

import cn.jpush.android.api.JPushInterface

import com.mvc.cryptovault_android.common.Constant.SP.*
import kotlinx.android.synthetic.main.activity_login.*


class LoginActivity : BaseMVPActivity<ILoginContract.LoginPresenter>(), View.OnClickListener, ILoginContract.ILoginView {
    private lateinit var dialogHelper: DialogHelper
    private lateinit var gt3GeetestUtils: GT3GeetestUtils
    private lateinit var gt3ConfigBean: GT3ConfigBean
    private var status: Int = 0
    private var uid: String? = null
    private var mToken = ""
    private var isValid = false

    override fun getLayoutId(): Int {
        return R.layout.activity_login
    }

    override fun initData() {

    }

    override fun initView() {
        // 务必在oncreate方法里处理
        gt3GeetestUtils = GT3GeetestUtils(this)
        // 配置bean文件，也可在oncreate初始化
        gt3ConfigBean = GT3ConfigBean()
        // 设置验证模式，1：bind，2：unbind
        gt3ConfigBean!!.pattern = 1
        // 设置点击灰色区域是否消失，默认不消息
        gt3ConfigBean!!.isCanceledOnTouchOutside = false
        // 设置debug模式，开代理可调试
        gt3ConfigBean!!.isDebug = false
        // 设置语言，如果为null则使用系统默认语言
        gt3ConfigBean!!.lang = SPUtils.getInstance().getString(Constant.LANGUAGE.DEFAULT_LANGUAGE)
        // 设置加载webview超时时间，单位毫秒，默认10000，仅且webview加载静态文件超时，不包括之前的http请求
        gt3ConfigBean!!.timeout = 10000
        // 设置webview请求超时(用户点选或滑动完成，前端请求后端接口)，单位毫秒，默认10000
        gt3ConfigBean!!.webviewTimeout = 10000
        // 设置回调监听
        gt3ConfigBean!!.listener = object : GT3Listener() {
            override fun onDialogResult(s: String?) {
                val validBean = JsonHelper.stringToJson(s, ValidResultBean::class.java) as ValidResultBean
                mPresenter.postValid(validBean.geetest_challenge, validBean.geetest_seccode, validBean.geetest_validate, status, uid)
            }

            override fun onStatistics(s: String) {

            }

            override fun onClosed(i: Int) {
                dialogHelper!!.dismiss()
            }

            override fun onSuccess(s: String) {

            }

            override fun onFailed(gt3ErrorBean: GT3ErrorBean) {

            }

            @SuppressLint("CheckResult")
            override fun onButtonClick() {
                mPresenter.getValid(login_phone!!.text.toString().trim())
            }
        }
        gt3GeetestUtils!!.init(gt3ConfigBean)
    }


    override fun onClick(v: View) {
        val email = login_phone!!.text.toString().trim()
        when (v.id) {
            R.id.login_submit -> {
                isValid = true
                val pwd = login_pwd!!.text.toString().trim()
                val code = login_code!!.text.toString().trim()
                mPresenter.login(mToken, email, pwd, code)
            }
            R.id.login_forget_pwd -> {
                SPUtils.getInstance().put(UPDATE_PASSWORD_TYPE, "1")
                startActivity(ForgetPasswordActivity::class.java)
            }
            R.id.send_code -> {
                dialogHelper!!.create(this, R.drawable.pending_icon_1, "发送验证码").show()
                mPresenter.sendCode(email)
            }
            R.id.back -> finish()
            R.id.login_pwd_show->{
                if (login_pwd.transformationMethod == HideReturnsTransformationMethod.getInstance()) {
                    login_pwd.transformationMethod = PasswordTransformationMethod.getInstance()
                    login_pwd_show.setImageResource(R.drawable.edit_hide)
                } else {
                    login_pwd.transformationMethod = HideReturnsTransformationMethod.getInstance()
                    login_pwd_show.setImageResource(R.drawable.edit_show)
                }
            }
        }
    }

    override fun initPresenter(): BasePresenter<*, *> {
        return LoginPresenter.newIntance()
    }

    override fun onDestroy() {
        super.onDestroy()
        gt3GeetestUtils!!.destory()
    }

    override fun showLoginStauts(isSuccess: Boolean, msg: String) {

        if (isSuccess) {
            dialogHelper!!.resetDialogResource(this, R.drawable.success_icon, msg)
            dialogHelper!!.dismissDelayed(object : DialogHelper.IDialogDialog {
                override fun callback() {
                    val mIntent = Intent(this@LoginActivity, MainActivity::class.java)
                    mIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
                    startActivity(mIntent)
                }
            }, 2000)
        } else {
            dialogHelper!!.resetDialogResource(this, R.drawable.miss_icon, msg)
            dialogHelper!!.dismissDelayed(null, 2000)
        }
    }

    @SuppressLint("CheckResult")
    override fun saveUserInfo(loginBean: LoginBean) {
        val data = loginBean.data
        SPUtils.getInstance().put(REFRESH_TOKEN, data.refreshToken)
        SPUtils.getInstance().put(TOKEN, data.token)
        SPUtils.getInstance().put(USER_ID, data.userId)
        SPUtils.getInstance().put(USER_EMAIL, data.email)
        SPUtils.getInstance().put(USER_PUBLIC_KEY, data.publicKey)
        SPUtils.getInstance().put(USER_SALT, data.salt)
        MyApplication.setTOKEN(data.token)
        RetrofitUtils.client(MyApplication.getBaseUrl(), ApiStore::class.java).getPushTag(MyApplication.getTOKEN()).compose(RxHelper.rxSchedulerHelper())
                .subscribe({ tagBean ->
                    if (tagBean.getCode() == 200 && tagBean.getData() != null) {
                        SPUtils.getInstance().put(TAG_NAME, tagBean.getData())
                        val tags = tagBean.getData().split(",".toRegex()).dropLastWhile({ it.isEmpty() }).toTypedArray()
                        for (i in tags.indices) {
                            if (i == 0) {
                                JPushInterface.setTags(application.applicationContext, Integer.parseInt(tags[i]), HashSet(Arrays.asList(tags[i])))
                            } else {
                                JPushInterface.addTags(application.applicationContext, Integer.parseInt(tags[i]), HashSet(Arrays.asList(tags[i])))
                            }
                        }
                    }
                    LogUtils.e("MyJPushMessageReceiver", "注册")
                    JPushInterface.setAlias(applicationContext, loginBean.data.userId, loginBean.data.userId.toString())
                }, { throwable -> LogUtils.e("MyJPushMessageReceiver", throwable.message) })
    }

    override fun userNotRegister(mnemo: String) {
        dialogHelper!!.dismissDelayed(null, 0)
        val mnemonicss = Arrays.asList(*mnemo.split(",".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray())
        val intent = Intent(this, VerificationMnemonicActivity::class.java)
        intent.putStringArrayListExtra("menmonicss", ArrayList(mnemonicss))
        startActivity(intent)
    }

    override fun showSendCode(isSuccess: Boolean, msg: String) {
        if (isSuccess) {
            dialogHelper!!.resetDialogResource(this, R.drawable.success_icon, msg)
            TimeVerification.getInstence().setOnTimeEndCallBack(object : OnTimeEndCallBack {
                override fun updata(time: Int) {
                    send_code!!.isEnabled = false
                    send_code!!.setBackgroundResource(R.drawable.shape_load_sendcode_bg)
                    send_code!!.setTextColor(ContextCompat.getColor(baseContext, R.color.edit_bg))
                    send_code!!.text = time.toString() + "s"
                }

                override fun exit() {
                    send_code!!.isEnabled = true
                    send_code!!.setBackgroundResource(R.drawable.shape_sendcode_bg)
                    send_code!!.setTextColor(ContextCompat.getColor(baseContext, R.color.login_content))
                    send_code!!.text = "重新发送"
                }
            }).updataTime()
        } else {
            dialogHelper!!.resetDialogResource(this, R.drawable.miss_icon, msg)
        }
        dialogHelper!!.dismissDelayed(null, 2000)
    }

    @Throws(JSONException::class)
    override fun showValid(result: LoginValidBean.DataBean?) {
        if (result != null) {
            status = result.status
            uid = result.uid
            gt3ConfigBean!!.api1Json = JSONObject(result.result)
            gt3GeetestUtils!!.getGeetest()
        }
    }

    override fun showVerification(message: String) {
        //                 开启验证
        dialogHelper!!.resetDialogResource(this, R.drawable.miss_icon, message)
        dialogHelper!!.dismissDelayed(object : DialogHelper.IDialogDialog {
            override fun callback() {
                if (isValid) {
                    gt3GeetestUtils!!.startCustomFlow()
                    isValid = false
                }
            }
        }, 2000)

    }

    override fun showSecondaryVerification(token: String) {
        if (token != "") {
            this.mToken = token
            gt3GeetestUtils!!.showSuccessDialog()
            val email = login_phone!!.text.toString().trim()
            val pwd = login_pwd!!.text.toString().trim()
            val code = login_code!!.text.toString().trim()
            LogUtils.e(pwd)
            SPUtils.getInstance().put(REG_EMAIL, email)
            val newsPwd = EncryptUtils.encryptMD5ToString(email + EncryptUtils.encryptMD5ToString(pwd))
            mPresenter.login(token, email, newsPwd, code)
        } else {
            gt3GeetestUtils!!.showFailedDialog()
        }
    }

    override fun show() {
        dialogHelper!!.create(this@LoginActivity, R.drawable.pending_icon_1, resources.getString(R.string.login_load)).show()
    }

    override fun initMVPData() {
        ImmersionBar.with(this).titleBar(R.id.status_bar).statusBarDarkFont(true).init()
    }

    override fun initMVPView() {
        dialogHelper = DialogHelper.instance
        login_pwd!!.addTextChangedListener(object : EditTextChange() {
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                val length = s.length
                login_pwd_show.visibility = if (length > 0) View.VISIBLE else View.INVISIBLE
            }
        })
    }

    override fun startActivity() {

    }
}
