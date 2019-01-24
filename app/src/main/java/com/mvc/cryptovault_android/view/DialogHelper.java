package com.mvc.cryptovault_android.view;

import android.app.Dialog;
import android.content.Context;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.mvc.cryptovault_android.R;
import com.mvc.cryptovault_android.listener.IDialogViewClickListener;

public class DialogHelper {
    private Dialog mDialog;
    private static DialogHelper mDialogHelper;

    public static DialogHelper getInstance() {
        if (mDialogHelper == null) {
            synchronized (DialogHelper.class) {
                mDialogHelper = new DialogHelper();
            }
        }
        return mDialogHelper;
    }

    public Dialog create(Context context, int resId) {
        mDialog = new Dialog(context, R.style.tras_dialog);
        mDialog.setCanceledOnTouchOutside(false);
        mDialog.setCancelable(false);
        View dialogView = LayoutInflater.from(context).inflate(resId, null);
        mDialog.setContentView(dialogView);
        return mDialog;
    }

    public Dialog create(Context context, int resId, String msg) {
        mDialog = new Dialog(context, R.style.tras_dialog);
        mDialog.setCanceledOnTouchOutside(false);
        mDialog.setCancelable(false);
        View dialogView = LayoutInflater.from(context).inflate(R.layout.layout_dialog, null);
        ImageView icon = dialogView.findViewById(R.id.dialog_icon);
        TextView title = dialogView.findViewById(R.id.dialog_title);
        Glide.with(context).load(resId).into(icon);
        title.setText(msg);
        mDialog.setContentView(dialogView);
        return mDialog;
    }

    public Dialog create(Context context, String msg, IDialogViewClickListener clickListener) {
        mDialog = new Dialog(context, R.style.tras_dialog);
        mDialog.setCanceledOnTouchOutside(false);
        mDialog.setCancelable(false);
        View dialogView = LayoutInflater.from(context).inflate(R.layout.layout_sub_dialog, null);
        TextView cancle = dialogView.findViewById(R.id.hint_cancle);
        TextView enter = dialogView.findViewById(R.id.hint_enter);
        TextView title = dialogView.findViewById(R.id.hint_title);
        title.setText(msg);
        mDialog.setContentView(dialogView);
        cancle.setOnClickListener(v -> clickListener.click(cancle.getId()));
        enter.setOnClickListener(v -> clickListener.click(enter.getId()));
        return mDialog;
    }

    public void resetDialogResource(Context context, int resId, String msg) {
        View dialogView = LayoutInflater.from(context).inflate(R.layout.layout_dialog, null);
        ImageView icon = dialogView.findViewById(R.id.dialog_icon);
        TextView title = dialogView.findViewById(R.id.dialog_title);
        Glide.with(context).load(resId).into(icon);
        title.setText(msg);
        mDialog.setContentView(dialogView);
    }

    public void dismissDelayed(IDialogDialog dialogDialog) {
        new Handler().postDelayed(() -> {
            dismiss();
            if (dialogDialog != null) {
                dialogDialog.callback();
            }
        }, 2000);
    }

    public void dismissDelayed(IDialogDialog dialogDialog, long delayMillis) {
        new Handler().postDelayed(() -> {
            dismiss();
            if (dialogDialog != null) {
                dialogDialog.callback();
            }
        }, delayMillis);
    }

    public void dismiss() {
        if (mDialog != null) {
            mDialog.dismiss();
        }
    }

    public interface IDialogDialog {
        void callback();
    }
}
