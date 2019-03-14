package com.mvc.cryptovault_android.activity

import com.gyf.barlibrary.ImmersionBar
import com.mvc.cryptovault_android.R
import com.mvc.cryptovault_android.adapter.rvAdapter.BlockTransactionAdapter
import com.mvc.cryptovault_android.base.BaseMVPActivity
import com.mvc.cryptovault_android.base.BasePresenter
import com.mvc.cryptovault_android.bean.BlockListBean
import com.mvc.cryptovault_android.bean.BlockTransactionBean
import com.mvc.cryptovault_android.contract.IBlockDetailContract
import com.mvc.cryptovault_android.presenter.BlockDetailPresenter

class BlockchainDetailActivity : BaseMVPActivity<IBlockDetailContract.IBlockDetailPresenter>(), IBlockDetailContract.IBlockDetailView {
    private lateinit var transactionAdapter: BlockTransactionAdapter
    private lateinit var transactionBean: ArrayList<BlockTransactionBean.DataBean>

    override fun blockListSuccess(blockListBean: List<BlockListBean.DataBean>) {
//
    }

    override fun blockListFailed(msg: String) {
//
    }

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
        ImmersionBar.with(this).statusBarView(R.id.status_bar).statusBarDarkFont(true).init()
        transactionBean = ArrayList()
        transactionAdapter = BlockTransactionAdapter(R.layout.item_block_list, transactionBean)
    }

    override fun startActivity() {
    }
}