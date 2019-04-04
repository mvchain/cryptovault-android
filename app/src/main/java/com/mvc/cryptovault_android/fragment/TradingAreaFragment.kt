package com.mvc.cryptovault_android.fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.widget.ImageView
import android.widget.RadioButton
import android.widget.TextView
import com.mvc.cryptovault_android.R
import com.mvc.cryptovault_android.adapter.TrandRecorAdapter
import com.mvc.cryptovault_android.base.BaseMVPFragment
import com.mvc.cryptovault_android.base.BasePresenter
import com.mvc.cryptovault_android.bean.TrandChildBean
import com.mvc.cryptovault_android.contract.IAreaContract
import com.mvc.cryptovault_android.contract.ISetPasswordContract
import com.mvc.cryptovault_android.listener.ISelectWindowListener
import com.mvc.cryptovault_android.presenter.AreaPresenter
import com.mvc.cryptovault_android.view.NoScrollViewPager
import com.mvc.cryptovault_android.view.PopViewHelper

class TradingAreaFragment : BaseMVPFragment<IAreaContract.AreaPresenter>(), IAreaContract.AreaView {
    private lateinit var mMenu: ImageView
    private lateinit var mSelect: TextView
    private lateinit var mRecordingDayMax: TextView
    private lateinit var mRecordingDayMin: TextView
    private lateinit var mRecordingCurrent: TextView
    private lateinit var mRecordingInRadio: RadioButton
    private lateinit var mRecordingOutRadio: RadioButton
    private lateinit var mRecordingVp: NoScrollViewPager
    private lateinit var recorAdapter: TrandRecorAdapter
    private lateinit var ratioList: ArrayList<TrandChildBean.DataBean>

    override fun initView() {
        mFragment = ArrayList()
        ratioList = ArrayList()
        mMenu = rootView.findViewById(R.id.menu)
        mSelect = rootView.findViewById(R.id.select_mvc)
        mRecordingVp = rootView.findViewById(R.id.recording_vp)
        mRecordingInRadio = rootView.findViewById(R.id.recording_in_radio)
        mRecordingDayMax = rootView.findViewById(R.id.recording_day_max_tv)
        mRecordingDayMin = rootView.findViewById(R.id.recording_day_min_tv)
        mRecordingCurrent = rootView.findViewById(R.id.recording_current_tv)
        mRecordingOutRadio = rootView.findViewById(R.id.recording_out_radio)
        recorAdapter = TrandRecorAdapter(childFragmentManager, mFragment)
    }

    private lateinit var mFragment: ArrayList<Fragment>

    override fun vrtSuccess(trandChildBean: ArrayList<TrandChildBean.DataBean>) {
        ratioList.addAll(trandChildBean)
        loadFragment(0)
        mRecordingVp.adapter = recorAdapter
    }

    private fun loadFragment(position: Int) {
        mFragment.clear()
        val purhFragment = RecordingFragment()
        val purhBundle = Bundle()
        purhBundle.putInt("transType", 1)
        purhBundle.putInt("transionType", 2) //如果获取的是购买挂单列表，那id就是
        purhBundle.putInt("pairId", ratioList[position].pairId)
        purhFragment.arguments = purhBundle
        mFragment.add(purhFragment)
        val sellFragment = RecordingFragment()
        val sellBundle = Bundle()
        sellBundle.putInt("transType", 2)
        sellBundle.putInt("transionType", 1)
        sellBundle.putInt("pairId", ratioList[position].pairId)
        sellFragment.arguments = sellBundle
        mFragment.add(sellFragment)
        recorAdapter.notifyDataSetChanged()
    }

    override fun vrtFailed(msg: String) {

    }


    private fun initPop() {
        initLeftPop()
        initRightPop()
    }

    private fun initRightPop() {
        PopViewHelper.instance.create(activity, R.layout.layout_ratio_pop, ratioList, object : ISelectWindowListener {
            override fun onclick(position: Int) {
                loadFragment(position)
            }

            override fun dismiss() {

            }
        })

    }

    private fun initLeftPop() {
        PopViewHelper.instance.create(activity, R.layout.layout_ratio_pop, ratioList, object : ISelectWindowListener {
            override fun onclick(position: Int) {

            }

            override fun dismiss() {

            }
        })
    }

    override fun getLayoutId(): Int {
        return R.layout.fragment_trading
    }

    override fun initPresenter(): BasePresenter<*, *> {
        return AreaPresenter.newInstance()
    }

    override fun initData() {
        super.initData()
        mPresenter.getAllVrtAndBalance()
        mPresenter.getVrt(1)
        initPop()
    }
}