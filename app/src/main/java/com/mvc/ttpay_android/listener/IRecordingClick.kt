package com.mvc.ttpay_android.listener

import com.mvc.ttpay_android.bean.RecorBean

interface IRecordingClick {
    fun startPurhActivity(transionType:Int,id:Int,recorBean: RecorBean.DataBean)
}