package com.mvc.cryptovault_android.fragment

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.Gravity
import android.view.View
import android.widget.ImageView
import android.widget.PopupWindow
import android.widget.RadioButton
import android.widget.TextView
import com.blankj.utilcode.util.LogUtils
import com.mvc.cryptovault_android.R
import com.mvc.cryptovault_android.activity.TrandPurhAndSellActivity
import com.mvc.cryptovault_android.activity.TrandPurhAndSellItemActivity
import com.mvc.cryptovault_android.adapter.TrandRecorAdapter
import com.mvc.cryptovault_android.base.BaseMVPFragment
import com.mvc.cryptovault_android.base.BasePresenter
import com.mvc.cryptovault_android.bean.PairTickersBean
import com.mvc.cryptovault_android.bean.RecorBean
import com.mvc.cryptovault_android.bean.TrandChildBean
import com.mvc.cryptovault_android.contract.IAreaContract
import com.mvc.cryptovault_android.listener.IRecordingClick
import com.mvc.cryptovault_android.listener.ISelectWindowListener
import com.mvc.cryptovault_android.presenter.AreaPresenter
import com.mvc.cryptovault_android.utils.TextUtils
import com.mvc.cryptovault_android.view.NoScrollViewPager
import com.mvc.cryptovault_android.view.PopViewHelper

class TradingAreaFragment : BaseMVPFragment<IAreaContract.AreaPresenter>(), IAreaContract.AreaView {
    override fun pairTickersSuccess(pairTickersBean: PairTickersBean.DataBean) {
        mRecordingCurrent.text = TextUtils.doubleToEight(pairTickersBean.price)
        mRecordingDayMax.text = TextUtils.doubleToEight(pairTickersBean.high)
        mRecordingDayMin.text = TextUtils.doubleToEight(pairTickersBean.low)
    }

    override fun pairTickersFailed(msg: String) {

    }

    private lateinit var mMask: View
    private lateinit var mMenu: ImageView
    private lateinit var mSelect: TextView
    private lateinit var mRecordingDayMax: TextView
    private lateinit var mRecordingDayMin: TextView
    private lateinit var mRecordingCurrent: TextView
    private lateinit var mRecordingInRadio: RadioButton
    private lateinit var mRecordingOutRadio: RadioButton
    private lateinit var mRecordingVp: NoScrollViewPager
    private lateinit var recorAdapter: TrandRecorAdapter
    private lateinit var mSelectPop: PopupWindow
    private lateinit var mMenuPop: PopupWindow
    private lateinit var ratioList: ArrayList<TrandChildBean.DataBean>
    private lateinit var menuList: ArrayList<String>
    private var pairId = 1
    private var createCarryOut: Boolean = false
    private var leftPosition = 0

    override fun initView() {
        mFragment = ArrayList()
        ratioList = ArrayList()
        menuList = ArrayList()
        menuList.add("购买MVC挂单")
        menuList.add("出售MVC挂单")
        menuList.add("交易记录")
        createCarryOut = true
        mMenu = rootView.findViewById(R.id.menu)
        mMask = rootView.findViewById(R.id.mask)
        mSelect = rootView.findViewById(R.id.select_mvc)
        mRecordingVp = rootView.findViewById(R.id.recording_vp)
        mRecordingInRadio = rootView.findViewById(R.id.recording_in_radio)
        mRecordingDayMax = rootView.findViewById(R.id.recording_day_max_tv)
        mRecordingDayMin = rootView.findViewById(R.id.recording_day_min_tv)
        mRecordingCurrent = rootView.findViewById(R.id.recording_current_tv)
        mRecordingOutRadio = rootView.findViewById(R.id.recording_out_radio)
        mRecordingOutRadio.setOnClickListener { mRecordingVp.currentItem = 0 }
        mRecordingInRadio.setOnClickListener { mRecordingVp.currentItem = 1 }
        mSelect.setOnClickListener {
            if (mMenuPop.isShowing) {
                mMenuPop.dismiss()
            }
            if (mSelectPop.isShowing) {
                mSelectPop.dismiss()
            } else {
                mSelectPop.showAsDropDown(mSelect, 0, 0, Gravity.BOTTOM)
                mMask.visibility = View.VISIBLE
            }
        }
        mMenu.setOnClickListener {
            if (mSelectPop.isShowing) {
                mSelectPop.dismiss()
            }
            if (mMenuPop.isShowing) {
                mMenuPop.dismiss()
            } else {
                mMenuPop.showAsDropDown(mMenu, 0, 0, Gravity.BOTTOM)
                mMask.visibility = View.VISIBLE
            }
        }

    }


    private lateinit var mFragment: ArrayList<Fragment>

    override fun vrtSuccess(trandChildBean: ArrayList<TrandChildBean.DataBean>) {
        ratioList.addAll(trandChildBean)
        mSelect.text = ratioList[0].tokenName
        loadFragment(0)
        initPop()
    }

    private fun loadFragment(position: Int) {
        mFragment = ArrayList()
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
        for (fragment in mFragment) {
            fragment as RecordingFragment
            fragment.setRecording(object : IRecordingClick {
                override fun startPurhActivity(transionType: Int, id: Int, recorBean: RecorBean.DataBean) {
                    val intent = Intent(activity, TrandPurhAndSellItemActivity::class.java)
                    var data = ratioList[leftPosition]
                    intent.putExtra("id", id)
                    intent.putExtra("data", data)
                    intent.putExtra("recorBean", recorBean)
                    intent.putExtra("type", transionType)
//        unitPrice
                    if (transionType == 1) {
                        intent.putExtra("unit_price", "出售MVC挂单")
                    } else {
                        intent.putExtra("unit_price", "购买MVC挂单")
                    }
                    intent.putExtra("allprice_unit", data.tokenName)
                    startActivity(intent)
                }
            })
        }
        recorAdapter = TrandRecorAdapter(fragmentManager, mFragment)
        mRecordingVp.adapter = recorAdapter
    }

    override fun vrtFailed(msg: String) {

    }


    private fun initPop() {
        initLeftPop()
        initRightPop()
    }

    private fun initRightPop() {
        mMenuPop = PopViewHelper.instance.create(activity, menuList, R.layout.layout_ratio_pop, object : ISelectWindowListener {
            override fun onclick(position: Int) {
                var intent = Intent()
                var data = ratioList[leftPosition]
                when (position) {
                    0 -> {
                        intent.setClass(activity, TrandPurhAndSellActivity::class.java)
                        intent.putExtra("title", "购买MVC挂单")
                        intent.putExtra("allprice_unit", data.tokenName)
                        intent.putExtra("unit_price", data.tokenName)
                        intent.putExtra("data", data)
                        intent.putExtra("type", 1)
                        startActivity(intent)
                    }
                    1 -> {
                        intent.setClass(activity, TrandPurhAndSellActivity::class.java)
                        intent.putExtra("title", "出售MVC挂单")
                        intent.putExtra("allprice_unit", data.tokenName)
                        intent.putExtra("unit_price", data.tokenName)
                        intent.putExtra("data", data)
                        intent.putExtra("type", 2)
                        startActivity(intent)
                    }
                    2 -> {

                    }
                }
                mMenuPop.dismiss()
            }

            override fun dismiss() {
                mMask.visibility = View.INVISIBLE
            }
        })

    }

    private fun initLeftPop() {
        mSelectPop = PopViewHelper.instance.create(activity, R.layout.layout_ratio_pop, ratioList, object : ISelectWindowListener {
            override fun onclick(position: Int) {
                refreshFragment(position)
                mSelectPop.dismiss()
            }

            override fun dismiss() {
                mMask.visibility = View.INVISIBLE
            }
        })
    }

    private fun refreshFragment(position: Int) {
        for (fragment in mFragment) {
            fragment as RecordingFragment
            fragment.setPairId(ratioList[position].pairId)
            fragment.eventRefresh(null)
            mPresenter.getPairTickers(ratioList[position].pairId)
            mSelect.text = ratioList[position].tokenName
            leftPosition = position
        }
    }

    override fun getLayoutId(): Int {
        return R.layout.fragment_trading
    }

    override fun initPresenter(): BasePresenter<*, *> {
        return AreaPresenter.newInstance()
    }

    override fun initData() {
        super.initData()
        ratioList.clear()
        mPresenter.getAllVrtAndBalance()
        mPresenter.getVrt(pairId)
        mPresenter.getPairTickers(pairId)
    }
}