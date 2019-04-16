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
import android.widget.RelativeLayout
import android.widget.TextView
import com.blankj.utilcode.util.*

import com.gyf.barlibrary.ImmersionBar
import com.mvc.ttpay_android.MyApplication
import com.mvc.ttpay_android.R
import com.mvc.ttpay_android.api.ApiStore
import com.mvc.ttpay_android.base.BaseActivity
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

import java.net.SocketTimeoutException

import okhttp3.MediaType
import okhttp3.RequestBody

import com.mvc.ttpay_android.common.Constant.SP.RECORDING_TYPE
import com.mvc.ttpay_android.common.Constant.SP.UPDATE_PASSWORD_TYPE
import com.mvc.ttpay_android.common.Constant.SP.USER_EMAIL
import io.reactivex.Observable
import kotlinx.android.synthetic.main.activity_purh_sell.*

class TrandPurhAndSellActivity : BaseActivity(), View.OnClickListener {

    private lateinit var data: TrandChildBean.DataBean
    private var mTitleTrand: TextView? = null
    private var mBackTrand: ImageView? = null
    private var mHistroyTrand: ImageView? = null
    private var mToolbarAbout: RelativeLayout? = null
    private lateinit var mHintPrice: TextView
    private var mPrice: TextView? = null
    private var mPriceVrt: TextView? = null
    private var mTitlePrice: TextView? = null
    private var mPriceCurrent: TextView? = null
    private var mNumPrice: TextView? = null
    private var mVRTHint: TextView? = null
    private lateinit var mEditPurh: EditText
    private var mPriceHintAll: TextView? = null
    private var mNumErrorHint: TextView? = null
    private var mAllPricePurh: TextView? = null
    private var mSubmitPurh: TextView? = null
    private val isNetWork: Boolean = false
    private var type: Int = 0
    private var unitPrice: String? = null
    private var mPopView: PopupWindow? = null
    private var dialogHelper: DialogHelper? = null
    private var mPurhDialog: Dialog? = null
    private var tokenBalance: Double = 0.toDouble()
    private var balance: Double = 0.toDouble()
    private var currentPricePurh: Double = 0.toDouble()
    private var minLimit: Double = 0.toDouble()

    override fun getLayoutId(): Int {
        return R.layout.activity_purh_sell
    }

    @SuppressLint("CheckResult", "SetTextI18n")
    override fun initData() {
        data = intent.getParcelableExtra("data")
        mTitleTrand!!.text = intent.getStringExtra("title")
        type = intent.getIntExtra("type", 0)
        unitPrice = intent.getStringExtra("unit_price")
        mEditPurh.hint = "输入" + (if (type == 2) "购买" else "出售") + "数量"
        mTitlePrice!!.text = (if (type == 2) "购买" else "出售") + "单价："
        mNumPrice!!.text = (if (type == 2) "购买" else "出售") + "数量"
        mHintPrice.text = "可用${data.tokenName}"
        unit_price.text = data.tokenName
        unit_num.text = "MVC"
        RetrofitUtils.client(MyApplication.getBaseUrl(), ApiStore::class.java).getTransactionInfo(token, data.pairId, type)
                .compose(RxHelper.rxSchedulerHelper())
                .subscribe({ trandPurhBean ->
                    if (trandPurhBean.code == 200) {
                        val data = trandPurhBean.data
                        mPrice!!.text = TextUtils.doubleToEight(data.tokenBalance)
                        mPriceVrt!!.text = TextUtils.doubleToEight(data.balance)
                        mPriceCurrent!!.text = "当前价格" + TextUtils.doubleToEight(data.price) + unitPrice
                        this.tokenBalance = data.tokenBalance
                        this.balance = data.balance
                        this.currentPricePurh = data.price
                        this.minLimit = data.minLimit
                        price_edit.hint = TextUtils.doubleToEight(data.price)
                        mAllPricePurh!!.text = "0.00000000 " + this.data!!.tokenName
                        mPrice!!.text = TextUtils.doubleToEight(data.tokenBalance)
                        mEditPurh.filters = arrayOf<InputFilter>(PointLengthFilter())
                        mEditPurh.addTextChangedListener(object : EditTextChange() {
                            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                                val numText = s.toString()
                                if (numText != "") {
                                    val num = java.lang.Double.valueOf(numText)
                                    if (num <= 0) {
                                        mAllPricePurh!!.text = "0.00000000 " + this@TrandPurhAndSellActivity.data.tokenName
                                        mSubmitPurh!!.isEnabled = false
                                        mSubmitPurh!!.setBackgroundResource(R.drawable.bg_toge_child_item_tv_blue_nocheck)
                                        mNumErrorHint!!.visibility = View.INVISIBLE
                                    } else {
                                        val currentPro = java.lang.Double.valueOf(mEditPurh.text.toString())
                                        var divPrice = price_edit.text.toString()
                                        var allPrice: Double
                                        allPrice = if (divPrice != "") {
                                            val dP = java.lang.Double.valueOf(divPrice)
                                            dP * num!!
                                        } else {
                                            data.price * num!!
                                        }
                                        mAllPricePurh!!.text = TextUtils.doubleToEight(allPrice) + " " + this@TrandPurhAndSellActivity.data!!.tokenName
                                        if (type == 1 && num > this@TrandPurhAndSellActivity.balance) {
                                            mSubmitPurh!!.setBackgroundResource(R.drawable.bg_toge_child_item_tv_blue_nocheck)
                                            mSubmitPurh!!.isEnabled = false
                                            mNumErrorHint!!.visibility = View.VISIBLE
                                            mNumErrorHint!!.text = mVRTHint!!.text.toString() + "不足"
                                        } else if (type == 2 && allPrice > this@TrandPurhAndSellActivity.tokenBalance) {
                                            mSubmitPurh!!.setBackgroundResource(R.drawable.bg_toge_child_item_tv_blue_nocheck)
                                            mSubmitPurh!!.isEnabled = false
                                            mNumErrorHint!!.visibility = View.VISIBLE
                                            mNumErrorHint!!.text = mHintPrice!!.text.toString() + "不足"
                                        } else if (num < minLimit) {
                                            mSubmitPurh!!.setBackgroundResource(R.drawable.bg_toge_child_item_tv_blue_nocheck)
                                            mSubmitPurh!!.isEnabled = false
                                            mNumErrorHint!!.visibility = View.VISIBLE
                                            mNumErrorHint!!.text = "最小挂单数量不可小于$minLimit"
                                        } else {
                                            mNumErrorHint!!.visibility = View.INVISIBLE
                                            mSubmitPurh!!.setBackgroundResource(R.drawable.bg_login_submit)
                                            mSubmitPurh!!.isEnabled = true
                                        }
                                    }
                                } else {
                                    mNumErrorHint!!.visibility = View.INVISIBLE
                                    mAllPricePurh!!.text = "0.00000000 " + this@TrandPurhAndSellActivity.data!!.tokenName!!
                                    mSubmitPurh!!.isEnabled = false
                                    mSubmitPurh!!.setBackgroundResource(R.drawable.bg_toge_child_item_tv_blue_nocheck)
                                }
                            }
                        })
                        price_edit.filters = arrayOf<InputFilter>(PointLengthFilter())
                        price_edit.addTextChangedListener(object : EditTextChange() {
                            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                                var numText = s.toString()
                                if (numText != "") {
                                    val num = java.lang.Double.valueOf(numText)
                                    if (mEditPurh.text.toString() == "" || num <= 0) {
                                        mNumErrorHint!!.visibility = View.INVISIBLE
                                        mAllPricePurh!!.text = "0.00000000 " + this@TrandPurhAndSellActivity.data!!.tokenName
                                        mSubmitPurh!!.isEnabled = false
                                        mSubmitPurh!!.setBackgroundResource(R.drawable.bg_toge_child_item_tv_blue_nocheck)
                                    } else {
                                        val currentPro = java.lang.Double.valueOf(mEditPurh.text.toString())
                                        var divPrice = price_edit.text.toString()
                                        var allPrice: Double
                                        allPrice = if (divPrice != "") {
                                            currentPro * num
                                        } else {
                                            data.price * currentPro
                                        }
                                        mAllPricePurh!!.text = TextUtils.doubleToEight(allPrice) + " " + this@TrandPurhAndSellActivity.data!!.tokenName
                                        if (type == 1 && allPrice > this@TrandPurhAndSellActivity.balance) {
                                            mSubmitPurh!!.setBackgroundResource(R.drawable.bg_toge_child_item_tv_blue_nocheck)
                                            mSubmitPurh!!.isEnabled = false
                                            mNumErrorHint!!.visibility = View.VISIBLE
                                            mNumErrorHint!!.text = mVRTHint!!.text.toString() + getString(R.string.insufficient)
                                        } else if (type == 2 && num > this@TrandPurhAndSellActivity.tokenBalance) {
                                            mSubmitPurh!!.setBackgroundResource(R.drawable.bg_toge_child_item_tv_blue_nocheck)
                                            mSubmitPurh!!.isEnabled = false
                                            mNumErrorHint!!.visibility = View.VISIBLE
                                            mNumErrorHint!!.text = mHintPrice!!.text.toString() + getString(R.string.insufficient)
                                        } else if (num < minLimit) {
                                            mSubmitPurh!!.setBackgroundResource(R.drawable.bg_toge_child_item_tv_blue_nocheck)
                                            mSubmitPurh!!.isEnabled = false
                                            mNumErrorHint!!.visibility = View.VISIBLE
                                            mNumErrorHint!!.text = "最小挂单数量不可小于$minLimit"
                                        } else {
                                            mNumErrorHint!!.visibility = View.INVISIBLE
                                            mSubmitPurh!!.setBackgroundResource(R.drawable.bg_login_submit)
                                            mSubmitPurh!!.isEnabled = true
                                        }
                                    }
                                } else {
                                    mNumErrorHint!!.visibility = View.INVISIBLE
                                    mAllPricePurh!!.text = "0.00000000 " + this@TrandPurhAndSellActivity.data!!.tokenName
                                    mSubmitPurh!!.isEnabled = false
                                    mSubmitPurh!!.setBackgroundResource(R.drawable.bg_toge_child_item_tv_blue_nocheck)
                                }

                            }
                        })
                    } else {

                    }
                }, { throwable ->

                })

    }

    override fun initView() {
        ImmersionBar.with(this).statusBarView(R.id.status_bar).statusBarDarkFont(true).init()
        mTitleTrand = findViewById(R.id.trand_title)
        mBackTrand = findViewById(R.id.trand_back)
        mHistroyTrand = findViewById(R.id.trand_histroy)
        mToolbarAbout = findViewById(R.id.about_toolbar)
        mHintPrice = findViewById(R.id.price_hint)
        mPrice = findViewById(R.id.price)
        mPriceVrt = findViewById(R.id.vrt_price)
        mTitlePrice = findViewById(R.id.price_title)
        mPriceCurrent = findViewById(R.id.current_price)
        mNumPrice = findViewById(R.id.price_num)
        mEditPurh = findViewById(R.id.purh_edit)
        mVRTHint = findViewById(R.id.vrt_hint)
        mNumErrorHint = findViewById(R.id.num_error_hint)
        mPriceHintAll = findViewById(R.id.all_price_hint)
        mAllPricePurh = findViewById(R.id.purh_all_price)
        mSubmitPurh = findViewById(R.id.purh_submit)
        mBackTrand!!.setOnClickListener(this)
        mHistroyTrand!!.setOnClickListener(this)
        mSubmitPurh!!.setOnClickListener(this)
        dialogHelper = DialogHelper.instance
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
                val currentNum = mEditPurh.text.toString()
                val currentAllPrice = mAllPricePurh!!.text.toString()
                if (currentNum == "" || java.lang.Double.valueOf(currentNum) <= 0) {
                    dialogHelper!!.create(this, R.drawable.miss_icon, if (type == 2) "购买数量不正确" else "出售数量不正确").show()
                    dialogHelper!!.dismissDelayed(null, 2000)
                    return
                }
//                if (type == 2 && java.lang.Double.parseDouble(currentNum) > balance) {
//                    dialogHelper!!.create(this, R.drawable.miss_icon, "最多可购买" + TextUtils.doubleToEight(balance)).show()
//                    dialogHelper!!.dismissDelayed(null, 2000)
//                    return
//                }
//                if (type == 1 && java.lang.Double.parseDouble(currentNum) > tokenBalance) {
//                    dialogHelper!!.create(this, R.drawable.miss_icon, "最多可出售" + TextUtils.doubleToEight(tokenBalance)).show()
//                    dialogHelper!!.dismissDelayed(null, 2000)
//                    return
//                }
                val payNum = currentAllPrice.split(" ".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()[0]
                val buyPrice = if (this.type == 2) currentNum else payNum
                mPopView = createPopWindow(this
                        , R.layout.layout_paycode
                        , if (this.type == 2) "确认购买" else "确认发布"
                        , "总计需支付"
                        , "${(if (this.type == 2) payNum else currentNum)} ${if (this.type == 2) data!!.tokenName else unitPrice}"
                        , if (this.type == 2) "购买数量" else "总价"
                        , "$buyPrice ${if (this.type == 2) unitPrice else data!!.tokenName}"
                        , if (this.type == 2) "购买单价" else "出售单价"
                        , "${if (price_edit.text.toString() == "") TextUtils.doubleToEight(currentPricePurh) else price_edit.text} ${data!!.tokenName}"
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
                    mPurhDialog = dialogHelper!!.create(this@TrandPurhAndSellActivity, R.drawable.pending_icon, "正在发布")
                    mPurhDialog!!.show()
                    mPopView!!.dismiss()
                    RetrofitUtils.client(MyApplication.getBaseUrl(), ApiStore::class.java).getUserSalt(MyApplication.getTOKEN(), email)
                            .compose(RxHelper.rxSchedulerHelper())
                            .flatMap {
                                Observable.just(it.data)
                            }.subscribe({ salt ->
                                val obj = JSONObject()
                                try {
                                    obj.put("id", 0)
                                    obj.put("pairId", data!!.pairId)
                                    obj.put("password", EncryptUtils.encryptMD5ToString(salt + EncryptUtils.encryptMD5ToString(num)))
                                    obj.put("price", if (price_edit.text.toString().isEmpty()) currentPricePurh else price_edit.text.toString())
                                    obj.put("transactionType", this.type)
                                    obj.put("value", currentNum)
                                } catch (e: JSONException) {
                                    e.printStackTrace()
                                }
                                val body = RequestBody.create(MediaType.parse("text/html"), obj.toString())
                                releaseSell(body)
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
    private fun releasePurh(body: RequestBody) {
        RetrofitUtils.client(MyApplication.getBaseUrl(), ApiStore::class.java).releaseOrder(token, body).compose(RxHelper.rxSchedulerHelper()).subscribe({ updateBean ->
            if (updateBean.code == 200) {
                dialogHelper!!.resetDialogResource(this@TrandPurhAndSellActivity, R.drawable.success_icon, "发布成功")
                EventBus.getDefault().post(RecordingEvent())
                dialogHelper!!.dismissDelayed(object : DialogHelper.IDialogDialog {
                    override fun callback() {
                        KeyboardUtils.hideSoftInput(this@TrandPurhAndSellActivity)
                        finish()
                    }

                })
            } else if (updateBean.code == 400) {
                dialogHelper!!.resetDialogResource(this@TrandPurhAndSellActivity, R.drawable.miss_icon, updateBean.message)
                dialogHelper!!.dismissDelayed(object : DialogHelper.IDialogDialog {
                    override fun callback() {
                        KeyboardUtils.hideSoftInput(this@TrandPurhAndSellActivity)
                    }

                })
            } else {
                dialogHelper!!.resetDialogResource(this@TrandPurhAndSellActivity, R.drawable.miss_icon, updateBean.message)
                dialogHelper!!.dismissDelayed(object : DialogHelper.IDialogDialog {
                    override fun callback() {
                        KeyboardUtils.hideSoftInput(this@TrandPurhAndSellActivity)
                    }
                })
            }
        }, { throwable ->
            if (throwable is SocketTimeoutException) {
                dialogHelper!!.resetDialogResource(this@TrandPurhAndSellActivity, R.drawable.miss_icon, "连接超时")
            } else {
                dialogHelper!!.resetDialogResource(this@TrandPurhAndSellActivity, R.drawable.miss_icon, throwable.message!!)
            }
            dialogHelper!!.dismissDelayed(object : DialogHelper.IDialogDialog {
                override fun callback() {
                    KeyboardUtils.hideSoftInput(this@TrandPurhAndSellActivity)
                }
            })
        })
    }

    /**
     * 发布出售订单
     *
     * @param body
     */
    @SuppressLint("CheckResult")
    private fun releaseSell(body: RequestBody) {
        RetrofitUtils.client(MyApplication.getBaseUrl(), ApiStore::class.java).releaseOrder(token, body).compose(RxHelper.rxSchedulerHelper()).subscribe({ updateBean ->
            if (updateBean.code == 200) {
                dialogHelper!!.resetDialogResource(this@TrandPurhAndSellActivity, R.drawable.success_icon, "发布成功")
                EventBus.getDefault().post(RecordingEvent())
                dialogHelper!!.dismissDelayed(object : DialogHelper.IDialogDialog {
                    override fun callback() {
                        KeyboardUtils.hideSoftInput(this@TrandPurhAndSellActivity)
                        finish()
                    }
                })
            } else if (updateBean.code == 400) {
                dialogHelper!!.resetDialogResource(this@TrandPurhAndSellActivity, R.drawable.miss_icon, updateBean.message)
                dialogHelper!!.dismissDelayed(object : DialogHelper.IDialogDialog {
                    override fun callback() {
                        KeyboardUtils.hideSoftInput(this@TrandPurhAndSellActivity)
                    }
                })
            } else {
                dialogHelper!!.resetDialogResource(this@TrandPurhAndSellActivity, R.drawable.miss_icon, updateBean.message)
                dialogHelper!!.dismissDelayed(object : DialogHelper.IDialogDialog {
                    override fun callback() {
                        KeyboardUtils.hideSoftInput(this@TrandPurhAndSellActivity)
                    }
                })
            }
        }, { throwable ->
            if (throwable is SocketTimeoutException) {
                dialogHelper!!.resetDialogResource(this@TrandPurhAndSellActivity, R.drawable.miss_icon, "连接超时")
            } else {
                dialogHelper!!.resetDialogResource(this@TrandPurhAndSellActivity, R.drawable.miss_icon, throwable.message!!)
            }
            dialogHelper!!.dismissDelayed(object : DialogHelper.IDialogDialog {
                override fun callback() {
                    KeyboardUtils.hideSoftInput(this@TrandPurhAndSellActivity)
                }
            })
            //能获取到报错信息
            //            if(throwable instanceof HttpException){
            //                HttpException httpException= (HttpException) throwable;
            //                try {
            //                    String errorBody= httpException.response().errorBody().string();
            //                    //TODO: parse To JSON Obj
            //                } catch (IOException e) {
            //                    e.printStackTrace();
            //                }
            //            }
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
