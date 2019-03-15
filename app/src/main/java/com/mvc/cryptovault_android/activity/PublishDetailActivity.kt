package com.mvc.cryptovault_android.activity

import android.annotation.SuppressLint
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import com.blankj.utilcode.util.LogUtils
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.gyf.barlibrary.ImmersionBar
import com.mvc.cryptovault_android.MyApplication
import com.mvc.cryptovault_android.R
import com.mvc.cryptovault_android.adapter.rvAdapter.PublishDetailAdapter
import com.mvc.cryptovault_android.api.ApiStore
import com.mvc.cryptovault_android.base.BaseActivity
import com.mvc.cryptovault_android.bean.PublishDetailListBean
import com.mvc.cryptovault_android.utils.RetrofitUtils
import com.mvc.cryptovault_android.utils.RxHelper
import com.mvc.cryptovault_android.utils.TextUtils
import com.mvc.cryptovault_android.view.RuleRecyclerLines
import kotlinx.android.synthetic.main.activity_publish_detail.*
import java.text.SimpleDateFormat
import java.util.ArrayList

class PublishDetailActivity : BaseActivity() {
    override fun getLayoutId(): Int {
        return R.layout.activity_publish_detail
    }

    override fun initData() {
    }

    private lateinit var detailList: ArrayList<PublishDetailListBean.DataBean>
    private lateinit var publishDetailAdapter: PublishDetailAdapter
    private var projectId = 0
    override fun initView() {
        projectId = intent.getIntExtra("projectId", 0)
        ImmersionBar.with(this).statusBarView(R.id.status_bar).statusBarDarkFont(true).init()
        detailList = ArrayList()
        publishDetailAdapter = PublishDetailAdapter(R.layout.layout_publish_detail_list, detailList)
        publish_detail_rv.adapter = publishDetailAdapter
//        publish_detail_rv.layoutManager = object : LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false) {
//            override fun canScrollVertically(): Boolean {
//                return true
//            }
//        }
        publish_detail_rv.addItemDecoration(RuleRecyclerLines(this, RuleRecyclerLines.HORIZONTAL_LIST, 1))
        publish_detail_rv.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView?, newState: Int) {
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    val layoutManager = recyclerView!!.layoutManager as LinearLayoutManager
                    val lastVisibleItemPosition = layoutManager.findLastVisibleItemPosition()
                    if (lastVisibleItemPosition + 1 == publishDetailAdapter.itemCount && publishDetailAdapter.itemCount >= 20) {
                        loadPublishDetailList(projectId, detailList[detailList.size - 1].id, 10)
                    }
                }
            }

            override fun onScrolled(recyclerView: RecyclerView?, dx: Int, dy: Int) {}
        })
        publish_detail_back.setOnClickListener { finish() }
        loadPublishDetail()
        loadPublishDetailList(projectId, 0, 20)
    }

    @SuppressLint("CheckResult")
    private fun loadPublishDetailList(projectId: Int, id: Int, pageSize: Int) {
        RetrofitUtils.client(MyApplication.getBaseUrl(),ApiStore::class.java).getPublishList(MyApplication.getTOKEN(), projectId, id, pageSize)
                .compose(RxHelper.rxSchedulerHelper())
                .subscribe({ publishList ->
                    if (publishList.code === 200) {
                        detailList.addAll(publishList.data)
                        if (detailList.size > 0) {
                            publish_detail_rv.visibility = View.VISIBLE
                            publish_detail_null.visibility = View.INVISIBLE
                            publishDetailAdapter.notifyDataSetChanged()
                        } else {
                            publish_detail_rv.visibility = View.INVISIBLE
                            publish_detail_null.visibility = View.VISIBLE
                        }
                    }
                }, { throwable ->
                    publish_detail_rv.visibility = View.INVISIBLE
                    publish_detail_null.visibility = View.VISIBLE
                    LogUtils.e(throwable.message)
                })
    }

    @SuppressLint("CheckResult")
    private fun loadPublishDetail() {
        RetrofitUtils.client(MyApplication.getBaseUrl(),ApiStore::class.java).getPublishDetail(MyApplication.getTOKEN(), projectId)
                .compose(RxHelper.rxSchedulerHelper())
                .subscribe({ publish ->
                    if (publish.code === 200) {
                        var bean = publish.data
                        publish_detail_toobar.text = "${bean.projectName}-参与详情"
                        publish_detail_title.text = bean.projectName
                        publish_detail_recevie_type.text = "每日释放比例${TextUtils.doubleToDouble(bean.releaseValue)}%"
                        publish_detail_pay.text = "${TextUtils.doubleToDouble(bean.successPayed)}/${TextUtils.doubleToDouble(bean.payed)} ${bean.baseTokenName}"
                        publish_detail_success.text = "${TextUtils.doubleToDouble(bean.successValue)}/${TextUtils.doubleToDouble(bean.value)} ${bean.baseTokenName}"
                        publish_detail_jg.text = "1 ${bean.tokenName} = ${bean.ratio} ${bean.baseTokenName}"
                        publish_detail_time.text = "${SimpleDateFormat("yyyy-MM-dd HH : mm").format(bean.startedAt)}"
                        val options = RequestOptions().fallback(R.drawable.default_project).placeholder(R.drawable.loading_img).error(R.drawable.default_project)
                        Glide.with(this@PublishDetailActivity).load(bean.projectImage).apply(options).into(publish_detail_icon)
                    }
                }, { throwable ->
                    publish_detail_rv.visibility = View.INVISIBLE
                    publish_detail_null.visibility = View.VISIBLE
                    LogUtils.e(throwable.message)
                })
    }
}