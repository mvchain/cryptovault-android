package com.mvc.cryptovault_android.activity

import android.content.Intent
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import com.blankj.utilcode.util.LogUtils
import com.gyf.barlibrary.ImmersionBar
import com.mvc.cryptovault_android.R
import com.mvc.cryptovault_android.adapter.rvAdapter.BlockTransactionAdapter
import com.mvc.cryptovault_android.base.BaseMVPActivity
import com.mvc.cryptovault_android.base.BasePresenter
import com.mvc.cryptovault_android.bean.BlockDetailBean
import com.mvc.cryptovault_android.bean.BlockListBean
import com.mvc.cryptovault_android.bean.BlockTransactionBean
import com.mvc.cryptovault_android.contract.IBlockDetailContract
import com.mvc.cryptovault_android.presenter.BlockDetailPresenter
import kotlinx.android.synthetic.main.activity_block_detail.*
import java.text.SimpleDateFormat
import java.util.*

class BlockchainDetailActivity : BaseMVPActivity<IBlockDetailContract.IBlockDetailPresenter>(), IBlockDetailContract.IBlockDetailView {
    override fun blockDetailSuccess(blockListBean: BlockDetailBean.DataBean) {
        swipe.isRefreshing = false
        current_block.text = "${blockListBean.blockId}"
        block_number.text = "${blockListBean.transactions}"
        block_time.text = "${SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Date(blockListBean.createdAt))}"
    }

    override fun blockDetailFailed(msg: String) {
    }

    override fun blockAllListSuccess(blockListBean: List<BlockTransactionBean.DataBean>) {
        swipe.isRefreshing = false
        transactionBean.addAll(blockListBean)
        transactionAdapter.notifyDataSetChanged()
    }

    override fun blockAllListFailed(msg: String) {

    }

    override fun blockListSuccess(blockListBean: List<BlockListBean.DataBean>) {

    }

    private lateinit var transactionAdapter: BlockTransactionAdapter
    private lateinit var transactionBean: ArrayList<BlockTransactionBean.DataBean>
    private lateinit var blockId: String

    override fun blockListFailed(msg: String) {
//
    }

    override fun initPresenter(): BasePresenter<*, *> {
        return BlockDetailPresenter.newInstance()
    }

    override fun initMVPData() {
        mPresenter.getBlockAllList(blockId, 20, 0)
        mPresenter.getBlockDetail(blockId)
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
        browser_transfer_rv.adapter = transactionAdapter
        browser_transfer_rv.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView?, newState: Int) {
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    var layoutManager = recyclerView?.layoutManager as LinearLayoutManager
                    var lastVisibleItemPosition = layoutManager?.findLastVisibleItemPosition()
                    if (lastVisibleItemPosition + 1 == transactionAdapter.itemCount && transactionAdapter.itemCount >= 20) {
                        mPresenter.getBlockAllList(blockId, 20, transactionBean[transactionBean.size - 1].transactionId)
                    }
                }
            }
        })
        transactionAdapter.setOnItemChildClickListener { adapter, view, position ->
            when (view.id) {
                R.id.layout -> {
                    var intent = Intent(this, PublicKeyActivity::class.java)
                    intent.putExtra("publicKey", transactionBean[position].hash)
                    startActivity(intent)
                }
            }
        }

        browser_header.attachTo(browser_transfer_rv)
        blockId = intent.getStringExtra("blockId")
        browser_back.setOnClickListener { finish() }
        swipe.setOnRefreshListener { onRefresh() }
        swipe.isRefreshing = true
    }

    private fun onRefresh() {
        transactionBean.clear()
        mPresenter.getBlockAllList(blockId, 20, 0)
        mPresenter.getBlockDetail(blockId)
    }

    override fun startActivity() {

    }
}