package com.mvc.cryptovault_android.activity

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.View
import com.blankj.utilcode.util.LogUtils
import com.gyf.barlibrary.ImmersionBar
import com.mvc.cryptovault_android.R
import com.mvc.cryptovault_android.base.BaseMVPActivity
import com.mvc.cryptovault_android.base.BasePresenter
import com.mvc.cryptovault_android.bean.FinancialDetailBean
import com.mvc.cryptovault_android.contract.IFinancialDetailContract
import com.mvc.cryptovault_android.event.FinancialDetailEvent
import com.mvc.cryptovault_android.presenter.FinancialDetailPresenter
import com.mvc.cryptovault_android.utils.TextUtils
import kotlinx.android.synthetic.main.activity_financial_detail.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe

class FinancialDetailActivity : BaseMVPActivity<IFinancialDetailContract.FinancialDetailPresenter>(), IFinancialDetailContract.FinancialDetailfoView {
    private lateinit var detail: FinancialDetailBean.DataBean
    private lateinit var baseName: String
    //    private var detailId = -1
    override fun startActivity() {

    }

    override fun showError(error: String) {
        LogUtils.e(error)
    }

    @SuppressLint("SetTextI18n")
    override fun showSuccess(bean: FinancialDetailBean.DataBean) {
        financial_title.text = bean.name
        financia_income.text = "${TextUtils.doubleToDouble(bean.incomeMin)}-${TextUtils.doubleToDouble(bean.incomeMax)} %"
        time_cycle.text = "需累计签到${bean.times}天"
        starting.text = "${TextUtils.doubleToEight(bean.minValue)} ${bean.baseTokenName}"
        content.text = bean.content
        rule.text = bean.rule
        detail = bean
        deposit.isEnabled = true
        deposit.setBackgroundResource(R.drawable.bg_login_submit)
        remaining_amount_progress.max = bean.limitValue.toInt()
        remaining_amount_progress.progress = (bean.limitValue - bean.sold).toInt()
        remaining_amount.text = "剩余总额度 ${TextUtils.doubleToFourPrice(bean.limitValue - bean.sold)} ${bean.baseTokenName}"
    }

    private var id = 0
    override fun initPresenter(): BasePresenter<*, *> {
        return FinancialDetailPresenter.newIntance()
    }

    override fun initMVPData() {
        mPresenter.getFinancialDetail(id)
    }

    override fun getLayoutId(): Int {
        return R.layout.activity_financial_detail
    }

    override fun initView() {
    }

    override fun initMVPView() {
        ImmersionBar.with(this).titleBar(R.id.status_bar).statusBarDarkFont(true).init()
        id = intent.getIntExtra("id", 0)
    }

    override fun initData() {

    }

    fun onClick(v: View) {
        when (v.id) {
            R.id.back -> {
                finish()
            }
            R.id.deposit -> {
                if (detail == null) {
                    return
                } else {
                    var idIntent = Intent(this, FinancialDepositActivity::class.java)
                    idIntent.putExtra("detail", detail)
                    startActivity(idIntent)
                }
            }
        }
    }

    @Subscribe
    fun refresh(detailEvent: FinancialDetailEvent) {
        mPresenter.getFinancialDetail(id)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        EventBus.getDefault().register(this)
    }

    override fun onDestroy() {
        EventBus.getDefault().unregister(this)
        super.onDestroy()
    }
}