package com.mvc.cryptovault_android.activity

import android.annotation.SuppressLint
import android.view.Gravity
import android.view.View
import android.widget.PopupWindow
import com.blankj.utilcode.util.KeyboardUtils
import com.blankj.utilcode.util.LogUtils
import com.blankj.utilcode.util.SPUtils
import com.blankj.utilcode.util.ToastUtils
import com.gyf.barlibrary.ImmersionBar
import com.mvc.cryptovault_android.MyApplication
import com.mvc.cryptovault_android.R
import com.mvc.cryptovault_android.api.ApiStore
import com.mvc.cryptovault_android.base.BaseActivity
import com.mvc.cryptovault_android.common.Constant.SP.UPDATE_PASSWORD_TYPE
import com.mvc.cryptovault_android.listener.IPayWindowListener
import com.mvc.cryptovault_android.listener.PswMaxListener
import com.mvc.cryptovault_android.utils.RetrofitUtils
import com.mvc.cryptovault_android.utils.RxHelper
import com.mvc.cryptovault_android.view.DialogHelper
import com.mvc.cryptovault_android.view.PopViewHelper
import com.mvc.cryptovault_android.view.PswText
import kotlinx.android.synthetic.main.activity_deposit.*
import okhttp3.MediaType
import okhttp3.RequestBody
import org.json.JSONObject

class FinancialDepositActivity : BaseActivity() {
    private var id = -1
    private lateinit var baseName: String

    private lateinit var dialogHelper: DialogHelper
    private lateinit var mPopView: PopupWindow
    override fun getLayoutId(): Int {
        return R.layout.activity_deposit
    }

    override fun initData() {

    }

    override fun initView() {
        ImmersionBar.with(this).titleBar(R.id.status_bar).statusBarDarkFont(true).init()
        id = intent.getIntExtra("id", 0)
        baseName = intent.getStringExtra("baseName")
        dialogHelper = DialogHelper.getInstance()
    }

    private fun initPopHelper() {
        mPopView = PopViewHelper.getInstance()
                .create(this
                        , R.layout.layout_paycode
                        , "${deposit_count.text} $baseName"
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
                    LogUtils.e(num)
//                    dialogHelper?.create(this@FinancialDepositActivity, R.drawable.pending_icon_1, "存入中").show()
//                    var json = JSONObject()
//                    json.put("transactionPassword", num)
//                    json.put("value", deposit_count.text.toString())
//                    var body = RequestBody.create(MediaType.parse("text/html"), json.toString())
//                    RetrofitUtils.client(ApiStore::class.java)
//                            .depositFinancial(MyApplication.getTOKEN(), body, id)
//                            .compose(RxHelper.rxSchedulerHelper())
//                            .subscribe({ date ->
//                                if (date.code === 200) {
//                                    dialogHelper.resetDialogResource(this@FinancialDepositActivity, R.drawable.success_icon, "存入成功")
//                                    KeyboardUtils.hideSoftInput(mPopView.contentView.findViewById<PswText>(R.id.pay_text))
//                                } else {
//                                    dialogHelper.resetDialogResource(this@FinancialDepositActivity, R.drawable.miss_icon, date.message)
//                                }
//                                dialogHelper.dismissDelayed { null }
//                            }, {
//                                dialogHelper.resetDialogResource(this@FinancialDepositActivity, R.drawable.miss_icon, it.message)
//                                dialogHelper.dismissDelayed { null }
//                            })
                }
        mPopView.contentView.post { KeyboardUtils.showSoftInput(mPopView.contentView.findViewById<View>(R.id.pay_text)) }
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