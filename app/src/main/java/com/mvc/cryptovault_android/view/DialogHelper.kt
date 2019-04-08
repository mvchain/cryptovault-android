package com.mvc.cryptovault_android.view

import android.app.Dialog
import android.content.Context
import android.os.Handler
import android.view.LayoutInflater
import android.widget.ImageView
import android.widget.TextView

import com.bumptech.glide.Glide
import com.mvc.cryptovault_android.R
import com.mvc.cryptovault_android.listener.IDialogViewClickListener

class DialogHelper {
    private lateinit var mDialog: Dialog

    fun create(context: Context, resId: Int): Dialog {
        mDialog = Dialog(context, R.style.tras_dialog)
        mDialog.setCanceledOnTouchOutside(false)
        mDialog.setCancelable(false)
        val dialogView = LayoutInflater.from(context).inflate(resId, null)
        mDialog.setContentView(dialogView)
        return mDialog
    }

    fun create(context: Context, resId: Int, msg: String): Dialog {
        mDialog = Dialog(context, R.style.tras_dialog)
        mDialog.setCanceledOnTouchOutside(false)
        mDialog.setCancelable(false)
        val dialogView = LayoutInflater.from(context).inflate(R.layout.layout_dialog, null)
        val icon = dialogView.findViewById<ImageView>(R.id.dialog_icon)
        val title = dialogView.findViewById<TextView>(R.id.dialog_title)
        Glide.with(context).load(resId).into(icon)
        title.text = msg
        mDialog.setContentView(dialogView)
        return mDialog
    }

    fun create(context: Context, clickListener: IDialogViewClickListener,msg:String): Dialog {
        mDialog = Dialog(context, R.style.tras_dialog)
        mDialog.setCanceledOnTouchOutside(false)
        mDialog.setCancelable(false)
        val dialogView = LayoutInflater.from(context).inflate(R.layout.layout_skip_dialog, null)
        dialogView.findViewById<TextView>(R.id.dialog_title).text = msg
        val submit = dialogView.findViewById<TextView>(R.id.dialog_submit)
        submit.setOnClickListener { clickListener.click(submit.id) }
        mDialog.setContentView(dialogView)
        return mDialog
    }

    fun create(context: Context, msg: String, clickListener: IDialogViewClickListener): Dialog {
        mDialog = Dialog(context, R.style.tras_dialog)
        mDialog.setCanceledOnTouchOutside(false)
        mDialog.setCancelable(false)
        val dialogView = LayoutInflater.from(context).inflate(R.layout.layout_sub_dialog, null)
        val cancle = dialogView.findViewById<TextView>(R.id.hint_cancle)
        val enter = dialogView.findViewById<TextView>(R.id.hint_enter)
        val title = dialogView.findViewById<TextView>(R.id.hint_title)
        title.text = msg
        mDialog.setContentView(dialogView)
        cancle.setOnClickListener { v -> clickListener.click(cancle.id) }
        enter.setOnClickListener { v -> clickListener.click(enter.id) }
        return mDialog
    }

    fun resetDialogResource(context: Context, resId: Int, msg: String) {
        val dialogView = LayoutInflater.from(context).inflate(R.layout.layout_dialog, null)
        val icon = dialogView.findViewById<ImageView>(R.id.dialog_icon)
        val title = dialogView.findViewById<TextView>(R.id.dialog_title)
        Glide.with(context).load(resId).into(icon)
        title.text = msg
        mDialog.setContentView(dialogView)
    }

    fun dismissDelayed(dialogDialog: IDialogDialog?) {
        Handler().postDelayed({
            dismiss()
            dialogDialog?.callback()
        }, 2000)
    }

    fun dismissDelayed(dialogDialog: IDialogDialog?, delayMillis: Long) {
        Handler().postDelayed({
            dismiss()
            dialogDialog?.callback()
        }, delayMillis)
    }

    fun dismiss() {
        if (mDialog != null) {
            mDialog.dismiss()
        }
    }

    interface IDialogDialog {
        fun callback()
    }

    companion object {
        val instance: DialogHelper by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED) {
            DialogHelper()
        }
    }
}
