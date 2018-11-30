package com.mvc.cryptovault_android.view;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.mvc.cryptovault_android.R;

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

    public Dialog create(Context context, int resId, String msg) {
        mDialog = new Dialog(context);
        mDialog.setCanceledOnTouchOutside(false);
        mDialog.setCancelable(false);
        View dialogView = LayoutInflater.from(context).inflate(R.layout.layout_dialog, (ViewGroup) mDialog.getWindow().getDecorView().getParent());
        ImageView icon = dialogView.findViewById(R.id.dialog_icon);
        TextView title = dialogView.findViewById(R.id.dialog_title);
        Glide.with(context).load(resId).into(icon);
        title.setText(msg);
        mDialog.setContentView(R.layout.layout_dialog);
        return mDialog;
    }

    public void dismiss() {
        if (mDialog != null) {
            mDialog.dismiss();
        }
    }
}
