package com.mvc.cryptovault_android.utils;

import android.graphics.drawable.Drawable;
import android.widget.TextView;

public class TextViewDrawUtils {

    public static void setLeftDraw(Drawable draw, TextView view) {
        draw.setBounds(0,0,draw.getMinimumWidth(),draw.getMinimumHeight());
        setBoundDraw(draw, null, null, null, view);
    }

    public static void setTopDraw(Drawable draw, TextView view) {
        draw.setBounds(0,0,draw.getMinimumWidth(),draw.getMinimumHeight());
        setBoundDraw(null, draw, null, null, view);
    }

    public static void setRigthDraw(Drawable draw, TextView view) {
        draw.setBounds(0,0,draw.getMinimumWidth(),draw.getMinimumHeight());
        setBoundDraw(null, null, draw, null, view);
    }

    public static void setBottomDraw(Drawable draw, TextView view) {
        draw.setBounds(0,0,draw.getMinimumWidth(),draw.getMinimumHeight());
        setBoundDraw(null, null, null, draw, view);
    }

    private static void setBoundDraw(Drawable leftDraw, Drawable topDraw, Drawable rightDraw, Drawable bottomDraw, TextView view) {
        view.setCompoundDrawables(leftDraw,topDraw,rightDraw,bottomDraw);
    }
}
