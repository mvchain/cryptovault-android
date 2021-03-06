package com.mvc.cryptovault_android.activity

import android.annotation.SuppressLint
import android.view.View
import com.blankj.utilcode.util.LogUtils
import com.gyf.barlibrary.ImmersionBar
import com.mvc.cryptovault_android.R
import com.mvc.cryptovault_android.base.BaseMVPActivity
import com.mvc.cryptovault_android.base.BasePresenter
import com.mvc.cryptovault_android.bean.BlockOrderOnIdBean
import com.mvc.cryptovault_android.contract.AssetsDetailContract
import com.mvc.cryptovault_android.presenter.AssetsDetailPresenter
import com.mvc.cryptovault_android.utils.TextUtils
import kotlinx.android.synthetic.main.activity_block_assets_detail.*

class BlockAssetsDetailActivity : BaseMVPActivity<AssetsDetailContract.AssetsDetailPresenter>(), AssetsDetailContract.AssetsDetailView {
    private var id = 0

    @SuppressLint("SetTextI18n")
    override fun detailSuccess(blockOrderOnIdBean: BlockOrderOnIdBean.DataBean) {
        group.visibility = View.VISIBLE
        if (blockOrderOnIdBean.classify == 0) {
            type_coin_buyer.visibility = View.GONE
            type_coin_purchase.visibility = View.GONE
            type_coin_seller.visibility = View.GONE
            type_coin_selling.visibility = View.GONE
            type_transfer_content.text = blockOrderOnIdBean.from
            type_receipt_content.text = blockOrderOnIdBean.to
            type_transfer_price_content.text = "${TextUtils.doubleToFour(blockOrderOnIdBean.buyValue)} ${blockOrderOnIdBean.buyTokenName}"
            detail_title.text = "转账"
        } else {
            type_transfer.visibility = View.GONE
            type_receipt.visibility = View.GONE
            type_transfer_price.visibility = View.GONE
            type_coin_buyer_content.text = blockOrderOnIdBean.to
            type_coin_purchase_content.text = "${TextUtils.doubleToFour(blockOrderOnIdBean.buyValue)} ${blockOrderOnIdBean.buyTokenName}"
            type_coin_seller_content.text = blockOrderOnIdBean.from
            type_coin_selling_content.text = "${TextUtils.doubleToFour(blockOrderOnIdBean.sellValue)} ${blockOrderOnIdBean.buyTokenName}"
            detail_title.text = "币币交易"
        }
    }

    override fun detailFailed(msg: String) {
        LogUtils.e(msg)
    }

    override fun initPresenter(): BasePresenter<*, *> {
        return AssetsDetailPresenter.newInstance()
    }

    override fun initMVPData() {
        mPresenter.getAssetsDetailToId(id)
    }

    override fun getLayoutId(): Int {
        return R.layout.activity_block_assets_detail
    }

    override fun initData() {
    }

    override fun initView() {
    }

    override fun initMVPView() {
        ImmersionBar.with(this).statusBarView(R.id.status_bar).statusBarDarkFont(true).init()
        id = intent.getIntExtra("id", 0)
    }

    override fun startActivity() {

    }
}