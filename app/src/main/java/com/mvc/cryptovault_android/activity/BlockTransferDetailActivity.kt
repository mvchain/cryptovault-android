package com.mvc.cryptovault_android.activity

import android.annotation.SuppressLint
import com.gyf.barlibrary.ImmersionBar
import com.mvc.cryptovault_android.R
import com.mvc.cryptovault_android.base.BaseMVPActivity
import com.mvc.cryptovault_android.base.BasePresenter
import com.mvc.cryptovault_android.bean.BlockTransferDetailBean
import com.mvc.cryptovault_android.contract.BlockTransferDetailContract
import com.mvc.cryptovault_android.presenter.BlockTransferDetailPresenter
import com.mvc.cryptovault_android.utils.TextUtils
import kotlinx.android.synthetic.main.activity_block_transfer_detail.*
import java.text.SimpleDateFormat
import java.util.*

class BlockTransferDetailActivity : BaseMVPActivity<BlockTransferDetailContract.BlockTransferDetailPresenter>(), BlockTransferDetailContract.BlockTransferDetailView {
    private lateinit var hash: String

    @SuppressLint("SimpleDateFormat", "SetTextI18n")
    override fun transferDetailSuccess(blockTransferDetailBean: BlockTransferDetailBean.DataBean) {
        detail_hash.text = blockTransferDetailBean.hash
        detail_time.text = SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Date(blockTransferDetailBean.createdAt))
        detail_number.text = "${blockTransferDetailBean.confirm}个确认"
        detail_price.text = TextUtils.doubleToFour(blockTransferDetailBean.value)
        detail_receivables.text = blockTransferDetailBean.from
        detail_transfer.text = blockTransferDetailBean.to
        detail_height.text = "${blockTransferDetailBean.height}"
    }

    override fun transferDetailFailed(msg: String) {
        showToast(msg)
    }

    override fun initPresenter(): BasePresenter<*, *> {
        return BlockTransferDetailPresenter.newInstance()
    }

    override fun initMVPData() {
        mPresenter.getTransferDetail(hash)
    }

    override fun getLayoutId(): Int {
        return R.layout.activity_block_transfer_detail
    }

    override fun initData() {

    }

    override fun initView() {

    }

    override fun initMVPView() {
        ImmersionBar.with(this).statusBarView(R.id.status_bar).statusBarDarkFont(true).init()
        hash = intent.getStringExtra("hash")
        detail_back.setOnClickListener { finish() }
    }

    override fun startActivity() {

    }

}