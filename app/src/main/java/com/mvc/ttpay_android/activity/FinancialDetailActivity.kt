package com.mvc.ttpay_android.activity

import android.animation.ValueAnimator
import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient
import com.blankj.utilcode.util.ConvertUtils
import com.blankj.utilcode.util.LogUtils
import com.gyf.barlibrary.ImmersionBar
import com.mvc.ttpay_android.R
import com.mvc.ttpay_android.base.BaseMVPActivity
import com.mvc.ttpay_android.base.BasePresenter
import com.mvc.ttpay_android.bean.FinancialDetailBean
import com.mvc.ttpay_android.contract.IFinancialDetailContract
import com.mvc.ttpay_android.event.FinancialDetailEvent
import com.mvc.ttpay_android.presenter.FinancialDetailPresenter
import com.mvc.ttpay_android.utils.TextUtils
import kotlinx.android.synthetic.main.activity_financial_detail.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe

class FinancialDetailActivity : BaseMVPActivity<IFinancialDetailContract.FinancialDetailPresenter>(), IFinancialDetailContract.FinancialDetailfoView {
    private lateinit var detail: FinancialDetailBean.DataBean
    private lateinit var baseName: String
    private lateinit var webView: WebView

    //    private var detailId = -1
    override fun startActivity() {

    }

    override fun showError(error: String) {
        LogUtils.e(error)
    }

    @SuppressLint("SetTextI18n")
    override fun showSuccess(bean: FinancialDetailBean.DataBean) {
        financial_title.text = bean.name
        financia_income.text = "${TextUtils.doubleToDouble(bean.incomeMin)}-${TextUtils.doubleToDouble(bean.incomeMax)} %"
        time_cycle.text = "${bean.times}天"
        starting.text = "${TextUtils.doubleToEight(bean.minValue)} ${bean.baseTokenName}"
        if ((webView.parent) != null) {
            (webView.parent as ViewGroup).removeView(webView)
        }
        webview_layout.addView(webView)
        webView.loadUrl("${getString(R.string.web_url)}?type=${2}&id=${bean.id}")
        webView.overScrollMode = WebView.OVER_SCROLL_NEVER
        webView.webChromeClient = object : WebChromeClient() {

        }
        webView.webViewClient = object : WebViewClient() {
            override fun onPageFinished(view: WebView, url: String?) {
                view.measure(View.MeasureSpec.UNSPECIFIED,View.MeasureSpec.UNSPECIFIED)
                var height = view.measuredHeight
                var flp = view.layoutParams
                flp.height = ConvertUtils.dp2px(view.height.toFloat())
                LogUtils.e("${view.contentHeight} $height  ${flp.height}")
                webView.layoutParams = flp
                super.onPageFinished(view, url)
            }
        }
        detail = bean
        deposit.isEnabled = true
        deposit.setBackgroundResource(R.drawable.bg_login_submit)
        remaining_amount_progress.max = bean.limitValue.toInt()
        remaining_amount_progress.progress = (bean.limitValue - bean.sold).toInt()
        remaining_amount.text = "剩余总额度 ${TextUtils.doubleToFourPrice(bean.limitValue - bean.sold)} ${bean.baseTokenName}"
//        val valueAnimator = ValueAnimator.ofInt(bean.limitValue.toInt(), (bean.limitValue - bean.sold).toInt()).setDuration(800)
//        valueAnimator.addUpdateListener { animation ->
//            remaining_amount_progress.progress = animation.animatedValue as Int
//        }
//        valueAnimator.start()
    }

    private var id = 0
    override fun initPresenter(): BasePresenter<*, *> {
        return FinancialDetailPresenter.newIntance()
    }

    override fun initMVPData() {
        mPresenter.getFinancialDetail(id)
    }

    override fun getLayoutId(): Int {
        return R.layout.activity_financial_detail
    }

    override fun initView() {
    }

    override fun initMVPView() {
        ImmersionBar.with(this).titleBar(R.id.status_bar).statusBarDarkFont(true).init()
        id = intent.getIntExtra("id", 0)
        webView = WebView(this)
        webView.settings.javaScriptEnabled = true
    }

    override fun initData() {

    }

    fun onClick(v: View) {
        when (v.id) {
            R.id.back -> {
                finish()
            }
            R.id.deposit -> {
                if (detail == null) {
                    return
                } else {
                    var idIntent = Intent(this, FinancialDepositActivity::class.java)
                    idIntent.putExtra("detail", detail)
                    startActivity(idIntent)
                }
            }
        }
    }

    @Subscribe
    fun refresh(detailEvent: FinancialDetailEvent) {
        mPresenter.getFinancialDetail(id)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        EventBus.getDefault().register(this)
    }

    override fun onDestroy() {
        EventBus.getDefault().unregister(this)
        webView.clearHistory()
        (webView.parent as ViewGroup).removeView(webView)
        webView.stopLoading()
        webView.settings.domStorageEnabled = false
        webView.settings.javaScriptEnabled = false
        webView.clearView()
        webView.removeAllViews()
        webView.destroy()
        super.onDestroy()
    }
}