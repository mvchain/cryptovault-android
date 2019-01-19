package com.mvc.cryptovault_android.activity

import android.content.Intent
import android.view.View
import com.gyf.barlibrary.ImmersionBar
import com.mvc.cryptovault_android.R
import com.mvc.cryptovault_android.base.BaseMVPActivity
import com.mvc.cryptovault_android.base.BasePresenter
import com.mvc.cryptovault_android.bean.FinancialDetailBean
import com.mvc.cryptovault_android.contract.FinancialDetailContract
import com.mvc.cryptovault_android.presenter.FinancialDetailPresenter
import kotlinx.android.synthetic.main.activity_financial_detail.*

class FinancialDetailActivity : BaseMVPActivity<FinancialDetailContract.FinancialDetailPresenter>(), FinancialDetailContract.FinancialDetailfoView {
    private var detailId = -1
    private lateinit var baseName: String
    //    private var detailId = -1
    override fun startActivity() {

    }

    override fun showError(error: String) {

    }

    override fun showSuccess(bean: FinancialDetailBean.DataBean) {
        financial_title.text = bean.name
        financia_income.text = "${bean.incomeMin}-${bean.incomeMax} %"
        time_cycle.text = "需累计签到${bean.times}天"
        starting.text = "${bean.minValue} ${bean.baseTokenName}"
        content.text = bean.content
        rule.text = bean.rule
        detailId = bean.id
        baseName = bean.baseTokenName
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
                if (detailId === -1) {
                    return
                } else {
                    var idIntent = Intent(this, FinancialDepositActivity::class.java)
                    idIntent.putExtra("id", detailId)
                    idIntent.putExtra("baseName", baseName)
//                    idIntent.putExtra("id", detailId)
                    startActivity(idIntent)
                }
            }
        }
    }
}