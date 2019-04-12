package com.mvc.cryptovault_android.fragment

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ObjectAnimator
import android.app.Dialog
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.support.annotation.RequiresApi
import android.support.v4.content.ContextCompat
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.PopupWindow
import android.widget.TextView
import android.widget.ViewFlipper

import com.bartoszlipinski.recyclerviewheader2.RecyclerViewHeader
import com.blankj.utilcode.util.ConvertUtils
import com.blankj.utilcode.util.SPUtils
import com.blankj.utilcode.util.ToastUtils
import com.mvc.cryptovault_android.R
import com.mvc.cryptovault_android.adapter.rvAdapter.WalletAssetsAdapter
import com.mvc.cryptovault_android.base.BaseMVPFragment
import com.mvc.cryptovault_android.base.BasePresenter
import com.mvc.cryptovault_android.contract.IWalletContract
import com.mvc.cryptovault_android.event.TrandFragmentEvent
import com.mvc.cryptovault_android.event.WalletAssetsListEvent
import com.mvc.cryptovault_android.event.WalletFragmentEvent
import com.mvc.cryptovault_android.event.WalletMsgEvent
import com.mvc.cryptovault_android.listener.IPopViewListener
import com.mvc.cryptovault_android.presenter.WalletPresenter
import com.mvc.cryptovault_android.utils.JsonHelper
import com.mvc.cryptovault_android.utils.TextUtils
import com.mvc.cryptovault_android.utils.ViewDrawUtils
import com.mvc.cryptovault_android.view.PopViewHelper

import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe


import cn.jpush.android.api.JPushInterface
import com.blankj.utilcode.util.LogUtils
import com.mvc.cryptovault_android.activity.*
import com.mvc.cryptovault_android.bean.*

import com.mvc.cryptovault_android.common.Constant.SP.ALLASSETS
import com.mvc.cryptovault_android.common.Constant.SP.ASSETS_LIST
import com.mvc.cryptovault_android.common.Constant.SP.CURRENCY_LIST
import com.mvc.cryptovault_android.common.Constant.SP.DEFAULT_SYMBOL
import com.mvc.cryptovault_android.common.Constant.SP.MSG_TIME
import com.mvc.cryptovault_android.common.Constant.SP.RATE_LIST
import com.mvc.cryptovault_android.common.Constant.SP.READ_MSG
import com.mvc.cryptovault_android.common.Constant.SP.SET_RATE
import kotlin.collections.ArrayList

class WalletFragment : BaseMVPFragment<IWalletContract.WalletPresenter>(), IWalletContract.IWalletView, View.OnClickListener, IPopViewListener {

    private lateinit var mHintAssets: ImageView
    private lateinit var mBrowser: ImageView
    private lateinit var mNullAssets: ImageView
    private lateinit var mAddAssets: ImageView
    private lateinit var mSignInView: ImageView
    private lateinit var mTypeAssets: TextView
    private lateinit var mPriceAssets: TextView
    private var mFlipper: ViewFlipper? = null
    private lateinit var mAssetsLayout: RecyclerViewHeader
    private lateinit var mRvAssets: RecyclerView
    private lateinit var assetsAdapter: WalletAssetsAdapter
    private lateinit var mData: MutableList<AssetListBean.DataBean>
    private lateinit var mExchange: MutableList<ExchangeRateBean.DataBean>
    private lateinit var mMsgBean: MutableList<MsgBean.DataBean>
    private lateinit var mSwipAsstes: SwipeRefreshLayout
    private lateinit var mPopView: PopupWindow
    private var createCarryOut: Boolean = false
    private val position: Int = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        EventBus.getDefault().register(this)
    }

    override fun initView() {
        mData = ArrayList()
        mExchange = ArrayList()
        mMsgBean = ArrayList()
        var note = LayoutInflater.from(activity).inflate(R.layout.layout_wellet_announcement, null)
        note.findViewById<TextView>(R.id.note_tv).text = "暂无通知"
        mHintAssets = rootView.findViewById(R.id.assets_hint)
        mBrowser = rootView.findViewById(R.id.assets_browser)
        mNullAssets = rootView.findViewById(R.id.assets_null)
        mAddAssets = rootView.findViewById(R.id.assets_add)
        mTypeAssets = rootView.findViewById(R.id.assets_type)
        mPriceAssets = rootView.findViewById(R.id.assets_price)
        mFlipper = rootView.findViewById(R.id.flipper)
        mRvAssets = rootView.findViewById(R.id.assets_rv)
        mAssetsLayout = rootView.findViewById(R.id.assets_layout)
        mSwipAsstes = rootView.findViewById(R.id.asstes_swip)
        mSignInView = rootView.findViewById(R.id.assets_sign_in)
        mSwipAsstes.post { mSwipAsstes.isRefreshing = true }
        mSwipAsstes.setOnRefreshListener { onRefresh() }
        mHintAssets.setOnClickListener(this)
        mAddAssets.setOnClickListener(this)
        mTypeAssets.setOnClickListener(this)
        mPriceAssets.setOnClickListener(this)
        mSignInView.setOnClickListener(this)
        mBrowser.setOnClickListener(this)
        createCarryOut = true
        mFlipper!!.addView(note)
    }

    override fun getLayoutId(): Int {
        return R.layout.fragment_home_wallet
    }


    override fun initPresenter(): BasePresenter<*, *> {
        return WalletPresenter.newInstance()
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    override fun onClick(v: View) {
        when (v.id) {
            R.id.assets_hint -> {
                // TODO 18/11/28
                SPUtils.getInstance().put(READ_MSG, true)
                startActivityForResult(Intent(activity, MsgActivity::class.java), 200)
            }
            R.id.assets_add ->
                // TODO 18/11/28
                startActivity(Intent(activity, IncreaseCurrencyActivity::class.java))
            R.id.assets_price, R.id.assets_type ->
                // TODO 18/11/28
                if (mPopView != null) {
                    if (mPopView.isShowing) {
                        mPopView.dismiss()
                    } else {
                        mPopView.showAsDropDown(mTypeAssets, -mTypeAssets.width + mTypeAssets.width / 3, 0, Gravity.CENTER)
                        ViewDrawUtils.setRigthDraw(ContextCompat.getDrawable(activity, R.drawable.down_icon), mTypeAssets)
                    }
                }
            R.id.assets_sign_in -> mPresenter.putSignIn()
            R.id.assets_browser -> startActivity(Intent(activity, BlockchainBrowserActivity::class.java))
        }
    }

    @Subscribe
    fun updateAssets(listEvent: WalletAssetsListEvent) {
        mPresenter.getAssetList()
    }

    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
        if (!isVisibleToUser && mFlipper != null) {
            mFlipper!!.stopFlipping()
        }
        if (isVisibleToUser && createCarryOut) {
            onRefresh()
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Subscribe
    fun updateRate(fragmentEvent: WalletFragmentEvent) {
        val position = fragmentEvent.position
        changeRate(position)
    }

    override fun initData() {
        super.initData()
        if (SPUtils.getInstance().getLong(MSG_TIME) == -1L) {
            SPUtils.getInstance().put(MSG_TIME, System.currentTimeMillis())
        }
        JPushInterface.requestPermission(activity)
        mRvAssets.layoutManager = LinearLayoutManager(activity)
        assetsAdapter = WalletAssetsAdapter(R.layout.item_home_assets_type, mData)
        assetsAdapter.setOnItemChildClickListener { adapter, view, position ->
            when (view.id) {
                R.id.item_assets_layout -> {
                    val intent = Intent(activity, HistoryActivity::class.java)
                    var type = 0
                    val currencyBean = JsonHelper.stringToJson(SPUtils.getInstance().getString(CURRENCY_LIST), CurrencyBean::class.java) as CurrencyBean
                    for (i in 0 until currencyBean.data.size) {
                        if (mData[position].tokenId == currencyBean.data[i].tokenId) {
                            type = currencyBean.data[i].tokenType
                            break
                        }
                    }
                    intent.putExtra("type", type)
                    intent.putExtra("tokenId", mData[position].tokenId)
                    intent.putExtra("tokenName", mData[position].tokenName)
                    intent.putExtra("data", mData[position])
                    intent.putExtra("rate_type", mTypeAssets.text.toString())
                    startActivity(intent)
                }
            }
        }
        mAssetsLayout.attachTo(mRvAssets)
        mRvAssets.adapter = assetsAdapter
        mPresenter.getAssetList()
        mPresenter.getWhetherToSignIn()
        mPresenter.getBanner()
        val msg_time = SPUtils.getInstance().getLong(MSG_TIME)
        mPresenter.getMsg(if (msg_time == -1L) System.currentTimeMillis() else msg_time, 0, 1)
        val defalutBean = JsonHelper.stringToJson(defalutRate, ExchangeRateBean.DataBean::class.java) as ExchangeRateBean.DataBean
        if (defalutBean != null) {
            val default_type = defalutBean.name
            mTypeAssets.text = default_type.substring(1, default_type.length)
        }
    }

    private fun initPop() {
        val content = ArrayList<String>()
        val rate_list = SPUtils.getInstance().getString(RATE_LIST)
        if (rate_list != "") {
            val rateBean = JsonHelper.stringToJson(rate_list, ExchangeRateBean::class.java) as ExchangeRateBean
            for (dataBean in rateBean.data) {
                content.add(dataBean.name)
                mExchange.add(dataBean)
            }
            mPopView = PopViewHelper.instance.create(activity, R.layout.layout_rate_pop, content, this)
        }
    }

    override fun bannerList(bannerBean: ArrayList<BannerBean.DataBean>) {
        mFlipper!!.removeAllViews()
        for (dataBean in bannerBean) {
            var note = LayoutInflater.from(activity).inflate(R.layout.layout_wellet_announcement, null)
            note.findViewById<TextView>(R.id.note_tv).text = dataBean.title
            note.setOnClickListener {
                startActivity(Intent(activity, BannerActivity::class.java).putExtra("id", dataBean.id))
            }
            mFlipper!!.addView(note)
        }
        mFlipper!!.startFlipping()
    }


    override fun refreshAssetList(asset: AssetListBean) {
        SPUtils.getInstance().put(ASSETS_LIST, JsonHelper.jsonToString(asset))
        val defalutBean = JsonHelper.stringToJson(defalutRate, ExchangeRateBean.DataBean::class.java) as ExchangeRateBean.DataBean
        if (defalutBean != null) {
            val default_type = defalutBean.name
            mTypeAssets.text = default_type.substring(1, default_type.length)
        }
        mPresenter.getAllAsset()
        mNullAssets.visibility = View.GONE
        mRvAssets.visibility = View.VISIBLE
        mData.clear()
        mData.addAll(asset.data)
        mSwipAsstes.post { mSwipAsstes.isRefreshing = false }
        assetsAdapter.notifyDataSetChanged()
        initPop()
    }

    /**
     * 刷新总资产余额
     *
     * @param allAssetBean
     */
    override fun refreshAllAsset(allAssetBean: AllAssetBean) {
        SPUtils.getInstance().put(ALLASSETS, JsonHelper.jsonToString(allAssetBean))
        mPriceAssets.text = TextUtils.rateToPrice(allAssetBean.data)
    }

    override fun refreshMsg(msgBean: MsgBean) {
        if (msgBean.data.size > 0) {
            mMsgBean.clear()
            mMsgBean.addAll(msgBean.data)
            mHintAssets.setImageResource(R.drawable.home_newnote_icon)
        } else {
            if (SPUtils.getInstance().getBoolean(READ_MSG, false)) {
                mHintAssets.setImageResource(R.drawable.home_note_icon)
            }
        }
    }

    override fun showSignin(isSignin: Boolean) {
        if (!isSignin && mSignInView.visibility == View.INVISIBLE) {
            val width = mSignInView.width.toFloat()
            mSignInView.visibility = View.VISIBLE
            infoLayoutStartAnimation(mAssetsLayout.width + width, mAssetsLayout.width - width + ConvertUtils.dp2px(3f))
        }
    }

    override fun signRequest(isSignin: Boolean) {
        if (isSignin && mSignInView.visibility == View.VISIBLE) {
            val dialog = Dialog(activity, R.style.translationDialog)
            val dialogView = LayoutInflater.from(activity).inflate(R.layout.layout_signin_dialog, null)
            val cancelView = dialogView.findViewById<ImageView>(R.id.dialog_cancel)
            cancelView.setOnClickListener { v -> dialog.dismiss() }
            dialog.setContentView(dialogView)
            dialog.show()
            val width = mSignInView.width.toFloat()
            val xAnimation = ObjectAnimator.ofFloat(mSignInView, "X", mAssetsLayout.width - width + ConvertUtils.dp2px(3f), mAssetsLayout.width + width)
            xAnimation.addListener(object : AnimatorListenerAdapter() {
                override fun onAnimationEnd(animation: Animator) {
                    mSignInView.visibility = View.INVISIBLE
                    super.onAnimationEnd(animation)
                }
            })
            xAnimation.duration = 200
            xAnimation.start()
        } else {
            ToastUtils.showLong("签到失败")
        }
    }

    private fun infoLayoutStartAnimation(startX: Float, endX: Float) {
        val xAnimation = ObjectAnimator.ofFloat(mSignInView, "X", startX, endX)
        xAnimation.duration = 200
        xAnimation.start()
    }

    override fun showNullAsset() {
        mNullAssets.visibility = View.VISIBLE
        mRvAssets.visibility = View.GONE
    }

    /**
     * 服务器出错/网络异常 都会走这里，此时的数据为本地缓存
     */
    override fun serverError() {
        val asset_list = SPUtils.getInstance().getString(ASSETS_LIST)
        val allAsset = SPUtils.getInstance().getString(ALLASSETS)
        initPop()
        if (asset_list != "") {
            val assetListBean = JsonHelper.stringToJson(asset_list, AssetListBean::class.java) as AssetListBean
            mData.clear()
            mData.addAll(assetListBean.data)
            assetsAdapter.notifyDataSetChanged()
            mNullAssets.visibility = View.GONE
            mRvAssets.visibility = View.VISIBLE
        } else {
            mNullAssets.visibility = View.VISIBLE
            mRvAssets.visibility = View.GONE
        }
        if (allAsset != "") {
            val allAssetBean = JsonHelper.stringToJson(allAsset, AllAssetBean::class.java) as AllAssetBean
            mPriceAssets.text = TextUtils.rateToPrice(allAssetBean.data)
        }
        mSwipAsstes.post { mSwipAsstes.isRefreshing = false }
    }

    /**
     * 刷新接口
     */
    fun onRefresh() {
        mPresenter.getAllAsset()
        mPresenter.getAssetList()
        mPresenter.getBanner()
        mPresenter.getWhetherToSignIn()
        val msg_time = SPUtils.getInstance().getLong(MSG_TIME)
        mPresenter.getMsg(if (msg_time == -1L) System.currentTimeMillis() else msg_time, 0, 1)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mSwipAsstes.post { mSwipAsstes.isRefreshing = false }
    }

    /**
     * 异步刷新接口
     */
    @Subscribe
    fun msgRefresh(msgEvent: WalletMsgEvent) {
        mPresenter.getAllAsset()
        mPresenter.getAssetList()
        mPresenter.getBanner()
        mPresenter.getWhetherToSignIn()
        val msg_time = SPUtils.getInstance().getLong(MSG_TIME)
        mPresenter.getMsg(if (msg_time == -1L) System.currentTimeMillis() else msg_time, 0, 1)
    }

    /**
     * 设置汇率的时候会回调该方法
     *
     * @param position
     */
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    override fun changeRate(position: Int) {
        val dataBean = mExchange[position]
        SPUtils.getInstance().put(SET_RATE, JsonHelper.jsonToString(dataBean))
        val symbol = dataBean.name
        val newSymbol = symbol.substring(0, 1)
        SPUtils.getInstance().put(DEFAULT_SYMBOL, "$newSymbol ")
        val default_type = dataBean.name
        mTypeAssets.text = default_type.substring(1, default_type.length)
        changeAssets()
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    override fun dismiss() {
        ViewDrawUtils.setRigthDraw(ContextCompat.getDrawable(activity, R.drawable.up_icon), mTypeAssets)
    }

    /**
     * 设置汇率之后需要更改金额
     */
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private fun changeAssets() {
        ViewDrawUtils.setRigthDraw(ContextCompat.getDrawable(activity, R.drawable.up_icon), mTypeAssets)
        val assetBean = JsonHelper.stringToJson(SPUtils.getInstance().getString(ALLASSETS), AllAssetBean::class.java) as AllAssetBean
        mPriceAssets.text = TextUtils.rateToPrice(assetBean.data)
        assetsAdapter.notifyDataSetChanged()
        EventBus.getDefault().post(TrandFragmentEvent())
    }

    override fun onDestroy() {
        super.onDestroy()
        EventBus.getDefault().unregister(this)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 200) {
            if (mMsgBean != null && mMsgBean.size > 0) {
                SPUtils.getInstance().put(MSG_TIME, mMsgBean[0].createdAt)
            }
            SPUtils.getInstance().put(READ_MSG, false)
            mHintAssets.setImageResource(R.drawable.home_note_icon)
        }
    }
}
