package com.mvc.cryptovault_android.listener;

/***
 *  验证码倒计时的回调
 */
public interface OnTimeEndCallBack {
    //更新时间
    void updata(int time);
    
    //计时结束
    void exit();
}
