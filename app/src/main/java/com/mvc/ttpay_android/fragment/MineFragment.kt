package com.mvc.ttpay_android.fragment

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v4.widget.SwipeRefreshLayout
import android.view.View
import android.widget.TextView

import com.allen.library.SuperTextView
import com.blankj.utilcode.util.SPUtils
import com.blankj.utilcode.util.ToastUtils
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.mvc.ttpay_android.R
import com.mvc.ttpay_android.activity.AboutActivity
import com.mvc.ttpay_android.activity.InvatitionActivity
import com.mvc.ttpay_android.activity.LanguageActivity
import com.mvc.ttpay_android.activity.SelectResetPasswordActivity
import com.mvc.ttpay_android.base.BaseMVPFragment
import com.mvc.ttpay_android.base.BasePresenter
import com.mvc.ttpay_android.bean.UserInfoBean
import com.mvc.ttpay_android.contract.IMineContract
import com.mvc.ttpay_android.presenter.MinePresenter
import com.mvc.ttpay_android.utils.JsonHelper
import com.mvc.ttpay_android.view.DialogHelper

import de.hdodenhof.circleimageview.CircleImageView

import com.mvc.ttpay_android.common.Constant.SP.USER_INFO
import com.mvc.ttpay_android.common.Constant.SP.USER_PUBLIC_KEY
import com.mvc.ttpay_android.listener.IDialogViewClickListener

class MineFragment : BaseMVPFragment<IMineContract.MinePresenter>(), IMineContract.IMineView, View.OnClickListener {

    private var mImgUser: CircleImageView? = null
    private var mNameUser: TextView? = null
    private var mPhoneUser: TextView? = null
    private var mKeyUser: TextView? = null
    private var mLanguageSys: SuperTextView? = null
    private var mAccountSecurity: SuperTextView? = null
    private var mInvitationRegistration: SuperTextView? = null
    private var mAbout: SuperTextView? = null
    private var mSwipMine: SwipeRefreshLayout? = null
    private var createCarryOut: Boolean = false
    private var dialogHelper: DialogHelper? = null
    private var mSingOut: TextView? = null


    override fun initData() {
        super.initData()
        getUserInfo()
    }

    private fun getUserInfo() {
        mPresenter.getUserInfo()
    }

    override fun initView() {
        dialogHelper = DialogHelper.instance
        createCarryOut = true
        mImgUser = rootView.findViewById(R.id.user_img)
        mNameUser = rootView.findViewById(R.id.user_name)
        mKeyUser = rootView.findViewById(R.id.user_key)
        mPhoneUser = rootView.findViewById(R.id.user_phone)
        mLanguageSys = rootView.findViewById(R.id.sys_language)
        mSingOut = rootView.findViewById(R.id.singout)
        mAccountSecurity = rootView.findViewById(R.id.account_security)
        mInvitationRegistration = rootView.findViewById(R.id.invitation_registration)
        mAbout = rootView.findViewById(R.id.about)
        mSwipMine = rootView.findViewById(R.id.mine_swip)
        mSwipMine!!.post { mSwipMine!!.isRefreshing = true }
        mSwipMine!!.setOnRefreshListener { this.refresh() }
        mAccountSecurity!!.setOnClickListener(this)
        mInvitationRegistration!!.setOnClickListener(this)
        mLanguageSys!!.setOnClickListener(this)
        mAbout!!.setOnClickListener(this)
        mSingOut!!.setOnClickListener(this)
        mKeyUser!!.setOnClickListener(this)
    }

    override fun getLayoutId(): Int {
        return R.layout.fragment_home_mine
    }

    override fun initPresenter(): BasePresenter<*, *> {
        return MinePresenter.newIntance()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mSwipMine!!.post { mSwipMine!!.isRefreshing = false }
    }

    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
        if (isVisibleToUser && createCarryOut) {
            refresh()
        }
    }

    override fun setUser(user: UserInfoBean) {
        mSwipMine!!.post { mSwipMine!!.isRefreshing = false }
        SPUtils.getInstance().put(USER_INFO, JsonHelper.jsonToString(user))
        val data = user.data
        mNameUser!!.text = data.nickname
        mPhoneUser!!.text = "邮箱 ${data.username}"
        mKeyUser!!.text = "公钥 ${SPUtils.getInstance().getString(USER_PUBLIC_KEY)}"
        val options = RequestOptions().fallback(R.drawable.portrait_icon).placeholder(R.drawable.loading_img).error(R.drawable.portrait_icon)
        Glide.with(activity).load(data.headImage).apply(options).into(mImgUser!!)
    }

    private fun refresh() {
        mPresenter.getUserInfo()
    }

    override fun serverError() {
        mSwipMine!!.post { mSwipMine!!.isRefreshing = false }
        val userJson = SPUtils.getInstance().getString(USER_INFO)
        if (userJson != "") {
            val infoBean = JsonHelper.stringToJson(userJson, UserInfoBean::class.java) as UserInfoBean
            if (infoBean != null) {
                val data = infoBean.data
                mNameUser!!.text = "-"
                mPhoneUser!!.text = "-"
                mKeyUser!!.text = " "
                val options = RequestOptions().fallback(R.drawable.portrait_icon).placeholder(R.drawable.loading_img).error(R.drawable.portrait_icon)
                Glide.with(activity).load(data.headImage).apply(options).into(mImgUser!!)
            }
        }
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.account_security -> startActivity(Intent(activity, SelectResetPasswordActivity::class.java))
            R.id.invitation_registration -> startActivity(Intent(activity, InvatitionActivity::class.java))
            R.id.sys_language -> startActivity(Intent(activity, LanguageActivity::class.java))
            R.id.about -> startActivity(Intent(activity, AboutActivity::class.java))
            R.id.singout -> {
                dialogHelper!!.create(activity, "确定要登出TTPay?", IDialogViewClickListener { viewId ->
                    when (viewId) {
                        R.id.hint_enter -> {
                            dialogHelper!!.dismiss()
                            startTaskActivity(activity)
                        }
                        R.id.hint_cancle -> dialogHelper!!.dismiss()
                    }
                }).show()
            }
            R.id.user_key -> {
                // TODO 18/12/07
                val cm = activity.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
                // 创建普通字符型ClipData
                val mClipData = ClipData.newPlainText("hash", mKeyUser!!.text.toString().replace("公钥", ""))
                // 将ClipData内容放到系统剪贴板里。
                cm.primaryClip = mClipData
                ToastUtils.showLong("公钥已复制至剪贴板")
            }
        }
    }
}
