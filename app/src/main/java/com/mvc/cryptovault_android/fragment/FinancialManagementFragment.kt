package com.mvc.cryptovault_android.fragment

import com.mvc.cryptovault_android.R
import com.mvc.cryptovault_android.base.BaseMVPFragment
import com.mvc.cryptovault_android.base.BasePresenter
import com.mvc.cryptovault_android.bean.FinancialBean
import com.mvc.cryptovault_android.bean.FinancialListBean
import com.mvc.cryptovault_android.contract.FinancialContract
import com.mvc.cryptovault_android.presenter.FinancialPresenter

class FinancialManagementFragment : BaseMVPFragment<FinancialContract.FinancialPresenter>(), FinancialContract.FinancialView {
    override fun showMeFinanciaSuccess(financialBean: FinancialBean.DataBean) {

    }

    override fun showFinanciaListSuccess(financialListBean: List<FinancialListBean.DataBean>) {

    }


    override fun initView() {
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
    }

    override fun showFinanciaListError() {
    }

    override fun showServerError() {
    }
}