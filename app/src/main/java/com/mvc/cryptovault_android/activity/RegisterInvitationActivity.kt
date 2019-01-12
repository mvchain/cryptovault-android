package com.mvc.cryptovault_android.activity

import android.view.View
import com.blankj.utilcode.util.SPUtils

import com.gyf.barlibrary.ImmersionBar
import com.mvc.cryptovault_android.R
import com.mvc.cryptovault_android.base.BaseMVPActivity
import com.mvc.cryptovault_android.base.BasePresenter
import com.mvc.cryptovault_android.common.Constant.SP.REG_INVITATION
import com.mvc.cryptovault_android.common.Constant.SP.REG_TEMPTOKEN
import com.mvc.cryptovault_android.contract.RegisterInvitation
import com.mvc.cryptovault_android.listener.OnTimeEndCallBack
import com.mvc.cryptovault_android.presenter.RegisterInvitationPresenter
import com.mvc.cryptovault_android.utils.TimeVerification
import com.mvc.cryptovault_android.view.DialogHelper
import kotlinx.android.synthetic.main.activity_register.*
import org.json.JSONObject

class RegisterInvitationActivity : BaseMVPActivity<RegisterInvitation.RegisterInvitationPresenter>(), View.OnClickListener, RegisterInvitation.InvitationView {

    override fun initPresenter(): BasePresenter<*, *> {
        return RegisterInvitationPresenter.newIntance()
    }

    override fun initMVPData() {

    }

    override fun initMVPView() {
        if (SPUtils.getInstance().getString(REG_INVITATION) != "") {
            startActivity(RegisterSetPwdActivity::class.java)
            finish()
        }
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

    private var dialogHelper: DialogHelper? = null
    private var invitationJson = JSONObject()
    override fun getLayoutId(): Int {
        return R.layout.activity_register
    }

    override fun initData() {

    }

    override fun initView() {

    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.reg_submit -> {
                if (checkNotNullValue()) {
                    dialogHelper?.create(this, R.drawable.pending_icon_1, "请稍后")?.show()
                    mPresenter.sendInvitationRequest(reg_invitation.text.toString(), reg_email.text.toString(), reg_code.text.toString())
                }
            }
            R.id.send_code -> {
                if (reg_email.text.toString() == "") {
                    dialogHelper?.create(this, R.drawable.miss_icon, "邮箱不可为空")?.show()
                    dialogHelper?.dismissDelayed(null)
                    return
                }
                dialogHelper?.create(this, R.drawable.pending_icon_1, "发送验证码")?.show()
                mPresenter.sendValiCode(reg_email.text.toString())
            }
            R.id.reg_back -> {
                finish()
            }
        }// TODO 19/01/11
    }

    /**
     * 检查元素是否为空
     */
    private fun checkNotNullValue(): Boolean {
        if (reg_invitation.text.toString() == "") {
            dialogHelper?.create(this, R.drawable.miss_icon, "邀请码不可为空")?.show()
            dialogHelper?.dismissDelayed(null)
            return false
        }
        if (reg_nickname.text.toString() == "") {
            dialogHelper?.create(this, R.drawable.miss_icon, "昵称不可为空")?.show()
            dialogHelper?.dismissDelayed(null)
            return false
        }
        if (reg_email.text.toString() == "") {
            dialogHelper?.create(this, R.drawable.miss_icon, "邮箱不可为空")?.show()
            dialogHelper?.dismissDelayed(null)
            return false
        }
        if (reg_code.text.toString() == "") {
            dialogHelper?.create(this, R.drawable.miss_icon, "验证码不可为空")?.show()
            dialogHelper?.dismissDelayed(null)
            return false
        }
        invitationJson.put("inviteCode", reg_invitation.text.toString())
        invitationJson.put("nickname", reg_nickname.text.toString())
        invitationJson.put("email", reg_email.text.toString())
        return true
    }

    override fun startActivity() {
        SPUtils.getInstance().put(REG_INVITATION, invitationJson.toString())
        startActivity(RegisterSetPwdActivity::class.java)
    }

    override fun savaTempToken(token: String) {
        //如果存在临时token，保存之后再保存当前信息
        SPUtils.getInstance().put(REG_TEMPTOKEN, token) //校验临时token 确定重新进入时返回的页面
        invitationJson.put("token", token)
        dialogHelper?.dismissDelayed(null, 0)
    }

    override fun showValiCode(email: String) {
        dialogHelper?.resetDialogResource(this, R.drawable.success_icon, email)
        dialogHelper?.dismissDelayed { null }
        TimeVerification.getInstence().setOnTimeEndCallBack(object : OnTimeEndCallBack {
            override fun updata(time: Int) {
                send_code.isEnabled = false
                send_code.text = "${time}s后重新获取"
            }

            override fun exit() {
                send_code.isEnabled = true
                send_code.text = "重新获取验证码"
            }
        }).updataTime()
    }

    override fun showError(error: String) {
        dialogHelper?.resetDialogResource(this, R.drawable.miss_icon, error)
        dialogHelper?.dismissDelayed { null }
    }

}
