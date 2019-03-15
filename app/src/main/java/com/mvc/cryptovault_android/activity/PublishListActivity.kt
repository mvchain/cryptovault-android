package com.mvc.cryptovault_android.activity

import android.annotation.SuppressLint
import android.content.Intent
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import com.blankj.utilcode.util.LogUtils
import com.gyf.barlibrary.ImmersionBar
import com.mvc.cryptovault_android.MyApplication
import com.mvc.cryptovault_android.R
import com.mvc.cryptovault_android.adapter.rvAdapter.PublishAllAdapter
import com.mvc.cryptovault_android.api.ApiStore
import com.mvc.cryptovault_android.base.BaseActivity
import com.mvc.cryptovault_android.bean.PublishBean
import com.mvc.cryptovault_android.utils.RetrofitUtils
import com.mvc.cryptovault_android.utils.RxHelper
import kotlinx.android.synthetic.main.activity_publish_list.*
import java.util.ArrayList

class PublishListActivity : BaseActivity() {
    private lateinit var publishAdapter: PublishAllAdapter
    private lateinit var publishBean: ArrayList<PublishBean.DataBean>

    override fun getLayoutId(): Int {
        return R.layout.activity_publish_list
    }

    override fun initData() {
        publishBean.clear()
        loadPublishList(15, 0)
    }

    override fun initView() {
        ImmersionBar.with(this).statusBarView(R.id.status_bar).statusBarDarkFont(true).init()
        publishBean = ArrayList()
        publishAdapter = PublishAllAdapter(R.layout.layout_publish_all, publishBean)
        publish_rv.adapter = publishAdapter
        publish_rv.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView?, newState: Int) {
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    var layoutManager = recyclerView?.layoutManager as LinearLayoutManager
                    var lastVisibleItemPosition = layoutManager?.findLastVisibleItemPosition()
                    if (lastVisibleItemPosition + 1 == publishAdapter.itemCount && publishAdapter.itemCount >= 15) {
                        loadPublishList(15, publishBean[publishBean.size - 1].projectId)
                    }
                }
            }
        })
        publishAdapter.setOnItemChildClickListener { adapter, view, position ->
            when (view.id) {
                R.id.publish_child_layout -> {
                    var intent = Intent(this@PublishListActivity, PublishDetailActivity::class.java)
                    intent.putExtra("projectId", publishBean[position].projectId)
                    startActivity(intent)
                }
            }
        }
        publish_swipe.setOnRefreshListener { refresh() }
        publish_back.setOnClickListener { finish() }
    }

    private fun refresh() {
        publishBean.clear()
        loadPublishList(15, 0)
    }

    @SuppressLint("CheckResult")
    private fun loadPublishList(pageSize: Int, projectId: Int) {
        RetrofitUtils.client(MyApplication.getBaseUrl(),ApiStore::class.java).getPublishAll(MyApplication.getTOKEN(), pageSize, projectId)
                .compose(RxHelper.rxSchedulerHelper())
                .subscribe({ publish ->
                    publish_swipe.isRefreshing = false
                    if (publish.code === 200) {
                        publish_rv.visibility = View.VISIBLE
                        publish_null.visibility = View.INVISIBLE
                        publishBean.addAll(publish.data)
                        publishAdapter.notifyDataSetChanged()
                    } else {
                        publish_rv.visibility = View.INVISIBLE
                        publish_null.visibility = View.VISIBLE
                    }
                }, { throwable ->
                    LogUtils.e(throwable.message)
                })

    }
}