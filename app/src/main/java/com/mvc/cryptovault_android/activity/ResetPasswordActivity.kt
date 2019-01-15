package com.mvc.cryptovault_android.activity

import android.view.View
import com.mvc.cryptovault_android.R

import com.mvc.cryptovault_android.base.BaseMVPActivity
import com.mvc.cryptovault_android.base.BasePresenter
import com.mvc.cryptovault_android.contract.ResetPasswordContract
import com.mvc.cryptovault_android.presenter.ResetPresenter

class ResetPasswordActivity : BaseMVPActivity<ResetPasswordContract.ResetPasswordPresenter>(), View.OnClickListener, ResetPasswordContract.ResetView {
    override fun initPresenter(): BasePresenter<*, *> {
        return ResetPresenter.newIntance()
    }

    override fun initMVPData() {
    }

    override fun initView() {
    }

    override fun initMVPView() {
    }

    override fun getLayoutId(): Int {
        return R.layout.activity_reset_password
    }

    override fun initData() {
    }

    override fun startActivity() {
    }

    override fun onClick(v: View?) {

    }

    override fun showError() {

    }

    override fun getRequestBody() {
    }
}
