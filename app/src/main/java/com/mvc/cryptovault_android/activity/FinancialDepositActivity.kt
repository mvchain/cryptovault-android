package com.mvc.cryptovault_android.activity

import android.support.v4.content.ContextCompat
import android.text.InputFilter
import android.view.Gravity
import android.view.View
import android.widget.PopupWindow
import com.blankj.utilcode.util.*
import com.gyf.barlibrary.ImmersionBar
import com.mvc.cryptovault_android.MyApplication
import com.mvc.cryptovault_android.R
import com.mvc.cryptovault_android.api.ApiStore
import com.mvc.cryptovault_android.base.BaseActivity
import com.mvc.cryptovault_android.bean.FinancialDetailBean
import com.mvc.cryptovault_android.common.Constant.SP.UPDATE_PASSWORD_TYPE
import com.mvc.cryptovault_android.common.Constant.SP.USER_EMAIL
import com.mvc.cryptovault_android.event.FinancialDetailEvent
import com.mvc.cryptovault_android.listener.EditTextChange
import com.mvc.cryptovault_android.listener.IPayWindowListener
import com.mvc.cryptovault_android.utils.PointLengthFilter
import com.mvc.cryptovault_android.utils.RetrofitUtils
import com.mvc.cryptovault_android.utils.RxHelper
import com.mvc.cryptovault_android.utils.TextUtils
import com.mvc.cryptovault_android.view.DialogHelper
import com.mvc.cryptovault_android.view.PopViewHelper
import com.mvc.cryptovault_android.view.PswText
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

    override fun initView() {
        ImmersionBar.with(this).titleBar(R.id.status_bar).statusBarDarkFont(true).init()
        detail = intent.getParcelableExtra("detail")
        dialogHelper = DialogHelper.getInstance()
        deposit_limit.text = "产品限额：${TextUtils.doubleToFour(detail.purchased)}/${TextUtils.doubleToFour(detail.userLimit)}"
        available.text = "可用${detail.baseTokenName}：${TextUtils.doubleToFour(detail.balance)}"
        financial_title.text = "${detail.name}存入"
        deposit_count.filters = arrayOf<InputFilter>(PointLengthFilter())
        deposit_count.addTextChangedListener(object : EditTextChange() {
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                var length = s!!.length
                if (length > 0) {
                    var price = java.lang.Double.parseDouble(s.toString())
                    if (price > detail.balance) {
                        available.text = "可用${detail.baseTokenName}：不足"
                        available.setTextColor(ContextCompat.getColor(this@FinancialDepositActivity, R.color.red))
                    } else {
                        available.text = "可用${detail.baseTokenName}：${TextUtils.doubleToFour(detail.balance)}"
                        available.setTextColor(ContextCompat.getColor(this@FinancialDepositActivity, R.color.login_content))
                    }
                } else {
                    available.text = "可用${detail.baseTokenName}：${TextUtils.doubleToFour(detail.balance)}"
                    available.setTextColor(ContextCompat.getColor(this@FinancialDepositActivity, R.color.login_content))
                }
            }
        })
    }

    private fun initPopHelper() {
        mPopView = PopViewHelper.getInstance()
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
                }
                ) { num ->
                    val email = SPUtils.getInstance().getString(USER_EMAIL)
                    KeyboardUtils.hideSoftInput(mPopView.contentView.findViewById<PswText>(R.id.pay_text))
                    mPopView.dismiss()
                    dialogHelper?.create(this@FinancialDepositActivity, R.drawable.pending_icon_1, "存入中").show()
                    var json = JSONObject()
                    LogUtils.e(num)
                    LogUtils.e(email)
                    json.put("transactionPassword", EncryptUtils.encryptMD5ToString(email + EncryptUtils.encryptMD5ToString(num)))
                    json.put("value", deposit_count.text.toString())
                    var body = RequestBody.create(MediaType.parse("text/html"), json.toString())
                    RetrofitUtils.client(ApiStore::class.java)
                            .depositFinancial(MyApplication.getTOKEN(), body, detail.id)
                            .compose(RxHelper.rxSchedulerHelper())
                            .subscribe({ date ->
                                if (date.code === 200) {
                                    dialogHelper.resetDialogResource(this@FinancialDepositActivity, R.drawable.success_icon, "存入成功")
                                    dialogHelper.dismissDelayed {
                                        EventBus.getDefault().post(FinancialDetailEvent())
                                        finish()
                                    }
                                } else {
                                    dialogHelper.resetDialogResource(this@FinancialDepositActivity, R.drawable.miss_icon, date.message)
                                    dialogHelper.dismissDelayed { null }
                                }
                            }, {
                                if (it is SocketTimeoutException) {
                                    dialogHelper!!.resetDialogResource(this@FinancialDepositActivity, R.drawable.miss_icon, "连接超时")
                                } else {
                                    dialogHelper!!.resetDialogResource(this@FinancialDepositActivity, R.drawable.miss_icon, it.message)
                                }
                                dialogHelper.dismissDelayed { null }
                            })
                }
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
            dialogHelper.dismissDelayed { null }
            return false
        }
        return true
    }
}