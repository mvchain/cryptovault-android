package com.mvc.cryptovault_android.fragment

import android.os.Bundle
import android.view.View
import com.mvc.cryptovault_android.R
import com.mvc.cryptovault_android.adapter.rvAdapter.FinanciaAdapter
import com.mvc.cryptovault_android.base.BaseMVPFragment
import com.mvc.cryptovault_android.base.BasePresenter
import com.mvc.cryptovault_android.bean.FinancialBean
import com.mvc.cryptovault_android.bean.FinancialListBean
import com.mvc.cryptovault_android.contract.FinancialContract
import com.mvc.cryptovault_android.presenter.FinancialPresenter
import kotlinx.android.synthetic.main.fragment_financial.view.*
import java.util.ArrayList

class FinancialManagementFragment : BaseMVPFragment<FinancialContract.FinancialPresenter>(), FinancialContract.FinancialView {
    private lateinit var mFinaList: ArrayList<FinancialListBean.DataBean>
    private lateinit var mFinaAdapter: FinanciaAdapter
    override fun showMeFinanciaSuccess(financialBean: FinancialBean.DataBean) {
        rootView.refresh.post { rootView.refresh.isRefreshing = false }

    }

    override fun showFinanciaListSuccess(financialListBean: List<FinancialListBean.DataBean>) {
        rootView.refresh.post { rootView.refresh.isRefreshing = false }
        if (mFinaList.size > 0) {
            mFinaList.addAll(financialListBean)
            mFinaAdapter.notifyDataSetChanged()
        }
    }

    override fun initView() {
        mFinaList = ArrayList()
        mFinaAdapter = FinanciaAdapter(R.layout.item_financia, mFinaList)
        rootView.financial_recyclerview.adapter = mFinaAdapter
        rootView.refresh.post { rootView.refresh.isRefreshing = true }
        rootView.refresh.setOnRefreshListener{ refresh() }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        rootView.refresh.post { rootView.refresh.isRefreshing = false }
    }
    override fun initData() {
        super.initData()
        mPresenter.getFinancialBalance()
        mPresenter.getFinancialList()
    }
    fun refresh(){
        mPresenter.getFinancialBalance()
        mPresenter.getFinancialList()
    }
    override fun getLayoutId(): Int {
        return R.layout.fragment_financial
    }

    override fun initPresenter(): BasePresenter<*, *> {
        return FinancialPresenter.newIntance()
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