package com.mvc.cryptovault_android.adapter.rvAdapter

import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.mvc.cryptovault_android.R
import com.mvc.cryptovault_android.bean.PublishBean
import java.text.SimpleDateFormat
import java.util.ArrayList

class PublishAllAdapter(resId:Int,date:ArrayList<PublishBean.DataBean>) :BaseQuickAdapter<PublishBean.DataBean,BaseViewHolder>(resId,date) {
    override fun convert(helper: BaseViewHolder, item: PublishBean.DataBean) {
        val options = RequestOptions().fallback(R.drawable.default_project).placeholder(R.drawable.loading_img).error(R.drawable.default_project)
        helper.setText(R.id.publish_child_title,item.projectName)
        helper.setText(R.id.publish_child_recevie_type,"每日释放比例${item.releaseValue}%")
        helper.setText(R.id.publish_child_recevie_bespoke,"发币时间 ${SimpleDateFormat("yyyy-MM-dd HH : mm").format(item.publishTime)}")
        var pubIcon = helper.getView<ImageView>(R.id.publish_child_icon)
        Glide.with(mContext).load(item.projectImage).apply(options).into(pubIcon)
        helper.addOnClickListener(R.id.publish_child_layout)
    }
}