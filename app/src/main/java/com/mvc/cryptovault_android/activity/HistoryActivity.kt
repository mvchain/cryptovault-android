package com.mvc.cryptovault_android.activity

import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.support.annotation.RequiresApi
import android.support.design.widget.TabLayout
import android.support.v4.app.Fragment
import android.support.v4.view.ViewPager
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView

import com.blankj.utilcode.util.LogUtils
import com.blankj.utilcode.util.SpanUtils
import com.blankj.utilcode.util.ToastUtils
import com.gyf.barlibrary.ImmersionBar
import com.mvc.cryptovault_android.MyApplication
import com.mvc.cryptovault_android.R
import com.mvc.cryptovault_android.adapter.HistoryPagerAdapter
import com.mvc.cryptovault_android.api.ApiStore
import com.mvc.cryptovault_android.base.BaseMVPActivity
import com.mvc.cryptovault_android.base.BasePresenter
import com.mvc.cryptovault_android.bean.ExchangeRateBean
import com.mvc.cryptovault_android.bean.AssetListBean
import com.mvc.cryptovault_android.bean.HistoryBeanEvent
import com.mvc.cryptovault_android.contract.HistroyContract
import com.mvc.cryptovault_android.event.HistroyEvent
import com.mvc.cryptovault_android.fragment.HistoryChildFragment
import com.mvc.cryptovault_android.presenter.HistoryPresenter
import com.mvc.cryptovault_android.utils.RetrofitUtils
import com.mvc.cryptovault_android.utils.RxHelper
import com.mvc.cryptovault_android.utils.TextUtils
import com.mvc.cryptovault_android.view.DialogHelper
import com.per.rslibrary.IPermissionRequest
import com.per.rslibrary.RsPermission
import com.uuzuche.lib_zxing.activity.CodeUtils

import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe

import java.util.ArrayList


class HistoryActivity : BaseMVPActivity<HistroyContract.HistroyPrecenter>(), HistroyContract.IHistroyView, View.OnClickListener {
    private lateinit var mBackHis: ImageView
    private lateinit var mQcodeHis: ImageView
    private lateinit var mPriceHis: TextView
    private lateinit var mActualHis: TextView
    private lateinit var mTabHis: TabLayout
    private lateinit var mVpHis: ViewPager
    private lateinit var fragments: ArrayList<Fragment>
    private lateinit var mExchange: List<ExchangeRateBean.DataBean>
    private lateinit var historyPagerAdapter: HistoryPagerAdapter
    private lateinit var mTitleHis: TextView
    private lateinit var mOutHis: TextView
    private lateinit var mInHis: TextView
    private lateinit var mLayoutSub: LinearLayout
    private lateinit var mIntent: Intent
    private var type: Int = 0
    private var tokenId: Int = 0
    private lateinit var tokenName: String
    private lateinit var rateType: String
    private lateinit var dataBean: AssetListBean.DataBean

    override fun initMVPData() {
        val transferFragment = HistoryChildFragment()
        val transferBundle = Bundle()
        transferBundle.putInt("tokenId", tokenId)
        transferBundle.putInt("action", 0)
        transferFragment.arguments = transferBundle
        fragments.add(transferFragment)
        val financesFragment = HistoryChildFragment()
        val financesBundle = Bundle()
        financesBundle.putInt("tokenId", tokenId)
        financesBundle.putInt("action", 4)
        financesFragment.arguments = financesBundle
        fragments.add(financesFragment)
        val transactionFragment = HistoryChildFragment()
        val transactionBundle = Bundle()
        transactionBundle.putInt("tokenId", tokenId)
        transactionBundle.putInt("action", 1)
        transactionFragment.arguments = transactionBundle
        fragments.add(transactionFragment)
        mVpHis.adapter = historyPagerAdapter
        mTabHis.setupWithViewPager(mVpHis)
    }

    override fun initMVPView() {
        mExchange = ArrayList()
        fragments = ArrayList()
        ImmersionBar.with(this).statusBarView(R.id.status_bar).statusBarDarkFont(true).init()
        historyPagerAdapter = HistoryPagerAdapter(supportFragmentManager, fragments)
        mBackHis = findViewById(R.id.his_back)
        mTitleHis = findViewById(R.id.his_title)
        mQcodeHis = findViewById(R.id.his_qcode)
        mPriceHis = findViewById(R.id.his_price)
        mActualHis = findViewById(R.id.his_actual)
        mTabHis = findViewById(R.id.his_tab)
        mVpHis = findViewById(R.id.his_vp)
        mOutHis = findViewById(R.id.his_out)
        mInHis = findViewById(R.id.his_in)
        mLayoutSub = findViewById(R.id.sub_layout)
        mBackHis.setOnClickListener(this)
        mQcodeHis.setOnClickListener(this)
        mOutHis.setOnClickListener(this)
        mInHis.setOnClickListener(this)
        initIntent()
    }

    @SuppressLint("SetTextI18n")
    private fun initIntent() {
        mIntent = intent
        type = mIntent.getIntExtra("type", 0)
        tokenId = mIntent.getIntExtra("tokenId", 0)
        tokenName = mIntent.getStringExtra("tokenName")
        dataBean = mIntent.getParcelableExtra("data")
        rateType = mIntent.getStringExtra("rate_type")
        mTitleHis.text = tokenName
        when (type) {
            0 -> {
                mLayoutSub.visibility = View.VISIBLE
                mOutHis.visibility = View.GONE
                mInHis.visibility = View.GONE
                mQcodeHis.visibility = View.GONE
            }
            1 -> {
                mLayoutSub.visibility = View.GONE
                mQcodeHis.visibility = View.GONE
            }
            2 -> {
                mLayoutSub.visibility = View.VISIBLE
                mOutHis.visibility = View.VISIBLE
                mInHis.visibility = View.VISIBLE
                mQcodeHis.visibility = View.VISIBLE
            }
        }
        mPriceHis.text = SpanUtils().append(TextUtils.rateToPrice(dataBean.ratio * dataBean.value) + " ").setFontSize(36, true)
                .append(rateType).setFontSize(10, true).create()
        mActualHis.text = TextUtils.doubleToFour(dataBean.value) + " " + dataBean.tokenName
    }

    override fun getLayoutId(): Int {
        return R.layout.activity_history
    }

    override fun initData() {

    }

    override fun initView() {
        EventBus.getDefault().register(this)

    }

    override fun onDestroy() {
        EventBus.getDefault().unregister(this)
        super.onDestroy()
    }

    override fun initPresenter(): BasePresenter<*, *> {
        return HistoryPresenter.newIntance()
    }

    override fun startActivity() {

    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    override fun onClick(v: View) {
        val intent = Intent()
        when (v.id) {
            R.id.his_back ->
                // TODO 18/11/29
                finish()
            R.id.his_qcode ->
                // TODO 18/11/29
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    RsPermission.getInstance().setRequestCode(200).setiPermissionRequest(object : IPermissionRequest {
                        override fun toSetting() {
                            RsPermission.getInstance().toSettingPer()
                        }

                        override fun cancle(i: Int) {
                            ToastUtils.showLong("未给予相机权限将无法扫描二维码")
                        }

                        override fun success(i: Int) {
                            val qcodeIntent = Intent(this@HistoryActivity, QCodeActivity::class.java)
                            qcodeIntent.putExtra("tokenId", tokenId)
                            startActivityForResult(qcodeIntent, 200)
                        }
                    }).requestPermission(this, Manifest.permission.CAMERA)
                } else {
                    intent.setClass(this@HistoryActivity, QCodeActivity::class.java)
                    intent.putExtra("tokenId", tokenId)
                    startActivityForResult(intent, 200)
                }
            R.id.his_out// TODO 18/12/05
            -> {
                intent.setClass(this, BTCTransferActivity::class.java)
                intent.putExtra("tokenId", tokenId)
                intent.putExtra("tokenName", tokenName)
                startActivity(intent)
            }
            R.id.his_in// TODO 18/12/05
            -> {
                intent.setClass(this, MineReceiptQRCodeActivity::class.java)
                intent.putExtra("tokenId", tokenId)
                intent.putExtra("tokenName", tokenName)
                startActivityForResult(intent, 300)
            }
        }
    }

    override fun showSuccess(msgs: List<String>) {

    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        RsPermission.getInstance().onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (data != null) {
            when (resultCode) {
                200 -> {
                    val qode = data.getBooleanExtra("QODE", false)
                    if (!qode) {
                        val dialogHelper = DialogHelper.instance
                        dialogHelper.create(this, R.drawable.miss_icon, "无效地址").show()
                        dialogHelper.dismissDelayed(null, 2000)
                        return
                    }
                    val stringExtra = data.getStringExtra(CodeUtils.RESULT_STRING)
                    val intent = Intent(this@HistoryActivity, BTCTransferActivity::class.java)
                    intent.putExtra("hash", stringExtra)
                    intent.putExtra("tokenId", tokenId)
                    intent.putExtra("tokenName", tokenName)
                    startActivity(intent)
                }
            }
        }
    }

    @Subscribe
    fun changePrice(event: HistroyEvent) {
        val price = event.price
        mActualHis.text = TextUtils.doubleToFour(java.lang.Double.parseDouble(mActualHis.text.toString().split(" ".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()[0]) - java.lang.Double.parseDouble(price))
        val newsPrice = mActualHis.text.toString().split(" ".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()[0]
        mPriceHis.text = TextUtils.rateToPrice(java.lang.Double.parseDouble(newsPrice) * dataBean.ratio)
    }

    @SuppressLint("CheckResult", "SetTextI18n")
    @Subscribe
    fun refreshPrice(event: HistoryBeanEvent) {
        RetrofitUtils.client(MyApplication.getBaseUrl(), ApiStore::class.java).getAssets(MyApplication.getTOKEN(), tokenId)
                .compose(RxHelper.rxSchedulerHelper())
                .subscribe({ assetsBean ->
                    val dataBean = assetsBean.data
                    mPriceHis.text = SpanUtils().append(TextUtils.rateToPrice(dataBean.ratio * dataBean.value) + " ").setFontSize(36, true)
                            .append(rateType).setFontSize(10, true).create()
                    mActualHis.text = TextUtils.doubleToFour(dataBean.value) + " " + dataBean.tokenName
                }, { throwable -> LogUtils.e(throwable.message) })
    }
}
