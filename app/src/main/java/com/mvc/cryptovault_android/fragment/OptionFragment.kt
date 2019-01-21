package com.mvc.cryptovault_android.fragment

import android.os.Bundle
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import com.mvc.cryptovault_android.R
import com.mvc.cryptovault_android.adapter.rvAdapter.OptionAdapter
import com.mvc.cryptovault_android.base.BaseMVPFragment
import com.mvc.cryptovault_android.base.BasePresenter
import com.mvc.cryptovault_android.bean.OptionBean
import com.mvc.cryptovault_android.contract.OptionContract
import com.mvc.cryptovault_android.presenter.OptionPresenter
import kotlinx.android.synthetic.main.fragment_option.*
import kotlinx.android.synthetic.main.fragment_option.view.*
import java.util.ArrayList

class OptionFragment : BaseMVPFragment<OptionContract.OptionPresenter>(), OptionContract.OptionView {
    private lateinit var optionList: ArrayList<OptionBean.DataBean>
    private lateinit var optionAdapter: OptionAdapter
    private var financialType = 0
    private var isRefresh = false

    override fun showSuccess(bean: List<OptionBean.DataBean>) {
        rootView.option_swipe.post { rootView.option_swipe.isRefreshing = false }
        if (isRefresh) {
            isRefresh = false
            optionList.clear()
        }
        optionList.addAll(bean)
        optionAdapter.notifyDataSetChanged()
        if(optionList.size==0){
            option_rv.visibility = View.GONE
            data_null.visibility = View.VISIBLE
        }else{
            option_rv.visibility = View.VISIBLE
            data_null.visibility = View.GONE
        }
    }

    override fun showNull() {
        option_swipe.post { option_swipe.isRefreshing = false }
        option_rv.visibility = View.GONE
        data_null.visibility = View.VISIBLE
    }

    override fun showError() {
        option_swipe.post { option_swipe.isRefreshing = false }
        option_rv.visibility = View.GONE
        data_null.visibility = View.VISIBLE
    }

    override fun initPresenter(): BasePresenter<*, *> {
        return OptionPresenter.newIntance()
    }

    override fun initView() {
        financialType = arguments!!.getInt("financialType")
        optionList = ArrayList()
        optionAdapter = OptionAdapter(R.layout.item_option_layout, optionList)
        rootView.option_rv.adapter = optionAdapter
        rootView.option_swipe.post { rootView.option_swipe.isRefreshing = true }
        rootView.option_swipe.setOnRefreshListener { refresh() }
        rootView.option_rv.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView?, newState: Int) {
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    val layoutManager = recyclerView!!.layoutManager as LinearLayoutManager
                    val lastVisibleItemPosition = layoutManager.findLastVisibleItemPosition()
                    if (lastVisibleItemPosition + 1 == optionAdapter.itemCount && optionAdapter.itemCount >= 10 && !isRefresh) {
                        mPresenter.getOptionInfo(financialType, optionList[optionList.size - 1].id, 10)
                    }
                }
            }

            override fun onScrolled(recyclerView: RecyclerView?, dx: Int, dy: Int) {

            }
        })
    }

    fun refresh() {
        isRefresh = true
        mPresenter.getOptionInfo(financialType, 0, 10)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        rootView.option_swipe.post { rootView.option_swipe.isRefreshing = false }
    }

    override fun getLayoutId(): Int {
        return R.layout.fragment_option
    }

    override fun initData() {
        super.initData()
        mPresenter.getOptionInfo(financialType, 0, 10)
    }
}