package com.mvc.cryptovault_android.activity

import android.annotation.SuppressLint
import android.view.View
import com.blankj.utilcode.util.LogUtils
import com.chad.library.adapter.base.BaseQuickAdapter
import com.gyf.barlibrary.ImmersionBar
import com.mvc.cryptovault_android.R
import com.mvc.cryptovault_android.adapter.rvAdapter.CheckMnemonicsAdapter
import com.mvc.cryptovault_android.adapter.rvAdapter.SortMnemonicsAdapter
import com.mvc.cryptovault_android.api.ApiStore
import com.mvc.cryptovault_android.base.BaseActivity
import com.mvc.cryptovault_android.bean.VerificationMnemonicBean
import com.mvc.cryptovault_android.utils.RetrofitUtils
import com.mvc.cryptovault_android.utils.RxHelper
import com.mvc.cryptovault_android.view.DialogHelper
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_language.*
import kotlinx.android.synthetic.main.activity_verification_mnomonic.*
import kotlinx.android.synthetic.main.activity_verification_password.*
import java.util.ArrayList

class ResetPasswordVerificationMnemonicsActivity : BaseActivity(), BaseQuickAdapter.OnItemChildClickListener {
    private lateinit var checkMnomonic: ArrayList<VerificationMnemonicBean>
    private lateinit var checkAdapter: CheckMnemonicsAdapter
    private lateinit var sortMnomonic: ArrayList<VerificationMnemonicBean>
    private lateinit var sortAdapter: SortMnemonicsAdapter
    private var dialogHelper: DialogHelper? = null
    private lateinit var email: String

    override fun getLayoutId(): Int {
        return R.layout.activity_reset_verification_mnomonic
    }

    fun onClick(v:View){
        when(v.id){
            R.id.back->{
                finish()
            }
            R.id.submit->{
                var sb = StringBuffer()
                for (mnomonic in checkMnomonic) {
                    sb.append("${mnomonic.content},")
                }
                var mnemonics = sb.toString().subSequence(0, sb.toString().length - 1)
                var mneIntent = intent
                mneIntent.putExtra("account",email)
                mneIntent.putExtra("value",mnemonics)
                startActivity(ResetPasswordActivity::class.java,mneIntent)
            }
        }
    }
    /**
     * 点击事件
     */
    override fun onItemChildClick(adapter: BaseQuickAdapter<*, *>?, view: View?, position: Int) {
        when (view?.id) {
            R.id.layout -> {
                var check = sortMnomonic[position].isCheck
                if (!check) {
                    sortMnomonic[position].isCheck = true
                    checkMnomonic.add(sortMnomonic[position])
                    sortAdapter.notifyDataSetChanged()
                    checkAdapter.notifyDataSetChanged()
                }
            }
            R.id.content -> {
                var checkPosition = checkMnomonic[position].position
                for (sort in sortMnomonic.indices) {
                    if (checkPosition === sortMnomonic[sort].position) {
                        sortMnomonic[sort].isCheck = false
                        sortAdapter.notifyDataSetChanged()
                        break
                    }
                }
                checkMnomonic.removeAt(position)
                checkAdapter.notifyDataSetChanged()
            }
        }
    }

    @SuppressLint("CheckResult")
    override fun initData() {
        RetrofitUtils.client(ApiStore::class.java).getUserMnemonic(email)
                .compose(RxHelper.rxSchedulerHelper())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ upset ->
                    if (upset.code === 200) {
                        for (currentPosition in upset.data.indices) {
                            var data = upset.data
                            sortMnomonic.add(VerificationMnemonicBean(data[currentPosition], currentPosition, false))
                        }
                        sortAdapter.notifyDataSetChanged()
                    }
                }, { throwavle ->
                    LogUtils.e(throwavle.message)
                })
    }

    override fun initView() {
        var emailIntent = intent
        email = emailIntent.getStringExtra("account")
        ImmersionBar.with(this).titleBar(R.id.status_bar).statusBarDarkFont(true).init()
        dialogHelper = DialogHelper.getInstance()
        checkMnomonic = ArrayList()
        sortMnomonic = ArrayList()
        checkAdapter = CheckMnemonicsAdapter(R.layout.item_mnemonics_rv, checkMnomonic)
        sortAdapter = SortMnemonicsAdapter(R.layout.item_verification_mnemonics_rv, sortMnomonic)
        sortAdapter.onItemChildClickListener = this
        checkAdapter.onItemChildClickListener = this
        mnemonics_sort_list.adapter = sortAdapter
        mnemonics_check_list.adapter = checkAdapter
    }
}