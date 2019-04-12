package com.mvc.ttpay_android.activity

import android.os.Bundle
import android.support.v4.app.Fragment
import com.gyf.barlibrary.ImmersionBar
import com.mvc.ttpay_android.R
import com.mvc.ttpay_android.adapter.OptionPagerAdapter
import com.mvc.ttpay_android.base.BaseActivity
import com.mvc.ttpay_android.fragment.OptionFragment
import kotlinx.android.synthetic.main.activity_option.*
import java.util.ArrayList

class MyOptionActivity : BaseActivity() {
    private var optionFGs = ArrayList<Fragment>()
    private lateinit var optionAdapter: OptionPagerAdapter

    override fun getLayoutId(): Int {
        return R.layout.activity_option
    }

    override fun initData() {
        //计息中
        var interested = OptionFragment()
        var interestedBundle = Bundle()
        interestedBundle.putInt("financialType", 1)
        interested.arguments = interestedBundle
        optionFGs.add(interested)
        //待提取
        var pending = OptionFragment()
        var pendingBundle = Bundle()
        pendingBundle.putInt("financialType", 2)
        pending.arguments = pendingBundle
        optionFGs.add(pending)
        //已提取
        var extracted = OptionFragment()
        var extractedBundle = Bundle()
        extractedBundle.putInt("financialType", 3)
        extracted.arguments = extractedBundle
        optionFGs.add(extracted)
        optionAdapter = OptionPagerAdapter(supportFragmentManager, optionFGs)
        option_vp.adapter = optionAdapter
        option_tab.setupWithViewPager(option_vp)
    }

    override fun initView() {
        ImmersionBar.with(this).titleBar(R.id.status_bar).statusBarDarkFont(true).init()
        back.setOnClickListener { finish() }
    }
}
