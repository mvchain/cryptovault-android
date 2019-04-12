package com.mvc.ttpay_android.activity

import android.content.Intent
import android.support.v4.content.ContextCompat
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import android.view.inputmethod.EditorInfo
import com.blankj.utilcode.util.KeyboardUtils
import com.gyf.barlibrary.ImmersionBar
import com.mvc.ttpay_android.R
import com.mvc.ttpay_android.adapter.rvAdapter.BlockBrowserListAdapter
import com.mvc.ttpay_android.adapter.rvAdapter.BlockListAdapter
import com.mvc.ttpay_android.adapter.rvAdapter.BlockNodeAdapter
import com.mvc.ttpay_android.base.BaseMVPActivity
import com.mvc.ttpay_android.base.BasePresenter
import com.mvc.ttpay_android.bean.BlockBrowserDataBean
import com.mvc.ttpay_android.bean.BlockListBean
import com.mvc.ttpay_android.bean.BlockNodeBean
import com.mvc.ttpay_android.bean.BlockTransactionBean
import com.mvc.ttpay_android.contract.IBrowserContract
import com.mvc.ttpay_android.presenter.BrowserPresenter
import kotlinx.android.synthetic.main.activity_blockchain.*
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class BlockchainBrowserActivity : BaseMVPActivity<IBrowserContract.IBrowserPresenter>(), IBrowserContract.IBrowserView {
    private lateinit var listAdapter: BlockListAdapter
    private lateinit var transactionAdapter: BlockBrowserListAdapter
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
    }

    override fun blockSuccess(blockBrowserDataBean: BlockBrowserDataBean) {
        browser_swipe.isRefreshing = false
        var nLastBean = blockBrowserDataBean.blockLastBean
        var nListBean = blockBrowserDataBean.blockListBean
        var nTransactionBean = blockBrowserDataBean.blockTransactionBean
        var nNodeBean = blockBrowserDataBean.blockNodeBean
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
        if(nNodeBean!=null){
            nodeBean.clear()
            nodeBean.addAll(nNodeBean)
            nodeAdapter.notifyDataSetChanged()
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
//        mPresenter.getBlockNode()
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
        listAdapter.setOnItemClickListener { adapter, view, position ->
            when (view.id) {
                R.id.layout -> {
                    var intent = Intent(this@BlockchainBrowserActivity, BlockchainDetailActivity::class.java)
                    intent.putExtra("blockId", "${listBean[position].blockId}")
                    startActivity(intent)
                }
            }
        }
        transactionAdapter = BlockBrowserListAdapter(R.layout.item_browser_block_list, transactionBean)
        nodeAdapter = BlockNodeAdapter(R.layout.item_block_node, nodeBean)
        browser_block_rv.adapter = listAdapter
        browser_transfer_rv.adapter = transactionAdapter
        browser_node_rv.adapter = nodeAdapter
        browser_block_rv.layoutManager = object : LinearLayoutManager(this) {
            override fun canScrollVertically(): Boolean {
                return false
            }
        }
        browser_transfer_rv.layoutManager = object : LinearLayoutManager(this) {
            override fun canScrollVertically(): Boolean {
                return false
            }
        }
        browser_node_rv.layoutManager = object : LinearLayoutManager(this) {
            override fun canScrollVertically(): Boolean {
                return false
            }
        }
        browser_swipe.setOnRefreshListener { onRefresh() }
        browser_swipe.isRefreshing = true
        transactionAdapter.setOnItemChildClickListener { adapter, view, position ->
            when (view.id) {
                R.id.layout -> {
                    var hash = transactionBean[position].hash
                    var intent = Intent(this@BlockchainBrowserActivity, BlockTransferDetailActivity::class.java)
                    intent.putExtra("hash", hash)
                    startActivity(intent)
                }
            }
        }
    }

    private fun onRefresh() {
        mPresenter.getBlockBrowserData()
//        mPresenter.getBlockNode()
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
                    browser_edit.setOnEditorActionListener { _, actionId, _ ->
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