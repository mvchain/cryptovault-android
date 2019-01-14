package com.mvc.cryptovault_android.activity

import com.blankj.utilcode.util.SPUtils
import com.gyf.barlibrary.ImmersionBar
import com.mvc.cryptovault_android.R
import com.mvc.cryptovault_android.api.ApiStore
import com.mvc.cryptovault_android.base.BaseActivity
import com.mvc.cryptovault_android.common.Constant.SP.REG_INVITATION
import com.mvc.cryptovault_android.common.Constant.SP.REG_TEMPTOKEN
import com.mvc.cryptovault_android.utils.RetrofitUtils
import com.mvc.cryptovault_android.utils.RxHelper
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_mine_mnemonics.*
import org.json.JSONObject

class VerificationMnemonicActivity : BaseActivity() {

    override fun getLayoutId(): Int {
        return R.layout.activity_verification_mnomonic
    }

    override fun initData() {
        var email = JSONObject(SPUtils.getInstance().getString(REG_INVITATION)).getString("email")
        RetrofitUtils.client(ApiStore::class.java).getUserMnemonic(email)
                .compose(RxHelper.rxSchedulerHelper())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe()
        back.setOnClickListener { finish() }
    }

    override fun initView() {
        ImmersionBar.with(this).titleBar(R.id.status_bar).statusBarDarkFont(true).init()
    }
}
