package com.mvc.cryptovault_android.activity

import android.view.View
import com.gyf.barlibrary.ImmersionBar
import com.mvc.cryptovault_android.R
import com.mvc.cryptovault_android.base.BaseMVPActivity
import com.mvc.cryptovault_android.base.BasePresenter
import com.mvc.cryptovault_android.contract.IBrowserContract
import com.mvc.cryptovault_android.presenter.BrowserPresenter

class BlockchainBrowserActivity : BaseMVPActivity<IBrowserContract.IBrowserPresenter>(), IBrowserContract.IBrowserView {
    override fun initPresenter(): BasePresenter<*, *> {
        return BrowserPresenter.newInstance()
    }

    override fun initMVPData() {

    }

    override fun getLayoutId(): Int {
        return R.layout.activity_blockchain
    }

    override fun initData() {

    }

    override fun initView() {

    }

    override fun initMVPView() {
        ImmersionBar.with(this).statusBarView(R.id.status_bar).statusBarDarkFont(true).init()

    }

    override fun startActivity() {

    }

    fun onClick(v: View) {
        when (v.id) {
            R.id.browser_back -> {
                finish()
            }

            R.id.browser_block_menu -> {

            }
        }
    }

}