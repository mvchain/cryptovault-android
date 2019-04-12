package com.mvc.ttpay_android.activity

import android.content.Intent
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import com.gyf.barlibrary.ImmersionBar
import com.mvc.ttpay_android.R
import com.mvc.ttpay_android.adapter.rvAdapter.BlockListAdapter
import com.mvc.ttpay_android.base.BaseMVPActivity
import com.mvc.ttpay_android.base.BasePresenter
import com.mvc.ttpay_android.bean.BlockDetailBean
import com.mvc.ttpay_android.bean.BlockListBean
import com.mvc.ttpay_android.bean.BlockTransactionBean
import com.mvc.ttpay_android.contract.IBlockDetailContract
import com.mvc.ttpay_android.presenter.BlockDetailPresenter
import kotlinx.android.synthetic.main.activity_block_list.*

class BlockchainListActivity : BaseMVPActivity<IBlockDetailContract.IBlockDetailPresenter>(), IBlockDetailContract.IBlockDetailView {
    private var isRefresh = false

    override fun blockDetailSuccess(blockListBean: BlockDetailBean.DataBean) {

    }

    override fun blockDetailFailed(msg: String) {
    }

    override fun blockAllListSuccess(blockListBean: List<BlockTransactionBean.DataBean>) {

    }

    override fun blockAllListFailed(msg: String) {
    }

    private lateinit var listAdapter: BlockListAdapter
    private lateinit var listBean: ArrayList<BlockListBean.DataBean>

    override fun initPresenter(): BasePresenter<*, *> {
        return BlockDetailPresenter.newInstance()
    }

    override fun initMVPData() {
        mPresenter.getBlockList(0, 20)
    }

    override fun getLayoutId(): Int {
        return R.layout.activity_block_list
    }

    override fun initData() {

    }

    override fun initView() {

    }

    override fun initMVPView() {
        ImmersionBar.with(this).statusBarView(R.id.status_bar).statusBarDarkFont(true).init()
        listBean = ArrayList()
        listAdapter = BlockListAdapter(R.layout.item_block_list, listBean)
        block_rv.adapter = listAdapter
        swipe.setOnRefreshListener { onRefresh() }
        swipe.isRefreshing = true
        block_rv.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView?, newState: Int) {
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    var layoutManager = recyclerView?.layoutManager as LinearLayoutManager
                    var lastVisibleItemPosition = layoutManager?.findLastVisibleItemPosition()
                    if (lastVisibleItemPosition + 1 == listAdapter.itemCount && listAdapter.itemCount >= 20 && !isRefresh) {
                        mPresenter.getBlockList(listBean[listBean.size - 1].blockId, 20)
                    }
                }
            }
        })
        listAdapter.setOnItemClickListener { adapter, view, position ->
            when (view.id) {
                R.id.layout -> {
                    var intent = Intent(this, BlockchainDetailActivity::class.java)
                    intent.putExtra("blockId", "${listBean[position].blockId}")
                    startActivity(intent)
                }
            }
        }
        block_back.setOnClickListener { finish() }
    }

    private fun onRefresh() {
        isRefresh = true
        mPresenter.getBlockList(0, 20)
    }

    override fun startActivity() {

    }

    override fun blockListFailed(msg: String) {
        if (isRefresh) {
            isRefresh = false
        }
        swipe.isRefreshing = false
    }

    override fun blockListSuccess(blockListBean: List<BlockListBean.DataBean>) {
        if (isRefresh) {
            isRefresh = false
            listBean.clear()
        }
        swipe.isRefreshing = false
        listBean.addAll(blockListBean)
        listAdapter.notifyDataSetChanged()
    }
}