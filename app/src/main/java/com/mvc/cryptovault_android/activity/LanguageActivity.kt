package com.mvc.cryptovault_android.activity

import com.blankj.utilcode.util.LogUtils
import com.gyf.barlibrary.ImmersionBar
import com.mvc.cryptovault_android.R
import com.mvc.cryptovault_android.base.BaseActivity
import com.mvc.cryptovault_android.common.Constant.LANGUAGE.*
import com.mvc.cryptovault_android.utils.LanguageUtils
import kotlinx.android.synthetic.main.activity_language.*


class LanguageActivity : BaseActivity() {
    lateinit var default_language: String
    override fun getLayoutId(): Int {
        return R.layout.activity_language
    }

    override fun initData() {
        switch_china.setOnSuperTextViewClickListener {
            LanguageUtils.changeLocale(CHINESE, resources.configuration, baseContext)
            switch_china.setRightIcon(R.drawable.list_icon_black)
            switch_english.setRightIcon(R.drawable.code_icon_black)
            recreate()
        }
        switch_english.setOnSuperTextViewClickListener {
            LanguageUtils.changeLocale(ENGLISH, resources.configuration, baseContext)
            switch_english.setRightIcon(R.drawable.list_icon_black)
            switch_china.setRightIcon(R.drawable.code_icon_black)
            recreate()
        }
        language_back.setOnClickListener {
            finish()
        }
//        baseContext.createConfigurationContext()
    }

    override fun initView() {
        ImmersionBar.with(this).statusBarView(R.id.status_bar).statusBarDarkFont(true).init()
        default_language = LanguageUtils.getUserSetLocal()
        LogUtils.e(default_language)
        if (default_language == CHINESE) {
            switch_china.setRightIcon(R.drawable.list_icon_black)
            switch_english.setRightIcon(R.drawable.code_icon_black)
        } else if (default_language == ENGLISH) {
            switch_english.setRightIcon(R.drawable.list_icon_black)
            switch_china.setRightIcon(R.drawable.code_icon_black)
        }
    }
}
