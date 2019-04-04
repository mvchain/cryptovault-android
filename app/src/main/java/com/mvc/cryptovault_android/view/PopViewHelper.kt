package com.mvc.cryptovault_android.view

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.ColorDrawable
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.PopupWindow
import android.widget.TextView

import com.mvc.cryptovault_android.R
import com.mvc.cryptovault_android.adapter.rvAdapter.MenuAdapter
import com.mvc.cryptovault_android.adapter.rvAdapter.RateAdapter
import com.mvc.cryptovault_android.adapter.rvAdapter.RatioAdapter
import com.mvc.cryptovault_android.bean.TrandChildBean
import com.mvc.cryptovault_android.listener.IPayWindowListener
import com.mvc.cryptovault_android.listener.IPopViewListener
import com.mvc.cryptovault_android.listener.ISelectWindowListener
import com.mvc.cryptovault_android.listener.PswMaxListener

import kotlin.collections.ArrayList

class PopViewHelper {
    private lateinit var mPopView: PopupWindow


    fun create(context: Context, layoutId: Int, content: ArrayList<String>, iPopViewListener: IPopViewListener?): PopupWindow {
        val linear = LayoutInflater.from(context.applicationContext).inflate(layoutId, null) as LinearLayout
        val mRateView = linear.findViewById<RecyclerView>(R.id.rate_rv)
        val adapter = RateAdapter(R.layout.item_rate_rv, content)
        mRateView.adapter = adapter
        mRateView.addItemDecoration(RuleRecyclerLines(context.applicationContext, RuleRecyclerLines.HORIZONTAL_LIST, 1))
        adapter.setOnItemChildClickListener { adapter1, view, position ->
            when (view.id) {
                R.id.rate_item_content -> if (iPopViewListener != null) {
                    mPopView.dismiss()
                    iPopViewListener.changeRate(position)
                }
            }
        }
        mPopView = PopupWindow(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        mPopView.setOnDismissListener {
            iPopViewListener?.dismiss()
        }
        mPopView.isOutsideTouchable = true
        mPopView.setBackgroundDrawable(ColorDrawable())
        mPopView.contentView = linear
        return mPopView
    }

    @SuppressLint("WrongConstant")
    fun create(context: Context, layoutId: Int, count: String, iPayWindowListener: IPayWindowListener?, maxListener: PswMaxListener): PopupWindow {
        val linear = LayoutInflater.from(context.applicationContext).inflate(layoutId, null) as LinearLayout
        linear.findViewById<View>(R.id.pay_address_layout).visibility = View.GONE
        linear.findViewById<View>(R.id.pay_fee_layout).visibility = View.GONE
        linear.findViewById<View>(R.id.pay_close).setOnClickListener { v -> iPayWindowListener!!.onclick(v) }
        linear.findViewById<View>(R.id.line).visibility = View.GONE
        val pswText = linear.findViewById<PswText>(R.id.pay_text)
        pswText.setOnClickListener { v -> iPayWindowListener!!.onclick(v) }
        pswText.setMaxListener(maxListener)
        linear.findViewById<View>(R.id.pay_forget).setOnClickListener { v -> iPayWindowListener!!.onclick(v) }
        (linear.findViewById<View>(R.id.pay_title) as TextView).text = "确认存入"
        (linear.findViewById<View>(R.id.pay_child_title) as TextView).text = "总共需支付"
        (linear.findViewById<View>(R.id.pay_price) as TextView).text = count
        mPopView = PopupWindow(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
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

    @SuppressLint("WrongConstant")
    fun create(context: Context, layoutId: Int, title: String, childTitle: String, price: String, address: String, money: String, isOut: Boolean, iPayWindowListener: IPayWindowListener?, maxListener: PswMaxListener): PopupWindow {
        val linear = LayoutInflater.from(context.applicationContext).inflate(layoutId, null) as LinearLayout
        linear.findViewById<View>(R.id.line).visibility = if (isOut) View.VISIBLE else View.GONE
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
        mAddressPay.text = address
        val mAddressLayoutPay = linear.findViewById<LinearLayout>(R.id.pay_address_layout)
        mAddressLayoutPay.visibility = if (isOut) View.VISIBLE else View.GONE
        val mFeePay = linear.findViewById<TextView>(R.id.pay_fee)
        mFeePay.text = money
        val mFeeLayoutPay = linear.findViewById<LinearLayout>(R.id.pay_fee_layout)
        mFeeLayoutPay.visibility = if (isOut) View.VISIBLE else View.GONE
        val mTextPay = linear.findViewById<PswText>(R.id.pay_text)
        mTextPay.setOnClickListener { v -> iPayWindowListener!!.onclick(v) }
        mTextPay.setMaxListener(maxListener)
        val mForgetPay = linear.findViewById<TextView>(R.id.pay_forget)
        mForgetPay.setOnClickListener { v -> iPayWindowListener!!.onclick(v) }
        mPopView = PopupWindow(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
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

    @SuppressLint("WrongConstant")
    fun create(context: Context, layoutId: Int, title: String, childTitle: String, price: String, money: String, iPayWindowListener: IPayWindowListener?, maxListener: PswMaxListener): PopupWindow {
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
        val mAddressLayoutPay = linear.findViewById<LinearLayout>(R.id.pay_address_layout)
        mAddressLayoutPay.visibility = View.GONE
        val mFeePay = linear.findViewById<TextView>(R.id.pay_fee)
        mFeePay.text = money
        val mFeeLayoutPay = linear.findViewById<LinearLayout>(R.id.pay_fee_layout)
        (mFeeLayoutPay.findViewById<View>(R.id.price) as TextView).text = "预约数量"
        mFeeLayoutPay.visibility = View.VISIBLE
        val mTextPay = linear.findViewById<PswText>(R.id.pay_text)
        mTextPay.setOnClickListener { v -> iPayWindowListener!!.onclick(v) }
        mTextPay.setMaxListener(maxListener)
        val mForgetPay = linear.findViewById<TextView>(R.id.pay_forget)
        mForgetPay.setOnClickListener { v -> iPayWindowListener!!.onclick(v) }
        mPopView = PopupWindow(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
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

    fun create(context: Context,  title: ArrayList<String>,layoutId: Int, iSelectWindowListener: ISelectWindowListener): PopupWindow {
        val ratioRv = LayoutInflater.from(context.applicationContext).inflate(layoutId, null) as RecyclerView
        var ratioAdapter = MenuAdapter(R.layout.item_ratio,title)
        ratioRv.adapter = ratioAdapter
        ratioAdapter.setOnItemChildClickListener { adapter, view, position ->
            iSelectWindowListener.onclick(position)
        }
        mPopView = PopupWindow(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        mPopView.setOnDismissListener {
            iSelectWindowListener.dismiss()
        }
        mPopView.contentView = ratioRv
        mPopView.isFocusable = true
        mPopView.setBackgroundDrawable(BitmapDrawable())
        mPopView.isOutsideTouchable = false
        return mPopView
    }

    fun create(context: Context, layoutId: Int, title: ArrayList<TrandChildBean.DataBean>, iSelectWindowListener: ISelectWindowListener): PopupWindow {
        val ratioRv = LayoutInflater.from(context.applicationContext).inflate(layoutId, null) as RecyclerView
        var ratioAdapter = RatioAdapter(R.layout.item_ratio,title)
        ratioRv.adapter = ratioAdapter
        ratioAdapter.setOnItemChildClickListener { adapter, view, position ->
            iSelectWindowListener.onclick(position)
        }
        mPopView = PopupWindow(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        mPopView.setOnDismissListener {
            iSelectWindowListener.dismiss()
        }
        mPopView.contentView = ratioRv
        mPopView.isFocusable = true
        mPopView.setBackgroundDrawable(BitmapDrawable())
        mPopView.isOutsideTouchable = false
        return mPopView
    }

    fun dismiss() {
        if (mPopView != null && mPopView.isShowing) {
            mPopView.dismiss()
        }
    }

    companion object {
        val instance: PopViewHelper by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED) {
            PopViewHelper()
        }
    }
}
