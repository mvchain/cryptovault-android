package com.mvc.cryptovault_android.fragment

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import com.blankj.utilcode.util.SPUtils
import com.blankj.utilcode.util.SpanUtils
import com.mvc.cryptovault_android.R
import com.mvc.cryptovault_android.activity.FinancialDetailActivity
import com.mvc.cryptovault_android.activity.MyOptionActivity
import com.mvc.cryptovault_android.adapter.rvAdapter.FinanciaAdapter
import com.mvc.cryptovault_android.base.BaseMVPFragment
import com.mvc.cryptovault_android.base.BasePresenter
import com.mvc.cryptovault_android.bean.ExchangeRateBean
import com.mvc.cryptovault_android.bean.FinancialBean
import com.mvc.cryptovault_android.bean.FinancialListBean
import com.mvc.cryptovault_android.common.Constant.SP.DEFAULT_RATE
import com.mvc.cryptovault_android.common.Constant.SP.SET_RATE
import com.mvc.cryptovault_android.contract.IFinancialContract
import com.mvc.cryptovault_android.presenter.FinancialPresenter
import com.mvc.cryptovault_android.utils.JsonHelper
import com.mvc.cryptovault_android.utils.TextUtils
import kotlinx.android.synthetic.main.fragment_financial.view.*
import java.util.ArrayList

class FinancialManagementFragment : BaseMVPFragment<IFinancialContract.FinancialPresenter>(), IFinancialContract.FinancialView {
    private lateinit var mFinaList: ArrayList<FinancialListBean.DataBean>
    private lateinit var mFinaAdapter: FinanciaAdapter
    private var isRefresh = false
    private var createCarryOut = false
    @SuppressLint("SetTextI18n")
    override fun showMeFinanciaSuccess(financialBean: FinancialBean.DataBean) {
        var set_rate = SPUtils.getInstance().getString(SET_RATE)
        val setBean = JsonHelper.stringToJson(set_rate, ExchangeRateBean.DataBean::class.java) as ExchangeRateBean.DataBean
        var defaultRate = setBean.name.split(" ")[1]
        rootView.refresh.post { rootView.refresh.isRefreshing = false }
        SPUtils.getInstance().getString(DEFAULT_RATE)
        var incomeDouble = TextUtils.rateToPrice(financialBean.balance.toDouble())
        rootView.me_financia.text = SpanUtils().append("$incomeDouble ").setFontSize(34, true)
                .append(defaultRate)
                .setFontSize(10, true).create()
        rootView.yesterday_earnings.text = "${TextUtils.rateToPrice(financialBean.lastIncome.toDouble())} $defaultRate"
        rootView.all_earnings.text = "${TextUtils.rateToPrice(financialBean.income.toDouble())} $defaultRate"
        rootView.my_option.setOnClickListener {
            startActivity(Intent(activity, MyOptionActivity::class.java))
        }
    }

    override fun showFinanciaListSuccess(financialListBean: List<FinancialListBean.DataBean>) {
        rootView.refresh.post { rootView.refresh.isRefreshing = false }
        if (isRefresh) {
            isRefresh = false
            mFinaList.clear()
        }
        mFinaList.addAll(financialListBean)
        mFinaAdapter.notifyDataSetChanged()
    }

    override fun initView() {
        createCarryOut = true
        mFinaList = ArrayList()
        mFinaAdapter = FinanciaAdapter(R.layout.item_financia, mFinaList)
        rootView.financial_recyclerview.adapter = mFinaAdapter
        rootView.refresh.post { rootView.refresh.isRefreshing = true }
        rootView.refresh.setOnRefreshListener { refresh() }
        mFinaAdapter.setOnItemChildClickListener { adapter, view, position ->
            when (view.id) {
                R.id.financial_layout -> {
                    var id = mFinaList[position].id
                    var idIntent = Intent(activity, FinancialDetailActivity::class.java)
                    idIntent.putExtra("id", id)
                    startActivity(idIntent)
                }
            }
        }
        rootView.financial_recyclerview.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView?, newState: Int) {
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    val layoutManager = recyclerView!!.layoutManager as LinearLayoutManager
                    val lastVisibleItemPosition = layoutManager.findLastVisibleItemPosition()
                    if (lastVisibleItemPosition + 1 == mFinaAdapter.itemCount && mFinaAdapter.itemCount >= 10 && !isRefresh) {
                        mPresenter.getFinancialList(mFinaList[mFinaList.size - 1].id, 10)
                    }
                }
            }

            override fun onScrolled(recyclerView: RecyclerView?, dx: Int, dy: Int) {

            }
        })
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        rootView.refresh.post { rootView.refresh.isRefreshing = false }
    }

    override fun initData() {
        super.initData()
        isRefresh = true
        mPresenter.getFinancialBalance()
        mPresenter.getFinancialList(0, 10)
    }

    fun refresh() {
        isRefresh = true
        mPresenter.getFinancialBalance()
        mPresenter.getFinancialList(0, 10)
    }

    override fun getLayoutId(): Int {
        return R.layout.fragment_financial
    }

    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
        if (isVisibleToUser && createCarryOut) {
            refresh()
        }
    }

    override fun initPresenter(): BasePresenter<*, *> {
        return FinancialPresenter.newInstance()
    }

    override fun startActivity() {

    }

    override fun showMeFinanciaError() {
        rootView.refresh.post { rootView.refresh.isRefreshing = false }
    }

    override fun showFinanciaListError() {
        rootView.refresh.post { rootView.refresh.isRefreshing = false }
    }

    override fun showServerError() {
        rootView.refresh.post { rootView.refresh.isRefreshing = false }
    }
}