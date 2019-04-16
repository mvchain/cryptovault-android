package com.mvc.ttpay_android.activity

import android.content.Intent
import android.view.View
import cn.jpush.android.api.JPushInterface
import com.blankj.utilcode.util.LogUtils
import com.blankj.utilcode.util.SPUtils
import com.blankj.utilcode.util.ToastUtils
import com.gyf.barlibrary.ImmersionBar
import com.mvc.ttpay_android.MainActivity
import com.mvc.ttpay_android.MyApplication
import com.mvc.ttpay_android.R
import com.mvc.ttpay_android.api.ApiStore
import com.mvc.ttpay_android.base.BaseActivity
import com.mvc.ttpay_android.common.Constant
import com.mvc.ttpay_android.listener.IDialogViewClickListener
import com.mvc.ttpay_android.utils.RetrofitUtils
import com.mvc.ttpay_android.utils.RxHelper
import com.mvc.ttpay_android.view.DialogHelper
import kotlinx.android.synthetic.main.activity_loginverifliygoogle.*
import java.util.*

class LoginVerificationGoogleActivity : BaseActivity() {
    private lateinit var dialogHelper: DialogHelper

    override fun getLayoutId(): Int {
        return R.layout.activity_loginverifliygoogle
    }

    override fun initData() {

    }

    override fun initView() {
        ImmersionBar.with(this).titleBar(R.id.status_bar).statusBarDarkFont(true).init()
        dialogHelper = DialogHelper.instance
    }

    fun onClick(v: View) {
        when (v.id) {
            R.id.back -> {
                finish()
            }
            R.id.google_forget -> {
                dialogHelper.create(this, IDialogViewClickListener { dialogHelper.dismiss() }, "请联系邮箱")
            }
            R.id.google_submit -> {
                var code = google_code.text.toString()
                if (code.isEmpty()) {
                    ToastUtils.showShort("Google验证码不可为空")
                    return
                }
                dialogHelper.create(this, R.drawable.pending_icon, getString(R.string.in_verification)).show()
                RetrofitUtils.client(MyApplication.getBaseUrl(), ApiStore::class.java)
                        .verificationGoogle(MyApplication.getTOKEN(), code)
                        .compose(RxHelper.rxSchedulerHelper())
                        .subscribe({ loginBean ->
                            if (loginBean.code == 200) {
                                val data = loginBean.data
                                SPUtils.getInstance().put(Constant.SP.TOKEN, loginBean.data.token)
                                SPUtils.getInstance().put(Constant.SP.REFRESH_TOKEN, data.refreshToken)
                                SPUtils.getInstance().put(Constant.SP.USER_ID, data.userId)
                                SPUtils.getInstance().put(Constant.SP.USER_EMAIL, data.email)
                                SPUtils.getInstance().put(Constant.SP.USER_PUBLIC_KEY, data.publicKey)
                                SPUtils.getInstance().put(Constant.SP.USER_SALT, data.salt)
                                SPUtils.getInstance().put(Constant.SP.USER_GOOGLE,data.googleCheck)
                                MyApplication.setTOKEN(data.token)
                                RetrofitUtils.client(MyApplication.getBaseUrl(), ApiStore::class.java).getPushTag(MyApplication.getTOKEN()).compose(RxHelper.rxSchedulerHelper())
                                        .subscribe({ tagBean ->
                                            if (tagBean.code == 200 && tagBean.data != null) {
                                                SPUtils.getInstance().put(Constant.SP.TAG_NAME, tagBean.data)
                                                val tags = tagBean.data.split(",".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
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
                                dialogHelper.resetDialogResource(this, R.drawable.success_icon, "登录成功")
                                dialogHelper.dismissDelayed(object : DialogHelper.IDialogDialog {
                                    override fun callback() {
                                        val mIntent = Intent(this@LoginVerificationGoogleActivity, MainActivity::class.java)
                                        mIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
                                        startActivity(mIntent)
                                    }
                                }, 2000)
                            } else {
                                dialogHelper.resetDialogResource(this, R.drawable.miss_icon, loginBean.message)
                                dialogHelper.dismissDelayed(null, 2000)
                            }
                        }, { error ->
                            dialogHelper.resetDialogResource(this, R.drawable.miss_icon, error.message!!)
                            dialogHelper.dismissDelayed(null, 2000)
                        })
            }
        }
    }
}