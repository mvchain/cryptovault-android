package com.mvc.ttpay_android.activity

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.graphics.drawable.BitmapDrawable
import android.text.InputFilter
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.EditText
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.PopupWindow
import android.widget.TextView

import com.blankj.utilcode.util.EncryptUtils
import com.blankj.utilcode.util.KeyboardUtils
import com.blankj.utilcode.util.LogUtils
import com.blankj.utilcode.util.SPUtils
import com.blankj.utilcode.util.ToastUtils
import com.gyf.barlibrary.ImmersionBar
import com.mvc.ttpay_android.MyApplication
import com.mvc.ttpay_android.R
import com.mvc.ttpay_android.api.ApiStore
import com.mvc.ttpay_android.base.BaseActivity
import com.mvc.ttpay_android.bean.RecorBean
import com.mvc.ttpay_android.event.RecordingEvent
import com.mvc.ttpay_android.bean.TrandChildBean
import com.mvc.ttpay_android.listener.EditTextChange
import com.mvc.ttpay_android.listener.IPayWindowListener
import com.mvc.ttpay_android.listener.PswMaxListener
import com.mvc.ttpay_android.utils.PointLengthFilter
import com.mvc.ttpay_android.utils.RetrofitUtils
import com.mvc.ttpay_android.utils.RxHelper
import com.mvc.ttpay_android.utils.TextUtils
import com.mvc.ttpay_android.view.DialogHelper
import com.mvc.ttpay_android.view.PswText

import org.greenrobot.eventbus.EventBus
import org.json.JSONException
import org.json.JSONObject

import okhttp3.MediaType
import okhttp3.RequestBody

import com.mvc.ttpay_android.common.Constant.SP.RECORDING_TYPE
import com.mvc.ttpay_android.common.Constant.SP.UPDATE_PASSWORD_TYPE
import com.mvc.ttpay_android.common.Constant.SP.USER_EMAIL
import io.reactivex.Observable

class TrandPurhAndSellItemActivity : BaseActivity(), View.OnClickListener {

    private var dialogHelper: DialogHelper? = null
    private var mPopView: PopupWindow? = null
    private var mTitleTrand: TextView? = null
    private var mBackTrand: ImageView? = null
    private var mHistroyTrand: ImageView? = null
    private var mHintPrice: TextView? = null
    private var mPrice: TextView? = null
    private var mPriceVrt: TextView? = null
    private var mNumPrice: TextView? = null
    private var mEditPurh: EditText? = null
    private var mAllPricePurh: TextView? = null
    private var mSubmitPurh: TextView? = null
    private var mVRTHint: TextView? = null
    private var mPurhDialog: Dialog? = null
    private var data: TrandChildBean.DataBean? = null
    private var recorBean: RecorBean.DataBean? = null
    private var type: Int = 0
    private var price: String? = null
    private var pairId: Int = 0
    private var unitPrice: String? = null
    private var allPriceUnit: String? = null
    private var tokenBalance: Double = 0.toDouble()
    private var balance: Double = 0.toDouble()
    private var currentPrice: Double = 0.toDouble()
    private var mHintBusiness: TextView? = null
    private var mNumErrorHint: TextView? = null
    private var mNameBusiness: TextView? = null
    private var mHintRemaining: TextView? = null
    private var mNumRemaining: TextView? = null
    private var mPriceHintSale: TextView? = null
    private var mPriceNumSale: TextView? = null


    override fun getLayoutId(): Int {
        return R.layout.activity_purh_sell_item
    }

    @SuppressLint("CheckResult", "SetTextI18n")
    override fun initData() {
        data = intent.getParcelableExtra("data")
        recorBean = intent.getSerializableExtra("recorBean") as RecorBean.DataBean
        type = intent.getIntExtra("type", 0)
        mTitleTrand!!.text = intent.getStringExtra("unit_price")
        pairId = intent.getIntExtra("id", 0)
        unitPrice = intent.getStringExtra("unit_price")
        allPriceUnit = intent.getStringExtra("allprice_unit")
        mEditPurh!!.hint = getString(R.string.input) + (if (type == 1) getString(R.string.sell) else getString(R.string.buy)) + getString(R.string.number)
        mHintBusiness!!.text = if (type == 1) getString(R.string.buyer) else getString(R.string.seller_2)
        mHintRemaining!!.text = getString(R.string.remaining) + (if (type == 1) getString(R.string.buy) else getString(R.string.sell)) + getString(R.string.the_amount)
        mPriceHintSale!!.text = (if (type == 1)  getString(R.string.buy) else getString(R.string.sell)) + getString(R.string.price)
        mNameBusiness!!.text = recorBean!!.nickname
        price = recorBean!!.price.toString()
        mNumRemaining!!.text = recorBean!!.limitValue.toString() + " MVC"
        mPriceNumSale!!.text = TextUtils.doubleToEight(recorBean!!.price.toDouble()) + " " + allPriceUnit
        this.currentPrice = recorBean!!.price.toDouble()
        mNumPrice!!.text = (if (type == 1) getString(R.string.sell) else getString(R.string.buy)) + getString(R.string.the_amount)
        RetrofitUtils.client(MyApplication.getBaseUrl(), ApiStore::class.java).getTransactionInfo(token, data!!.pairId, type)
                .compose(RxHelper.rxSchedulerHelper())
                .subscribe({ trandPurhBean ->
                    if (trandPurhBean.code == 200) {
                        val data = trandPurhBean.data
                        this.tokenBalance = data.tokenBalance
                        this.balance = data.balance
                        mPrice!!.text = TextUtils.doubleToEight(data.tokenBalance)

                        mPriceVrt!!.text = TextUtils.doubleToEight(data.balance)
                        mHintPrice!!.text = getString(R.string.available) + this@TrandPurhAndSellItemActivity.data!!.tokenName
                        mAllPricePurh!!.text = "0.00000000 " + allPriceUnit!!
                        mPrice!!.text = TextUtils.doubleToEight(data.tokenBalance)
                        mEditPurh!!.filters = arrayOf<InputFilter>(PointLengthFilter())
                        mEditPurh!!.addTextChangedListener(object : EditTextChange() {
                            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                                val numText = s.toString()
                                if (numText != "") {
                                    val num = java.lang.Double.valueOf(numText)
                                    if (num <= 0) {
                                        mNumErrorHint!!.visibility = View.INVISIBLE
                                        mAllPricePurh!!.text = "0.00000000 " + allPriceUnit!!
                                        mSubmitPurh!!.isEnabled = false
                                        mSubmitPurh!!.setBackgroundResource(R.drawable.bg_toge_child_item_tv_blue_nocheck)
                                    } else {
                                        mAllPricePurh!!.text = TextUtils.doubleToEight(recorBean!!.price.toDouble() * num!!) + " " + allPriceUnit
                                        if (num > this@TrandPurhAndSellItemActivity.recorBean!!.limitValue) {
                                            if (type == 1) {
                                                mNumErrorHint!!.visibility = View.VISIBLE
                                                mNumErrorHint!!.text = "${getString(R.string.the_rest_can_be_purchased)}MVC${getString(R.string.insufficient)}"
                                                mSubmitPurh!!.setBackgroundResource(R.drawable.bg_toge_child_item_tv_blue_nocheck)
                                                mSubmitPurh!!.isEnabled = false
                                            } else if (type == 2) {
                                                mNumErrorHint!!.visibility = View.VISIBLE
                                                mNumErrorHint!!.text = "${getString(R.string.the_rest_can_be_purchased)}${this@TrandPurhAndSellItemActivity.data!!.tokenName}\${getString(R.string.insufficient)"
                                                mSubmitPurh!!.setBackgroundResource(R.drawable.bg_toge_child_item_tv_blue_nocheck)
                                                mSubmitPurh!!.isEnabled = false
                                            }
                                        } else {
                                            mNumErrorHint!!.visibility = View.INVISIBLE
                                            mSubmitPurh!!.setBackgroundResource(R.drawable.bg_login_submit)
                                            mSubmitPurh!!.isEnabled = true
                                        }
                                    }
                                } else {
                                    mNumErrorHint!!.visibility = View.INVISIBLE
                                    mAllPricePurh!!.text = "0.00000000 " + allPriceUnit!!
                                    mSubmitPurh!!.isEnabled = false
                                    mSubmitPurh!!.setBackgroundResource(R.drawable.bg_toge_child_item_tv_blue_nocheck)
                                }
                            }
                        })
                    } else {

                    }
                }, { throwable -> LogUtils.e("TrandPurhAndSellItemAct", throwable.message) })
    }

    override fun initView() {
        ImmersionBar.with(this).statusBarView(R.id.status_bar).statusBarDarkFont(true).init()
        dialogHelper = DialogHelper.instance
        mTitleTrand = findViewById(R.id.trand_title)
        mBackTrand = findViewById(R.id.trand_back)
        mHistroyTrand = findViewById(R.id.trand_histroy)
        mHintPrice = findViewById(R.id.price_hint)
        mPrice = findViewById(R.id.price)
        mPriceVrt = findViewById(R.id.vrt_price)
        mHintBusiness = findViewById(R.id.business_hint)
        mNameBusiness = findViewById(R.id.business_name)
        mHintRemaining = findViewById(R.id.remaining_hint)
        mNumRemaining = findViewById(R.id.remaining_num)
        mPriceHintSale = findViewById(R.id.sale_price_hint)
        mPriceNumSale = findViewById(R.id.sale_price_num)
        mNumPrice = findViewById(R.id.price_num)
        mEditPurh = findViewById(R.id.purh_edit)
        mNumErrorHint = findViewById(R.id.num_error_hint)
        mAllPricePurh = findViewById(R.id.purh_all_price)
        mSubmitPurh = findViewById(R.id.purh_submit)
        mVRTHint = findViewById(R.id.vrt_hint)
        mBackTrand!!.setOnClickListener(this)
        mSubmitPurh!!.setOnClickListener(this)
        mHistroyTrand!!.setOnClickListener(this)
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.trand_back -> {
                // TODO 18/12/14
                if (mPopView != null) {
                    KeyboardUtils.hideSoftInput(mPopView!!.contentView.findViewById<View>(R.id.pay_text))
                }
                finish()
            }
            R.id.trand_histroy -> {
                // TODO 18/12/14
                val intent = Intent(this, TrandOrderActivity::class.java)
                intent.putExtra("pairId", data!!.pairId.toString() + "")
                intent.putExtra("transactionType", (type - 1).toString() + "")
                startActivity(intent)
            }
            R.id.purh_submit -> {
                // TODO 18/12/14
                val currentNum = mEditPurh!!.text.toString()
                val currentAllPrice = mAllPricePurh!!.text.toString()
                if (currentNum == "" || java.lang.Double.valueOf(currentNum) <= 0) {
                    dialogHelper!!.create(this, R.drawable.miss_icon, if (type == 1) getString(R.string.the_number_of_shares_sold_is_incorrect) else getString(R.string.incorrect_purchase_quantity)).show()
                    dialogHelper!!.dismissDelayed(null, 2000)
                    return
                }
                val type = if (this.type == 1) allPriceUnit else data!!.pair.substring(data!!.pair.indexOf("/") + 1, data!!.pair.length)
                val numType = if (this.type == 1) data!!.pair.substring(data!!.pair.indexOf("/") + 1, data!!.pair.length) else data!!.pair.substring(0, data!!.pair.indexOf("/"))
                val payNum = currentAllPrice.split(" ".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()[0]
                val buyPrice = if (this.type == 1) TextUtils.doubleToEight(java.lang.Double.parseDouble(currentNum) * currentPrice) else currentNum
                mPopView = createPopWindow(this
                        , R.layout.layout_paycode, if (this.type == 1) getString(R.string.determined_to_sell) else getString(R.string.confirm_purchase)
                        , getString(R.string.total_payment),
                        "${(if (this.type == 1) currentNum else payNum)} ${if (this.type == 1) "MVC" else data!!.tokenName}"
                        , if (this.type == 1) getString(R.string.total_price) else getString(R.string.purchase_quantity), "$buyPrice ${if (this.type == 1) data!!.tokenName else "MVC"}"
                        , if (this.type == 1)  getString(R.string.sell_unit_price) else  getString(R.string.purchase_unit_price), TextUtils.doubleToEight(currentPrice) + " " + allPriceUnit
                        , object : IPayWindowListener {
                    override fun onclick(view: View) {
                        when (view.id) {
                            R.id.pay_close -> {
                                mPopView!!.dismiss()
                                ToastUtils.showLong(getString(R.string.cancel_the_deal))
                            }
                            R.id.pay_text -> {
                                KeyboardUtils.showSoftInput(mPopView!!.contentView.findViewById<View>(R.id.pay_text))
                                setAlpha(0.5f)
                            }
                            R.id.pay_forget -> {
                                SPUtils.getInstance().put(UPDATE_PASSWORD_TYPE, "2")
                                startActivity(ForgetPasswordActivity::class.java)
                                KeyboardUtils.hideSoftInput(mPopView!!.contentView.findViewById<View>(R.id.pay_text))
                            }
                        }
                    }

                    override fun dismiss() {
                        setAlpha(1f)
                    }
                }, PswMaxListener { num ->
                    val email = SPUtils.getInstance().getString(USER_EMAIL)
                    KeyboardUtils.hideSoftInput(mPopView!!.contentView.findViewById<View>(R.id.pay_text))
                    mPurhDialog = dialogHelper!!.create(this@TrandPurhAndSellItemActivity, R.drawable.pending_icon, getString(R.string.publishing))
                    mPurhDialog!!.show()
                    mPopView!!.dismiss()
                    RetrofitUtils.client(MyApplication.getBaseUrl(), ApiStore::class.java).getUserSalt(MyApplication.getTOKEN(), email)
                            .compose(RxHelper.rxSchedulerHelper())
                            .flatMap {
                                Observable.just(it.data)
                            }.subscribe({ salt ->
                                val obj = JSONObject()
                                try {
                                    obj.put("id", pairId)
                                    obj.put("pairId", data!!.pairId)
                                    obj.put("password", EncryptUtils.encryptMD5ToString(salt + EncryptUtils.encryptMD5ToString(num)))
                                    obj.put("price", price)
                                    obj.put("transactionType", this.type)
                                    obj.put("value", currentNum)
                                } catch (e: JSONException) {
                                    e.printStackTrace()
                                }

                                val body = RequestBody.create(MediaType.parse("text/html"), obj.toString())
                                releaseOrder(body)
                            }, { error ->
                                LogUtils.e(error.message)
                            })
                })
                mPopView!!.showAtLocation(mSubmitPurh, Gravity.BOTTOM, 0, 0)
                mPopView!!.contentView.post { KeyboardUtils.showSoftInput(mPopView!!.contentView.findViewById<View>(R.id.pay_text)) }
                setAlpha(0.5f)
            }
        }
    }

    /**
     * 发布购买订单
     *
     * @param body
     */
    @SuppressLint("CheckResult")
    private fun releaseOrder(body: RequestBody) {
        RetrofitUtils.client(MyApplication.getBaseUrl(), ApiStore::class.java).releaseOrder(token, body).compose(RxHelper.rxSchedulerHelper()).subscribe({ updateBean ->
            if (updateBean.code == 200) {
                dialogHelper!!.resetDialogResource(this@TrandPurhAndSellItemActivity, R.drawable.success_icon, (if (type == 1) getString(R.string.sell) else getString(R.string.buy)) + getString(R.string.success))
                EventBus.getDefault().post(RecordingEvent())
                dialogHelper!!.dismissDelayed(object : DialogHelper.IDialogDialog {
                    override fun callback() {
                        finish()
                    }
                }, 2000)
            } else if (updateBean.code == 400) {
                dialogHelper!!.resetDialogResource(this@TrandPurhAndSellItemActivity, R.drawable.miss_icon, updateBean.message)
                dialogHelper!!.dismissDelayed(null, 2000)
            } else {
                dialogHelper!!.resetDialogResource(this@TrandPurhAndSellItemActivity, R.drawable.miss_icon, updateBean.message)
                dialogHelper!!.dismissDelayed(null, 2000)
            }
        }, { throwable ->
            dialogHelper!!.resetDialogResource(this@TrandPurhAndSellItemActivity, R.drawable.miss_icon, throwable.message!!)
            dialogHelper!!.dismissDelayed(null, 2000)
            LogUtils.e("TrandPurhAndSellActivity", throwable.message)
        })
    }


    @SuppressLint("WrongConstant")
    fun createPopWindow(context: Context, layoutId: Int, title: String, childTitle: String, price: String, numhint: String, numtv: String, pricehint: String, pricetv: String, iPayWindowListener: IPayWindowListener?, maxListener: PswMaxListener): PopupWindow {
        val linear = LayoutInflater.from(context.applicationContext).inflate(layoutId, null) as LinearLayout
        linear.findViewById<View>(R.id.line).visibility = View.GONE
        //初始化控件
        val mTitlePay = linear.findViewById<TextView>(R.id.pay_title)
        mTitlePay.text = title
        val mClosePay = linear.findViewById<ImageView>(R.id.pay_close)
        mClosePay.setOnClickListener { v -> iPayWindowListener!!.onclick(v) }
        val mChildTitlePay = linear.findViewById<TextView>(R.id.pay_child_title)
        mChildTitlePay.text = childTitle
        val mPricePay = linear.findViewById<TextView>(R.id.pay_price)
        mPricePay.text = price
        val mAddressPay = linear.findViewById<TextView>(R.id.pay_address)
        mAddressPay.text = numtv
        val mAddressLayoutPay = linear.findViewById<LinearLayout>(R.id.pay_address_layout)
        (mAddressLayoutPay.findViewById<View>(R.id.address) as TextView).text = numhint
        val mFeePay = linear.findViewById<TextView>(R.id.pay_fee)
        mFeePay.text = pricetv
        val mFeeLayoutPay = linear.findViewById<LinearLayout>(R.id.pay_fee_layout)
        (mFeeLayoutPay.findViewById<View>(R.id.price) as TextView).text = pricehint
        val mTextPay = linear.findViewById<PswText>(R.id.pay_text)
        mTextPay.setOnClickListener { v -> iPayWindowListener!!.onclick(v) }
        mTextPay.setMaxListener(maxListener)
        val mForgetPay = linear.findViewById<TextView>(R.id.pay_forget)
        mForgetPay.setOnClickListener { v -> iPayWindowListener!!.onclick(v) }
        val mPopView = PopupWindow(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        mPopView.setOnDismissListener {
            iPayWindowListener?.dismiss()
        }
        mPopView.contentView = linear
        mPopView.isFocusable = true
        mPopView.setBackgroundDrawable(BitmapDrawable())
        mPopView.isOutsideTouchable = false
        mPopView.softInputMode = PopupWindow.INPUT_METHOD_NEEDED or WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE
        return mPopView
    }
}
