package com.mvc.cryptovault_android.activity

import android.annotation.SuppressLint
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import com.gyf.barlibrary.ImmersionBar
import com.mvc.cryptovault_android.R
import com.mvc.cryptovault_android.adapter.rvAdapter.DailyAdapter
import com.mvc.cryptovault_android.base.BaseMVPActivity
import com.mvc.cryptovault_android.base.BasePresenter
import com.mvc.cryptovault_android.bean.OptionDailyIncomeBean
import com.mvc.cryptovault_android.bean.OptionDetailBean
import com.mvc.cryptovault_android.contract.OptionDetailContract
import com.mvc.cryptovault_android.event.OptionEvent
import com.mvc.cryptovault_android.listener.IDialogViewClickListener
import com.mvc.cryptovault_android.presenter.OptionDetailPresenter
import com.mvc.cryptovault_android.utils.TextUtils
import com.mvc.cryptovault_android.view.DialogHelper
import com.mvc.cryptovault_android.view.RuleRecyclerLines
import kotlinx.android.synthetic.main.activity_option_detail.*
import org.greenrobot.eventbus.EventBus
import java.util.ArrayList

class OptionDetailActivity : BaseMVPActivity<OptionDetailContract.OptionDetailPresenter>(), OptionDetailContract.OptionDetailView {
    private lateinit var dailyAdapter: DailyAdapter
    private lateinit var dailyList: ArrayList<OptionDailyIncomeBean.DataBean>
    private var isRefresh = false
    private lateinit var dialogHelper: DialogHelper


    override fun showExtractSuccess() {
        dialogHelper.resetDialogResource(this, R.drawable.success_icon, "取出成功")
        dialogHelper.dismissDelayed {
            EventBus.getDefault().post(OptionEvent())
        }
        submit.text = "已取出"
        submit.setBackgroundResource(R.drawable.bg_toge_child_item_tv_blue_nocheck)
        submit.isEnabled = false
    }

    override fun showExtractError(error:String) {
        dialogHelper.resetDialogResource(this, R.drawable.miss_icon, error)
        dialogHelper.dismissDelayed {
            null
        }
    }

    @SuppressLint("SetTextI18n")
    override fun showDetailSuccess(detail: OptionDetailBean.DataBean) {
        daily_swipe.post { daily_swipe.isRefreshing = false }
        investment_amount.text = TextUtils.doubleToDouble(detail.value) + detail.baseTokenName
        cumulative_income.text = TextUtils.doubleToDouble(detail.income) + detail.baseTokenName
        remaining_days.text = "${detail.times}天"
        detail_title.text = "${detail.financialName}持仓详情"
        detail_content.text = detail.financialName
        interest_rate.text = "年化收益率：${TextUtils.doubleToDouble(detail.incomeMin)}-${TextUtils.doubleToDouble(detail.incomeMax)} %"
    }

    override fun showDailyIncome(incom: ArrayList<OptionDailyIncomeBean.DataBean>) {
        daily_swipe.post { daily_swipe.isRefreshing = false }
        if (isRefresh) {
            isRefresh = false
            dailyList.clear()
        }
        dailyList.addAll(incom)
        dailyAdapter.notifyDataSetChanged()
        if (dailyList.size > 0) {
            daily_rv.visibility = View.VISIBLE
            daily_null.visibility = View.GONE
        } else {
            daily_rv.visibility = View.GONE
            daily_null.visibility = View.VISIBLE
        }
    }

    override fun showDetailError() {
        daily_swipe.post { daily_swipe.isRefreshing = false }
        investment_amount.text = "0"
        cumulative_income.text = "0"
        remaining_days.text = "0"

    }

    override fun showDailyIncomeError() {
        daily_swipe.post { daily_swipe.isRefreshing = false }
        daily_rv.visibility = View.GONE
        daily_null.visibility = View.VISIBLE
    }

    override fun startActivity() {

    }


    private var id = 0
    private var financialType = 0

    override fun initPresenter(): BasePresenter<*, *> {
        return OptionDetailPresenter.getInstance()
    }

    override fun initMVPData() {
        mPresenter.getDailyIncome(id, 0, 15)
        mPresenter.getOptionDetail(id)
        daily_rv.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView?, newState: Int) {
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    val layoutManager = recyclerView!!.layoutManager as LinearLayoutManager
                    val lastVisibleItemPosition = layoutManager.findLastVisibleItemPosition()
                    if (lastVisibleItemPosition + 1 == dailyAdapter.getItemCount() && dailyAdapter.getItemCount() >= 10 && !isRefresh) {
                        mPresenter.getDailyIncome(id, dailyList[dailyList.size - 1].id, 15)
                    }
                }
            }
            override fun onScrolled(recyclerView: RecyclerView?, dx: Int, dy: Int) {}
        })
    }

    override fun initView() {

    }

    override fun initMVPView() {
        dailyList = ArrayList()
        dialogHelper = DialogHelper.getInstance()
        dailyAdapter = DailyAdapter(R.layout.item_daily, dailyList)
        daily_rv.addItemDecoration(RuleRecyclerLines(this, RuleRecyclerLines.HORIZONTAL_LIST, 1))
        daily_rv.adapter = dailyAdapter
        id = intent.getIntExtra("id", 0)
        financialType = intent.getIntExtra("financialType", 0)
        ImmersionBar.with(this).titleBar(R.id.status_bar).statusBarDarkFont(true).init()
        back.setOnClickListener { finish() }
        daily_swipe.post { daily_swipe.isRefreshing = true }
        daily_swipe.setOnRefreshListener { refresh() }
        when (financialType) {
            1 -> {
                submit.setBackgroundResource(R.drawable.bg_toge_child_item_tv_blue_nocheck)
                submit.isEnabled = false
            }
            2 -> {
                submit.setBackgroundResource(R.drawable.bg_login_submit)
                submit.isEnabled = true
            }
            3 -> {
                submit.text = "已取出"
                submit.setBackgroundResource(R.drawable.bg_toge_child_item_tv_blue_nocheck)
                submit.isEnabled = false
            }
        }
        submit.setOnClickListener {
            dialogHelper.create(this, "确定取出？", object : IDialogViewClickListener {
                override fun click(viewId: Int) {
                    when (viewId) {
                        R.id.hint_cancle -> {
                            dialogHelper.dismiss()
                        }
                        R.id.hint_enter -> {
                            dialogHelper.dismiss()
                            extractOptionDetail()
                        }
                    }
                }
            }).show()
        }
    }

    private fun extractOptionDetail() {
        dialogHelper.create(this, R.drawable.pending_icon_1, "正在取出").show()
        mPresenter.extractOptionDetail(id)
    }


    fun refresh() {
        isRefresh = true
        mPresenter.getDailyIncome(id, 0, 15)
        mPresenter.getOptionDetail(id)
    }

    override fun getLayoutId(): Int {
        return R.layout.activity_option_detail
    }

    override fun initData() {

    }
}