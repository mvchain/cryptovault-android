package com.mvc.cryptovault_android.activity

import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import com.gyf.barlibrary.ImmersionBar
import com.mvc.cryptovault_android.R
import com.mvc.cryptovault_android.adapter.rvAdapter.BlockListAdapter
import com.mvc.cryptovault_android.base.BaseMVPActivity
import com.mvc.cryptovault_android.base.BasePresenter
import com.mvc.cryptovault_android.bean.BlockListBean
import com.mvc.cryptovault_android.contract.IBlockDetailContract
import com.mvc.cryptovault_android.presenter.BlockDetailPresenter
import kotlinx.android.synthetic.main.activity_block_list.*

class BlockchainListActivity : BaseMVPActivity<IBlockDetailContract.IBlockDetailPresenter>(), IBlockDetailContract.IBlockDetailView {
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
        block_rv.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView?, newState: Int) {
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    var layoutManager = recyclerView?.layoutManager as LinearLayoutManager
                    var lastVisibleItemPosition = layoutManager?.findLastVisibleItemPosition()
                    if (lastVisibleItemPosition + 1 == listAdapter.itemCount && listAdapter.itemCount >= 20) {
                        mPresenter.getBlockList(listBean[listBean.size - 1].blockId, 20)
                    }
                }
            }
        })
        block_back.setOnClickListener { finish() }
    }

    private fun onRefresh() {
        listBean.clear()
        mPresenter.getBlockList(0, 20)
    }

    override fun startActivity() {

    }

    override fun blockListFailed(msg: String) {

    }

    override fun blockListSuccess(blockListBean: List<BlockListBean.DataBean>) {
        listBean.addAll(blockListBean)
        listAdapter.notifyDataSetChanged()
    }
}