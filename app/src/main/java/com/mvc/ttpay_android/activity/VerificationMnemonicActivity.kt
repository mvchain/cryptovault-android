package com.mvc.ttpay_android.activity

import android.annotation.SuppressLint
import android.content.Intent
import android.view.View
import cn.jpush.android.api.JPushInterface
import com.blankj.utilcode.util.LogUtils
import com.blankj.utilcode.util.SPUtils
import com.chad.library.adapter.base.BaseQuickAdapter
import com.gyf.barlibrary.ImmersionBar
import com.mvc.ttpay_android.MainActivity
import com.mvc.ttpay_android.MyApplication
import com.mvc.ttpay_android.R
import com.mvc.ttpay_android.adapter.rvAdapter.CheckMnemonicsAdapter
import com.mvc.ttpay_android.adapter.rvAdapter.SortMnemonicsAdapter
import com.mvc.ttpay_android.api.ApiStore
import com.mvc.ttpay_android.base.BaseActivity
import com.mvc.ttpay_android.bean.TagBean
import com.mvc.ttpay_android.bean.VerificationMnemonicBean
import com.mvc.ttpay_android.common.Constant.SP.*
import com.mvc.ttpay_android.utils.RetrofitUtils
import com.mvc.ttpay_android.utils.RxHelper
import com.mvc.ttpay_android.view.DialogHelper
import kotlinx.android.synthetic.main.activity_verification_mnomonic.*
import okhttp3.MediaType
import okhttp3.RequestBody
import org.json.JSONObject
import java.net.SocketTimeoutException
import java.util.*
import kotlin.collections.HashSet

class VerificationMnemonicActivity : BaseActivity(), BaseQuickAdapter.OnItemChildClickListener {
    private lateinit var checkMnomonic: ArrayList<VerificationMnemonicBean>
    private lateinit var checkAdapter: CheckMnemonicsAdapter
    private lateinit var sortMnomonic: ArrayList<VerificationMnemonicBean>
    private lateinit var sortAdapter: SortMnemonicsAdapter
    private lateinit var list: ArrayList<String>
    private var dialogHelper: DialogHelper? = null

    private var email = SPUtils.getInstance().getString(REG_EMAIL)
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

    override fun getLayoutId(): Int {
        return R.layout.activity_verification_mnomonic
    }

    @SuppressLint("CheckResult")
    override fun initData() {
        initOutOrderToSort(list)
        back.setOnClickListener { finish() }
        submit.setOnClickListener {
            dialogHelper!!.create(this, R.drawable.pending_icon_1, "校验助记词中").show()
            var sb = StringBuffer()
            for (mnomonic in checkMnomonic) {
                sb.append("${mnomonic.content},")
            }
            var mnemonics = sb.toString().subSequence(0, sb.toString().length - 1)
            var bodyJson = JSONObject()
            bodyJson.put("email", email)
            bodyJson.put("mnemonics", mnemonics)
            val body = RequestBody.create(MediaType.parse("text/html"), bodyJson.toString())
            RetrofitUtils.client(MyApplication.getBaseUrl(), ApiStore::class.java).postUserMnemonic(body)
                    .compose(RxHelper.rxSchedulerHelper())
                    .subscribe({ loginBean ->
                        if (loginBean.code == 200) {
                            val data = loginBean.data
                            SPUtils.getInstance().put(REFRESH_TOKEN, data.refreshToken)
                            SPUtils.getInstance().put(TOKEN, data.token)
                            SPUtils.getInstance().put(USER_ID, data.userId)
                            SPUtils.getInstance().put(USER_EMAIL, data.email)
                            SPUtils.getInstance().put(USER_PUBLIC_KEY, data.publicKey)
                            SPUtils.getInstance().put(USER_SALT,data.salt)
                            SPUtils.getInstance().put(USER_GOOGLE,data.googleCheck)
                            MyApplication.setTOKEN(data.token)
                            RetrofitUtils.client(MyApplication.getBaseUrl(), ApiStore::class.java).getPushTag(MyApplication.getTOKEN()).compose<TagBean>(RxHelper.rxSchedulerHelper<TagBean>())
                                    .subscribe({ tagBean ->
                                        if (tagBean.getCode() == 200 && tagBean.getData() != null) {
                                            SPUtils.getInstance().put(TAG_NAME, tagBean.getData())
                                            val tags = tagBean.data.split(",".toRegex()).toTypedArray()
                                            for (i in tags.indices) {
                                                if (i == 0) {
                                                    JPushInterface.setTags(application.applicationContext, Integer.parseInt(tags[i]), HashSet(Arrays.asList(tags[i])))
                                                } else {
                                                    JPushInterface.addTags(application.applicationContext, Integer.parseInt(tags[i]), HashSet(Arrays.asList(tags[i])))
                                                }
                                            }
                                        }
                                        JPushInterface.setAlias(applicationContext, loginBean.data.userId, loginBean.data.userId.toString())
                                    }, { throwable -> LogUtils.e("VerificationMnemonicActivity", throwable.message) })
                            dialogHelper!!.resetDialogResource(this, R.drawable.success_icon, "校验成功，登录中")
                            dialogHelper?.dismissDelayed(object : DialogHelper.IDialogDialog {
                                override fun callback() {
                                    SPUtils.getInstance().remove(REG_TEMPTOKEN)
                                    SPUtils.getInstance().remove(REG_INVITATION)
                                    SPUtils.getInstance().remove(REG_MINEMNEMONICS)
                                    SPUtils.getInstance().remove(REG_PRIVATEKEY)
                                    SPUtils.getInstance().remove(REG_REGISTER)
                                    val intent = Intent(this@VerificationMnemonicActivity, MainActivity::class.java)
                                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
                                    startActivity(intent)
                                }
                            })
                        } else {
                            dialogHelper!!.resetDialogResource(this, R.drawable.miss_icon, loginBean.message)
                            dialogHelper!!.dismissDelayed(null)
                        }
                    }, { error ->
                        if (error is SocketTimeoutException) {
                            dialogHelper!!.resetDialogResource(this, R.drawable.pending_icon_1, getString(R.string.connection_timed_out))
                        } else {
                            dialogHelper!!.resetDialogResource(this, R.drawable.pending_icon_1, error.message!!)
                        }
                        dialogHelper!!.dismissDelayed(null)
                    })
        }
    }

    fun initOutOrderToSort(list: ArrayList<String>) {
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
        ImmersionBar.with(this).titleBar(R.id.status_bar).statusBarDarkFont(true).init()
        dialogHelper = DialogHelper.instance
        list = intent.getStringArrayListExtra("menmonicss")
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
