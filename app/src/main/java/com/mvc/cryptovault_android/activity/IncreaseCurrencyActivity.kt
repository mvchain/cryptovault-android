package com.mvc.cryptovault_android.activity

import android.annotation.SuppressLint
import android.app.Dialog
import android.os.Build
import android.support.annotation.RequiresApi
import android.support.v4.content.ContextCompat
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import android.widget.TextView

import com.blankj.utilcode.util.KeyboardUtils
import com.blankj.utilcode.util.LogUtils
import com.blankj.utilcode.util.SPUtils
import com.chad.library.adapter.base.BaseQuickAdapter
import com.gyf.barlibrary.ImmersionBar
import com.mvc.cryptovault_android.MyApplication
import com.mvc.cryptovault_android.R
import com.mvc.cryptovault_android.adapter.rvAdapter.IncreaseAdapter
import com.mvc.cryptovault_android.api.ApiStore
import com.mvc.cryptovault_android.base.BaseMVPActivity
import com.mvc.cryptovault_android.base.BasePresenter
import com.mvc.cryptovault_android.bean.AssetListBean
import com.mvc.cryptovault_android.bean.IncreaseBean
import com.mvc.cryptovault_android.bean.UpdateBean
import com.mvc.cryptovault_android.common.Constant
import com.mvc.cryptovault_android.contract.IncreaseContract
import com.mvc.cryptovault_android.event.WalletAssetsListEvent
import com.mvc.cryptovault_android.listener.EditTextChange
import com.mvc.cryptovault_android.listener.IDialogViewClickListener
import com.mvc.cryptovault_android.presenter.IncreasePresenter
import com.mvc.cryptovault_android.utils.JsonHelper
import com.mvc.cryptovault_android.utils.RetrofitUtils
import com.mvc.cryptovault_android.utils.RxHelper
import com.mvc.cryptovault_android.view.DialogHelper
import com.mvc.cryptovault_android.view.RuleRecyclerLines
import kotlinx.android.synthetic.main.activity_increase.*

import org.greenrobot.eventbus.EventBus
import org.json.JSONException
import org.json.JSONObject

import java.util.ArrayList

import okhttp3.MediaType
import okhttp3.RequestBody

class IncreaseCurrencyActivity : BaseMVPActivity<IncreaseContract.IncreasePresenter>(), IncreaseContract.IIncreaseView, View.OnClickListener, BaseQuickAdapter.OnItemChildClickListener {
   private var isSearch = false
   private lateinit var mBean: ArrayList<IncreaseBean>
   private lateinit var mSearch: ArrayList<IncreaseBean>
   private lateinit var allIncreaseAdapter: IncreaseAdapter
   private lateinit var searchIncreaseAdapter: IncreaseAdapter
   private lateinit var mHintDialog: Dialog
   private lateinit var listBean: AssetListBean
    //    assertVisibleDTO

    override fun initMVPData() {
        allIncreaseAdapter = IncreaseAdapter(R.layout.item_increase_rv, mBean)
        searchIncreaseAdapter = IncreaseAdapter(R.layout.item_increase_rv, mSearch)
        increase_rv.addItemDecoration(RuleRecyclerLines(this, RuleRecyclerLines.HORIZONTAL_LIST, 1))
        increase_rv.adapter = allIncreaseAdapter
        increase_serach_rv.addItemDecoration(RuleRecyclerLines(this, RuleRecyclerLines.HORIZONTAL_LIST, 1))
        increase_serach_rv.adapter = searchIncreaseAdapter
        increase_edit.addTextChangedListener(object : EditTextChange() {
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                val searchTv = s.toString()
                if (searchTv == "") {
                    increase_serach_null.visibility = View.GONE
                    increase_serach_rv.visibility = View.GONE
                    increase_rv.visibility = View.VISIBLE
                } else {
                    mPresenter.getCurrencySerach(searchTv)
                }
            }
        })
        mPresenter.getCurrencyAll()
        searchIncreaseAdapter.onItemChildClickListener = this
        allIncreaseAdapter.onItemChildClickListener = this
    }

    private fun initCache() {
        listBean = JsonHelper.stringToJson(SPUtils.getInstance().getString(Constant.SP.ASSETS_LIST), AssetListBean::class.java) as AssetListBean
    }

    override fun initMVPView() {
        mBean = ArrayList()
        mSearch = ArrayList()
        increase_rv.layoutManager = LinearLayoutManager(this)
        increase_serach_rv.layoutManager = LinearLayoutManager(this)
        ImmersionBar.with(this).statusBarView(status_bar).statusBarDarkFont(true).init()

    }

    override fun getLayoutId(): Int {
        return R.layout.activity_increase
    }

    override fun initData() {

    }

    override fun initView() {}

    override fun startActivity() {

    }

    override fun initPresenter(): BasePresenter<*, *> {
        return IncreasePresenter.newIntance()
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    override fun onClick(v: View) {
        when (v.id) {
            R.id.increase_back -> finish()
            R.id.increase_search -> {
                // TODO 18/12/03
                isSearch = !isSearch
                if (isSearch) {
                    increase_search.setImageDrawable(ContextCompat.getDrawable(baseContext, R.drawable.cancel_icon_black))
                    increase_edit.visibility = View.VISIBLE
                    increase_title.visibility = View.GONE
                    increase_edit.isFocusable = true
                    increase_edit.requestFocus()
                    KeyboardUtils.showSoftInput(this)
                } else {
                    increase_search.setImageDrawable(ContextCompat.getDrawable(baseContext, R.drawable.search_icon_black))
                    increase_edit.visibility = View.GONE
                    increase_title.visibility = View.VISIBLE
                    //                    在关闭搜索框的时候搜索内容也关闭掉
                    increase_serach_null.visibility = View.GONE
                    increase_serach_rv.visibility = View.GONE
                    increase_rv.visibility = View.VISIBLE
                    KeyboardUtils.hideSoftInput(this)
                }
            }
        }
    }


    override fun showCurrency(beanList: List<IncreaseBean>) {
        mBean.clear()
        mBean.addAll(beanList)
        increase_serach_null.visibility = View.GONE
        increase_serach_rv.visibility = View.GONE
        increase_rv.visibility = View.VISIBLE
        allIncreaseAdapter.notifyDataSetChanged()
    }

    override fun showSearchCurrency(beanList: List<IncreaseBean>) {
        mSearch.clear()
        mSearch.addAll(beanList)
        increase_serach_null.visibility = View.GONE
        increase_serach_rv.visibility = View.VISIBLE
        increase_rv.visibility = View.GONE
        searchIncreaseAdapter.notifyDataSetChanged()
    }

    override fun showNull() {
        increase_serach_null.visibility = View.VISIBLE
        increase_serach_rv.visibility = View.GONE
        increase_rv.visibility = View.GONE
    }

    override fun onItemChildClick(adapter: BaseQuickAdapter<*, *>, view: View, position: Int) {
        when (view.id) {
            R.id.item_increase_add -> if (increase_serach_rv.visibility == View.VISIBLE) {
                // 搜索的结果
                if (!mSearch[position].isAdd) {
                    mHintDialog = DialogHelper.instance.create(this, "确定移除" + mSearch[position].title + "?", IDialogViewClickListener { viewId ->
                        when (viewId) {
                            R.id.hint_cancle -> mHintDialog.dismiss()
                            R.id.hint_enter -> {
                                mHintDialog.dismiss()
                                pullStack(position)
                            }
                        }
                    })
                } else {
                    pushStack(position)
                }
            } else {
                //全部列表结果
                if (!mBean[position].isAdd) {
                    mHintDialog = DialogHelper.instance.create(this, "确定移除" + mBean[position].title + "?", IDialogViewClickListener { viewId ->
                        when (viewId) {
                            R.id.hint_cancle -> mHintDialog.dismiss()
                            R.id.hint_enter -> {
                                mHintDialog.dismiss()
                                pullStack(position)
                            }
                        }
                    })
                    mHintDialog.show()
                } else {
                    pushStack(position)
                }
            }
        }
    }

    /**
     * @param position
     */
    @SuppressLint("CheckResult")
    private fun pullStack(position: Int) {
        var currencyId = 0
        if (increase_serach_rv.visibility == View.VISIBLE) {
            currencyId = mSearch[position].currencyId
        } else {
            currencyId = mBean[position].currencyId
        }
        val json = JSONObject()
        try {
            json.put("addTokenIdArr", "")
            json.put("removeTokenIdArr", currencyId)
        } catch (e: JSONException) {
            e.printStackTrace()
        }

        val body = RequestBody.create(MediaType.parse("text/html"), json.toString())
        RetrofitUtils.client(MyApplication.getBaseUrl(), ApiStore::class.java).updateAssetList(token, body).compose<UpdateBean>(RxHelper.rxSchedulerHelper<UpdateBean>()).subscribe({ updateBean ->
            if (updateBean.getCode() == 200 && updateBean.isData()) {
                EventBus.getDefault().post(WalletAssetsListEvent())
            }
        }, { throwable -> LogUtils.d("IncreaseCurrencyActivit", throwable.message) })
        if (increase_serach_rv.visibility == View.VISIBLE) {
            val add = mSearch[position].isAdd
            mSearch[position].isAdd = !add
        } else {
            val add = mBean[position].isAdd
            mBean[position].isAdd = !add
        }
        allIncreaseAdapter.notifyDataSetChanged()
        searchIncreaseAdapter.notifyDataSetChanged()
    }

    @SuppressLint("CheckResult")
    private fun pushStack(position: Int) {
        var currencyId = 0
        if (increase_serach_rv.visibility == View.VISIBLE) {
            currencyId = mSearch[position].currencyId
        } else {
            currencyId = mBean[position].currencyId
        }
        val json = JSONObject()
        try {
            json.put("addTokenIdArr", currencyId)
            json.put("removeTokenIdArr", "")
        } catch (e: JSONException) {
            e.printStackTrace()
        }

        val body = RequestBody.create(MediaType.parse("text/html"), json.toString())
        RetrofitUtils.client(MyApplication.getBaseUrl(), ApiStore::class.java).updateAssetList(token, body).compose<UpdateBean>(RxHelper.rxSchedulerHelper<UpdateBean>()).subscribe({ updateBean ->
            if (updateBean.getCode() == 200 && updateBean.isData()) {
                EventBus.getDefault().post(WalletAssetsListEvent())
            }
        }, { throwable -> LogUtils.e("IncreaseCurrencyActivit", throwable.message) })
        if (increase_serach_rv.visibility == View.VISIBLE) {
            val add = mSearch[position].isAdd
            mSearch[position].isAdd = !add
        } else {
            val add = mBean[position].isAdd
            mBean[position].isAdd = !add
        }
        allIncreaseAdapter.notifyDataSetChanged()
        searchIncreaseAdapter.notifyDataSetChanged()
    }
}
