package com.mvc.cryptovault_android.activity

import android.content.Intent
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import com.gyf.barlibrary.ImmersionBar
import com.mvc.cryptovault_android.R
import com.mvc.cryptovault_android.adapter.rvAdapter.BlockAssetsAdapter
import com.mvc.cryptovault_android.adapter.rvAdapter.BlockOrderAdapter
import com.mvc.cryptovault_android.base.BaseMVPActivity
import com.mvc.cryptovault_android.base.BasePresenter
import com.mvc.cryptovault_android.bean.BlockBalanceBean
import com.mvc.cryptovault_android.bean.BlockOrderBean
import com.mvc.cryptovault_android.contract.IBlockAssetsContract
import com.mvc.cryptovault_android.presenter.BlockAssetsPresenter
import kotlinx.android.synthetic.main.activity_block_assets.*

class BlockAssetsActivity : BaseMVPActivity<IBlockAssetsContract.BlockAssetsPresenter>(), IBlockAssetsContract.BlockAssetsView {
    private var type = 0
    private lateinit var publicKey: String
    private lateinit var blockOrderAdapter: BlockOrderAdapter
    private lateinit var blockAssetsAdapter: BlockAssetsAdapter
    private lateinit var blockOrderBean: ArrayList<BlockOrderBean.DataBean>
    private lateinit var blockAssetsBean: ArrayList<BlockBalanceBean.DataBean>
    override fun balanceSuccess(blockBalanceBean: ArrayList<BlockBalanceBean.DataBean>) {
        assets_swipe.isRefreshing = false
        blockAssetsBean.addAll(blockBalanceBean)
        blockAssetsAdapter.notifyDataSetChanged()
    }

    override fun orderSuccess(orderBalanceBean: ArrayList<BlockOrderBean.DataBean>) {
        assets_swipe.isRefreshing = false
        blockOrderBean.addAll(orderBalanceBean)
        blockOrderAdapter.notifyDataSetChanged()
    }

    override fun balanceFailed(msg: String) {
        assets_swipe.isRefreshing = false
    }

    override fun orderFailed(msg: String) {
        assets_swipe.isRefreshing = false
    }

    override fun initPresenter(): BasePresenter<*, *> {
        return BlockAssetsPresenter.newInstance()
    }

    override fun initMVPData() {
        if (type == 0) {
            mPresenter.getBlockBalance(publicKey)
            blockAssetsBean.clear()
        } else {
            blockOrderBean.clear()
            mPresenter.getBlockOrder(0, 20, publicKey)
        }
    }

    override fun getLayoutId(): Int {
        return R.layout.activity_block_assets
    }

    override fun initData() {
    }

    override fun initView() {

    }

    override fun initMVPView() {
        ImmersionBar.with(this).statusBarView(R.id.status_bar).statusBarDarkFont(true).init()
        blockOrderBean = ArrayList()
        blockAssetsBean = ArrayList()
        blockOrderAdapter = BlockOrderAdapter(R.layout.item_block_assets, blockOrderBean)
        blockAssetsAdapter = BlockAssetsAdapter(R.layout.item_block_assets, blockAssetsBean)
        type = intent.getIntExtra("type", 0)
        publicKey = intent.getStringExtra("publicKey")
        if (type == 0) {
            assets_type.text = "币种"
            assets_actual.text = "余额"
            assets_title.text = "资产"
            assets_rv.adapter = blockAssetsAdapter
        } else {
            assets_type.text = "交易类型"
            assets_actual.text = "交易时间"
            assets_title.text = "交易记录"
            assets_rv.adapter = blockOrderAdapter
            assets_rv.addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrollStateChanged(recyclerView: RecyclerView?, newState: Int) {
                    if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                        var layoutManager = recyclerView?.layoutManager as LinearLayoutManager
                        var lastVisibleItemPosition = layoutManager.findLastVisibleItemPosition()
                        if (lastVisibleItemPosition + 1 == blockOrderAdapter.itemCount && blockOrderAdapter.itemCount >= 20) {
                            mPresenter.getBlockOrder(blockOrderBean[blockOrderBean.size].id, 20, publicKey)
                        }
                    }
                }
            })
            blockOrderAdapter.setOnItemChildClickListener { adapter, view, position ->
                when (view.id) {
                    R.id.layout -> {
                        var id = blockOrderBean[position].id
                        var intent = Intent(this@BlockAssetsActivity, BlockAssetsDetailActivity::class.java)
                        intent.putExtra("id", id)
                        startActivity(intent)
                    }
                }
            }
        }
        assets_swipe.isRefreshing = true
        assets_swipe.setOnRefreshListener { onRefresh() }
        assets_back.setOnClickListener { finish() }
    }

    private fun onRefresh() {
        if (type == 0) {
            blockAssetsBean.clear()
            mPresenter.getBlockBalance(publicKey)
        } else {
            blockOrderBean.clear()
            mPresenter.getBlockOrder(0, 20, publicKey)
        }
    }

    override fun startActivity() {
    }
}