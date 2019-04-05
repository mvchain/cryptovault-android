package com.mvc.cryptovault_android.listener

import com.mvc.cryptovault_android.bean.RecorBean

interface IRecordingClick {
    fun startPurhActivity(transionType:Int,id:Int,recorBean: RecorBean.DataBean)
}