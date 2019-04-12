package com.mvc.ttpay_android.activity

import android.annotation.SuppressLint
import android.support.v4.content.ContextCompat
import android.text.InputFilter
import android.view.Gravity
import android.view.View
import android.widget.PopupWindow
import com.blankj.utilcode.util.*
import com.gyf.barlibrary.ImmersionBar
import com.mvc.ttpay_android.MyApplication
import com.mvc.ttpay_android.R
import com.mvc.ttpay_android.api.ApiStore
import com.mvc.ttpay_android.base.BaseActivity
import com.mvc.ttpay_android.bean.FinancialDetailBean
import com.mvc.ttpay_android.common.Constant.SP.UPDATE_PASSWORD_TYPE
import com.mvc.ttpay_android.common.Constant.SP.USER_EMAIL
import com.mvc.ttpay_android.event.FinancialDetailEvent
import com.mvc.ttpay_android.listener.EditTextChange
import com.mvc.ttpay_android.listener.IPayWindowListener
import com.mvc.ttpay_android.listener.PswMaxListener
import com.mvc.ttpay_android.utils.PointLengthFilter
import com.mvc.ttpay_android.utils.RetrofitUtils
import com.mvc.ttpay_android.utils.RxHelper
import com.mvc.ttpay_android.utils.TextUtils
import com.mvc.ttpay_android.view.DialogHelper
import com.mvc.ttpay_android.view.PopViewHelper
import com.mvc.ttpay_android.view.PswText
import kotlinx.android.synthetic.main.activity_deposit.*
import okhttp3.MediaType
import okhttp3.RequestBody
import org.greenrobot.eventbus.EventBus
import org.json.JSONObject
import java.net.SocketTimeoutException

class FinancialDepositActivity : BaseActivity() {
    private lateinit var detail: FinancialDetailBean.DataBean

    private lateinit var dialogHelper: DialogHelper
    private lateinit var mPopView: PopupWindow
    override fun getLayoutId(): Int {
        return R.layout.activity_deposit
    }

    override fun initData() {

    }

    @SuppressLint("SetTextI18n")
    override fun initView() {
        ImmersionBar.with(this).titleBar(R.id.status_bar).statusBarDarkFont(true).init()
        detail = intent.getParcelableExtra("detail")
        dialogHelper = DialogHelper.instance
        deposit_limit.text = "每用户参与限额：${TextUtils.doubleToEight(detail.purchased)}/${TextUtils.doubleToEight(detail.userLimit)}"
        available.text = "可用${detail.baseTokenName}：${TextUtils.doubleToEight(detail.balance)}"
        financial_title.text = "${detail.name}存入"
        remaining_amount_progress.max = detail.limitValue.toInt()
        remaining_amount_progress.progress = (detail.limitValue - detail.sold).toInt()
        remaining_amount.text = "剩余总额度 ${TextUtils.doubleToFourPrice(detail.limitValue - detail.sold)} ${detail.baseTokenName}"
        deposit_count.filters = arrayOf<InputFilter>(PointLengthFilter())
        deposit_count.addTextChangedListener(object : EditTextChange() {
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                var length = s!!.length
                if (length > 0) {
                    var price = java.lang.Double.parseDouble(s.toString())
                    if (price > detail.balance) {
                        available.text = "可用${detail.baseTokenName}：不足"
                        available.setTextColor(ContextCompat.getColor(this@FinancialDepositActivity, R.color.red))
                        submit.setBackgroundResource(R.drawable.bg_toge_child_item_tv_blue_nocheck)
                        submit.isEnabled = false
                    } else {
                        available.text = "可用${detail.baseTokenName}：${TextUtils.doubleToEight(detail.balance)}"
                        available.setTextColor(ContextCompat.getColor(this@FinancialDepositActivity, R.color.login_content))
                        submit.setBackgroundResource(R.drawable.bg_login_submit)
                        submit.isEnabled = true
                    }
                } else {
                    available.text = "可用${detail.baseTokenName}：${TextUtils.doubleToEight(detail.balance)}"
                    available.setTextColor(ContextCompat.getColor(this@FinancialDepositActivity, R.color.login_content))
                    submit.setBackgroundResource(R.drawable.bg_login_submit)
                    submit.isEnabled = true
                }
            }
        })
    }

    private fun initPopHelper() {
        mPopView = PopViewHelper.instance
                .create(this
                        , R.layout.layout_paycode
                        , "${deposit_count.text} ${detail.baseTokenName}"
                        , object : IPayWindowListener {
                    override fun onclick(view: View?) {
                        when (view?.id) {
                            R.id.pay_close -> {
                                mPopView.dismiss()
                                KeyboardUtils.hideSoftInput(mPopView.contentView.findViewById<PswText>(R.id.pay_text))
                                ToastUtils.showLong("取消存入")
                            }
                            R.id.pay_text -> {
                                KeyboardUtils.showSoftInput(mPopView.contentView.findViewById<PswText>(R.id.pay_text))
                                setAlpha(0.5f)
                            }
                            R.id.pay_forget -> {
                                SPUtils.getInstance().put(UPDATE_PASSWORD_TYPE, "2")
                                startActivity(ForgetPasswordActivity::class.java)
                                KeyboardUtils.hideSoftInput(mPopView.contentView.findViewById<PswText>(R.id.pay_text))
                            }
                        }
                    }

                    override fun dismiss() {
                        setAlpha(1f)
                        KeyboardUtils.hideSoftInput(mPopView.contentView.findViewById<PswText>(R.id.pay_text))
                    }
                }, PswMaxListener { num ->
                    val email = SPUtils.getInstance().getString(USER_EMAIL)
                    KeyboardUtils.hideSoftInput(mPopView.contentView.findViewById<PswText>(R.id.pay_text))
                    mPopView.dismiss()
                    dialogHelper.create(this@FinancialDepositActivity, R.drawable.pending_icon_1, "存入中").show()
                    RetrofitUtils.client(MyApplication.getBaseUrl(), ApiStore::class.java).getUserSalt(MyApplication.getTOKEN(), email)
                            .compose(RxHelper.rxSchedulerHelper())
                            .flatMap {
                                salt->
                                var json = JSONObject()
                                json.put("transactionPassword", EncryptUtils.encryptMD5ToString("${salt.data}${EncryptUtils.encryptMD5ToString(num)}"))
                                json.put("value", deposit_count.text.toString())
                                var body = RequestBody.create(MediaType.parse("text/html"), json.toString())
                                RetrofitUtils.client(MyApplication.getBaseUrl(),ApiStore::class.java)
                                        .depositFinancial(MyApplication.getTOKEN(), body, detail.id)
                                        .compose(RxHelper.rxSchedulerHelper())
                            }.subscribe({ date ->
                                if (date.code == 200) {
                                    dialogHelper.resetDialogResource(this@FinancialDepositActivity, R.drawable.success_icon, "存入成功")
                                    dialogHelper.dismissDelayed(object :DialogHelper.IDialogDialog{
                                        override fun callback() {
                                            EventBus.getDefault().post(FinancialDetailEvent())
                                            finish()
                                        }
                                    },1500)
                                } else {
                                    dialogHelper.resetDialogResource(this@FinancialDepositActivity, R.drawable.miss_icon, date.message)
                                    dialogHelper.dismissDelayed(null,1500)
                                }
                            }, {
                                if (it is SocketTimeoutException) {
                                    dialogHelper!!.resetDialogResource(this@FinancialDepositActivity, R.drawable.miss_icon, "连接超时")
                                } else {
                                    dialogHelper!!.resetDialogResource(this@FinancialDepositActivity, R.drawable.miss_icon, it.message!!)
                                }
                                dialogHelper.dismissDelayed(null)
                            })
                })
        mPopView.contentView.post { mPopView.contentView.findViewById<PswText>(R.id.pay_text).performClick() }
        mPopView.showAtLocation(deposit_count, Gravity.BOTTOM, 0, 0)
        setAlpha(0.5f)
    }

    fun onClick(v: View) {
        when (v.id) {
            R.id.back -> {
                finish()
            }
            R.id.submit -> {
                if (checkNotNullValue()) {
                    initPopHelper()
                }
            }
        }
    }

    private fun checkNotNullValue(): Boolean {
        if (deposit_count.text.toString() == "") {
            dialogHelper.create(this, R.drawable.miss_icon, "存入金额不许为空").show()
            dialogHelper.dismissDelayed(null)
            return false
        }
        return true
    }
}