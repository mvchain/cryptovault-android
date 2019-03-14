package com.mvc.cryptovault_android.activity

import android.content.Intent
import android.support.v4.content.ContextCompat
import android.view.View
import android.view.inputmethod.EditorInfo
import com.blankj.utilcode.util.KeyboardUtils
import com.gyf.barlibrary.ImmersionBar
import com.mvc.cryptovault_android.R
import com.mvc.cryptovault_android.adapter.rvAdapter.BlockListAdapter
import com.mvc.cryptovault_android.adapter.rvAdapter.BlockNodeAdapter
import com.mvc.cryptovault_android.adapter.rvAdapter.BlockTransactionAdapter
import com.mvc.cryptovault_android.base.BaseMVPActivity
import com.mvc.cryptovault_android.base.BasePresenter
import com.mvc.cryptovault_android.bean.BlockBrowserDataBean
import com.mvc.cryptovault_android.bean.BlockListBean
import com.mvc.cryptovault_android.bean.BlockNodeBean
import com.mvc.cryptovault_android.bean.BlockTransactionBean
import com.mvc.cryptovault_android.contract.IBrowserContract
import com.mvc.cryptovault_android.presenter.BrowserPresenter
import kotlinx.android.synthetic.main.activity_blockchain.*
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class BlockchainBrowserActivity : BaseMVPActivity<IBrowserContract.IBrowserPresenter>(), IBrowserContract.IBrowserView {
    private lateinit var listAdapter: BlockListAdapter
    private lateinit var transactionAdapter: BlockTransactionAdapter
    private lateinit var nodeAdapter: BlockNodeAdapter
    private lateinit var listBean: ArrayList<BlockListBean.DataBean>
    private lateinit var nodeBean: ArrayList<BlockNodeBean>
    private lateinit var transactionBean: ArrayList<BlockTransactionBean.DataBean>
    private var isSearch = false

    override fun addressFailed(msg: String) {
        showToast(msg)
    }

    override fun addressSuccess(address: String) {
        var intent = Intent(this@BlockchainBrowserActivity, PublicKeyActivity::class.java)
        intent.putExtra("publicKey", address)
        startActivity(intent)
    }

    override fun blockNodeSuccess(blockNodeBean: ArrayList<BlockNodeBean>) {
        browser_swipe.isRefreshing = false
        nodeBean.addAll(blockNodeBean)
        nodeAdapter.notifyDataSetChanged()
    }

    override fun blockSuccess(blockBrowserDataBean: BlockBrowserDataBean) {
        browser_swipe.isRefreshing = false
        var nLastBean = blockBrowserDataBean.blockLastBean
        var nListBean = blockBrowserDataBean.blockListBean
        var nTransactionBean = blockBrowserDataBean.blockTransactionBean
        if (nLastBean != null) {
            var lastData = nLastBean.data
            browser_height.text = "${lastData.blockId}"
            browser_number.text = "${lastData.transactionCount}"
            browser_timer.text = "5 s"
            browser_version.text = "${lastData.version}"
            browser_price.text = "${lastData.total}"
            browser_news_time.text = "${SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Date(lastData.serviceTime))}"
        }
        if (nListBean != null) {
            listBean.clear()
            listBean.addAll(nListBean.data)
            listAdapter.notifyDataSetChanged()
        }
        if (nTransactionBean != null) {
            transactionBean.clear()
            transactionBean.addAll(nTransactionBean.data)
            transactionAdapter.notifyDataSetChanged()
        }
    }

    override fun blockFailed(msg: String) {
        browser_swipe.isRefreshing = false
    }

    override fun initPresenter(): BasePresenter<*, *> {
        return BrowserPresenter.newInstance()
    }

    override fun initMVPData() {
        mPresenter.getBlockBrowserData()
        mPresenter.getBlockNode()
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
        listBean = ArrayList()
        nodeBean = ArrayList()
        transactionBean = ArrayList()
        listAdapter = BlockListAdapter(R.layout.item_block_list, listBean)
        transactionAdapter = BlockTransactionAdapter(R.layout.item_block_list, transactionBean)
        nodeAdapter = BlockNodeAdapter(R.layout.item_block_node, nodeBean)
        browser_block_rv.adapter = listAdapter
        browser_transfer_rv.adapter = transactionAdapter
        browser_node_rv.adapter = nodeAdapter
        browser_swipe.setOnRefreshListener { onRefresh() }
        browser_swipe.isRefreshing = true
    }

    private fun onRefresh() {
        mPresenter.getBlockBrowserData()
        mPresenter.getBlockNode()
    }

    override fun startActivity() {

    }

    fun onClick(v: View) {
        when (v.id) {
            R.id.browser_back -> {
                finish()
            }

            R.id.browser_block_menu -> {
                startActivity(Intent(this, BlockchainListActivity::class.java))
            }

            R.id.browser_search -> {
                isSearch = !isSearch
                if (isSearch) {
                    browser_search.setImageDrawable(ContextCompat.getDrawable(baseContext, R.drawable.cancel_icon_black))
                    browser_edit.visibility = View.VISIBLE
                    browser_title.visibility = View.GONE
                    browser_edit.isFocusable = true
                    browser_edit.requestFocus()
                    KeyboardUtils.showSoftInput(this)
                    browser_edit.setOnEditorActionListener { v, actionId, event ->
                        if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                            var searchMsg = browser_edit.text.toString().trim()
                            if (browser_edit.text.toString().trim() == "") {
                                showToast("请输入搜索关键字")
                                false
                            } else {
                                // 搜索功能主体
                                if (isNumberSearch(searchMsg)) {
                                    var intent = Intent(this@BlockchainBrowserActivity, BlockchainDetailActivity::class.java)
                                    intent.putExtra("blockId", searchMsg)
                                    startActivity(intent)
                                } else {
                                    mPresenter.getBlockAddressExist(searchMsg)
                                }
                                true
                            }
                        }
                        false
                    }
                } else {
                    browser_search.setImageDrawable(ContextCompat.getDrawable(baseContext, R.drawable.search_icon_black))
                    browser_edit.visibility = View.GONE
                    browser_title.visibility = View.VISIBLE
//                    在关闭搜索框的时候搜索内容也关闭掉
                    KeyboardUtils.hideSoftInput(this)
                }
            }
        }
    }

    fun isNumberSearch(str: String): Boolean {
        var i = str.length
        while (--i >= 0) {
            if (!Character.isDigit(str[i])) {
                return false
            }
        }
        return true
    }

}