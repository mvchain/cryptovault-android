package com.mvc.cryptovault_android.fragment

import com.mvc.cryptovault_android.R
import com.mvc.cryptovault_android.base.BaseMVPFragment
import com.mvc.cryptovault_android.base.BasePresenter
import com.mvc.cryptovault_android.contract.IAreaContract
import com.mvc.cryptovault_android.presenter.AreaPresenter

class TradingAreaFragment :BaseMVPFragment<IAreaContract.AreaPresenter>(),IAreaContract.AreaView {
    override fun initView() {
    }

    override fun getLayoutId(): Int {
        return R.layout.fragment_trading
    }

    override fun initPresenter(): BasePresenter<*, *> {
        return AreaPresenter.newInstance()
    }

    override fun startActivity() {

    }
}