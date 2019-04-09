package com.mvc.cryptovault_android.activity

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import com.chad.library.adapter.base.BaseQuickAdapter
import com.gyf.barlibrary.ImmersionBar
import com.mvc.cryptovault_android.MyApplication
import com.mvc.cryptovault_android.R
import com.mvc.cryptovault_android.adapter.rvAdapter.CheckMnemonicsAdapter
import com.mvc.cryptovault_android.adapter.rvAdapter.SortMnemonicsAdapter
import com.mvc.cryptovault_android.api.ApiStore
import com.mvc.cryptovault_android.base.BaseActivity
import com.mvc.cryptovault_android.bean.VerificationMnemonicBean
import com.mvc.cryptovault_android.event.PayPwdRefreshEvent
import com.mvc.cryptovault_android.utils.RetrofitUtils
import com.mvc.cryptovault_android.utils.RxHelper
import com.mvc.cryptovault_android.view.DialogHelper
import kotlinx.android.synthetic.main.activity_reset_verification_mnomonic.*
import okhttp3.MediaType
import okhttp3.RequestBody
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.json.JSONObject
import java.net.SocketTimeoutException
import java.util.*

class ResetPasswordVerificationMnemonicsActivity : BaseActivity(), BaseQuickAdapter.OnItemChildClickListener {
    private lateinit var checkMnomonic: ArrayList<VerificationMnemonicBean>
    private lateinit var checkAdapter: CheckMnemonicsAdapter
    private lateinit var sortMnomonic: ArrayList<VerificationMnemonicBean>
    private lateinit var sortAdapter: SortMnemonicsAdapter
    private lateinit var list: ArrayList<String>
    private var dialogHelper: DialogHelper? = null
    private lateinit var email: String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        EventBus.getDefault().register(this)
    }

    override fun onDestroy() {
        EventBus.getDefault().unregister(this)
        super.onDestroy()
    }
    override fun getLayoutId(): Int {
        return R.layout.activity_reset_verification_mnomonic
    }

    fun onClick(v: View) {
        when (v.id) {
            R.id.back -> {
                finish()
            }
            R.id.submit -> {
                var sb = StringBuffer()
                for (mnomonic in checkMnomonic) {
                    sb.append("${mnomonic.content},")
                }
                var mnemonics = sb.toString().subSequence(0, sb.toString().length - 1)
                dialogHelper!!.create(this, R.drawable.pending_icon_1, "校验助记词中").show()
                var json = JSONObject()
                json.put("email", email)
                json.put("resetType", 2)
                json.put("value", mnemonics)
                var body = RequestBody.create(MediaType.parse("text/html"), json.toString())
                RetrofitUtils.client(MyApplication.getBaseUrl(),ApiStore::class.java)
                        .updatePassword(body)
                        .compose(RxHelper.rxSchedulerHelper())
                        .subscribe({ httpToken ->
                            if (httpToken.code === 200) {
                                dialogHelper?.dismissDelayed(null, 0)
                                var tokenIntent = intent
                                tokenIntent.putExtra("token", httpToken.data)
                                startActivity(ResetPasswordActivity::class.java, tokenIntent)
                            } else {
                                dialogHelper!!.resetDialogResource(this, R.drawable.miss_icon, httpToken.message)
                                dialogHelper!!.dismissDelayed(null)
                            }
                        }, { error ->
                            if (error is SocketTimeoutException) {
                                dialogHelper!!.resetDialogResource(this, R.drawable.pending_icon_1, "连接超时")
                            }else{
                                dialogHelper!!.resetDialogResource(this, R.drawable.pending_icon_1, error.message!!)
                            }
                            dialogHelper!!.dismissDelayed(null)
                        })
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
        initOutOrderToSort(list)
    }

    private fun initOutOrderToSort(list: ArrayList<String>) {
        var index = 0
        var random = Random()
        while (list.size > 0) {
            var rd = Math.abs(random.nextInt() % list.size)
            sortMnomonic.add(VerificationMnemonicBean(list[rd], index, false))
            list.removeAt(rd)
            index++
        }
    }

    override fun initView() {
        var emailIntent = intent
        email = emailIntent.getStringExtra("email")
        list = intent.getStringArrayListExtra("menmonicss")
        ImmersionBar.with(this).titleBar(R.id.status_bar).statusBarDarkFont(true).init()
        dialogHelper = DialogHelper.instance
        checkMnomonic = ArrayList()
        sortMnomonic = ArrayList()
        checkAdapter = CheckMnemonicsAdapter(R.layout.item_mnemonics_rv, checkMnomonic)
        sortAdapter = SortMnemonicsAdapter(R.layout.item_verification_mnemonics_rv, sortMnomonic)
        sortAdapter.onItemChildClickListener = this
        checkAdapter.onItemChildClickListener = this
        mnemonics_sort_list.adapter = sortAdapter
        mnemonics_check_list.adapter = checkAdapter
    }
    @Subscribe
    public fun updatePaySuccess(pay : PayPwdRefreshEvent){
        finish()
    }
}