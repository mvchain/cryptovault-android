package com.mvc.ttpay_android.activity

import android.webkit.WebView
import android.webkit.WebViewClient
import com.gyf.barlibrary.ImmersionBar
import com.mvc.ttpay_android.R
import com.mvc.ttpay_android.base.BaseActivity
import kotlinx.android.synthetic.main.activity_banner_web.*

class BannerActivity : BaseActivity() {
    private lateinit var webView: WebView


    override fun getLayoutId(): Int {
        return R.layout.activity_banner_web
    }

    override fun initData() {

    }

    override fun initView() {
        ImmersionBar.with(this).titleBar(R.id.status_bar).statusBarDarkFont(true).init()
        webView = WebView(this)
        webView.settings.javaScriptEnabled = true
        web_layout.addView(webView)
        webView.loadUrl("${getString(R.string.web_url)}?type=${1}&id=${intent.getIntExtra("id", 0)}")
        webView.webViewClient = object : WebViewClient() {
            override fun onPageFinished(view: WebView?, url: String?) {
                super.onPageFinished(view, url)
                banner_title.text = view!!.title
            }
        }
        banner_back.setOnClickListener { finish() }
    }
}