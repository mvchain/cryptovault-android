package com.mvc.cryptovault_android.activity

import android.view.View
import com.mvc.cryptovault_android.R
import com.mvc.cryptovault_android.base.BaseActivity
import kotlinx.android.synthetic.main.activity_verification_password.*

/**
 * 验证信息的activity
 * 验证私钥，助记词，邮箱
 * 0邮箱 1私钥 2助记词
 */
class VerificationInfoActivity : BaseActivity(), View.OnClickListener {
    private val TYPE_EMAIL = 0
    private val TYPE_PRIVATEKEY = 1
    private val TYPE_MNEMONICS = 2
    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.back -> {
                finish()
            }
            R.id.send_code -> {

            }
            R.id.submit -> {

            }

        }
    }

    override fun getLayoutId(): Int {
        return R.layout.activity_verification_password
    }

    override fun initData() {
    }

    override fun initView() {
        var getIntent = intent
        var type = getIntent.getIntExtra("type", -1)
        when (type) {
            TYPE_EMAIL -> {
                verification_title.text = "邮箱验证"
            }
            TYPE_PRIVATEKEY -> {
                verification_title.text = "私钥验证"
            }
            TYPE_MNEMONICS -> {
                verification_title.text = "输入邮箱账户"
            }
        }
    }
}