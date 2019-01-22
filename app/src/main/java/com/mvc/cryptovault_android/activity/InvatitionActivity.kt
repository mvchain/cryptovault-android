package com.mvc.cryptovault_android.activity

import android.Manifest
import android.animation.ObjectAnimator
import android.annotation.SuppressLint
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.provider.MediaStore
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.*
import android.widget.LinearLayout
import android.widget.PopupWindow
import android.widget.TextView
import com.blankj.utilcode.util.ToastUtils
import com.bumptech.glide.Glide
import com.gyf.barlibrary.ImmersionBar
import com.mvc.cryptovault_android.MyApplication
import com.mvc.cryptovault_android.R
import com.mvc.cryptovault_android.adapter.rvAdapter.InvatitionAdapter
import com.mvc.cryptovault_android.api.ApiStore
import com.mvc.cryptovault_android.base.BaseActivity
import com.mvc.cryptovault_android.bean.InvatationBean
import com.mvc.cryptovault_android.utils.RetrofitUtils
import com.mvc.cryptovault_android.utils.RxHelper
import com.mvc.cryptovault_android.view.DialogHelper
import com.mvc.cryptovault_android.view.RuleRecyclerLines
import com.per.rslibrary.IPermissionRequest
import com.per.rslibrary.RsPermission
import com.uuzuche.lib_zxing.activity.CodeUtils
import kotlinx.android.synthetic.main.activity_invatition.*
import java.util.ArrayList

class InvatitionActivity : BaseActivity(), View.OnTouchListener {
    private var y = 0f

    override fun onTouch(v: View?, event: MotionEvent?): Boolean {
        when(event?.action){
            MotionEvent.ACTION_DOWN->{
                y = event.y
            }
            MotionEvent.ACTION_UP->{
                if(event.y > y){
                    var height = info_layout.height
                    infoLayoutStartAnimation(-height + 0f, 0f)
                }
            }
        }
        return true
    }

    private var dialogHelper: DialogHelper? = null
    private lateinit var invaList: ArrayList<InvatationBean.DataBean>
    private lateinit var invaAdapter: InvatitionAdapter
    private lateinit var mPop: PopupWindow
    private lateinit var mPopLayout: LinearLayout

    override fun getLayoutId(): Int {
        return R.layout.activity_invatition
    }

    @SuppressLint("CheckResult")
    override fun initData() {
        RetrofitUtils.client(ApiStore::class.java).getInvitation(MyApplication.getTOKEN())
                .compose(RxHelper.rxSchedulerHelper())
                .subscribe({ http ->
                    if (http.code === 200) {
                        me_invatition.text = http.data
                    } else {
                        ToastUtils.showShort("邀请码获取失败")
                    }
                }, {
                    ToastUtils.showShort("邀请码获取失败")
                })
        invaList.clear()
        loadInvatationList(0)
    }

    @SuppressLint("CheckResult")
    private fun loadInvatationList(userId: Int) {
        RetrofitUtils.client(ApiStore::class.java).getRecommendInvatation(MyApplication.getTOKEN(), userId, 20)
                .compose(RxHelper.rxSchedulerHelper())
                .subscribe({ invation ->
                    if (invation.code === 200) {
                        invaList.addAll(invation.data)
                        invaAdapter.notifyDataSetChanged()
                        mPopLayout.findViewById<TextView>(R.id.invatation_null).visibility = View.GONE
                        mPopLayout.findViewById<RecyclerView>(R.id.invatition_list).visibility = View.VISIBLE
                    }
                }, {
                    mPopLayout.findViewById<TextView>(R.id.invatation_null).visibility = View.VISIBLE
                    mPopLayout.findViewById<RecyclerView>(R.id.invatition_list).visibility = View.GONE
                })
    }

    fun onClick(v: View) {
        when (v.id) {
            R.id.back -> {
                finish()
            }
            R.id.share -> {
                /** * 分享图片 */
                RsPermission.getInstance().setiPermissionRequest(object : IPermissionRequest {
                    override fun toSetting() {
                        RsPermission.getInstance().toSettingPer()
                    }

                    override fun cancle(i: Int) {
                        dialogHelper!!.create(this@InvatitionActivity, R.drawable.miss_icon, "权限不足").show()
                        dialogHelper!!.dismissDelayed(null, 1000)
                    }

                    override fun success(i: Int) {
                        info_layout.isDrawingCacheEnabled = true
                        copy_me_invatition.visibility = View.GONE
                        copy_download_url.visibility = View.GONE
                        val drawingCache = Bitmap.createBitmap(info_layout.drawingCache)
                        copy_me_invatition.visibility = View.VISIBLE
                        copy_download_url.visibility = View.VISIBLE
                        val parintent = Intent()
                        val parseUri = Uri.parse(MediaStore.Images.Media.insertImage(contentResolver, drawingCache, null, null))
                        parintent.action = Intent.ACTION_SEND//设置分享行为
                        parintent.type = "image/*"  //设置分享内容的类型
                        parintent.putExtra(Intent.EXTRA_STREAM, parseUri)
                        //创建分享的Dialog
                        val share_intent = Intent.createChooser(parintent, "分享到:")
                        startActivity(share_intent)
                        drawingCache.recycle()
                        info_layout.isDrawingCacheEnabled = false
                    }
                }).requestPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
            }
            R.id.copy_me_invatition -> {
                var clipboardManager = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
                var clipMsg = ClipData.newPlainText("invatation", me_invatition.text.toString())
                clipboardManager.primaryClip = clipMsg
                ToastUtils.showLong("邀请码复制成功")
            }
            R.id.copy_download_url -> {
                var clipboardManager = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
                var clipMsg = ClipData.newPlainText("download_url", download_url.text.toString())
                clipboardManager.primaryClip = clipMsg
                ToastUtils.showLong("下载地址复制成功")
            }
            R.id.me_invation_list -> {
                var height = info_layout.height
                infoLayoutStartAnimation(0f, -height + 0f)
                if (mPop != null && !mPop.isShowing) {
                    mPop.showAsDropDown(info_layout, 0, 0, Gravity.BOTTOM)
                }
            }
        }
    }

    fun infoLayoutStartAnimation(startY: Float, endY: Float) {
        var transAnimation = ObjectAnimator.ofFloat(info_layout, "Y", startY, endY)
        transAnimation.duration = 1000
        transAnimation.start()
    }

    override fun onDestroy() {
        if (mPop.isShowing) {
            mPop.dismiss()
        }
        super.onDestroy()
    }

    override fun initView() {
        ImmersionBar.with(this).statusBarView(R.id.status_bar).statusBarDarkFont(true).init()
        dialogHelper = DialogHelper.getInstance()
        invaList = ArrayList()
        invaAdapter = InvatitionAdapter(R.layout.item_invatation, invaList)
        mPop = createPopWindow()
        val mBitmap = CodeUtils.createImage(download_url.text.toString(), 400, 400, BitmapFactory.decodeResource(resources, R.mipmap.vp_logo))
        Glide.with(this).load(mBitmap).into(qcode)
    }

    private fun createPopWindow(): PopupWindow {
        var mPop = PopupWindow(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
        mPopLayout = LayoutInflater.from(this).inflate(R.layout.layout_invatition_pop, null) as LinearLayout
        mPop.contentView = mPopLayout
        var mInvatationRecyclerVie = mPopLayout.findViewById<RecyclerView>(R.id.invatition_list)
        var rule = RuleRecyclerLines(this, RuleRecyclerLines.HORIZONTAL_LIST, 1)
        rule.setColor(R.color.white)
        var invatationNull = mPopLayout.findViewById<TextView>(R.id.invatation_null)
        invatationNull.setOnTouchListener(this)
        mInvatationRecyclerVie.addItemDecoration(rule)
        mInvatationRecyclerVie.adapter = invaAdapter
        mInvatationRecyclerVie.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView?, newState: Int) {
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    var layoutManager = recyclerView?.layoutManager as LinearLayoutManager
                    var lastVisibleItemPosition = layoutManager?.findLastVisibleItemPosition()
                    var fristVisibleItemPosition = layoutManager?.findFirstCompletelyVisibleItemPosition()
                    if (lastVisibleItemPosition + 1 == invaAdapter.itemCount && invaAdapter.itemCount >= 20) {
                        loadInvatationList(invaList[invaList.size - 1].userId)
                    } else if (fristVisibleItemPosition == 0) {
                        mPop.dismiss()
                        var height = info_layout.height
                        infoLayoutStartAnimation(-height + 0f, 0f)
                    }
                }
            }

        })
        mPop.animationStyle = R.style.MyPopupWindow_anim_style
        return mPop
    }

    override fun onBackPressed() {
        if (mPop.isShowing) {
            mPop.dismiss()
            var height = info_layout.height
            infoLayoutStartAnimation(-height + 0f, 0f)
        } else {
            super.onBackPressed()
        }
    }
}