package com.mvc.cryptovault_android.activity

import android.view.View
import com.blankj.utilcode.util.LogUtils
import com.blankj.utilcode.util.SPUtils

import com.gyf.barlibrary.ImmersionBar
import com.mvc.cryptovault_android.R
import com.mvc.cryptovault_android.base.BaseActivity
import com.mvc.cryptovault_android.common.Constant.SP.REG_INVITATION
import com.mvc.cryptovault_android.view.DialogHelper
import kotlinx.android.synthetic.main.activity_register.*
import org.json.JSONObject

class RegisterActivity : BaseActivity(), View.OnClickListener {
    private var dialogHelper: DialogHelper? = null
    private var invitationJson = JSONObject()
    override fun getLayoutId(): Int {
        return R.layout.activity_register
    }

    override fun initData() {

    }

    override fun initView() {
        ImmersionBar.with(this).titleBar(R.id.status_bar).statusBarDarkFont(true).init()
        dialogHelper = DialogHelper.getInstance()
        reg_layout.viewTreeObserver.addOnDrawListener {
            if (reg_invitation.text.toString() != ""
                    && reg_nickname.text.toString() != ""
                    && reg_email.text.toString() != ""
                    && reg_code.text.toString() != "") {
                reg_submit.setBackgroundResource(R.drawable.bg_login_submit)
                reg_submit.isEnabled = true
            } else {
                reg_submit.setBackgroundResource(R.drawable.bg_toge_child_item_tv_blue_nocheck)
                reg_submit.isEnabled = false

            }
        }
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.reg_submit -> {
                checkNotNullValue()
                SPUtils.getInstance().put(REG_INVITATION, invitationJson.toString())
            }
            R.id.send_code -> {

            }
            R.id.reg_back -> {
                finish()
            }
        }// TODO 19/01/11
    }

    /**
     * 检查元素是否为空
     */
    private fun checkNotNullValue() {
        if (reg_invitation.text.toString() == "") {
            dialogHelper!!.create(this, R.drawable.miss_icon, "邀请码不可为空").show()
            dialogHelper!!.dismissDelayed(null)
            return
        }
        if (reg_nickname.text.toString() == "") {
            dialogHelper!!.create(this, R.drawable.miss_icon, "昵称不可为空").show()
            dialogHelper!!.dismissDelayed(null)
            return
        }
        if (reg_email.text.toString() == "") {
            dialogHelper!!.create(this, R.drawable.miss_icon, "邮箱不可为空").show()
            dialogHelper!!.dismissDelayed(null)
            return
        }
        if (reg_code.text.toString() == "") {
            dialogHelper!!.create(this, R.drawable.miss_icon, "验证码不可为空").show()
            dialogHelper!!.dismissDelayed(null)
            return
        }
        invitationJson.put("invitation", reg_invitation.text.toString())
        invitationJson.put("nickname", reg_nickname.text.toString())
        invitationJson.put("email", reg_email.text.toString())
        invitationJson.put("code", reg_code.text.toString())
    }
}
