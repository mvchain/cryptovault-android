package com.mvc.cryptovault_android.activity

import com.gyf.barlibrary.ImmersionBar
import com.mvc.cryptovault_android.R
import com.mvc.cryptovault_android.base.BaseMVPActivity
import com.mvc.cryptovault_android.base.BasePresenter
import com.mvc.cryptovault_android.contract.BlockAssetsContract
import com.mvc.cryptovault_android.presenter.BlockAssetsPresenter
import kotlinx.android.synthetic.main.activity_block_assets.*

class BlockAssetsActivity : BaseMVPActivity<BlockAssetsContract.BlockAssetsPresenter>(), BlockAssetsContract.BlockAssetsView {


    override fun initPresenter(): BasePresenter<*, *> {
        return BlockAssetsPresenter.newInstance()
    }

    override fun initMVPData() {
    }

    override fun getLayoutId(): Int {
        return R.layout.activity_block_assets
    }

    override fun initData() {
    }

    override fun initView() {

    }

    override fun initMVPView() {
        ImmersionBar.with(this).statusBarView(R.id.status_bar).statusBarDarkFont(true).init()
        var type = intent.getIntExtra("type", 0)
        if (type === 0) {
            assets_type.text = "币种"
            assets_actual.text = "余额"
            assets_title.text = "资产"
        } else {
            assets_type.text = "交易类型"
            assets_actual.text = "交易时间"
            assets_title.text = "交易记录"
        }
    }

    override fun startActivity() {
    }
}