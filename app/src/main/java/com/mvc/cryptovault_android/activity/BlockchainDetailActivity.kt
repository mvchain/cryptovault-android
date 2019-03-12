package com.mvc.cryptovault_android.activity

import com.mvc.cryptovault_android.R
import com.mvc.cryptovault_android.base.BaseMVPActivity
import com.mvc.cryptovault_android.base.BasePresenter
import com.mvc.cryptovault_android.contract.IBlockDetailContract
import com.mvc.cryptovault_android.presenter.BlockDetailPresenter

class BlockchainDetailActivity : BaseMVPActivity<IBlockDetailContract.IBlockDetailPresenter>(), IBlockDetailContract.IBlockDetailView {
    override fun initPresenter(): BasePresenter<*, *> {
        return BlockDetailPresenter.newInstance()
    }

    override fun initMVPData() {
    }

    override fun getLayoutId(): Int {
        return R.layout.activity_block_detail
    }

    override fun initData() {
    }

    override fun initView() {
    }

    override fun initMVPView() {
    }

    override fun startActivity() {
    }
}