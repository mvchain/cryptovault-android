package com.mvc.ttpay_android.contract

import com.mvc.ttpay_android.base.BasePresenter
import com.mvc.ttpay_android.base.IBaseActivity
import com.mvc.ttpay_android.base.IBaseModel
import com.mvc.ttpay_android.bean.HttpTokenBean
import com.mvc.ttpay_android.bean.LoginBean
import com.mvc.ttpay_android.bean.LoginValidBean

import org.json.JSONException

import io.reactivex.Observable

interface ILoginContract {
    abstract class LoginPresenter : BasePresenter<ILoginModel, ILoginView>() {
        abstract fun login(imageToken: String, email: String, pwd: String, code: String)

        abstract fun sendCode(cellphone: String)


        abstract fun getValid(email: String)

        abstract fun postValid(geetest_challenge: String, geetest_seccode: String, geetest_validate: String, status: Int, uid: String)
    }

    interface ILoginModel : IBaseModel {
        /**
         * 请求登录
         *
         * @param email
         * @param pwd
         * @return
         */
        fun getLoginStatus(imageToken: String, email: String, pwd: String, code: String): Observable<LoginBean>

        fun sendCode(cellphone: String): Observable<HttpTokenBean>

        fun getValid(email: String): Observable<LoginValidBean>

        fun postValid(geetest_challenge: String, geetest_seccode: String, geetest_validate: String, status: Int, uid: String): Observable<HttpTokenBean>

    }

    interface ILoginView : IBaseActivity {
        /**
         * 登录是否成功
         *
         * @param msg
         */
        fun showLoginStatus(isSuccess: Boolean, msg: String, loginBean: LoginBean?)

        /**
         * 用户未激活
         *
         * @param mnemonicss
         */
        fun userNotRegister(mnemonicss: String)

        /**
         * 验证码是否发送成功
         *
         * @param msg
         */
        fun showSendCode(isSuccess: Boolean, msg: String)

        @Throws(JSONException::class)
        fun showValid(result: LoginValidBean.DataBean)

        @Throws(JSONException::class)
        fun showVerification(message: String)

        fun showSecondaryVerification(token: String)

        /**
         * 显示dialog
         */
        fun show()
    }
}
