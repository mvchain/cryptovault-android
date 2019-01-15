package com.mvc.cryptovault_android.model;

import android.support.annotation.Nullable;

import com.mvc.cryptovault_android.api.ApiStore;
import com.mvc.cryptovault_android.base.BaseModel;
import com.mvc.cryptovault_android.bean.HttpTokenBean;
import com.mvc.cryptovault_android.bean.LoginBean;
import com.mvc.cryptovault_android.bean.UpdateBean;
import com.mvc.cryptovault_android.contract.LoginContract;
import com.mvc.cryptovault_android.utils.RetrofitUtils;
import com.mvc.cryptovault_android.utils.RxHelper;

import org.json.JSONException;
import org.json.JSONObject;

import io.reactivex.Observable;
import io.reactivex.functions.Function;
import okhttp3.MediaType;
import okhttp3.RequestBody;

public class LoginModel extends BaseModel implements LoginContract.ILoginModel {

    @Nullable
    public static LoginModel getInstance() {
        return new LoginModel();
    }

    @Override
    public Observable<LoginBean> getLoginStatus(String phone, String pwd,String code) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("username", phone);
            jsonObject.put("password", pwd);
            jsonObject.put("validCode", code);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        String loginInfo = jsonObject.toString();
        RequestBody requestBody = RequestBody.create(MediaType.parse("text/html"), loginInfo);
        return RetrofitUtils.client(ApiStore.class).login(requestBody)
                .compose(RxHelper.rxSchedulerHelper())
                .map(loginBean -> loginBean);
    }

    @Override
    public Observable<HttpTokenBean> sendCode(String cellphone) {
        return RetrofitUtils.client(ApiStore.class).sendValiCode(cellphone)
                .compose(RxHelper.rxSchedulerHelper())
                .map(httpTokenBean -> httpTokenBean);
    }
}
