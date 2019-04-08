package com.mvc.cryptovault_android.activity

import android.content.Intent
import android.view.View
import com.gyf.barlibrary.ImmersionBar
import com.mvc.cryptovault_android.MainActivity
import com.mvc.cryptovault_android.R
import com.mvc.cryptovault_android.base.BaseActivity
import com.mvc.cryptovault_android.listener.IDialogViewClickListener
import com.mvc.cryptovault_android.view.DialogHelper

class TurnGoogleActivity : BaseActivity() {
    private lateinit var dialogHelper: DialogHelper

    override fun getLayoutId(): Int {
        return R.layout.activity_turngoogle
    }

    override fun initData() {
    }

    override fun initView() {
        ImmersionBar.with(this).titleBar(R.id.status_bar).statusBarDarkFont(true).init()
        dialogHelper = DialogHelper.instance

    }

    fun onClick(v: View) {
        when (v.id) {
            R.id.google_skip -> {
                showDialogHint()
            }
            R.id.google_submit -> {
                startActivity(GoogleCodeActivity::class.java)
            }
        }
    }

    override fun onBackPressed() {
        showDialogHint()
    }

    fun showDialogHint() {
        dialogHelper.create(this, IDialogViewClickListener { viewId ->
            when (viewId) {
                R.id.dialog_submit -> {
                    dialogHelper.dismiss()
                    val intent = Intent(this, MainActivity::class.java)
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
                    startActivity(intent)
                    finish()
                }
            }
        },"跳过后，您可在账户安全里开启Google验证").show()
    }
}