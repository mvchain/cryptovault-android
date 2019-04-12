package com.mvc.ttpay_android.activity

import android.content.Intent
import com.gyf.barlibrary.ImmersionBar
import com.mvc.ttpay_android.R
import com.mvc.ttpay_android.base.BaseMVPActivity
import com.mvc.ttpay_android.base.BasePresenter
import com.mvc.ttpay_android.contract.IPublicKeyContract
import com.mvc.ttpay_android.presenter.PublicKeyPresenter
import kotlinx.android.synthetic.main.activity_public_key.*

class PublicKeyActivity : BaseMVPActivity<IPublicKeyContract.IPublicKeyPresenter>(), IPublicKeyContract.IPublicKeyView {
    private lateinit var publicKey: String

    override fun initPresenter(): BasePresenter<*, *> {
        return PublicKeyPresenter.newInstance()
    }

    override fun initMVPData() {
    }

    override fun getLayoutId(): Int {
        return R.layout.activity_public_key
    }

    override fun initData() {

    }

    override fun initView() {

    }

    override fun initMVPView() {
        ImmersionBar.with(this).statusBarView(R.id.status_bar).statusBarDarkFont(true).init()
        publicKey = intent.getStringExtra("publicKey")
        public_key.text = publicKey
        public_back.setOnClickListener {
            finish()
        }
        public_assets.setOnClickListener {
            var intent = Intent(this, BlockAssetsActivity::class.java)
            intent.putExtra("type", 0)
            intent.putExtra("publicKey", public_key.text.toString())
            startActivity(intent)
        }
        public_transfer.setOnClickListener {
            var intent = Intent(this, BlockAssetsActivity::class.java)
            intent.putExtra("type", 1)
            intent.putExtra("publicKey", public_key.text.toString())
            startActivity(intent)
        }


    }

    override fun startActivity() {
    }
}